package text;

import util.DBUtil;


import java.sql.Connection;
import java.sql.PreparedStatement;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Register extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Gradient Background
		javafx.scene.shape.Rectangle bg = new javafx.scene.shape.Rectangle(440, 520);
		bg.setFill(new javafx.scene.paint.LinearGradient(0, 0, 1, 1, true, null,
			new javafx.scene.paint.Stop(0, Color.web("#11998e")),
			new javafx.scene.paint.Stop(1, Color.web("#38ef7d"))
		));

		// White Card
		VBox card = new VBox(16);
		card.setAlignment(Pos.CENTER_LEFT);
		card.setPadding(new Insets(35, 40, 30, 40));
		card.setStyle(
			"-fx-background-color: white;" +
			"-fx-background-radius: 20;" +
			"-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 25, 0, 0, 8);"
		);
		card.setMaxWidth(340);

		// Title
		Label title = new Label("Create Account");
		title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
		title.setTextFill(Color.web("#333"));

		Label desc = new Label("Join BUFF system today");
		desc.setFont(Font.font("Segoe UI", 13));
		desc.setTextFill(Color.web("#999"));
		VBox titleBox = new VBox(4, title, desc);

		// Input Field Style
		String fieldStyle =
			"-fx-background-color: #f6f7fb;" +
			"-fx-border-color: transparent;" +
			"-fx-border-radius: 10;" +
			"-fx-background-radius: 10;" +
			"-fx-padding: 12 16;" +
			"-fx-font-size: 14px;";

		String labelStyle =
			"-fx-font-size: 13px;" +
			"-fx-font-family: 'Segoe UI';" +
			"-fx-text-fill: #555;";

		Label lbname = new Label("Username");
		lbname.setStyle(labelStyle);
		TextField tfname = new TextField();
		tfname.setPromptText("Choose a username");
		tfname.setStyle(fieldStyle);
		tfname.setPrefHeight(42);

		Label lbpassword = new Label("Password");
		lbpassword.setStyle(labelStyle);
		PasswordField pswd = new PasswordField();
		pswd.setPromptText("Create a password");
		pswd.setStyle(fieldStyle);
		pswd.setPrefHeight(42);

		Button registerBtn = new Button("Create Account");
		registerBtn.setStyle(
			"-fx-background-color: linear-gradient(to right, #11998e, #38ef7d);" +
			"-fx-text-fill: white;" +
			"-fx-font-size: 15px;" +
			"-fx-font-weight: bold;" +
			"-fx-font-family: 'Segoe UI';" +
			"-fx-padding: 12 28;" +
			"-fx-background-radius: 50;" +
			"-fx-cursor: hand;"
		);
		registerBtn.setMaxWidth(Double.MAX_VALUE);

		// Assemble
		card.getChildren().addAll(titleBox, lbname, tfname, lbpassword, pswd, registerBtn);
		StackPane root = new StackPane(bg, card);
		StackPane.setMargin(card, new Insets(35));

		// Events (keep original logic)
		registerBtn.setOnAction(e -> {
			if (tfname.getText().equals("")) {
				error("Username cannot be empty!");
				return;
			}
			if (pswd.getText().equals("")) {
				error("Password cannot be empty!");
				return;
			}
			boolean success = false;
			registersuccess open = new registersuccess();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			if (success) { primaryStage.close(); }

			try {
				Connection conn = DBUtil.getConnection();
				String sql = "insert into userlogin(id,name,password) values(null,?,?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, tfname.getText());
				ps.setString(2, pswd.getText());

				ps.executeUpdate();
				conn.close();

			} catch (Exception e1) {
				e1.printStackTrace();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Registration Failed");
				alert.setContentText("Make sure MySQL is running on 127.0.0.1:3306.\n" + e1.getMessage());
				alert.showAndWait();
				return;
			}
		});

		Scene scene = new Scene(root, 440, 520);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Register - BUFF");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public void error(String error) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Notice");
		alert.setHeaderText(null);
		alert.setContentText(error);
		alert.showAndWait();
	}

}
