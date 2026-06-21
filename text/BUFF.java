package text;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BUFF extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// ====== Pure Black Background ======
		Pane root = new Pane();
		root.setPrefSize(320, 670);
		root.setStyle("-fx-background-color: #000000;");

		// ====== Top Bar: BUFF logo + tagline ======
		Pane topBar = new Pane();
		topBar.setPrefSize(320, 50);
		topBar.setStyle("-fx-background-color: #0a0a0a;"
				+ "-fx-border-color: #1a1a1a; -fx-border-width: 0 0 1 0;");

		// BUFF logo - bold, mimicking real BUFF
		Text logo = new Text("BUFF");
		logo.setFill(Color.WHITE);
		logo.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 18));
		logo.setLayoutX(10);
		logo.setLayoutY(32);

		// Tagline
		topBar.getChildren().addAll(logo);

		// ====== Center Content ======
		VBox centerBox = new VBox(18);
		centerBox.setAlignment(Pos.CENTER);
		centerBox.setLayoutX(0);
		centerBox.setLayoutY(80);
		centerBox.setPrefWidth(320);
		centerBox.setPrefHeight(480);

		// Main title
		Text mainTitle = new Text("BUFF");
		mainTitle.setFill(Color.WHITE);
		mainTitle.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 32));

		// Subtitle
		Text subTitle = new Text("Global CS:GO Skin Trading\nPlatform");
		subTitle.setFill(Color.web("#666666"));
		subTitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));

		// Divider
		Separator line = new Separator();
		line.setMaxWidth(120);
		line.setStyle("-fx-border-color: rgba(255,255,255,0.2);");

		VBox titleGroup = new VBox(6, mainTitle, subTitle);
		titleGroup.setAlignment(Pos.CENTER);

		// ====== Button Group ======
		String btnPrimary =
			"-fx-font: bold 14 'Segoe UI';"
			+ "-fx-text-fill: #000000;"
			+ "-fx-background-color: #ffd700;"
			+ "-fx-background-radius: 4;"
			+ "-fx-padding: 12 50;"
			+ "-fx-cursor: hand;";

		String btnSecondary =
			"-fx-font: bold 12 'Segoe UI';"
			+ "-fx-text-fill: #aaaaaa;"
			+ "-fx-background-color: #111111;"
			+ "-fx-background-radius: 4;"
			+ "-fx-border-color: #333333;"
			+ "-fx-border-radius: 4;"
			+ "-fx-border-width: 1;"
			+ "-fx-padding: 10 40;"
			+ "-fx-cursor: hand;";

		// Login button (gold, prominent)
		Button loginBtn = new Button("Login");
		loginBtn.setStyle(btnPrimary);
		loginBtn.setMinWidth(200);

		// hover effect
		loginBtn.setOnMouseEntered(e ->
			loginBtn.setStyle(btnPrimary
				+ "-fx-background-color: #ffed4a;"));
		loginBtn.setOnMouseExited(e ->
			loginBtn.setStyle(btnPrimary));

		VBox btnGroup = new VBox(12, loginBtn);
		btnGroup.setAlignment(Pos.CENTER);

		// ====== Footer text ======
		Text footer = new Text("v1.0 - 24/7 Online Trading\nSecure & Reliable");
		footer.setFill(Color.web("#444444"));
		footer.setFont(Font.font("Segoe UI", 10));

		centerBox.getChildren().addAll(titleGroup, line, btnGroup, footer);

		// ====== Bottom Bar ======
		Pane bottomBar = new Pane();
		bottomBar.setPrefSize(320, 36);
		bottomBar.setLayoutY(634);
		bottomBar.setStyle("-fx-background-color: #0a0a0a;"
				+ "-fx-border-color: #1a1a1a; -fx-border-width: 1 0 0 0;");

		Text copyright = new Text("BUFF Market \u00a9 2026");
		copyright.setFill(Color.web("#444444"));
		copyright.setFont(Font.font("Segoe UI", 9));
		copyright.setLayoutX(10);
		copyright.setLayoutY(18);

		bottomBar.getChildren().add(copyright);

		// ====== Button Events ======
		loginBtn.setOnAction(e -> {
				Test open = new Test();
			try {
				open.start(new Stage());
				primaryStage.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		// ====== Assembly ======
		root.getChildren().addAll(topBar, centerBox, bottomBar);

		Scene scene = new Scene(root, 320, 670);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("BUFF");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
