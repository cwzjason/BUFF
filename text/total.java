package text;

import AK.ak_erxi;
import AK.ak_keranbing;
import AK.ak_yifa;
import AWP.awp_jiutoujinshe;
import AWP.awp_yehuo;
import AWP.awp_yongheng;
import BufferflyKnife.HDD;
import BufferflyKnife.hdd_tufu;
import Karambit.zzd_dalishi;
import Karambit.zzd_hongwang;
import M4A1.m4a1_yinhuaji;
import M9Knife.m9_dalishi;
import M9Knife.m9_huya;
import M9Knife.m9_ziluolan;
import SpecialistGloves.zy_jianbian;
import SpecialistGloves.zy_tegong;
import SportGloves.yd_chaodaoti;
import SportGloves.yd_dangong;
import SportGloves.yd_maiami;
import SportGloves.yd_migong;
import SportGloves.yd_panduola;
import SportGloves.yd_shuangxi;
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

public class total extends main{

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
        pane.setPrefHeight(1150);
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
    	
        ImageView imageView1 = new ImageView("image/specialist_miami_vice1.jpg");
        imageView1.setFitHeight(90);
        imageView1.setFitWidth(140);
        imageView1.setX(0);
        imageView1.setY(60);
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
    	name1.setFont(Font.font(null, FontWeight.BOLD,10));
    	name1.setLayoutX(0);
    	name1.setLayoutY(160);
    	Text price1 = new Text("$ 5306");
    	price1.setFill(Color.ORANGE);
    	price1.setFont(Font.font(null, FontWeight.BOLD,10));
    	price1.setLayoutX(0);
    	price1.setLayoutY(170);
    	
    	ImageView imageView2 = new ImageView("image/awp_eternal_spear1.jpg");
    	imageView2.setFitHeight(90);
    	imageView2.setFitWidth(140);
    	imageView2.setX(160);
    	imageView2.setY(60);
        imageView2.setOnMouseClicked(e -> {
        	boolean success = false;
				awp_yongheng open = new awp_yongheng();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name2 = new Text("AWP | Dragon Lore");
    	name2.setFont(Font.font(null, FontWeight.BOLD,10));
    	name2.setLayoutX(160);
    	name2.setLayoutY(160);
    	Text price2 = new Text("$ 12733");
    	price2.setFill(Color.ORANGE);
    	price2.setFont(Font.font(null, FontWeight.BOLD,10));
    	price2.setLayoutX(160);
    	price2.setLayoutY(170);

        ImageView imageView3 = new ImageView("image/ak47_one_shot_one_kill1.jpg");
        imageView3.setFitHeight(90);
        imageView3.setFitWidth(140);
        imageView3.setX(0);
        imageView3.setY(180);
        imageView3.setOnMouseClicked(e -> {
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
    	Text name3 = new Text("AK-47 | Redline");
    	name3.setFont(Font.font(null, FontWeight.BOLD,10));
    	name3.setLayoutX(0);
    	name3.setLayoutY(280);
    	Text price3 = new Text("$ 267");
    	price3.setFill(Color.ORANGE);
    	price3.setFont(Font.font(null, FontWeight.BOLD,10));
    	price3.setLayoutX(0);
    	price3.setLayoutY(290);
    	
      	ImageView imageView4 = new ImageView("image/butterfly_black_laminate1.png");
      	imageView4.setFitHeight(90);
      	imageView4.setFitWidth(140);
    	imageView4.setX(160);
    	imageView4.setY(180);
    	imageView4.setOnMouseClicked(e -> {
    		boolean success = false;
				HDD open = new HDD();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name4 = new Text("Butterfly Knife | Black Laminate");
    	name4.setFont(Font.font(null, FontWeight.BOLD,10));
    	name4.setLayoutX(160);
    	name4.setLayoutY(280);
    	Text price4 = new Text("$ 1728");
    	price4.setFill(Color.ORANGE);
    	price4.setFont(Font.font(null, FontWeight.BOLD,10));
    	price4.setLayoutX(160);
    	price4.setLayoutY(290);
    	
        ImageView imageView5 = new ImageView("image/m4a1_printstream1.jpg");
        imageView5.setFitHeight(90);
        imageView5.setFitWidth(140);
        imageView5.setX(0);
        imageView5.setY(300);
        imageView5.setOnMouseClicked(e -> {
        	boolean success = false;
				m4a1_yinhuaji open = new m4a1_yinhuaji();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name5 = new Text("M4A1-S | Printstream");
    	name5.setFont(Font.font(null, FontWeight.BOLD,10));
    	name5.setLayoutX(0);
    	name5.setLayoutY(400);
    	Text price5 = new Text("$ 432");
    	price5.setFill(Color.ORANGE);
    	price5.setFont(Font.font(null, FontWeight.BOLD,10));
    	price5.setLayoutX(0);
    	price5.setLayoutY(410);
    	
        ImageView imageView6 = new ImageView("image/specialist_slingshot1.jpg");
        imageView6.setFitHeight(90);
        imageView6.setFitWidth(140);
        imageView6.setX(160);
        imageView6.setY(300);
        imageView6.setOnMouseClicked(e -> {
        	boolean success = false;
				yd_dangong open = new yd_dangong();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name6 = new Text("Sport Gloves | Slingshot");
    	name6.setFont(Font.font(null, FontWeight.BOLD,10));
    	name6.setLayoutX(160);
    	name6.setLayoutY(400);
    	Text price6 = new Text("$ 3358");
    	price6.setFill(Color.ORANGE);
    	price6.setFont(Font.font(null, FontWeight.BOLD,10));
    	price6.setLayoutX(160);
    	price6.setLayoutY(410);
    	
        ImageView imageView7 = new ImageView("image/m9_marble_fade1.jpg");
        imageView7.setFitHeight(90);
        imageView7.setFitWidth(140);
        imageView7.setX(0);
        imageView7.setY(420);
        imageView7.setOnMouseClicked(e -> {
        	boolean success = false;
				m9_dalishi open = new m9_dalishi();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name7 = new Text("M9 Bayonet | Marble Fade");
    	name7.setFont(Font.font(null, FontWeight.BOLD,10));
    	name7.setLayoutX(0);
    	name7.setLayoutY(520);
    	Text price7 = new Text("$ 1695");
    	price7.setFill(Color.ORANGE);
    	price7.setFont(Font.font(null, FontWeight.BOLD,10));
    	price7.setLayoutX(0);
    	price7.setLayoutY(530);
    	
        ImageView imageView8 = new ImageView("image/sport_fade1.jpg");
        imageView8.setFitHeight(90);
        imageView8.setFitWidth(140);
        imageView8.setX(160);
        imageView8.setY(420);
        imageView8.setOnMouseClicked(e -> {
        	boolean success = false;
				zy_jianbian open = new zy_jianbian();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name8 = new Text("Specialist Gloves | Fade");
    	name8.setFont(Font.font(null, FontWeight.BOLD,10));
    	name8.setLayoutX(160);
    	name8.setLayoutY(520);
    	Text price8 = new Text("$ 1670");
    	price8.setFill(Color.ORANGE);
    	price8.setFont(Font.font(null, FontWeight.BOLD,10));
    	price8.setLayoutX(160);
    	price8.setLayoutY(530);	
    	
        ImageView imageView9 = new ImageView("image/m9_violet1.jpg");
        imageView9.setFitHeight(90);
        imageView9.setFitWidth(140);
        imageView9.setX(0);
        imageView9.setY(540);
        imageView9.setOnMouseClicked(e -> {
        	boolean success = false;
				m9_ziluolan open = new m9_ziluolan();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name9 = new Text("M9 Bayonet | Ultraviolet");
    	name9.setFont(Font.font(null, FontWeight.BOLD,10));
    	name9.setLayoutX(0);
    	name9.setLayoutY(640);
    	Text price9 = new Text("$ 1412");
    	price9.setFill(Color.ORANGE);
    	price9.setFont(Font.font(null, FontWeight.BOLD,10));
    	price9.setLayoutX(0);
    	price9.setLayoutY(650);
    	
        ImageView imageView10 = new ImageView("image/specialist_hedge_maze1.jpg");
        imageView10.setFitHeight(90);
        imageView10.setFitWidth(140);
        imageView10.setX(160);
        imageView10.setY(540);
        imageView10.setOnMouseClicked(e -> {
        	boolean success = false;
				yd_migong open = new yd_migong();
			try {
				open.start(new Stage());
				success = true;
			} catch  (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name10 = new Text("Sport Gloves | Hedge Maze");
    	name10.setFont(Font.font(null, FontWeight.BOLD,10));
    	name10.setLayoutX(160);
    	name10.setLayoutY(640);
    	Text price10 = new Text("$ 8209");
    	price10.setFill(Color.ORANGE);
    	price10.setFont(Font.font(null, FontWeight.BOLD,10));
    	price10.setLayoutX(160);
    	price10.setLayoutY(650);
    	
        ImageView imageView11 = new ImageView("image/butterfly_slaughter1.jpg");
        imageView11.setFitHeight(90);
        imageView11.setFitWidth(140);
        imageView11.setX(0);
        imageView11.setY(660);
        imageView11.setOnMouseClicked(e -> {
        	boolean success = false;
				hdd_tufu open = new hdd_tufu();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name11 = new Text("Butterfly Knife | Slaughter");
    	name11.setFont(Font.font(null, FontWeight.BOLD,10));
    	name11.setLayoutX(0);
    	name11.setLayoutY(760);
    	Text price11 = new Text("$ 1765");
    	price11.setFill(Color.ORANGE);
    	price11.setFont(Font.font(null, FontWeight.BOLD,10));
    	price11.setLayoutX(0);
    	price11.setLayoutY(770);
    	
        ImageView imageView12 = new ImageView("image/ak47_combustible_ice1.jpg");
        imageView12.setFitHeight(90);
        imageView12.setFitWidth(140);
        imageView12.setX(160);
        imageView12.setY(660);
        imageView12.setOnMouseClicked(e -> {
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
    	Text name12 = new Text("AK-47 | Fuel Injector");
    	name12.setFont(Font.font(null, FontWeight.BOLD,10));
    	name12.setLayoutX(160);
    	name12.setLayoutY(760);
    	Text price12 = new Text("$ 9");
    	price12.setFill(Color.ORANGE);
    	price12.setFont(Font.font(null, FontWeight.BOLD,10));
    	price12.setLayoutX(160);
    	price12.setLayoutY(770);
    	
        ImageView imageView13 = new ImageView("image/awp_hydra1.jpg");
        imageView13.setFitHeight(90);
        imageView13.setFitWidth(140);
        imageView13.setX(0);
        imageView13.setY(780);
        imageView13.setOnMouseClicked(e -> {
        	boolean success = false;
				awp_jiutoujinshe open = new awp_jiutoujinshe();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name13 = new Text("AWP | Hydra");
    	name13.setFont(Font.font(null, FontWeight.BOLD,10));
    	name13.setLayoutX(0);
    	name13.setLayoutY(880);
    	Text price13 = new Text("$ 3354");
    	price13.setFill(Color.ORANGE);
    	price13.setFont(Font.font(null, FontWeight.BOLD,10));
    	price13.setLayoutX(0);
    	price13.setLayoutY(890);
    	
        ImageView imageView14 = new ImageView("image/m9_tiger_tooth1.jpg");
        imageView14.setFitHeight(90);
        imageView14.setFitWidth(140);
        imageView14.setX(160);
        imageView14.setY(780);
        imageView14.setOnMouseClicked(e -> {
        	boolean success = false;
				m9_huya open = new m9_huya();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name14 = new Text("M9 Bayonet | Tiger Tooth");
    	name14.setFont(Font.font(null, FontWeight.BOLD,10));
    	name14.setLayoutX(160);
    	name14.setLayoutY(880);
    	Text price14 = new Text("$ 5306");
    	price14.setFill(Color.ORANGE);
    	price14.setFont(Font.font(null, FontWeight.BOLD,10));
    	price14.setLayoutX(160);
    	price14.setLayoutY(890);
    	
    	ImageView imageView15 = new ImageView("image/sport_field_agent1.jpg");
    	imageView15.setFitHeight(90);
    	imageView15.setFitWidth(140);
        imageView15.setX(0);
        imageView15.setY(900);
        imageView15.setOnMouseClicked(e -> {
        	boolean success = false;
				zy_tegong open = new zy_tegong();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name15 = new Text("Specialist Gloves | Field Agent");
    	name15.setFont(Font.font(null, FontWeight.BOLD,10));
    	name15.setLayoutX(0);
    	name15.setLayoutY(1000);
    	Text price15 = new Text("$ 990");
    	price15.setFill(Color.ORANGE);
    	price15.setFont(Font.font(null, FontWeight.BOLD,10));
    	price15.setLayoutX(0);
    	price15.setLayoutY(1010);
    	
    	ImageView imageView16 = new ImageView("image/awp_wildfire1.jpg");
    	imageView16.setFitHeight(90);
    	imageView16.setFitWidth(140);
    	imageView16.setX(160);
    	imageView16.setY(900);
        imageView16.setOnMouseClicked(e -> {
        	boolean success = false;
				awp_yehuo open = new awp_yehuo();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Text name16 = new Text("AWP | Wildfire");
    	name16.setFont(Font.font(null, FontWeight.BOLD,10));
    	name16.setLayoutX(160);
    	name16.setLayoutY(1000);
    	Text price16 = new Text("$ 5306");
    	price16.setFill(Color.ORANGE);
    	price16.setFont(Font.font(null, FontWeight.BOLD,10));
    	price16.setLayoutX(160);
    	price16.setLayoutY(1010);
    	
        ImageView imageView17 = new ImageView("image/ak47_asiimov1.jpg");
        imageView17.setFitHeight(90);
        imageView17.setFitWidth(140);
        imageView17.setX(0);
        imageView17.setY(1020);
        imageView17.setOnMouseClicked(e -> {
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
    	Text name17 = new Text("AK-47 | Asiimov");
    	name17.setFont(Font.font(null, FontWeight.BOLD,10));
    	name17.setLayoutX(0);
    	name17.setLayoutY(1120);
    	Text price17 = new Text("$ 210");
    	price17.setFill(Color.ORANGE);
    	price17.setFont(Font.font(null, FontWeight.BOLD,10));
    	price17.setLayoutX(0);
    	price17.setLayoutY(1130);
    	
        ImageView imageView18 = new ImageView("image/karambit_crimson_web1.jpg");
        imageView18.setFitHeight(90);
        imageView18.setFitWidth(140);
        imageView18.setX(160);
        imageView18.setY(1020);
        imageView18.setOnMouseClicked(e -> {
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
    	Text name18 = new Text("Karambit | Crimson Web");
    	name18.setFont(Font.font(null, FontWeight.BOLD,10));
    	name18.setLayoutX(160);
    	name18.setLayoutY(1120);
    	Text price18 = new Text("$ 1439");
    	price18.setFill(Color.ORANGE);
    	price18.setFont(Font.font(null, FontWeight.BOLD,10));
    	price18.setLayoutX(160);
    	price18.setLayoutY(1130);

    	
    	pane.getChildren().addAll(button,name1,imageView1,name2,imageView2,price2,price1,name3,imageView3,price3,name4,imageView4,price4,name5,imageView5,price5,name6,imageView6,price6,name7,imageView7,price7,name8,imageView8,price8,name9,imageView9,price9,name10,imageView10,price10,name11,imageView11,price11,name12,imageView12,price12,name13,imageView13,price13,name14,imageView14,price14,name15,imageView15,price15,name16,imageView16,price16,name17,imageView17,price17,name18,imageView18,price18);
    	
        primaryStage.setTitle("Home");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setWidth(300);
        primaryStage.setHeight(550);
        primaryStage.setScene(new Scene(sp));     
        primaryStage.show();
	}

}
 
