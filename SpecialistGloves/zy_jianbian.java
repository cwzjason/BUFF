package SpecialistGloves;

import text.QuickBuyHelper;
import util.DBUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class zy_jianbian extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	Pane pane = new Pane();	
		pane.setStyle("-fx-background-color: #696969");
		
    	Button button = new Button("< BUFF");
    	button.setMinWidth(100);
    	button.setStyle("-fx-font: 16 SimHei; -fx-base: #696969;-fx-background-insets: 0;");
    	button.setLayoutX(0);
    	button.setLayoutY(10);
    	button.setOnAction(e -> {
    		boolean success = false;
				ZHUANYE open = new ZHUANYE();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (success) { primaryStage.close(); }
		});
    	
      	ImageView imageView = new ImageView("image/sport_fade2.jpg");
      	imageView.setFitHeight(300);
    	imageView.setFitWidth(300);
    	imageView.setX(0);
    	imageView.setY(40);
    	
    	Text text1 = new Text("Specialist Gloves | Fade");
    	text1.setFill(Color.WHITE);
    	text1.setFont(Font.font(null, FontWeight.BOLD,22));
    	text1.setLayoutX(0);
    	text1.setLayoutY(370);
    	
	   	TextArea description = new TextArea();
	   	description.setStyle("-fx-base: #000000; -fx-text-fill:#FFFFFF");
    	description.setMaxWidth(300);
    	description.setMaxHeight(100);
    	description.setText("Description:\r\nDurable, breathable, stylish; these gloves are specifically designed to withstand (and deliver) punishment.");
    	description.setLayoutX(0);
    	description.setLayoutY(400);
    	
    	Button button1 = new Button("Buy");
    	button1.setMinWidth(100);
    	button1.setStyle("-fx-font: 16 SimHei; -fx-base: #4169E1");
    	button1.setLayoutX(200);
    	button1.setLayoutY(510);
    	button1.setOnAction(e -> QuickBuyHelper.show(primaryStage, "SpecGloves-Fade", 1607));
    	Text text2 = new Text("Price:");

    	text2.setFill(Color.WHITE);
    	text2.setFont(Font.font(null, FontWeight.BOLD,20));
    	text2.setLayoutX(0);
    	text2.setLayoutY(533);
    	
    	Text text3 = new Text("$ 1670");
    	text3.setFill(Color.ORANGE);
    	text3.setFont(Font.font(null, FontWeight.BOLD,20));
    	text3.setLayoutX(50);
    	text3.setLayoutY(533);
    	
    	pane.getChildren().addAll(text1,text2,text3,imageView,button,button1,description);
        primaryStage.initStyle(StageStyle.UNDECORATED);

    	Scene scene = new Scene(pane,300,550);
       	primaryStage.setTitle("text");
    	primaryStage.setScene(scene);
    	primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

