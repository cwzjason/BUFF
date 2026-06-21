package Karambit;

import java.util.HashMap;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import text.classification;
import text.main;

public class ZZD extends main{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		ScrollPane sp = new ScrollPane();
		sp.setStyle("-fx-padding: 0;\r\n" + 
				"	-fx-background-color: transparent;\r\n" + 
				"	-fx-border-color: transparent;\r\n" + 
				"	-fx-hbar-policy: never;\r\n" + 
				"	-fx-vbar-policy: never;\r\n" + 
				"");
        Pane pane = new Pane();
        pane.setPrefWidth(640);
        pane.setPrefHeight(700);
        sp.setContent(pane);
        sp.setVvalue(0);
        
    	Button button = new Button("< BUFF");
    	button.setMinWidth(100);
    	button.setStyle("-fx-font: 22 SimHei; -fx-base: #FFFFFF;-fx-background-insets: 0;");
    	button.setLayoutX(0);
    	button.setLayoutY(10);
    	button.setOnAction(e -> {
    		boolean success = false;
				main open = new main();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
        
        ImageView imageView1 = new ImageView("image/karambit_black_laminate1.png");
      	imageView1.setFitHeight(90);
    	imageView1.setFitWidth(140);
    	imageView1.setX(0);
    	imageView1.setY(60);
    	imageView1.setOnMouseClicked(e -> {
    		boolean success = false;
				zzd_heiya open = new zzd_heiya();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name1 = new Text("Karambit | Black Laminate");
    	name1.setFont(Font.font(null, FontWeight.BOLD,10));
    	name1.setLayoutX(0);
    	name1.setLayoutY(160);
    	Text price1 = new Text("$ 1370");
    	price1.setFill(Color.ORANGE);
    	price1.setFont(Font.font(null, FontWeight.BOLD,10));
    	price1.setLayoutX(0);
    	price1.setLayoutY(170);

    	
    	ImageView imageView2 = new ImageView("image/karambit_legend1.jpg");
    	imageView2.setFitHeight(90);
    	imageView2.setFitWidth(140);
      	imageView2.setX(160);
      	imageView2.setY(60);
      	imageView2.setOnMouseClicked(e -> {
    		boolean success = false;
				zzd_chuanshuo open = new zzd_chuanshuo();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name2 = new Text("Karambit | Legend");
    	name2.setFont(Font.font(null, FontWeight.BOLD,10));
    	name2.setLayoutX(160);
    	name2.setLayoutY(160);
    	Text price2 = new Text("$ 3065");
    	price2.setFill(Color.ORANGE);
    	price2.setFont(Font.font(null, FontWeight.BOLD,10));
    	price2.setLayoutX(160);
    	price2.setLayoutY(170);

        ImageView imageView3 = new ImageView("image/karambit_crimson_web1.jpg");
        imageView3.setFitHeight(90);
        imageView3.setFitWidth(140);
      	imageView3.setX(0);
    	imageView3.setY(180);
    	imageView3.setOnMouseClicked(e -> {
    		boolean success = false;
				zzd_hongwang open = new zzd_hongwang();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name3 = new Text("Karambit | Crimson Web");
    	name3.setFont(Font.font(null, FontWeight.BOLD,10));
    	name3.setLayoutX(0);
    	name3.setLayoutY(280);
    	Text price3 = new Text("$ 1439");
    	price3.setFill(Color.ORANGE);
    	price3.setFont(Font.font(null, FontWeight.BOLD,10));
    	price3.setLayoutX(0);
    	price3.setLayoutY(290);
    	
      	ImageView imageView4 = new ImageView("image/karambit_marble_fade1.jpg");
      	imageView4.setFitHeight(90);
      	imageView4.setFitWidth(140);
      	imageView4.setX(160);
      	imageView4.setY(180);
    	imageView4.setOnMouseClicked(e -> {
    		boolean success = false;
				zzd_dalishi open = new zzd_dalishi();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name4 = new Text("Karambit | Marble Fade");
    	name4.setFont(Font.font(null, FontWeight.BOLD,10));
    	name4.setLayoutX(160);
    	name4.setLayoutY(280);
    	Text price4 = new Text("$ 1777");
    	price4.setFill(Color.ORANGE);
    	price4.setFont(Font.font(null, FontWeight.BOLD,10));
    	price4.setLayoutX(160);
    	price4.setLayoutY(290);
    	
    	
      	ImageView imageView5 = new ImageView("image/karambit_autotronic1.jpg");
      	imageView5.setFitHeight(90);
      	imageView5.setFitWidth(140);
      	imageView5.setX(0);
      	imageView5.setY(300);
    	imageView5.setOnMouseClicked(e -> {
    		boolean success = false;
				zdd_zidong open = new zdd_zidong();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name5 = new Text("Karambit | Autotronic");
    	name5.setFont(Font.font(null, FontWeight.BOLD,10));
    	name5.setLayoutX(0);
    	name5.setLayoutY(400);
    	Text price5 = new Text("$ 2239");
    	price5.setFill(Color.ORANGE);
    	price5.setFont(Font.font(null, FontWeight.BOLD,10));
    	price5.setLayoutX(0);
    	price5.setLayoutY(410);
    	
    	
    	pane.getChildren().addAll(button,name1,imageView1,price1,name2,imageView2,price2,name3,imageView3,price3,name4,imageView4,price4,name5,imageView5,price5);
    	
        primaryStage.setTitle("Home");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setWidth(300);
        primaryStage.setHeight(550);
        primaryStage.setScene(new Scene(sp));
        primaryStage.show();
	}

}
 

