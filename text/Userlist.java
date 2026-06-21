package text;

import util.DBUtil;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.security.auth.Refreshable;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Userlist extends Application {
	String url = "jdbc:mysql://127.0.0.1:3306/db1";
	String user = "root";
	String pwd = "123456";
	ResultSet rst = null;
	Connection cont = null;
	Statement ppst = null;

	public void start(Stage primaryStage) throws Exception {

		GridPane pane = new GridPane();
		TableView<User> table = new TableView();
		TableColumn Id = new TableColumn("id");
		TableColumn name = new TableColumn("name");
		TableColumn PassWord = new TableColumn("password");
		
	
		
		
		table.setOnMouseClicked(e->{
			
			if(e.getClickCount()==2 && (table.getSelectionModel().getSelectedItem().getId()==1)) {
				zhangdan open=new zhangdan();
				try {
					open.start(new Stage());
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
			}
			
		});

		Label id = new Label("ID");
		Label Name = new Label("name");
		Label Password = new Label("password");

		TextField idfield = new TextField();
		TextField namefield = new TextField();
		TextField passwordfield = new TextField();

		Button delete = new Button("Delete");
		Button add = new Button("Add");

		// Set table column widths
		Id.setMinWidth(100);
		name.setMinWidth(100);
		PassWord.setMinWidth(100);

		// Map data columns
		Id.setCellValueFactory(new PropertyValueFactory<>("id"));
		name.setCellValueFactory(new PropertyValueFactory<>("name"));
		PassWord.setCellValueFactory(new PropertyValueFactory<>("password"));
		table.getColumns().addAll(Id, name, PassWord);
		// Import data into table

		data(table, Id, name, PassWord);

		pane.add(delete, 2, 3);
		pane.add(add, 1, 3);
		pane.add(id, 0, 0);
		pane.add(idfield, 1, 0);
		pane.add(Name, 2, 0);
		pane.add(namefield, 3, 0);
		pane.add(Password, 4, 0);
		pane.add(passwordfield, 5, 0);
		pane.add(table, 3, 6);

		Scene scene = new Scene(pane, 800, 800);
		primaryStage.setScene(scene);
		primaryStage.show();

		delete.setOnAction(e -> {
			String url = "jdbc:mysql://127.0.0.1:3306/db1";
			String username = "root";
			String password = "123456";

			try {

				Connection conn = DBUtil.getConnection();
				String sql = "delete from userlogin where name=?";
				PreparedStatement ps = cont.prepareStatement(sql);
				ps.setString(1, table.getSelectionModel().getSelectedItem().getName());
				ps.executeUpdate();
				conn.close();

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			data(table, Id, name, PassWord);

		});

		add.setOnAction(e -> {
			String url = "jdbc:mysql://127.0.0.1:3306/db1";
			String username = "root";
			String password = "123456";
			try {

				Connection conn = DBUtil.getConnection();
				String sql = "insert into userlogin (id,name,password) values(?,?,?) ";
				PreparedStatement ps = cont.prepareStatement(sql);
				ps.setString(1, idfield.getText());
				ps.setString(2,namefield.getText());
				ps.setString(3, passwordfield.getText());

				idfield.setText("");
				namefield.setText("");
				passwordfield.setText("");
				
				
				ps.executeUpdate();
				conn.close();

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			data(table, Id, name, PassWord);

		});

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

	public void data(TableView table, TableColumn Id, TableColumn name, TableColumn password) {

		String sql = "SELECT id, name,password FROM userlogin";
		ObservableList data = FXCollections.observableArrayList();
		try {
			cont = DBUtil.getConnection();
			ppst = cont.createStatement();
			rst = ppst.executeQuery(sql);
			// System.out.print("Connected");
			while (rst.next()) {
				data.add(new User(rst.getInt(1), rst.getString(2), rst.getString(3)));

				table.setItems(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

}
