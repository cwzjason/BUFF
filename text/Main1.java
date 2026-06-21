package text;

import util.DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main1 extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
	

		Stage s1 = new Stage();
		s1.setTitle("BUFF");
		s1.getIcons().add(new Image("image/Buff.png"));

		AnchorPane pane = new AnchorPane();

		pane.setStyle("-fx-background-color:#130c0e;");
		Scene scene = new Scene(pane);
		scene.setCursor(Cursor.CROSSHAIR);
		s1.setScene(scene);
		s1.setHeight(670);
		s1.setWidth(320);
		s1.initStyle(StageStyle.UNDECORATED);

		Group group = new Group();

		Label label = new Label("BUFF");
		label.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 60));
		label.setTextFill(Color.WHITESMOKE);
		label.setLayoutX(100);
		label.setLayoutY(80);

		BorderStroke bos1 = new BorderStroke(Paint.valueOf("#7bbfea"), BorderStrokeStyle.SOLID, new CornerRadii(10),
				new BorderWidths(3));
		Border bo1 = new Border(bos1);
		BorderStroke bos2 = new BorderStroke(Paint.valueOf("#ed1941"), BorderStrokeStyle.SOLID, new CornerRadii(10),
				new BorderWidths(3));
		Border bo2 = new Border(bos2);

		BackgroundFill bgf = new BackgroundFill(Paint.valueOf("#fffffb"), new CornerRadii(10), new Insets(1));
		Background bg1 = new Background(bgf);

		Button clear = new Button("Clear");
		clear.setCursor(Cursor.HAND);
		clear.setFont(Font.font(8));
		clear.setPrefWidth(40);
		clear.setPrefHeight(20);
		clear.setBackground(bg1);
		clear.setBorder(bo1);

		Button login = new Button("Login");
		login.setCursor(Cursor.HAND);
		login.setFont(Font.font(8));
		login.setPrefWidth(40);
		login.setPrefHeight(25);
		login.setBackground(bg1);
		login.setBorder(bo1);
		Button register = new Button("Register");

		register.setCursor(Cursor.HAND);
		register.setFont(Font.font(8));
		register.setPrefWidth(50);
		register.setPrefHeight(20);
		register.setBackground(bg1);
		register.setBorder(bo1);

		Label username = new Label("Username:");
		username.setFont(new Font(15));
		username.setTextFill(Color.WHITESMOKE);
		Label password = new Label("  Password:");
		password.setTextFill(Color.WHITESMOKE);
		password.setFont(new Font(15));

		TextField tfname = new TextField();

		PasswordField pswd = new PasswordField();

		GridPane pane2 = new GridPane();

		pane2.setStyle("-fx-background-color: #130c0e;" + "-fx-background-radius:10");
		pane2.setLayoutX(10);
		pane2.setLayoutY(280);
		pane2.setPrefWidth(300);
		pane2.setPrefHeight(150);

		pane2.add(username, 0, 0);
		pane2.add(password, 0, 1);
		pane2.add(tfname, 1, 0);
		pane2.add(pswd, 1, 1);
		pane2.add(clear, 2, 0);
		pane2.add(login, 1, 2);
		pane2.add(register, 2, 2);

	

		group.getChildren().add(label);
		group.getChildren().add(pane2);
		pane.getChildren().addAll(group);

		clear.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				tfname.clear();
				pswd.clear();
			}

		});



		login.setOnAction(e -> {
			User user = getuser.getUser(tfname.getText(), pswd.getText());
		
			if (user == null) {
				loginfail open = new loginfail();
				tfname.setText("");
				pswd.clear();

				try {
					open.start(new Stage());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				loginsuccess open = new loginsuccess();

				try {
					open.start(new Stage());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				s1.close();

			}
		});

		register.setOnAction(e -> {
			try {
				String url = "jdbc:mysql://127.0.0.1:3306/db1" + "";
				String username1 = "root";
				String password1 = "123456";
				Connection conn = DBUtil.getConnection();
				String sql = "insert into userlogin(id,name,password) values(null,?,?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, tfname.getText());
				ps.setString(2, pswd.getText());

				ps.executeUpdate();
				conn.close();

			} catch (Exception e1) {
				// TODO: handle exception
				e1.printStackTrace();
			}
			if (tfname.getText().equals("")) {
				error("Username cannot be empty!");
				
				return;
			}
			if (pswd.getText().equals("")) {
				error("Password cannot be empty!");
				return;
			}
			registersuccess open = new registersuccess();
			try {
				
				open.start(new Stage());
				tfname.setText("");
				pswd.clear();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
s1.close();

			
			

			

		});

		s1.show();
	}
	public void error(String error) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Notice");
		alert.contentTextProperty().set(error);
		alert.showAndWait();
	}


}
