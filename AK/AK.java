package AK;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class AK extends main{

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
        ImageView imageView1 = new ImageView("image/ak47_neon_rider1.jpg");
        imageView1.setFitHeight(90);
        imageView1.setFitWidth(140);
        imageView1.setX(0);
        imageView1.setY(60);
        imageView1.setOnMouseClicked(e -> {
        	boolean success = false;
				ak_nihong open = new ak_nihong();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name1 = new Text("AK-47 | Neon Rider");
    	name1.setFont(Font.font(null, FontWeight.BOLD,10));
    	name1.setLayoutX(0);
    	name1.setLayoutY(160);
    	Text price1 = new Text("$ 132");
    	price1.setFill(Color.ORANGE);
    	price1.setFont(Font.font(null, FontWeight.BOLD,10));
    	price1.setLayoutX(0);
    	price1.setLayoutY(170);
    	
    	ImageView imageView2 = new ImageView("image/ak47_asiimov1.jpg");
    	imageView2.setFitHeight(90);
    	imageView2.setFitWidth(140);
    	imageView2.setX(160);
    	imageView2.setY(60);
        imageView2.setOnMouseClicked(e -> {
        	boolean success = false;
				ak_erxi open = new ak_erxi();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name2 = new Text("AK-47 | Asiimov");
    	name2.setFont(Font.font(null, FontWeight.BOLD,10));
    	name2.setLayoutX(160);
    	name2.setLayoutY(160);
    	Text price2 = new Text("$ 210");
    	price2.setFill(Color.ORANGE);
    	price2.setFont(Font.font(null, FontWeight.BOLD,10));
    	price2.setLayoutX(160);
    	price2.setLayoutY(170);

        ImageView imageView3 = new ImageView("image/ak47_vulcan1.jpg");
        imageView3.setFitHeight(90);
        imageView3.setFitWidth(140);
        imageView3.setX(0);
        imageView3.setY(180);
        imageView3.setOnMouseClicked(e -> {
        	boolean success = false;
				ak_huoshen open = new ak_huoshen();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name3 = new Text("AK-47 | Vulcan");
    	name3.setFont(Font.font(null, FontWeight.BOLD,10));
    	name3.setLayoutX(0);
    	name3.setLayoutY(280);
    	Text price3 = new Text("$ 900");
    	price3.setFill(Color.ORANGE);
    	price3.setFont(Font.font(null, FontWeight.BOLD,10));
    	price3.setLayoutX(0);
    	price3.setLayoutY(290);
    	
      	ImageView imageView4 = new ImageView("image/ak47_fire_serpent1.jpg");
      	imageView4.setFitHeight(90);
      	imageView4.setFitWidth(140);
    	imageView4.setX(160);
    	imageView4.setY(180);
    	imageView4.setOnMouseClicked(e -> {
    		boolean success = false;
				ak_huoshe open = new ak_huoshe();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name4 = new Text("AK-47 | Fire Serpent");
    	name4.setFont(Font.font(null, FontWeight.BOLD,10));
    	name4.setLayoutX(160);
    	name4.setLayoutY(280);
    	Text price4 = new Text("$ 2859");
    	price4.setFill(Color.ORANGE);
    	price4.setFont(Font.font(null, FontWeight.BOLD,10));
    	price4.setLayoutX(160);
    	price4.setLayoutY(290);
    	
        ImageView imageView5 = new ImageView("image/ak47_empress1.jpg");
        imageView5.setFitHeight(90);
        imageView5.setFitWidth(140);
        imageView5.setX(0);
        imageView5.setY(300);
        imageView5.setOnMouseClicked(e -> {
        	boolean success = false;
				ak_huanghou open = new ak_huanghou();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name5 = new Text("AK-47 | Empress");
    	name5.setFont(Font.font(null, FontWeight.BOLD,10));
    	name5.setLayoutX(0);
    	name5.setLayoutY(400);
    	Text price5 = new Text("$ 141");
    	price5.setFill(Color.ORANGE);
    	price5.setFont(Font.font(null, FontWeight.BOLD,10));
    	price5.setLayoutX(0);
    	price5.setLayoutY(410);
    	
        ImageView imageView6 = new ImageView("image/ak47_dark_rock1.jpg");
        imageView6.setFitHeight(90);
        imageView6.setFitWidth(140);
        imageView6.setX(160);
        imageView6.setY(300);
        imageView6.setOnMouseClicked(e -> {
        	boolean success = false;
				ak_moyan open = new ak_moyan();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name6 = new Text("AK-47 | Dark Rock");
    	name6.setFont(Font.font(null, FontWeight.BOLD,10));
    	name6.setLayoutX(160);
    	name6.setLayoutY(400);
    	Text price6 = new Text("$ 9");
    	price6.setFill(Color.ORANGE);
    	price6.setFont(Font.font(null, FontWeight.BOLD,10));
    	price6.setLayoutX(160);
    	price6.setLayoutY(410);
    	
        ImageView imageView7 = new ImageView("image/ak47_combustible_ice1.jpg");
        imageView7.setFitHeight(90);
        imageView7.setFitWidth(140);
        imageView7.setX(0);
        imageView7.setY(420);
        imageView7.setOnMouseClicked(e -> {
        	boolean success = false;
				ak_keranbing open = new ak_keranbing();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name7 = new Text("AK-47 | Combustible Ice");
    	name7.setFont(Font.font(null, FontWeight.BOLD,10));
    	name7.setLayoutX(0);
    	name7.setLayoutY(520);
    	Text price7 = new Text("$ 37");
    	price7.setFill(Color.ORANGE);
    	price7.setFont(Font.font(null, FontWeight.BOLD,10));
    	price7.setLayoutX(0);
    	price7.setLayoutY(530);
    	
        ImageView imageView8 = new ImageView("image/ak47_one_shot_one_kill1.jpg");
        imageView8.setFitHeight(90);
        imageView8.setFitWidth(140);
        imageView8.setX(160);
        imageView8.setY(420);
        imageView8.setOnMouseClicked(e -> {
        	boolean success = false;
				ak_yifa open = new ak_yifa();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name8 = new Text("AK-47 | One Shot One Kill");
    	name8.setFont(Font.font(null, FontWeight.BOLD,10));
    	name8.setLayoutX(160);
    	name8.setLayoutY(520);
    	Text price8 = new Text("$ 267");
    	price8.setFill(Color.ORANGE);
    	price8.setFont(Font.font(null, FontWeight.BOLD,10));
    	price8.setLayoutX(160);
    	price8.setLayoutY(530);
    	
    	pane.getChildren().addAll(button,name1,imageView1,price1,name2,imageView2,price2,name3,imageView3,price3,name4,imageView4,price4,name5,imageView5,price5,name6,imageView6,price6,name7,imageView7,price7,name8,imageView8,price8);
    	
        primaryStage.setTitle("AK-47");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setWidth(300);
        primaryStage.setHeight(550);
        primaryStage.setScene(new Scene(sp));
        primaryStage.show();
	}

}
 

