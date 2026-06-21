package text;



import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Test extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Gradient Background
		javafx.scene.shape.Rectangle bg = new javafx.scene.shape.Rectangle(480, 580);
		bg.setFill(new javafx.scene.paint.LinearGradient(0, 0, 1, 1, true, null,
			new javafx.scene.paint.Stop(0, Color.web("#667eea")),
			new javafx.scene.paint.Stop(1, Color.web("#764ba2"))
		));

		// White Card
		VBox card = new VBox(18);
		card.setAlignment(Pos.CENTER_LEFT);
		card.setPadding(new Insets(40, 45, 35, 45));
		card.setStyle(
			"-fx-background-color: white;" +
			"-fx-background-radius: 20;" +
			"-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 25, 0, 0, 8);"
		);
		card.setMaxWidth(380);

		// Title
		Label title = new Label("User Login");
		title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));
		title.setTextFill(Color.web("#333"));

		Label desc = new Label("Sign in to your account");
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

		// Username Field
		Label lbname = new Label("Username");
		lbname.setStyle(labelStyle);
		TextField tfname = new TextField();
		tfname.setPromptText("Enter your username");
		tfname.setStyle(fieldStyle);
		tfname.setPrefHeight(44);

		// Password Field
		Label lbpassword = new Label("Password");
		lbpassword.setStyle(labelStyle);
		PasswordField pswd = new PasswordField();
		pswd.setPromptText("Enter your password");
		pswd.setStyle(fieldStyle);
		pswd.setPrefHeight(44);

		// Button Row
		Button loginBtn = new Button("Sign In");
		loginBtn.setStyle(
			"-fx-background-color: linear-gradient(to right, #667eea, #764ba2);" +
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
			"-fx-background-color: #eee;" +
			"-fx-text-fill: #555;" +
			"-fx-font-size: 14px;" +
			"-fx-font-family: 'Segoe UI';" +
			"-fx-padding: 12 20;" +
			"-fx-background-radius: 50;" +
			"-fx-cursor: hand;"
		);
		clearBtn.setPrefWidth(100);

		Button registerBtn = new Button("Register");
		registerBtn.setStyle(
			"-fx-background-color: transparent;" +
			"-fx-text-fill: #764ba2;" +
			"-fx-font-size: 13px;" +
			"-fx-font-family: 'Segoe UI';" +
			"-fx-underline: true;" +
			"-fx-cursor: hand;"
		);
		HBox btnRow = new HBox(12, clearBtn, loginBtn);
		btnRow.setAlignment(Pos.CENTER_LEFT);

		HBox registerRow = new HBox(registerBtn);
		registerRow.setAlignment(Pos.CENTER_RIGHT);

		// Assemble Card
		card.getChildren().addAll(titleBox, lbname, tfname, lbpassword, pswd, btnRow, registerRow);

		StackPane root = new StackPane(bg, card);
		StackPane.setMargin(card, new Insets(30));

		// Events (keep original logic)
		clearBtn.setOnAction(e -> {
			tfname.setText("");
			pswd.clear();
		});

		loginBtn.setOnAction(e -> {
			User user = getuser.getUser(tfname.getText(), pswd.getText());
		
			if (user == null) {
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
				loginsuccess open = new loginsuccess();

				try {
					open.start(new Stage());
				success = true;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (success) { primaryStage.close(); }

			}
		});
		
		registerBtn.setOnAction(e -> {
			boolean success = false;
				Register open = new Register();

			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (success) { primaryStage.close(); }

		});

		Scene scene = new Scene(root, 480, 580);
		primaryStage.setScene(scene);
		primaryStage.setTitle("User Login - BUFF");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}
