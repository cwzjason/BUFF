package text;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class managerinterface extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		GridPane pane = new GridPane();
		Button Userlist = new Button("User List");
		Button Exit = new Button("Exit");
		pane.add(Userlist, 0, 0);
		pane.add(Exit, 0, 1);

		Userlist.setOnAction(e -> {
			Userlist open = new Userlist();
			try {
				open.start(new Stage());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		Exit.setOnAction(e -> {
			primaryStage.close();

		});

		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
