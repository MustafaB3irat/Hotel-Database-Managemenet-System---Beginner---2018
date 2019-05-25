package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class controlTotalincome {

	@FXML
	private DatePicker from;

	@FXML
	private DatePicker to;

	@FXML
	private ListView<String> list;

	@FXML
	public void clearInfo(ActionEvent event) {
		list.setItems(null);
		from.setValue(null);
		to.setValue(null);

	}

	@FXML
	public void showTotalIncome(ActionEvent a) throws SQLException {
		float sum = 0;
		if (from.getValue() == null || to.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error !");
			alert.setHeaderText("You must specify all input fields");

			alert.showAndWait();
		} else {
			ResultSet r = Main.db.findTotalIncome(from.getValue(), to.getValue());
			ObservableList<String> l = FXCollections.observableArrayList();
			while (r.next()) {
				sum = sum + (float) Double.parseDouble(r.getString(3));
				String s = "ID: " + r.getString(1) + ", amount= " + r.getString(3);
				l.add(s);
			}
			if (l.size() == 0)
				l.add("Their isn't any bills");
			String ss = " Total Salaries =" + sum;
			l.add(ss);
			list.setItems(l);
		}

	}

	public void moveToPerson(ActionEvent event) throws IOException {
		Main.setParent("personManeger", 500, 600, "Person ");
	}

	public void moveToMainMenu(MouseEvent event) throws IOException {
		Main.setParent("ManagerMenu", 700, 600, "Manager Menu ");
	}

	public void moveToBill(ActionEvent event) throws IOException {
		Main.setParent("Bill", 700, 600, "Pay Bill");
	}

	public void moveToRoom(ActionEvent event) throws IOException {
		Main.setParent("RoomMan", 700, 600, "Room");
	}

	public void moveToAvailableRooms(ActionEvent event) throws IOException {
		Main.setParent("AvailableRooms", 700, 600, "Available Rooms ");
	}

	public void moveToIncome(ActionEvent event) throws IOException {
		Main.setParent("totalincome", 700, 600, "Total Income");
	}

	public void moveToSalary(ActionEvent event) throws IOException {
		Main.setParent("salaries", 700, 600, "Salaries");
	}
}
