package Manager;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import text.loginfail;

public class ManagerTest extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Dark Gradient Background (Admin Style)
		javafx.scene.shape.Rectangle bg = new javafx.scene.shape.Rectangle(480, 560);
		bg.setFill(new javafx.scene.paint.LinearGradient(0, 0, 1, 1, true, null,
			new javafx.scene.paint.Stop(0, Color.web("#141e30")),
			new javafx.scene.paint.Stop(1, Color.web("#243b55"))
		));

		// Dark Card
		VBox card = new VBox(18);
		card.setAlignment(Pos.CENTER_LEFT);
		card.setPadding(new Insets(38, 45, 32, 45));
		card.setStyle(
			"-fx-background-color: rgba(255,255,255,0.06);" +
			"-fx-background-radius: 20;" +
			"-fx-border-color: rgba(255,255,255,0.12);" +
			"-fx-border-radius: 20;" +
			"-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.35), 25, 0, 0, 8);"
		);
		card.setMaxWidth(370);

		// Title
		Label title = new Label("Admin Login");
		title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
		title.setTextFill(Color.WHITE);

		Label desc = new Label("Authorized personnel only");
		desc.setFont(Font.font("Segoe UI", 12));
		desc.setTextFill(Color.web("#8892a6"));
		VBox titleBox = new VBox(4, title, desc);

		// Input Field Style (Dark)
		String fieldStyle =
			"-fx-background-color: rgba(255,255,255,0.08);" +
			"-fx-border-color: rgba(255,255,255,0.15);" +
			"-fx-border-radius: 10;" +
			"-fx-background-radius: 10;" +
			"-fx-padding: 12 16;" +
			"-fx-font-size: 14px;" +
			"-fx-text-fill: white;";

		String labelStyle =
			"-fx-font-size: 13px;" +
			"-fx-font-family: 'Segoe UI';" +
			"-fx-text-fill: #a0aab8;";

		Label lbname = new Label("Username");
		lbname.setStyle(labelStyle);
		TextField tfname = new TextField();
		tfname.setPromptText("Admin username");
		tfname.setStyle(fieldStyle);
		tfname.setPrefHeight(44);

		Label lbpassword = new Label("Password");
		lbpassword.setStyle(labelStyle);
		PasswordField pswd = new PasswordField();
		pswd.setPromptText("Admin password");
		pswd.setStyle(fieldStyle);
		pswd.setPrefHeight(44);

		// Buttons
		Button loginBtn = new Button("Sign In");
		loginBtn.setStyle(
			"-fx-background-color: linear-gradient(to right, #f093fb, #f5576c);" +
			"-fx-text-fill: white;" +
			"-fx-font-size: 15px;" +
			"-fx-font-weight: bold;" +
			"-fx-font-family: 'Segoe UI';" +
			"-fx-padding: 12 30;" +
			"-fx-background-radius: 50;" +
			"-fx-cursor: hand;"
		);
		loginBtn.setPrefWidth(160);

		Button clearBtn = new Button("Clear");
		clearBtn.setStyle(
			"-fx-background-color: rgba(255,255,255,0.1);" +
			"-fx-text-fill: #c0c8d8;" +
			"-fx-font-size: 14px;" +
			"-fx-font-family: 'Segoe UI';" +
			"-fx-padding: 12 22;" +
			"-fx-background-radius: 50;" +
			"-fx-cursor: hand;"
		);
		clearBtn.setPrefWidth(100);

		HBox btnRow = new HBox(12, clearBtn, loginBtn);
		btnRow.setAlignment(Pos.CENTER_LEFT);

		// Assemble
		card.getChildren().addAll(titleBox, lbname, tfname, lbpassword, pswd, btnRow);
		StackPane root = new StackPane(bg, card);
		StackPane.setMargin(card, new Insets(32));

		// Events (keep original logic)
		clearBtn.setOnAction(e -> {
			tfname.setText("");
			pswd.clear();
		});
		
		loginBtn.setOnAction(e -> {
			Manager manager = getmanager.getManager(tfname.getText(), pswd.getText());
		
			if (manager == null) {
				loginfail open = new loginfail();
				tfname.setText("");
				pswd.clear();

				try {
					open.start(new Stage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} else {
				boolean success = false;
				managerloginsuccess open = new managerloginsuccess();

				try {
					open.start(new Stage());
				success = true;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (success) { primaryStage.close(); }

			}
		});

		Scene scene = new Scene(root, 480, 560);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Admin Login - BUFF");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}
