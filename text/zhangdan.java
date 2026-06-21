package text;

import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class zhangdan extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        // ===== Check login status =====
        if (!Session.getInstance().isLoggedIn()) {
            showAlert("Please login first to access your shopping cart.");
            primaryStage.close();
            return;
        }

        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setStyle("-fx-background-color: #1a1a2e; -fx-padding: 20;");

        // ---- Title ----
        Label title = new Label("🛒 Shopping Cart");
        title.setFont(Font.font("Segoe UI", 24));
        title.setTextFill(Color.WHITE);
        pane.add(title, 0, 0, 4, 1);

        // ---- User info ----
        Label userLabel = new Label("👤 " + Session.getInstance().getUsername());
        userLabel.setFont(Font.font("Segoe UI", 13));
        userLabel.setTextFill(Color.web("#a0a8cc"));
        pane.add(userLabel, 0, 1, 4, 1);

        // ---- Table ----
        TableView<CartItem> table = new TableView<>();
        table.setPrefSize(600, 350);

        TableColumn<CartItem, String> nameCol = new TableColumn<>("Product");
        nameCol.setMinWidth(160);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<CartItem, Integer> priceCol = new TableColumn<>("Price");
        priceCol.setMinWidth(100);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<CartItem, Integer> amountCol = new TableColumn<>("Qty");
        amountCol.setMinWidth(60);
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<CartItem, String> timeCol = new TableColumn<>("Time");
        timeCol.setMinWidth(160);
        timeCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        table.getColumns().addAll(nameCol, priceCol, amountCol, timeCol);
        table.setPlaceholder(new Label("No items in cart."));
        pane.add(table, 0, 2, 5, 1);

        // ---- Load data with integrity guard ----
        Label statusLabel = new Label();
        statusLabel.setFont(Font.font("Segoe UI", 12));
        statusLabel.setMaxWidth(600);
        statusLabel.setWrapText(true);
        pane.add(statusLabel, 0, 7, 5, 1);

        refreshTableWithGuard(table, statusLabel);
        updateStatusDisplay(statusLabel, table);

        // ---- Total display ----
        Label totalLabel = new Label("Total:");
        totalLabel.setFont(Font.font("Segoe UI", 16));
        totalLabel.setTextFill(Color.WHITE);
        pane.add(totalLabel, 0, 3);

        TextField totalField = new TextField(String.format("$%,d", calculateTotal()));
        totalField.setEditable(false);
        totalField.setMaxWidth(150);
        totalField.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        pane.add(totalField, 1, 3);

        // ---- Update section ----
        Label qtyLabel = new Label("New Qty:");
        qtyLabel.setTextFill(Color.WHITE);
        pane.add(qtyLabel, 0, 4);

        TextField qtyField = new TextField();
        qtyField.setMaxWidth(60);
        pane.add(qtyField, 1, 4);

        // ---- Buttons ----
        String btnStyle = "-fx-font-size: 13px; -fx-padding: 8 16; -fx-background-radius: 6; -fx-cursor: hand; -fx-font-weight: bold;";

        Button deleteBtn = new Button("🗑 Delete");
        deleteBtn.setStyle(btnStyle + "-fx-background-color: #e74c3c; -fx-text-fill: white;");
        pane.add(deleteBtn, 0, 5);

        Button updateBtn = new Button("✏ Update Qty");
        updateBtn.setStyle(btnStyle + "-fx-background-color: #f39c12; -fx-text-fill: white;");
        pane.add(updateBtn, 1, 5);

        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setStyle(btnStyle + "-fx-background-color: #3498db; -fx-text-fill: white;");
        pane.add(refreshBtn, 2, 5);

        Button checkoutBtn = new Button("💰 Checkout");
        checkoutBtn.setStyle(btnStyle + "-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 15px; -fx-padding: 10 30;");
        pane.add(checkoutBtn, 3, 5);

        Button backBtn = new Button("← Back");
        backBtn.setStyle(btnStyle + "-fx-background-color: #7f8c8d; -fx-text-fill: white;");
        pane.add(backBtn, 4, 5);

        // Recovery button (hidden by default, shown when data loss detected)
        Button recoverBtn = new Button("🔧 Recover Cart");
        recoverBtn.setStyle(btnStyle + "-fx-background-color: #8e44ad; -fx-text-fill: white;");
        recoverBtn.setVisible(false);
        pane.add(recoverBtn, 0, 6, 2, 1);

        // ---- Actions ----

        // Delete selected item
        deleteBtn.setOnAction(e -> {
            if (!checkConnectionThenProceed("delete")) return;
            CartItem selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Please select an item to delete.");
                return;
            }
            String result = DataIntegrityGuard.safeCartOperation("Delete", () -> {
                try {
                    CartService.removeFromCart(selected.getName(), selected.getPrice(), selected.getAmount());
                } catch (Exception ex) { throw new RuntimeException(ex); }
            });
            if (result != null) {
                DataIntegrityGuard.showErrorAlert("Delete Failed", result);
                return;
            }
            refreshTableWithGuard(table, statusLabel);
            totalField.setText(String.format("$%,d", calculateTotalSafe()));
            updateStatusDisplay(statusLabel, table);
        });

        // Update quantity
        updateBtn.setOnAction(e -> {
            if (!checkConnectionThenProceed("update")) return;
            CartItem selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Please select an item to update.");
                return;
            }
            String newQty = qtyField.getText().trim();
            if (newQty.isEmpty()) {
                showAlert("Please enter a new quantity.");
                return;
            }
            try {
                int qty = Integer.parseInt(newQty);
                if (qty <= 0) {
                    showAlert("Quantity must be > 0. Use Delete to remove items.");
                    return;
                }
                String result = DataIntegrityGuard.safeCartOperation("Update Qty", () -> {
                    try {
                        CartService.updateAmount(selected.getName(), selected.getPrice(), qty);
                    } catch (Exception ex) { throw new RuntimeException(ex); }
                });
                if (result != null) {
                    DataIntegrityGuard.showErrorAlert("Update Failed", result);
                    return;
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid quantity number.");
                return;
            }
            qtyField.clear();
            refreshTableWithGuard(table, statusLabel);
            totalField.setText(String.format("$%,d", calculateTotalSafe()));
            updateStatusDisplay(statusLabel, table);
        });

        // Refresh
        refreshBtn.setOnAction(e -> {
            DataIntegrityGuard.clearDegradedMode();
            refreshTableWithGuard(table, statusLabel);
            totalField.setText(String.format("$%,d", calculateTotalSafe()));
            updateStatusDisplay(statusLabel, table);
        });

        // ====== CHECKOUT: 结算 + 支付回调验证 + 归档 + 清空购物车 ======
        checkoutBtn.setOnAction(e -> {
            // ---- Data integrity pre-check ----
            String validationError = CartService.validateCart();
            if (validationError != null) {
                showAlert(validationError);
                return;
            }

            int total = calculateTotal();
            if (total == 0) {
                showAlert("Your cart is empty! Add items first.");
                return;
            }

            // ---- Show payment dialog ----
            PaymentDialog payment = new PaymentDialog();
            try {
                payment.start(new Stage());
            } catch (Exception e1) {
                e1.printStackTrace();
                showAlert("Failed to open payment window: " + e1.getMessage());
                return;
            }

            // ---- Payment callback (transaction protected + data consistency check) ----
            int userId = Session.getInstance().getUserId();
            String paymentMethod = PaymentDialog.lastSelectedMethod;
            OrderService.PaymentResult result = OrderService.processPayment(userId, paymentMethod);

            if (!result.success) {
                showAlert("Checkout failed: " + result.errorMessage);
                return;
            }

            // ---- Data consistency check ----
            String consistencyError = OrderService.validateOrderConsistency(result.orderId);
            if (consistencyError != null) {
                System.err.println("[Consistency Warning] " + consistencyError);
                // Log only, do not interrupt flow
            }

            // ---- Success dialog ----
            int seq = Session.getInstance().nextOrderSequence();
            showSuccessDialog(seq, result.orderId, result.totalAmount);

            // ---- Refresh UI ----
            refreshTableWithGuard(table, statusLabel);
            totalField.setText("$0");
            updateStatusDisplay(statusLabel, table);
        });

        // Back to main page
        backBtn.setOnAction(e -> {
            try {
                main open = new main();
                open.start(new Stage());
                primaryStage.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // Recover cart from checkout backup
        recoverBtn.setOnAction(e -> {
            int restored = OrderService.recoverCartFromCheckoutBackup();
            if (restored > 0) {
                showAlert("Recovered " + restored + " items from last checkout!");
                OrderService.clearCheckoutBackup();
            } else {
                showAlert("No backup data available to recover.");
            }
            recoverBtn.setVisible(false);
            refreshTableWithGuard(table, statusLabel);
            totalField.setText(String.format("$%,d", calculateTotalSafe()));
            updateStatusDisplay(statusLabel, table);
        });

        Scene scene = new Scene(pane, 780, 560);
        primaryStage.setTitle("Shopping Cart");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // ====== Helper methods ======

    /** Check DB connection; return false and show error if unavailable */
    private boolean checkConnectionThenProceed(String operation) {
        String diag = DataIntegrityGuard.diagnoseConnection();
        if (diag != null) {
            DataIntegrityGuard.showErrorAlert("Connection Error",
                "Cannot perform '" + operation + "'.\n\n" + diag);
            return false;
        }
        return true;
    }

    /** Load cart with integrity guard: fresh DB, or cached if DB down */
    private void refreshTableWithGuard(TableView<CartItem> table, Label statusLabel) {
        DataIntegrityGuard.LoadResult<CartItem> result = DataIntegrityGuard.loadCartWithFallback();
        ObservableList<CartItem> list = FXCollections.observableArrayList();

        // Validate data
        String validationError = DataIntegrityGuard.validateCartData(result.data);
        if (validationError != null) {
            statusLabel.setText("⚠ Data Issue: " + validationError);
            statusLabel.setTextFill(Color.ORANGE);
            System.out.println("[Cart Validate] Issue: " + validationError);
            // Attempt auto-repair
            int removed = DataIntegrityGuard.attemptCartRepair(result.data, () -> {
                refreshTableWithGuard(table, statusLabel);
            });
            if (removed > 0) {
                statusLabel.setText("🔧 Auto-repaired: removed " + removed + " corrupted item(s). Refreshing...");
                statusLabel.setTextFill(Color.ORANGE);
            }
        }

        list.addAll(result.data);

        // Update placeholder based on load status
        if (result.isFailed()) {
            table.setPlaceholder(new Label("Failed to load data. Check connection and retry."));
        } else if (result.isCached()) {
            table.setPlaceholder(new Label("Showing cached data (database unavailable)."));
        } else {
            table.setPlaceholder(new Label("No items in cart."));
        }

        table.setItems(list);
    }

    /** Calculate total safely, return 0 on failure */
    private int calculateTotalSafe() {
        try {
            return DataIntegrityGuard.withRetry("calculateTotal", () -> {
                return CartService.calculateTotal();
            });
        } catch (Exception e) {
            System.err.println("[Cart Total] Failed: " + e.getMessage());
            return 0;
        }
    }

    /** Update status label: show degraded warning or backup recovery hint */
    private void updateStatusDisplay(Label statusLabel, TableView<CartItem> table) {
        StringBuilder sb = new StringBuilder();

        if (DataIntegrityGuard.isDegradedMode()) {
            sb.append("⚠ Offline mode - showing cached data. Click Refresh to retry.");
            statusLabel.setTextFill(Color.ORANGE);
        } else if (OrderService.hasOrphanedCartBackup()) {
            int backupItems = OrderService.getBackupItemCount();
            sb.append("💡 Detected " + backupItems + " item(s) from failed checkout. Use 'Recover Cart' to restore.");
            statusLabel.setTextFill(Color.web("#f1c40f"));
            // Find and show the recover button
            for (javafx.scene.Node node : ((GridPane) statusLabel.getParent()).getChildren()) {
                if (node instanceof Button && ((Button) node).getText().contains("Recover")) {
                    node.setVisible(true);
                    break;
                }
            }
        } else {
            sb.append("✅ " + table.getItems().size() + " item(s) loaded");
            statusLabel.setTextFill(Color.web("#27ae60"));
        }

        statusLabel.setText(sb.toString());
    }

    /** Legacy calculateTotal for checkout flow */
    private int calculateTotal() {
        try {
            return CartService.calculateTotal();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Notice");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showSuccessDialog(int sequenceNum, int orderId, int total) {
        VBox root = new VBox(12);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f0c29, #302b63, #24243e);"
                + "-fx-padding: 32 40 30 40;");

        // 勾勾图标
        Label checkIcon = new Label("\u2714");
        checkIcon.setStyle("-fx-font-size: 48px; -fx-text-fill: #00e676; -fx-font-weight: bold;");

        // 标题 — 使用会话内序号
        Label titleText = new Label("Order #" + sequenceNum + " Completed!");
        titleText.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Total 独占一行，大字突出显示
        Label totalLabel = new Label(String.format("$%,d", total));
        totalLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: #ffd54f;"
                + " -fx-padding: 8 0 4 0;");

        Label totalHint = new Label("Total Amount");
        totalHint.setStyle("-fx-font-size: 13px; -fx-text-fill: #8888aa;");

        // 把 total 和 hint 放在一起
        VBox totalBox = new VBox(2, totalHint, totalLabel);
        totalBox.setAlignment(Pos.CENTER);

        // 次要信息
        Label subText = new Label("Cart cleared · Order archived\nRecord #" + orderId
                + " · Items recorded in history.");
        subText.setStyle("-fx-font-size: 12px; -fx-text-fill: #8888aa;");
        subText.setAlignment(Pos.CENTER);

        Separator sep = new Separator();
        sep.setPrefWidth(240);
        sep.setStyle("-fx-border-color: #333355;");

        Button doneButton = new Button("OK");
        doneButton.setPrefSize(160, 44);
        doneButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;"
                + " -fx-background-color: linear-gradient(#4caf50, #388e3c);"
                + " -fx-text-fill: white; -fx-background-radius: 10;"
                + " -fx-cursor: hand;");
        doneButton.setOnAction(e -> ((Stage) doneButton.getScene().getWindow()).close());

        root.getChildren().addAll(checkIcon, titleText, totalBox, subText, sep, doneButton);

        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        root.setEffect(new javafx.scene.effect.DropShadow(20, Color.BLACK));
        Scene scene = new Scene(root, 420, 400);
        stage.setScene(scene);
        stage.showAndWait();
    }
}


// ====== Payment Dialog (增强版：记录支付方式) ======
class PaymentDialog extends Application {
    public static String lastSelectedMethod = "WeChat/微信"; // 默认支付方式

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox(16);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #0f0c29, #302b63, #24243e);"
                + "-fx-padding: 24 28 28 28;");

        Label titleLabel = new Label("Select Payment Method");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        HBox imgBox = new HBox(12);
        imgBox.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView("image/vx.jpg");
        imageView.setFitHeight(160);
        imageView.setFitWidth(155);
        imageView.setPreserveRatio(true);
        javafx.scene.effect.Reflection ref = new javafx.scene.effect.Reflection();
        ref.setFraction(0.3);
        ref.setTopOffset(-5);
        imageView.setEffect(ref);

        ImageView imageView1 = new ImageView("image/zfb.jpg");
        imageView1.setFitHeight(160);
        imageView1.setFitWidth(155);
        imageView1.setPreserveRatio(true);
        imageView1.setEffect(ref);

        // 点击选择支付方式
        imageView.setOnMouseClicked(e -> {
            lastSelectedMethod = "WeChat/微信";
            titleLabel.setText("Selected: WeChat Pay ✅");
        });
        imageView1.setOnMouseClicked(e -> {
            lastSelectedMethod = "Alipay/支付宝";
            titleLabel.setText("Selected: Alipay ✅");
        });

        imgBox.getChildren().addAll(imageView, imageView1);

        Separator sep = new Separator();
        sep.setPrefWidth(300);
        sep.setStyle("-fx-border-color: #333355;");

        Button button = new Button("Confirm Payment");
        button.setPrefSize(180, 46);
        button.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;"
                + " -fx-background-color: linear-gradient(#5c6bc0, #3949ab);"
                + " -fx-text-fill: white; -fx-background-radius: 10;"
                + " -fx-cursor: hand;");
        button.setOnAction(e -> primaryStage.close());

        root.getChildren().addAll(titleLabel, imgBox, sep, button);

        primaryStage.initStyle(StageStyle.UNDECORATED);
        root.setEffect(new javafx.scene.effect.DropShadow(20, Color.BLACK));
        Scene scene = new Scene(root, 400, 340);
        primaryStage.setTitle("Payment");
        primaryStage.setScene(scene);
        primaryStage.showAndWait();  // 阻塞直到确认支付
    }
}
