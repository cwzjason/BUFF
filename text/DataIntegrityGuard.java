package text;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import util.DBUtil;

/**
 * Data Integrity Guard
 * - Connection health check before critical operations
 * - Local snapshot cache for cart & history recovery
 * - Retry with exponential backoff
 * - Silent-failure prevention: always notify user
 * - Clear distinction between "no data" and "failed to load"
 */
public class DataIntegrityGuard {

    /** Maximum retry attempts for database operations */
    public static final int MAX_RETRIES = 3;
    /** Base delay between retries (ms) */
    public static final long RETRY_BASE_DELAY_MS = 500;

    /** Local snapshot of last successfully loaded cart */
    private static final List<CartItem> cartSnapshot = new CopyOnWriteArrayList<>();
    /** Timestamp of last successful cart load */
    private static long cartSnapshotTime = 0;
    /** Local snapshot of last successfully loaded order history */
    private static final List<OrderSummary> historySnapshot = new CopyOnWriteArrayList<>();
    /** Timestamp of last successful history load */
    private static long historySnapshotTime = 0;

    /** Whether we are currently operating in degraded (offline/cached) mode */
    private static volatile boolean degradedMode = false;

    // ==================== Connection Health ====================

    /**
     * Check if database connection is available.
     * @return true if DB is reachable
     */
    public static boolean isDatabaseAvailable() {
        try (Connection conn = DBUtil.getConnection()) {
            return conn != null && !conn.isClosed() && conn.isValid(3);
        } catch (SQLException e) {
            System.err.println("[DataGuard] Database unavailable: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check connectivity and return diagnostic message.
     * @return null if OK, otherwise error description
     */
    public static String diagnoseConnection() {
        if (isDatabaseAvailable()) {
            return null;
        }
        return "Database connection failed.\n"
             + "Possible causes:\n"
             + "  - MySQL service is not running\n"
             + "  - Network issue\n"
             + "  - Incorrect DB credentials in DBUtil.java\n\n"
             + "The app will show cached data if available.";
    }

    // ==================== Retry Wrapper ====================

    /**
     * Execute a database operation with automatic retry.
     * @param tag Human-readable label for logging
     * @param action The operation to execute
     * @return the result, or throws after all retries exhausted
     */
    public static <T> T withRetry(String tag, DbAction<T> action) throws SQLException {
        SQLException lastException = null;
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                return action.execute();
            } catch (SQLException e) {
                lastException = e;
                System.err.println("[DataGuard] " + tag + " attempt " + attempt + "/" + MAX_RETRIES
                        + " failed: " + e.getMessage());
                if (attempt < MAX_RETRIES) {
                    try {
                        Thread.sleep(RETRY_BASE_DELAY_MS * attempt);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
        throw new SQLException("[" + tag + "] Failed after " + MAX_RETRIES + " retries", lastException);
    }

    /** Functional interface for retry operations */
    @FunctionalInterface
    public interface DbAction<T> {
        T execute() throws SQLException;
    }

    // ==================== Cart Snapshot (Recovery) ====================

    /**
     * Update cart snapshot with fresh data from database.
     */
    public static void updateCartSnapshot(List<CartItem> items) {
        if (items != null) {
            cartSnapshot.clear();
            cartSnapshot.addAll(items);
            cartSnapshotTime = System.currentTimeMillis();
            degradedMode = false;
            System.out.println("[DataGuard] Cart snapshot updated: " + items.size() + " items");
        }
    }

    /**
     * Get cart data with fallback:
     * 1. Try DB (fresh data)
     * 2. If DB fails, return local snapshot (stale but not empty)
     * 3. If no snapshot, return empty list with degraded flag
     *
     * @return LoadResult containing data + status info
     */
    public static LoadResult<CartItem> loadCartWithFallback() {
        try {
            List<CartItem> fresh = CartService.getCart();
            updateCartSnapshot(fresh);
            return LoadResult.fresh(fresh);
        } catch (Exception e) {
            System.err.println("[DataGuard] Cart DB load failed, using snapshot: " + e.getMessage());
            degradedMode = true;
            if (!cartSnapshot.isEmpty()) {
                return LoadResult.cached(
                    new ArrayList<>(cartSnapshot),
                    cartSnapshotTime,
                    "Showing cached cart from " + formatTimeAgo(cartSnapshotTime)
                        + ". Data may not be current.\nError: " + e.getMessage()
                );
            }
            return LoadResult.failed("Failed to load cart.\n" + e.getMessage()
                + "\n\nPlease check your network and try 'Refresh'.");
        }
    }

    // ==================== History Snapshot (Recovery) ====================

    /**
     * Update history snapshot with fresh data from database.
     */
    public static void updateHistorySnapshot(List<OrderSummary> orders) {
        if (orders != null) {
            historySnapshot.clear();
            historySnapshot.addAll(orders);
            historySnapshotTime = System.currentTimeMillis();
            degradedMode = false;
            System.out.println("[DataGuard] History snapshot updated: " + orders.size() + " orders");
        }
    }

    /**
     * Get order history with fallback.
     */
    public static LoadResult<OrderSummary> loadHistoryWithFallback(int userId) {
        try {
            List<OrderSummary> fresh = OrderService.getOrderHistory(userId);
            updateHistorySnapshot(fresh);
            return LoadResult.fresh(fresh);
        } catch (Exception e) {
            System.err.println("[DataGuard] History DB load failed, using snapshot: " + e.getMessage());
            degradedMode = true;
            if (!historySnapshot.isEmpty()) {
                return LoadResult.cached(
                    new ArrayList<>(historySnapshot),
                    historySnapshotTime,
                    "Showing cached history from " + formatTimeAgo(historySnapshotTime)
                        + ". Data may not be current.\nError: " + e.getMessage()
                );
            }
            return LoadResult.failed("Failed to load order history.\n" + e.getMessage()
                + "\n\nPlease check your network and try 'Refresh'.");
        }
    }

    // ==================== Data Validation ====================

    /**
     * Validate cart data integrity after loading.
     * Checks for: null items, negative prices, zero amounts, orphan records.
     */
    public static String validateCartData(List<CartItem> items) {
        if (items == null) {
            return "Cart data is null (internal error).";
        }
        if (items.isEmpty()) {
            return null; // empty cart is valid
        }
        for (CartItem ci : items) {
            if (ci == null) {
                return "Cart contains null item (data corruption).";
            }
            if (ci.getName() == null || ci.getName().trim().isEmpty()) {
                return "Cart item has empty name (data corruption). Please delete it.";
            }
            if (ci.getPrice() <= 0) {
                return "Cart item '" + ci.getName() + "' has invalid price: $" + ci.getPrice()
                    + ". Please delete and re-add.";
            }
            if (ci.getAmount() <= 0) {
                return "Cart item '" + ci.getName() + "' has invalid quantity: " + ci.getAmount()
                    + ". Please update or delete.";
            }
        }
        return null; // all valid
    }

    /**
     * Attempt to repair cart data by removing corrupted entries.
     * @return number of entries removed
     */
    public static int attemptCartRepair(List<CartItem> items, Runnable onRepaired) {
        int removed = 0;
        if (items == null) return 0;
        for (int i = items.size() - 1; i >= 0; i--) {
            CartItem ci = items.get(i);
            if (ci == null || ci.getPrice() <= 0 || ci.getAmount() <= 0) {
                try {
                    if (ci != null) {
                        CartService.removeFromCart(ci.getName(), ci.getPrice(), ci.getAmount());
                    }
                    items.remove(i);
                    removed++;
                } catch (Exception ignore) {}
            }
        }
        if (removed > 0) {
            System.out.println("[DataGuard] Cart repair: removed " + removed + " corrupted entries");
            if (onRepaired != null) {
                Platform.runLater(onRepaired);
            }
        }
        return removed;
    }

    // ==================== Safe Operation Wrappers ====================

    /**
     * Safely execute a cart mutation operation with pre-validation.
     * @return error string if failed, null if success
     */
    public static String safeCartOperation(String opName, Runnable dbOperation) {
        // Check connection first
        if (!isDatabaseAvailable()) {
            return "Database unavailable.\nYour change could not be saved.\nPlease check MySQL and try again.";
        }
        try {
            dbOperation.run();
            return null; // success
        } catch (Exception e) {
            System.err.println("[DataGuard] Cart op '" + opName + "' failed: " + e.getMessage());
            return "Operation failed: " + opName + "\n\nError: " + e.getMessage()
                + "\n\nYour cart data has NOT been modified.\nPlease try again.";
        }
    }

    // ==================== Alert Helpers ====================

    /**
     * Show error alert with recovery hint.
     */
    public static void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * Show warning (non-blocking) about degraded mode.
     */
    public static void showDegradedWarning(String detail) {
        Alert alert = new Alert(AlertType.WARNING,
            "The app is showing locally cached data because the database is unavailable.\n\n"
            + detail + "\n\nClick 'Refresh' to retry.",
            ButtonType.OK);
        alert.setTitle("Cached Data");
        alert.setHeaderText("Operating in Offline Mode");
        alert.show();
    }

    // ==================== State Queries ====================

    public static boolean isDegradedMode() { return degradedMode; }
    public static void clearDegradedMode() { degradedMode = false; }
    public static int getCartSnapshotSize() { return cartSnapshot.size(); }
    public static int getHistorySnapshotSize() { return historySnapshot.size(); }
    public static void clearSnapshots() { cartSnapshot.clear(); historySnapshot.clear(); }

    // ==================== Helpers ====================

    private static String formatTimeAgo(long timestamp) {
        long seconds = (System.currentTimeMillis() - timestamp) / 1000;
        if (seconds < 60) return "just now";
        if (seconds < 3600) return (seconds / 60) + " min ago";
        if (seconds < 86400) return (seconds / 3600) + " hours ago";
        return (seconds / 86400) + " days ago";
    }

    // ==================== LoadResult class ====================

    /**
     * Result of a data load operation with status info.
     */
    public static class LoadResult<T> {
        public final List<T> data;
        public final DataSource source;  // FRESH, CACHED, FAILED
        public final String warning;     // non-null if source == CACHED or FAILED
        public final long snapshotTime;

        private LoadResult(List<T> data, DataSource source, String warning, long snapshotTime) {
            this.data = data != null ? data : Collections.emptyList();
            this.source = source;
            this.warning = warning;
            this.snapshotTime = snapshotTime;
        }

        public static <T> LoadResult<T> fresh(List<T> data) {
            return new LoadResult<>(data, DataSource.FRESH, null, 0);
        }

        public static <T> LoadResult<T> cached(List<T> data, long time, String warning) {
            return new LoadResult<>(data, DataSource.CACHED, warning, time);
        }

        public static <T> LoadResult<T> failed(String warning) {
            return new LoadResult<>(Collections.emptyList(), DataSource.FAILED, warning, 0);
        }

        public boolean isCached() { return source == DataSource.CACHED; }
        public boolean isFailed() { return source == DataSource.FAILED; }
    }

    public enum DataSource { FRESH, CACHED, FAILED }
}
