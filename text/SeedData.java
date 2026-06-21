package text;

import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Demo Data Seeder
 * Restores sample cart items and order history for the current user
 * when data has been lost from the database.
 */
public class SeedData {

    /**
     * Seed demo cart items for the given user.
     * Checks if cart is empty first; only inserts if cart is empty.
     * @param userId the current user's ID
     * @return number of items inserted
     */
    public static int seedCart(int userId) throws SQLException {
        // Check if cart already has items
        String checkSql = "SELECT COUNT(*) FROM orderlist WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(checkSql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("[SeedData] Cart already has data, skip seeding.");
                    return 0;
                }
            }
        }

        // Demo cart items
        String[][] cartItems = {
            {"AK-47 | Redline",                "267",  "2"},
            {"M4A1-S | Printstream",           "432",  "1"},
            {"Butterfly Knife | Black Laminate", "1728", "1"},
        };

        String sql = "INSERT INTO orderlist(user_id, name, price, amount, created_at) VALUES(?, ?, ?, ?, NOW())";
        int count = 0;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (String[] item : cartItems) {
                ps.setInt(1, userId);
                ps.setString(2, item[0]);
                ps.setDouble(3, Double.parseDouble(item[1]));
                ps.setInt(4, Integer.parseInt(item[2]));
                ps.addBatch();
                count++;
            }
            ps.executeBatch();
        }
        System.out.println("[SeedData] Seeded " + count + " cart items for user " + userId);
        return count;
    }

    /**
     * Seed demo order history (totallist + orderitems) for the given user.
     * Checks if history is empty first.
     * @param userId the current user's ID
     * @return number of orders inserted
     */
    public static int seedOrderHistory(int userId) throws SQLException {
        // Check if history already has orders
        String checkSql = "SELECT COUNT(*) FROM totallist WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(checkSql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("[SeedData] Order history already has data, skip seeding.");
                    return 0;
                }
            }
        }

        Connection conn = null;
        int orderCount = 0;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            // --- Order 1: Sport Gloves | Vice ($5306) x1 ---
            String orderSql = "INSERT INTO totallist(user_id, money, date) VALUES(?, ?, NOW())";
            int orderId1;
            try (PreparedStatement ps = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ps.setDouble(2, 5306.00);
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    keys.next();
                    orderId1 = keys.getInt(1);
                }
            }

            String itemSql = "INSERT INTO orderitems(order_id, name, price, amount) VALUES(?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(itemSql)) {
                ps.setInt(1, orderId1);
                ps.setString(2, "Sport Gloves | Vice");
                ps.setDouble(3, 5306.00);
                ps.setInt(4, 1);
                ps.executeUpdate();
            }
            orderCount++;

            // --- Order 2: AWP | Dragon Lore ($12733) x1 + AK-47 | Redline ($267) x1 = $13000 ---
            int orderId2;
            try (PreparedStatement ps = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ps.setDouble(2, 13000.00);
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    keys.next();
                    orderId2 = keys.getInt(1);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(itemSql)) {
                ps.setInt(1, orderId2);
                ps.setString(2, "AWP | Dragon Lore");
                ps.setDouble(3, 12733.00);
                ps.setInt(4, 1);
                ps.addBatch();

                ps.setInt(1, orderId2);
                ps.setString(2, "AK-47 | Redline");
                ps.setDouble(3, 267.00);
                ps.setInt(4, 1);
                ps.addBatch();

                ps.executeBatch();
            }
            orderCount++;

            conn.commit();
            System.out.println("[SeedData] Seeded " + orderCount + " orders for user " + userId);
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
        return orderCount;
    }

    /**
     * Seed all demo data (cart + history) for the current user.
     * @param userId the current user's ID
     * @return summary message
     */
    public static String seedAll(int userId) {
        StringBuilder result = new StringBuilder();
        try {
            int cartCount = seedCart(userId);
            result.append("Cart: ").append(cartCount).append(" items");
            if (cartCount > 0) {
                result.append(" (AK-47, M4A1-S, Butterfly Knife)");
            } else {
                result.append(" (already has data)");
            }
        } catch (SQLException e) {
            result.append("Cart seeding failed: ").append(e.getMessage());
        }

        result.append("\n");

        try {
            int orderCount = seedOrderHistory(userId);
            result.append("Orders: ").append(orderCount).append(" records");
            if (orderCount > 0) {
                result.append(" (2 orders with items)");
            } else {
                result.append(" (already has data)");
            }
        } catch (SQLException e) {
            result.append("Order seeding failed: ").append(e.getMessage());
        }

        System.out.println("[SeedData] Result: " + result.toString());
        return result.toString();
    }
}
