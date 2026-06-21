package M4A1;

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

public class M4A1 extends main{

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
				e1.printStackTrace();
			}
			if (success) { primaryStage.close(); }
		});
        ImageView imageView1 = new ImageView("image/m4a1_printstream1.jpg");
        imageView1.setFitHeight(90);
        imageView1.setFitWidth(140);
        imageView1.setX(0);
        imageView1.setY(60);
        imageView1.setOnMouseClicked(e -> {
        	boolean success = false;
				m4a1_yinhuaji open = new m4a1_yinhuaji();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (success) { primaryStage.close(); }
		});
    	Text name1 = new Text("M4A1-S | Printstream");
    	name1.setFont(Font.font(null, FontWeight.BOLD,10));
    	name1.setLayoutX(0);
    	name1.setLayoutY(160);
    	Text price1 = new Text("$ 432");
    	price1.setFill(Color.ORANGE);
    	price1.setFont(Font.font(null, FontWeight.BOLD,10));
    	price1.setLayoutX(0);
    	price1.setLayoutY(170);
    	
    	ImageView imageView2 = new ImageView("image/m4a1_nightmare1.jpg");
    	imageView2.setFitHeight(90);
    	imageView2.setFitWidth(140);
    	imageView2.setX(160);
    	imageView2.setY(60);
        imageView2.setOnMouseClicked(e -> {
        	boolean success = false;
				m4a1_mengyan open = new m4a1_mengyan();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (success) { primaryStage.close(); }
		});
    	Text name2 = new Text("M4A1-S | Nightmare");
    	name2.setFont(Font.font(null, FontWeight.BOLD,10));
    	name2.setLayoutX(160);
    	name2.setLayoutY(160);
    	Text price2 = new Text("$ 38");
    	price2.setFill(Color.ORANGE);
    	price2.setFont(Font.font(null, FontWeight.BOLD,10));
    	price2.setLayoutX(160);
    	price2.setLayoutY(170);

        ImageView imageView3 = new ImageView("image/m4a1_destroyer20001.jpg");
        imageView3.setFitHeight(90);
        imageView3.setFitWidth(140);
        imageView3.setX(0);
        imageView3.setY(180);
        imageView3.setOnMouseClicked(e -> {
        	boolean success = false;
				m4a1_huimiezhe open = new m4a1_huimiezhe();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (success) { primaryStage.close(); }
		});
    	Text name3 = new Text("M4A1-S | Destroyer 2000");
    	name3.setFont(Font.font(null, FontWeight.BOLD,10));
    	name3.setLayoutX(0);
    	name3.setLayoutY(280);
    	Text price3 = new Text("$ 27");
    	price3.setFill(Color.ORANGE);
    	price3.setFont(Font.font(null, FontWeight.BOLD,10));
    	price3.setLayoutX(0);
    	price3.setLayoutY(290);
    	
      	ImageView imageView4 = new ImageView("image/m4a1_golden_serpent1.jpg");
      	imageView4.setFitHeight(90);
      	imageView4.setFitWidth(140);
    	imageView4.setX(160);
    	imageView4.setY(180);
        imageView4.setOnMouseClicked(e -> {
        	boolean success = false;
				m4a1_jinshe open = new m4a1_jinshe();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (success) { primaryStage.close(); }
		});
    	Text name4 = new Text("M4A1-S | Golden Serpent");
    	name4.setFont(Font.font(null, FontWeight.BOLD,10));
    	name4.setLayoutX(160);
    	name4.setLayoutY(280);
    	Text price4 = new Text("$ 180");
    	price4.setFill(Color.ORANGE);
    	price4.setFont(Font.font(null, FontWeight.BOLD,10));
    	price4.setLayoutX(160);
    	price4.setLayoutY(290);
    	
    	pane.getChildren().addAll(button,name1,imageView1,price1,name2,imageView2,price2,name3,imageView3,price3,name4,imageView4,price4);
    	
        primaryStage.setTitle("Home");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setWidth(300);
        primaryStage.setHeight(550);
        primaryStage.setScene(new Scene(sp));
        primaryStage.show();
	}

}
 

