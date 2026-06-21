package text;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class loginfail extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Background pane with gradient
        StackPane root = new StackPane();
        root.setBackground(new Background(new BackgroundFill(
            new LinearGradient(0, 0, 1, 1, true, CycleMethod.REPEAT,
                new Stop(0, Color.web("#667eea")),
                new Stop(1, Color.web("#764ba2"))),
            CornerRadii.EMPTY, Insets.EMPTY)));

        // Card container
        VBox card = new VBox(20);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(35, 40, 35, 40));
        card.setMaxWidth(320);
        card.setStyle("-fx-background-color: rgba(255,255,255,0.95); " +
                      "-fx-background-radius: 18; " +
                      "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 20, 0, 0, 8);");

        // Error icon + message
        javafx.scene.text.Text errorIcon = new javafx.scene.text.Text("\u2717");
        errorIcon.setFont(Font.font("System", FontWeight.BOLD, 42));
        errorIcon.setFill(Color.web("#e74c3c"));

        Label label = new Label("Login Failed");
        label.setFont(Font.font("System", FontWeight.BOLD, 20));
        label.setTextFill(Color.web("#2c3e50"));

        Label subLabel = new Label("Invalid username or password");
        subLabel.setFont(Font.font("System", 14));
        subLabel.setTextFill(Color.web("#7f8c8d"));

        // Confirm button
        Button confirmBtn = new Button("Confirm");
        confirmBtn.setPrefWidth(200);
        confirmBtn.setPrefHeight(42);
        confirmBtn.setFont(Font.font("System", FontWeight.BOLD, 14));
        confirmBtn.setTextFill(Color.WHITE);
        confirmBtn.setBackground(new Background(new BackgroundFill(
            new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#667eea")),
                new Stop(1, Color.web("#764ba2"))),
            new CornerRadii(21), Insets.EMPTY)));
        confirmBtn.setEffect(new DropShadow(0, 0, 6, Color.web("#667eea44")));
        confirmBtn.setCursor(javafx.scene.Cursor.HAND);
        confirmBtn.setOnMouseEntered(e -> {
            confirmBtn.setEffect(new DropShadow(0, 0, 12, Color.web("#667eea88")));
            confirmBtn.setScaleX(1.03);
            confirmBtn.setScaleY(1.03);
        });
        confirmBtn.setOnMouseExited(e -> {
            confirmBtn.setEffect(new DropShadow(0, 0, 6, Color.web("#667eea44")));
            confirmBtn.setScaleX(1.0);
            confirmBtn.setScaleY(1.0);
        });

        confirmBtn.setOnAction(e -> primaryStage.close());

        card.getChildren().addAll(errorIcon, label, subLabel, confirmBtn);
        root.getChildren().add(card);

        Scene scene = new Scene(root, 380, 300);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
