package text;

import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单业务逻辑层
 * 处理支付回调验证、订单创建、历史查询、数据一致性校验
 * Enhanced: cart backup before clear, transaction safety, data recovery
 */
public class OrderService {

    /** Backup of cart items before checkout - for data loss recovery */
    private static List<CartItem> lastCheckoutCartBackup = null;

    /**
     * 支付回调结果
     */
    public static class PaymentResult {
        public boolean success;
        public int orderId;
        public int totalAmount;
        public String errorMessage;

        public static PaymentResult ok(int orderId, int totalAmount) {
            PaymentResult r = new PaymentResult();
            r.success = true;
            r.orderId = orderId;
            r.totalAmount = totalAmount;
            return r;
        }

        public static PaymentResult fail(String msg) {
            PaymentResult r = new PaymentResult();
            r.success = false;
            r.errorMessage = msg;
            return r;
        }
    }

    /**
     * 支付回调处理 + 订单创建（事务保护）
     *
     * 流程:
     *  1. 验证用户身份
     *  2. 校验购物车数据完整性
     *  3. 开启事务
     *  4. 创建订单总记录 (totallist)
     *  5. 归档物品明细 (orderitems)
     *  6. 清空当前用户购物车
     *  7. 提交事务（任何步骤失败则回滚）
     */
    public static PaymentResult processPayment(int userId, String paymentMethod) {
        Connection conn = null;
        int orderId = 0;
        int total = 0;

        try {
            // ===== Step 1: Validate user identity =====
            if (userId <= 0) {
                return PaymentResult.fail("Invalid user identity. Please login again.");
            }

            // ===== Step 2: 校验购物车数据完整性 =====
            String cartError = CartService.validateCart();
            if (cartError != null) {
                return PaymentResult.fail(cartError);
            }

            // 计算总金额
            total = CartService.calculateTotal();
            if (total <= 0) {
                return PaymentResult.fail("Invalid cart total. Please refresh and retry.");
            }

            // ===== Step 3: Verify payment callback =====
            if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
                return PaymentResult.fail("No payment method selected.");
            }
            // Mock payment callback: in production, verify third-party payment platform signature
            if (!verifyPaymentCallback(userId, total)) {
                return PaymentResult.fail("Payment verification failed. Payment not completed.");
            }

            // ===== Step 4: Begin transaction =====
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); // disable auto-commit

            // ===== Step 5: Create order record =====
            String sql1 = "INSERT INTO totallist(user_id, money, date) VALUES(?, ?, NOW())";
            try (PreparedStatement ps1 = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS)) {
                ps1.setInt(1, userId);
                ps1.setInt(2, total);
                int affected = ps1.executeUpdate();
                if (affected == 0) {
                    conn.rollback();
                    return PaymentResult.fail("Failed to create order: database write error.");
                }

                // Retrieve generated order ID
                try (ResultSet keys = ps1.getGeneratedKeys()) {
                    if (keys.next()) {
                        orderId = keys.getInt(1);
                    } else {
                        conn.rollback();
                        return PaymentResult.fail("Failed to retrieve order number.");
                    }
                }
            }

            // ===== Step 6: Archive cart items to order details =====
            String sql2 = "INSERT INTO orderitems(order_id, name, price, amount) "
                        + "SELECT ?, name, price, amount FROM orderlist WHERE user_id = ?";
            try (PreparedStatement ps2 = conn.prepareStatement(sql2)) {
                ps2.setInt(1, orderId);
                ps2.setInt(2, userId);
                int itemsArchived = ps2.executeUpdate();
                if (itemsArchived == 0) {
                    conn.rollback();
                    return PaymentResult.fail("Failed to archive order items: no items in cart.");
                }
            }

            // ===== Step 7: Backup cart THEN clear (safety net) =====
            // Save cart snapshot BEFORE clearing, so we can recover if something goes wrong
            try {
                lastCheckoutCartBackup = CartService.backupCart();
                System.out.println("[Payment] Cart backup saved: " + lastCheckoutCartBackup.size() + " items");
            } catch (Exception backupEx) {
                System.err.println("[Payment] Cart backup failed (non-fatal): " + backupEx.getMessage());
                lastCheckoutCartBackup = null;
            }
            CartService.clearCart(conn, userId);

            // ===== Step 8: Commit transaction =====
            conn.commit();

            System.out.println("[Payment Success] user_id=" + userId
                + " order_id=" + orderId
                + " amount=" + total
                + " method=" + paymentMethod);

            return PaymentResult.ok(orderId, total);

        } catch (Exception e) {
            // ===== 异常回滚 =====
            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println("[TX Rollback] Order creation failed, rolled back: " + e.getMessage());
                } catch (SQLException rollbackEx) {
                    System.err.println("[Rollback Failed] " + rollbackEx.getMessage());
                }
            }
            e.printStackTrace();
            return PaymentResult.fail("Payment processing error: " + e.getMessage());

        } finally {
            // ===== 恢复自动提交并关闭连接 =====
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 模拟支付回调验证
     * 在实际集成中，此处需：
     *   - 校验第三方支付平台的回调签名
     *   - 对比订单金额与支付金额
     *   - 检查订单是否已处理（防重复通知）
     */
    private static boolean verifyPaymentCallback(int userId, int totalAmount) {
        // Mock validation: verify amount > 0, user exists
        if (totalAmount <= 0) {
            System.err.println("[Payment Verify Failed] Invalid amount: " + totalAmount);
            return false;
        }

        // Verify user exists in database
        String sql = "SELECT COUNT(*) FROM userlogin WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    System.err.println("[Payment Verify Failed] User not found: " + userId);
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("[Payment Verify Error] " + e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * 查询当前用户的历史订单列表
     */
    public static List<OrderSummary> getOrderHistory(int userId) throws SQLException {
        List<OrderSummary> list = new ArrayList<>();

        String sql = "SELECT t.id, t.money, t.date, "
                   + "COALESCE((SELECT COUNT(*) FROM orderitems oi WHERE oi.order_id = t.id), 0) AS item_count "
                   + "FROM totallist t "
                   + "WHERE t.user_id = ? "
                   + "ORDER BY t.id DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new OrderSummary(
                        rs.getInt("id"),
                        rs.getInt("money"),
                        rs.getTimestamp("date"),
                        rs.getInt("item_count")
                    ));
                }
            }
        }
        return list;
    }

    /**
     * 查询指定订单的物品明细
     */
    public static List<OrderItem> getOrderItems(int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();

        String sql = "SELECT name, price, amount, (price * amount) AS subtotal "
                   + "FROM orderitems WHERE order_id = ? ORDER BY name";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(new OrderItem(
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("amount"),
                        rs.getInt("subtotal")
                    ));
                }
            }
        }
        return items;
    }

    /**
     * 数据一致性校验：检查订单的 totallist.money 是否等于 orderitems 小计之和
     * @return null 表示一致，否则返回错误描述
     */
    public static String validateOrderConsistency(int orderId) {
        try (Connection conn = DBUtil.getConnection()) {

            // Get order total
            String sql1 = "SELECT money FROM totallist WHERE id = ?";
            int orderTotal;
            try (PreparedStatement ps = conn.prepareStatement(sql1)) {
                ps.setInt(1, orderId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return "Order #" + orderId + " does not exist.";
                    }
                    orderTotal = rs.getInt("money");
                }
            }

            // Calculate sum of item subtotals
            String sql2 = "SELECT COALESCE(SUM(price * amount), 0) FROM orderitems WHERE order_id = ?";
            int itemsTotal;
            try (PreparedStatement ps = conn.prepareStatement(sql2)) {
                ps.setInt(1, orderId);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    itemsTotal = rs.getInt(1);
                }
            }

            if (orderTotal != itemsTotal) {
                return String.format("Order #%d data mismatch: order total=%d, items sum=%d",
                    orderId, orderTotal, itemsTotal);
            }

            return null; // data consistent

        } catch (SQLException e) {
            return "Consistency check error: " + e.getMessage();
        }
    }

    /**
     * Check if there is a cart backup from a previous failed checkout.
     * This means data may have been lost and can potentially be recovered.
     * @return true if stale backup exists (checkout was interrupted)
     */
    public static boolean hasOrphanedCartBackup() {
        return lastCheckoutCartBackup != null && !lastCheckoutCartBackup.isEmpty();
    }

    /**
     * Attempt to restore cart from the last checkout backup.
     * Use this when the cart appears empty but a checkout was recently attempted.
     * @return the number of items restored
     */
    public static int recoverCartFromCheckoutBackup() {
        if (lastCheckoutCartBackup == null || lastCheckoutCartBackup.isEmpty()) {
            System.out.println("[OrderService] No cart backup to restore from.");
            return 0;
        }
        int restored = CartService.restoreCartFromBackup(lastCheckoutCartBackup);
        if (restored > 0) {
            System.out.println("[OrderService] Recovered " + restored + " items from checkout backup.");
            // Clear backup after successful restore
            lastCheckoutCartBackup = null;
        }
        return restored;
    }

    /**
     * Get the size of the checkout backup (for UI display).
     */
    public static int getBackupItemCount() {
        return lastCheckoutCartBackup != null ? lastCheckoutCartBackup.size() : 0;
    }

    /**
     * Clear the checkout backup explicitly.
     */
    public static void clearCheckoutBackup() {
        lastCheckoutCartBackup = null;
    }
}

/**
 * 订单物品明细模型
 */
class OrderItem {
    private String name;
    private int price;
    private int amount;
    private int subtotal;

    public OrderItem(String name, int price, int amount, int subtotal) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.subtotal = subtotal;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getAmount() { return amount; }
    public int getSubtotal() { return subtotal; }
}
