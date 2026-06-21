package text;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 通用商品购买对话框
 * 所有商品详情页可通过此工具类统一处理购买逻辑，自动关联当前用户
 *
 * 使用方式：
 *   QuickBuyHelper.show(primaryStage, "SportGloves-Vice", 5306);
 */
public class QuickBuyHelper {

    public static void show(Stage ownerStage, String productName, int productPrice) {
        Stage buyStage = new Stage();
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #696969");

        Text text = new Text("Enter quantity: ");
        text.setFill(Color.DODGERBLUE);
        text.setFont(Font.font(null, FontWeight.BOLD, 22));
        text.setLayoutX(0);
        text.setLayoutY(30);

        TextField textfield = new TextField();
        textfield.setMaxWidth(200);
        textfield.setMaxHeight(40);
        textfield.setLayoutX(0);
        textfield.setLayoutY(60);

        Button button = new Button("Add to Cart");
        button.setMinWidth(100);
        button.setStyle("-fx-font: 16 SimHei; -fx-base: #4169E1");
        button.setLayoutX(170);
        button.setLayoutY(60);
        button.setOnMouseClicked(e -> {
            // 登录校验
            if (!Session.getInstance().isLoggedIn()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Notice");
                alert.setHeaderText("Please Login First");
                alert.setContentText("You need to login before adding items to cart.");
                alert.showAndWait();
                buyStage.close();
                return;
            }

            String qtyText = textfield.getText().trim();
            if (qtyText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Notice");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a quantity.");
                alert.showAndWait();
                return;
            }

            int qty;
            try {
                qty = Integer.parseInt(qtyText);
                if (qty <= 0) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Notice");
                    alert.setHeaderText(null);
                    alert.setContentText("Quantity must be greater than 0.");
                    alert.showAndWait();
                    return;
                }
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Notice");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid number.");
                alert.showAndWait();
                return;
            }

            try {
                CartService.addToCart(productName, productPrice, qty);
                buyStage.close();
            } catch (Exception e1) {
                e1.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Add to Cart Failed");
                alert.setContentText(e1.getMessage());
                alert.showAndWait();
            }
        });

        pane.getChildren().addAll(text, textfield, button);

        Scene scene = new Scene(pane, 280, 100);
        buyStage.initStyle(StageStyle.UNDECORATED);
        buyStage.setTitle("Buy");
        buyStage.setScene(scene);
        buyStage.show();
    }
}
