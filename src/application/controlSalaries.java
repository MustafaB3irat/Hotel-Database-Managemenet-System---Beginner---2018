package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class controlSalaries {

	@FXML
	private ListView<String> list;

	@FXML
	public void clearInfo(ActionEvent event) {
		list.setItems(null);
	}

	@FXML
	public void showSalaries(ActionEvent a) throws SQLException {
		float sum = 0;
		ResultSet r = Main.db.findSalaries();
		ObservableList<String> l = FXCollections.observableArrayList();
		while (r.next()) {
			String s = "Employee name : " + r.getString(1) + ", Salary : " + r.getString(2);
			l.add(s);
			sum = sum + (float) Double.parseDouble(r.getString(2));
		}
		if (l.size() == 0)
			l.add("Their isn't any employees salaries");

		String ss = " Total Salaries =" + sum;
		l.add(ss);
		list.setItems(l);

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
