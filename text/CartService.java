package text;

import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Cart business logic layer
 * Provides user-isolated cart CRUD operations
 * Enhanced with data integrity guards: retry, backup, validation
 */
public class CartService {

    /**
     * Add item to current user's cart
     */
    public static void addToCart(String productName, int price, int amount)
            throws SQLException, IllegalStateException {

        // Pre-validation: reject invalid data before touching DB
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive, got: " + price);
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Quantity must be positive, got: " + amount);
        }

        int userId = Session.getInstance().getUserId();

        // Pre-check: validate user still exists
        if (!userExists(userId)) {
            throw new IllegalStateException("Your session may be expired. Please re-login.");
        }

        String sql = "INSERT INTO orderlist(user_id, name, price, amount, created_at) VALUES(?, ?, ?, ?, NOW())";
        DataIntegrityGuard.withRetry("addToCart", () -> {
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ps.setString(2, productName);
                ps.setInt(3, price);
                ps.setInt(4, amount);
                int affected = ps.executeUpdate();
                if (affected == 0) {
                    throw new SQLException("No rows inserted - database write failed.");
                }
            }
            return null;
        });
    }

    /**
     * Get current user's cart items
     */
    public static List<CartItem> getCart() throws SQLException, IllegalStateException {
        int userId = Session.getInstance().getUserId();
        List<CartItem> items = new ArrayList<>();

        String sql = "SELECT id, name, price, amount, created_at FROM orderlist WHERE user_id = ? ORDER BY id";
        System.out.println("[getCart SQL] " + sql + "  userId=" + userId);

        DataIntegrityGuard.withRetry("getCart", () -> {
            items.clear();
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        CartItem ci = new CartItem(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("price"),
                            rs.getInt("amount"),
                            rs.getString("created_at")
                        );
                        items.add(ci);
                    }
                }
            }
            return null;
        });

        System.out.println("[getCart Result] Total items: " + items.size());
        return items;
    }

    /**
     * Update item quantity in cart
     */
    public static void updateAmount(String productName, int price, int newAmount)
            throws SQLException, IllegalStateException {
        if (newAmount <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0. Use delete to remove.");
        }
        int userId = Session.getInstance().getUserId();

        String sql = "UPDATE orderlist SET amount = ? WHERE user_id = ? AND name = ? AND price = ?";
        DataIntegrityGuard.withRetry("updateAmount", () -> {
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, newAmount);
                ps.setInt(2, userId);
                ps.setString(3, productName);
                ps.setInt(4, price);
                int affected = ps.executeUpdate();
                if (affected == 0) {
                    throw new SQLException("Item not found in cart - may have been removed.");
                }
            }
            return null;
        });
    }

    /**
     * Remove item from current user's cart
     */
    public static void removeFromCart(String productName, int price, int amount)
            throws SQLException, IllegalStateException {
        int userId = Session.getInstance().getUserId();

        String sql = "DELETE FROM orderlist WHERE user_id = ? AND name = ? AND price = ? AND amount = ? LIMIT 1";
        DataIntegrityGuard.withRetry("removeFromCart", () -> {
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ps.setString(2, productName);
                ps.setInt(3, price);
                ps.setInt(4, amount);
                int affected = ps.executeUpdate();
                if (affected == 0) {
                    throw new SQLException("Item not found - may have been already removed.");
                }
            }
            return null;
        });
    }

    /**
     * Take a snapshot backup of current cart before a destructive operation.
     * Returns the backed-up items so they can be restored if needed.
     */
    public static List<CartItem> backupCart() {
        try {
            List<CartItem> snapshot = getCart();
            DataIntegrityGuard.updateCartSnapshot(snapshot);
            System.out.println("[CartService] Cart backed up: " + snapshot.size() + " items");
            return snapshot;
        } catch (Exception e) {
            System.err.println("[CartService] Cart backup failed: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Restore cart from backup (emergency recovery).
     * Only used when data loss is detected.
     */
    public static int restoreCartFromBackup(List<CartItem> backupItems) {
        int restored = 0;
        if (backupItems == null || backupItems.isEmpty()) return 0;
        try {
            for (CartItem ci : backupItems) {
                try {
                    addToCart(ci.getName(), ci.getPrice(), ci.getAmount());
                    restored++;
                } catch (Exception e) {
                    System.err.println("[CartService] Restore failed for " + ci.getName() + ": " + e.getMessage());
                }
            }
            System.out.println("[CartService] Restored " + restored + "/" + backupItems.size() + " items");
        } catch (Exception e) {
            System.err.println("[CartService] Bulk restore failed: " + e.getMessage());
        }
        return restored;
    }

    /**
     * Clear current user's cart (called after successful payment)
     */
    public static void clearCart(Connection conn, int userId) throws SQLException {
        String sql = "DELETE FROM orderlist WHERE user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            int removed = ps.executeUpdate();
            System.out.println("[CartService] Cleared " + removed + " items for user " + userId);
        }
    }

    /**
     * Clear current user's cart (standalone connection version)
     */
    public static void clearCart() throws SQLException, IllegalStateException {
        int userId = Session.getInstance().getUserId();
        String sql = "DELETE FROM orderlist WHERE user_id = ?";
        DataIntegrityGuard.withRetry("clearCart", () -> {
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ps.executeUpdate();
            }
            return null;
        });
    }

    /**
     * Calculate current user's cart total
     */
    public static int calculateTotal() throws SQLException, IllegalStateException {
        int userId = Session.getInstance().getUserId();

        String sql = "SELECT COALESCE(SUM(price * amount), 0) FROM orderlist WHERE user_id = ?";
        int[] result = new int[1];

        DataIntegrityGuard.withRetry("calculateTotal", () -> {
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        result[0] = rs.getInt(1);
                    }
                }
            }
            return null;
        });

        return result[0];
    }

    /**
     * Get number of items in current user's cart
     */
    public static int getItemCount() throws SQLException, IllegalStateException {
        int userId = Session.getInstance().getUserId();

        String sql = "SELECT COUNT(*) FROM orderlist WHERE user_id = ?";
        int[] count = new int[1];

        DataIntegrityGuard.withRetry("getItemCount", () -> {
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        count[0] = rs.getInt(1);
                    }
                }
            }
            return null;
        });

        return count[0];
    }

    /**
     * Validate cart data integrity - called before checkout
     * @return null if valid; otherwise error description
     */
    public static String validateCart() {
        try {
            int userId = Session.getInstance().getUserId();

            String sql = "SELECT COUNT(*) FROM orderlist WHERE user_id = ?";
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        return "Cart is empty. Cannot checkout.";
                    }
                }
            }

            String sql2 = "SELECT COUNT(*) FROM orderlist WHERE user_id = ? AND (price <= 0 OR amount <= 0)";
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql2)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        return "Cart contains invalid data (negative price or zero quantity). "
                             + "Please refresh and remove corrupted items.";
                    }
                }
            }

            return null;
        } catch (Exception e) {
            return "Data validation error: " + e.getMessage() + "\nPlease check your connection and retry.";
        }
    }

    // ==================== Internal helpers ====================

    /**
     * Check if user still exists in database (session validity check).
     */
    private static boolean userExists(int userId) {
        String sql = "SELECT COUNT(*) FROM userlogin WHERE id = ?";
        try {
            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next() && rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("[CartService] User existence check failed: " + e.getMessage());
            return false;
        }
    }
}
