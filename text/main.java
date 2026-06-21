package text;

import AK.ak_yifa;
import AWP.awp_yongheng;
import BufferflyKnife.hdd_heiya;
import M4A1.m4a1_yinhuaji;
import SportGloves.yd_dangong;
import SportGloves.yd_maiami;
import javafx.application.Application;
import javafx.application.Platform;
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

public class main extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
launch(args);
	}

    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        ScrollPane sp = new ScrollPane();
        sp.setStyle("-fx-padding: 0;\r\n" +
                "   -fx-background-color: transparent;\r\n" +
                "   -fx-border-color: transparent;\r\n" +
                "   -fx-hbar-policy: never;\r\n" +
                "   -fx-vbar-policy: never;\r\n" +
                "");
        Pane pane = new Pane();
        pane.setPrefWidth(320);
        pane.setPrefHeight(660);
        sp.setContent(pane);
        sp.setVvalue(0);

        // ====== Exit Button (bottom-right corner) ======
        Button exitBtn = new Button("Exit");
        exitBtn.setTextFill(Color.WHITE);
        exitBtn.setStyle(
            "-fx-font: bold 12 'Segoe UI';" +
            "-fx-base: #B22222;" +
            "-fx-background-insets: 0;" +
            "-fx-background-radius: 4;" +
            "-fx-padding: 5 14;" +
            "-fx-cursor: hand;"
        );
        exitBtn.setLayoutX(248);
        exitBtn.setLayoutY(634);
        exitBtn.setOnAction(e -> Platform.exit());

        ImageView imageView00 = new ImageView("image/Hot_Topics.jpg");
        imageView00.setFitHeight(80);
        imageView00.setFitWidth(90);
        imageView00.setX(228);
        imageView00.setY(8);
   	
   	Button test1=new Button("Order History");
   	Button test=new Button("Shopping Cart");
   	test.setMinWidth(95);
   	test.setLayoutX(110);
   	test.setLayoutY(135);
   	test1.setMinWidth(95);
   	test1.setLayoutX(215);
   	test1.setLayoutY(135);
   	test1.setOnAction(e->{
   		boolean success = false;
				totallist open=new totallist();
   		try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
   		if (success) { primaryStage.close(); }
   	});
   	
   	test.setOnAction(e->{
   		boolean success = false;
				zhangdan open=new zhangdan();
   		try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
   		if (success) { primaryStage.close(); }
   	});
   	
   	
        Button button00 = new Button("Hot Topics");
    	button00.setTextFill(Color.WHITE);
    	button00.setMinWidth(85);
    	button00.setStyle("-fx-font: 14 SimHei; -fx-base: #B22222; -fx-background-insets: 0;");
    	button00.setLayoutX(228);
    	button00.setLayoutY(98);
    	button00.setOnAction(e -> {
    		boolean success = false;
				hot_topics open = new hot_topics();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (success) { primaryStage.close(); }
		});
    	
        ImageView imageView01 = new ImageView("image/Category.jpg");
        imageView01.setFitHeight(80);
        imageView01.setFitWidth(90);
        imageView01.setX(5);
        imageView01.setY(8);
    imageView01.setOnMouseClicked(e -> {
    	boolean success = false;
				classification open = new classification();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (success) { primaryStage.close(); }
		});
    	Button button01 = new Button("Filter");
    	button01.setTextFill(Color.WHITE);
    	button01.setMinWidth(95);
    	button01.setStyle("-fx-font: 14 SimHei; -fx-base: #20B2AA; -fx-background-insets: 0;");
    	button01.setLayoutX(5);
    	button01.setLayoutY(98);
    	button01.setOnAction(e -> {
    		boolean success = false;
				classification open = new classification();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (success) { primaryStage.close(); }
		});
    	
    	Button button02 = new Button("All Items");
    	button02.setStyle("-fx-font: 14 SimHei; -fx-base: #FFFFFF;-fx-background-insets: 0;");
    	button02.setMinWidth(95);
    	button02.setLayoutX(5);
    	button02.setLayoutY(135);
    	button02.setOnMouseClicked(e -> {
    		boolean success = false;
				total open = new total();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (success) { primaryStage.close(); }
		});

        ImageView imageView1 = new ImageView("image/specialist_miami_vice1.jpg");
        imageView1.setFitHeight(90);
        imageView1.setFitWidth(140);
        imageView1.setX(4);
        imageView1.setY(180);
        imageView1.setOnMouseClicked(e -> {
    	boolean success = false;
				yd_maiami open = new yd_maiami();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (success) { primaryStage.close(); }
		});
    	Text name1 = new Text("Sport Gloves | Vice");
    	name1.setWrappingWidth(140);
    	name1.setFont(Font.font(null, FontWeight.BOLD,10));
    	name1.setLayoutX(4);
    	name1.setLayoutY(278);
    	Text price1 = new Text("$ 5306");
    	price1.setFill(Color.ORANGE);
    	price1.setFont(Font.font(null, FontWeight.BOLD,10));
    	price1.setLayoutX(4);
    	price1.setLayoutY(308);
    	
    	ImageView imageView2 = new ImageView("image/awp_eternal_spear1.jpg");
    	imageView2.setFitHeight(90);
    	imageView2.setFitWidth(140);
    	imageView2.setX(164);
    	imageView2.setY(180);
        imageView2.setOnMouseClicked(e -> {
    	boolean success = false;
				awp_yongheng open = new awp_yongheng();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (success) { primaryStage.close(); }
		});
    	Text name2 = new Text("AWP | Dragon Lore");
    	name2.setWrappingWidth(140);
    	name2.setFont(Font.font(null, FontWeight.BOLD,10));
    	name2.setLayoutX(164);
    	name2.setLayoutY(278);
    	Text price2 = new Text("$ 12733");
    	price2.setFill(Color.ORANGE);
    	price2.setFont(Font.font(null, FontWeight.BOLD,10));
    	price2.setLayoutX(164);
    	price2.setLayoutY(308);

        ImageView imageView3 = new ImageView("image/ak47_one_shot_one_kill1.jpg");
        imageView3.setFitHeight(90);
        imageView3.setFitWidth(140);
        imageView3.setX(4);
        imageView3.setY(340);
        imageView3.setOnMouseClicked(e -> {
    	boolean success = false;
				ak_yifa open = new ak_yifa();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (success) { primaryStage.close(); }
		});
    	Text name3 = new Text("AK-47 | Redline");
    	name3.setWrappingWidth(140);
    	name3.setFont(Font.font(null, FontWeight.BOLD,10));
    	name3.setLayoutX(4);
    	name3.setLayoutY(438);
    	Text price3 = new Text("$ 267");
    	price3.setFill(Color.ORANGE);
    	price3.setFont(Font.font(null, FontWeight.BOLD,10));
    	price3.setLayoutX(4);
    	price3.setLayoutY(468);
    	
      	ImageView imageView4 = new ImageView("image/butterfly_black_laminate1.png");
      	imageView4.setFitHeight(90);
      	imageView4.setFitWidth(140);
    	imageView4.setX(164);
    	imageView4.setY(340);
        imageView4.setOnMouseClicked(e -> {
    	boolean success = false;
				hdd_heiya open = new hdd_heiya();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (success) { primaryStage.close(); }
		});
    	Text name4 = new Text("Butterfly Knife | Black Laminate");
    	name4.setWrappingWidth(140);
    	name4.setFont(Font.font(null, FontWeight.BOLD,10));
    	name4.setLayoutX(164);
    	name4.setLayoutY(438);
    	Text price4 = new Text("$ 1728");
    	price4.setFill(Color.ORANGE);
    	price4.setFont(Font.font(null, FontWeight.BOLD,10));
    	price4.setLayoutX(164);
    	price4.setLayoutY(468);
    	
        ImageView imageView5 = new ImageView("image/m4a1_printstream1.jpg");
        imageView5.setFitHeight(90);
        imageView5.setFitWidth(140);
        imageView5.setX(4);
        imageView5.setY(500);
        imageView5.setOnMouseClicked(e -> {
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
    	Text name5 = new Text("M4A1-S | Printstream");
    	name5.setWrappingWidth(140);
    	name5.setFont(Font.font(null, FontWeight.BOLD,10));
    	name5.setLayoutX(4);
    	name5.setLayoutY(598);
    	Text price5 = new Text("$ 432");
    	price5.setFill(Color.ORANGE);
    	price5.setFont(Font.font(null, FontWeight.BOLD,10));
    	price5.setLayoutX(4);
    	price5.setLayoutY(628);
    	
        ImageView imageView6 = new ImageView("image/specialist_slingshot1.jpg");
        imageView6.setFitHeight(90);
        imageView6.setFitWidth(140);
        imageView6.setX(164);
        imageView6.setY(500);
        imageView6.setOnMouseClicked(e -> {
    	boolean success = false;
				yd_dangong open = new yd_dangong();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (success) { primaryStage.close(); }
		});
    	Text name6 = new Text("Sport Gloves | Slingshot");
    	name6.setWrappingWidth(140);
    	name6.setFont(Font.font(null, FontWeight.BOLD,10));
    	name6.setLayoutX(164);
    	name6.setLayoutY(598);
    	Text price6 = new Text("$ 3358");
    	price6.setFill(Color.ORANGE);
    	price6.setFont(Font.font(null, FontWeight.BOLD,10));
    	price6.setLayoutX(164);
    	price6.setLayoutY(628);

    	
    	pane.getChildren().addAll(exitBtn, imageView00, imageView01, button00, button01, button02, name1, imageView1, price1, name2, imageView2, price2, name3, imageView3, price3, name4, imageView4, price4, name5, imageView5, price5, name6, imageView6, price6);
    	pane.getChildren().addAll(test, test1);
    	
    	
    	
    	primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Home");
        primaryStage.setWidth(320);
        primaryStage.setHeight(670);
        primaryStage.setScene(new Scene(sp));
        primaryStage.show();
	}

}
