package application;

import java.io.IOException;
import java.sql.SQLException;

import databaseAPI.DBHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	private static Stage primaryStage;
	public static int back;
	static String prev = "Main";
	public static DBHandler db;

	@Override
	public void start(Stage primaryStage) throws IOException {
		Main.primaryStage = primaryStage;
		showMain();
	}

	public static void main(String[] args) throws SQLException {
		db = new DBHandler();

		launch(args);
	}
	
	
	

	public static void setParent(String fxml, int h, int w, String title) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/application/" + fxml + ".fxml"));
		Pane pane = loader.load();
		Scene scene = new Scene(pane, h, w);
		primaryStage.setTitle(title);
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);

		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
		primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);

		primaryStage.show();

		if (fxml.compareTo("AvailRoomsClient") != 0 && fxml.compareTo("insertClient") != 0) {
			prev = fxml;

			switchMenu(prev);

		}

		if (fxml.compareTo("clientEdit") != 0 && fxml.compareTo("insertClient") != 0) {
			prev = fxml;
			switchMenu(prev);

		}

	}

	public static void showMain() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/application/Main.fxml"));
		Pane pane = loader.load();
		Scene scene = new Scene(pane, 640, 400);
		primaryStage.setResizable(false);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("Royal Hotel!");
		primaryStage.setScene(scene);

		primaryStage.show();
		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
		primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
	}

	public static void hide() throws IOException {
		primaryStage.hide();
	}

	public static int switchMenu(String s) {

		if (prev.compareTo("AvailableRooms") == 0)
			back = 1;
		else if (prev.compareTo("ReceptioniestMenu") == 0 || prev.compareTo("Bill") == 0 || prev.compareTo("reserve") == 0
				|| prev.compareTo("Room") == 0)
			back = 2;
		else if (prev.compareTo("employeesMenu") == 0 || prev.compareTo("RoomEmp") == 0)
			back = 3;
		else if (prev.compareTo("Manager") == 0 || prev.compareTo("personManager") == 0
				|| prev.compareTo("RoomMan") == 0 || prev.compareTo("totalincome") == 0
				|| prev.compareTo("salaries") == 0)
			back = 4;

		return back;
	}

}
