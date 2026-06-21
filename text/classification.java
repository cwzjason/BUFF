package text;

import AK.AK;
import AWP.AWP;
import BufferflyKnife.HDD;
import Karambit.ZZD;
import M4A1.M4A1;
import M9Knife.M9;
import SpecialistGloves.ZHUANYE;
import SportGloves.YUNDONG;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class classification extends main{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Pane pane = new Pane();
		
		Text type = new Text("Category");
		type.setFont(Font.font(14));
		type.setLayoutX(10);
		type.setLayoutY(20);
		
    	Button button0 = new Button("All");
    	button0.setTextFill(Color.BLACK);
    	button0.setMinWidth(100);
    	button0.setStyle("-fx-font: 16 STXihei; -fx-base: #FFFFFF");
    	button0.setLayoutX(10);
    	button0.setLayoutY(40);
    	button0.setOnAction(e -> {
    		boolean success = false;
				total open = new total();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
		Text knife = new Text("Knife");
		knife.setFill(Color.DODGERBLUE);
		knife.setFont(Font.font(14));
		knife.setLayoutX(10);
		knife.setLayoutY(100);
		
    	Button hdd = new Button("Butterfly Knife");
    	hdd.setTextFill(Color.BLACK);
    	hdd.setMinWidth(100);
    	hdd.setStyle("-fx-font: 16 STXihei; -fx-base: #FFFFFF");
    	hdd.setLayoutX(10);
    	hdd.setLayoutY(120);
    	hdd.setOnAction(e -> {
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
    	Button M9 = new Button("M9 Bayonet");
    	M9.setTextFill(Color.BLACK);
    	M9.setMinWidth(100);
    	M9.setStyle("-fx-font: 16 STXihei; -fx-base: #FFFFFF");
    	M9.setLayoutX(140);
    	M9.setLayoutY(120);
    	M9.setOnAction(e -> {
    		boolean success = false;
				M9 open = new M9();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	
    	Button zzd = new Button("Karambit");
    	zzd.setTextFill(Color.BLACK);
    	zzd.setMinWidth(100);
    	zzd.setStyle("-fx-font: 16 STXihei; -fx-base: #FFFFFF");
    	zzd.setLayoutX(10);
    	zzd.setLayoutY(160);
    	zzd.setOnAction(e -> {
    		boolean success = false;
				ZZD open = new ZZD();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	
		Text gun = new Text("Ranged Weapons");
		gun.setFill(Color.DODGERBLUE);
		gun.setFont(Font.font(14));
		gun.setLayoutX(10);
		gun.setLayoutY(220);
		
    	Button ak = new Button("AK-47");
    	ak.setTextFill(Color.BLACK);
    	ak.setMinWidth(100);
    	ak.setStyle("-fx-font: 16 STXihei; -fx-base: #FFFFFF");
    	ak.setLayoutX(10);
    	ak.setLayoutY(240);
    	ak.setOnAction(e -> {
    		boolean success = false;
				AK open = new AK();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Button M4A1 = new Button("M4A1");
    	M4A1.setTextFill(Color.BLACK);
    	M4A1.setMinWidth(100);
    	M4A1.setStyle("-fx-font: 16 STXihei; -fx-base: #FFFFFF");
    	M4A1.setLayoutX(140);
    	M4A1.setLayoutY(240);
    	M4A1.setOnAction(e -> {
    		boolean success = false;
				M4A1 open = new M4A1();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	Button AWP = new Button("AWP");
    	AWP.setTextFill(Color.BLACK);
    	AWP.setMinWidth(100);
    	AWP.setStyle("-fx-font: 16 STXihei; -fx-base: #FFFFFF");
    	AWP.setLayoutX(10);
    	AWP.setLayoutY(280);
    	AWP.setOnAction(e -> {
    		boolean success = false;
				AWP open = new AWP();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	
    	
		Text glove = new Text("Gloves");
		glove.setFill(Color.DODGERBLUE);
		glove.setFont(Font.font(14));
		glove.setLayoutX(10);
		glove.setLayoutY(340);
		
    	Button glove1 = new Button("Sport Gloves");
    	glove1.setTextFill(Color.BLACK);
    	glove1.setMinWidth(100);
    	glove1.setStyle("-fx-font: 16 STXihei; -fx-base: #FFFFFF");
    	glove1.setLayoutX(10);
    	glove1.setLayoutY(360);
    	glove1.setOnAction(e -> {
    		boolean success = false;
				YUNDONG open = new YUNDONG();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	
    	Button glove2 = new Button("Specialist Gloves");
    	glove2.setTextFill(Color.BLACK);
    	glove2.setMinWidth(100);
    	glove2.setStyle("-fx-font: 16 STXihei; -fx-base: #FFFFFF");
    	glove2.setLayoutX(10);
    	glove2.setLayoutY(400);
    	glove2.setOnAction(e -> {
    		boolean success = false;
				ZHUANYE open = new ZHUANYE();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			if (success) { primaryStage.close(); }

		});
    	
    	Button rt = new Button("Back");
    	rt.setTextFill(Color.WHITE);
    	rt.setMinWidth(100);
    	rt.setStyle("-fx-font: 16 STXihei; -fx-base: #00BFFF");
    	rt.setLayoutX(80);
    	rt.setLayoutY(460);
    	rt.setOnAction(e -> {
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
    	
    	pane.getChildren().addAll(type,button0,knife,hdd,M9,zzd,gun,ak,M4A1,AWP,glove,glove1,glove2,rt);
        primaryStage.initStyle(StageStyle.UNDECORATED);
    	Scene scene = new Scene(pane,260,500);
       	primaryStage.setTitle("text");
    	primaryStage.setScene(scene);
    	primaryStage.show();
	}

}
