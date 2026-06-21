package text;

import util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class totallist extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // ===== Check login status =====
        if (!Session.getInstance().isLoggedIn()) {
            showAlert("Please login first to view your order history.");
            primaryStage.close();
            return;
        }

        int currentUserId = Session.getInstance().getUserId();

        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setStyle("-fx-background-color: #1a1a2e; -fx-padding: 20;");

        // ---- Title ----
        Label title = new Label("📋 Order History");
        title.setFont(Font.font("Segoe UI", 24));
        title.setTextFill(Color.WHITE);
        pane.add(title, 0, 0, 4, 1);

        // ---- User info ----
        Label userLabel = new Label("👤 " + Session.getInstance().getUsername() + "'s Order History");
        userLabel.setFont(Font.font("Segoe UI", 13));
        userLabel.setTextFill(Color.web("#a0a8cc"));
        pane.add(userLabel, 0, 1, 4, 1);

        // ---- Hint label ----
        Label hint = new Label("💡 Double-click an order to view its items");
        hint.setFont(Font.font("Segoe UI", 13));
        hint.setTextFill(Color.web("#a0a8cc"));
        pane.add(hint, 0, 2, 4, 1);

        // ---- Table ----
        TableView<OrderSummary> table = new TableView<>();
        table.setPrefSize(700, 400);

        TableColumn<OrderSummary, Integer> idCol = new TableColumn<>("Order #");
        idCol.setMinWidth(80);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<OrderSummary, Integer> moneyCol = new TableColumn<>("Total ($)");
        moneyCol.setMinWidth(120);
        moneyCol.setCellValueFactory(new PropertyValueFactory<>("money"));

        TableColumn<OrderSummary, Timestamp> dateCol = new TableColumn<>("Date");
        dateCol.setMinWidth(200);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<OrderSummary, Integer> itemsCol = new TableColumn<>("Items");
        itemsCol.setMinWidth(100);
        itemsCol.setCellValueFactory(new PropertyValueFactory<>("itemCount"));

        table.getColumns().addAll(idCol, moneyCol, dateCol, itemsCol);
        table.setPlaceholder(new Label("No orders found."));
        pane.add(table, 0, 3, 4, 1);

        // ---- Status label (for error/cache notifications) ----
        Label statusLabel = new Label();
        statusLabel.setFont(Font.font("Segoe UI", 12));
        statusLabel.setMaxWidth(700);
        statusLabel.setWrapText(true);
        pane.add(statusLabel, 0, 5, 4, 1);

        // ---- Load data with integrity guard ----
        loadDataWithGuard(table, currentUserId, statusLabel);

        // ---- Buttons ----
        String btnStyle = "-fx-font-size: 13px; -fx-padding: 8 20; -fx-background-radius: 6;"
                        + " -fx-cursor: hand; -fx-font-weight: bold;";

        Button viewBtn = new Button("🔍 View Items");
        viewBtn.setStyle(btnStyle + "-fx-background-color: #3498db; -fx-text-fill: white;");
        pane.add(viewBtn, 0, 4);

        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setStyle(btnStyle + "-fx-background-color: #9b59b6; -fx-text-fill: white;");
        pane.add(refreshBtn, 1, 4);

        Button backBtn = new Button("← Back");
        backBtn.setStyle(btnStyle + "-fx-background-color: #7f8c8d; -fx-text-fill: white;");
        pane.add(backBtn, 2, 4);

        // ====== Actions ======

        // Double-click: view order detail
        table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                OrderSummary selected = table.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    showOrderItemsDialog(selected.getId(), selected.getMoney());
                }
            }
        });

        // View Items button
        viewBtn.setOnAction(e -> {
            OrderSummary selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Please select an order first.");
                return;
            }
            showOrderItemsDialog(selected.getId(), selected.getMoney());
        });

        // Refresh with connection health check
        refreshBtn.setOnAction(e -> {
            String diag = DataIntegrityGuard.diagnoseConnection();
            if (diag != null) {
                DataIntegrityGuard.showErrorAlert("Connection Error",
                    "Cannot refresh data.\n\n" + diag);
                return;
            }
            DataIntegrityGuard.clearDegradedMode();
            loadDataWithGuard(table, currentUserId, statusLabel);
        });

        // Back
        backBtn.setOnAction(e -> {
            try {
                main open = new main();
                open.start(new Stage());
                primaryStage.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        Scene scene = new Scene(pane, 720, 600);
        primaryStage.setTitle("Order History");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // ====== Load order history with integrity guard ======
    private void loadDataWithGuard(TableView<OrderSummary> table, int userId, Label statusLabel) {
        DataIntegrityGuard.LoadResult<OrderSummary> result =
                DataIntegrityGuard.loadHistoryWithFallback(userId);
        ObservableList<OrderSummary> data = FXCollections.observableArrayList();
        data.addAll(result.data);
        table.setItems(data);

        if (result.isFailed()) {
            statusLabel.setText("❌ " + result.warning);
            statusLabel.setTextFill(Color.RED);
            table.setPlaceholder(new Label("Failed to load data. Check connection and retry."));
        } else if (result.isCached()) {
            statusLabel.setText("⚠ Cached data: " + result.warning);
            statusLabel.setTextFill(Color.ORANGE);
            table.setPlaceholder(new Label("Showing cached data (database unavailable)."));
        } else {
            statusLabel.setText("✅ " + data.size() + " order(s) loaded");
            statusLabel.setTextFill(Color.web("#27ae60"));
            table.setPlaceholder(new Label("No orders found."));
        }
    }

    /** Legacy load method (kept for backward compat) */
    private void loadData(TableView<OrderSummary> table, int userId) {
        ObservableList<OrderSummary> data = FXCollections.observableArrayList();
        try {
            data.addAll(OrderService.getOrderHistory(userId));
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Failed to load order history: " + e.getMessage());
        }
        table.setItems(data);
    }

    // ====== Show order items detail dialog ======
    private void showOrderItemsDialog(int orderId, int orderTotal) {
        // Data consistency check
        String consistencyError = OrderService.validateOrderConsistency(orderId);
        if (consistencyError != null) {
            System.err.println("[Consistency Warning] " + consistencyError);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== Order #").append(orderId).append(" ===\n\n");

        int itemsTotal = 0;
        try {
            for (OrderItem item : OrderService.getOrderItems(orderId)) {
                sb.append(String.format("  %-3dx  %-30s  $%-8d  = $%d\n",
                    item.getAmount(),
                    item.getName(),
                    item.getPrice(),
                    item.getSubtotal()
                ));
                itemsTotal += item.getSubtotal();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            sb.append("Error loading items: ").append(e.getMessage()).append("\n");
        }

        sb.append("\n───────────────────────────\n");
        sb.append(String.format("  Order Total:    $%d\n", orderTotal));
        sb.append(String.format("  Items Subtotal: $%d\n", itemsTotal));

        if (orderTotal == itemsTotal) {
            sb.append("  ✅ Data consistent");
        } else {
            sb.append(String.format("  ⚠️ Data mismatch! Diff: $%d", orderTotal - itemsTotal));
        }

        showInfoDialog(sb.toString());
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Notice");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfoDialog(String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Order Items");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
