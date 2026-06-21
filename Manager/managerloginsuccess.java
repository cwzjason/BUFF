package Manager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import text.managerinterface;

public class managerloginsuccess extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Button button=new Button("Confirm");
		GridPane pane=new GridPane();
		Label label=new Label("Login Successful");
		pane.add(label, 5, 0);
		pane.add(button, 10, 10);
		
		
		button.setOnAction(e->{
			boolean success = false;
				managerinterface open=new managerinterface();
			try {
				open.start(new Stage());
				success = true;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (success) { primaryStage.close(); }
		});
		
		
		
		Scene scene=new Scene(pane,200,200);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
