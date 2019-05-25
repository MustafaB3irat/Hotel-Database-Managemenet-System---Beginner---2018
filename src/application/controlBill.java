package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class controlBill {

	@FXML
	private TextField bId, amount;

	@FXML
	private DatePicker date;

	@FXML
	public void clearInfo(ActionEvent event) {
		bId.setText("");
		date.setValue(null);
		amount.setText("");
	}

	@FXML
	public void payApill(ActionEvent a) throws SQLException {
		ResultSet r = Main.db.findBill(bId.getText());
		if (r.first()) {
			if (r.getString(5) == null || r.getString(5).equals(""))
				Main.db.payBill(bId.getText(), date.getValue(), amount.getText());
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error !");
				alert.setHeaderText("The bill is already paid");

				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error !");
			alert.setHeaderText("No such bill !");

			alert.showAndWait();
		}
	}

	@FXML
	public void handleBidChange(KeyEvent a) {
		try {

			ResultSet r = Main.db.findBill(bId.getText());
			r.first();
			amount.setText(r.getString(3));
			LocalDate dt = LocalDate.parse(r.getString(2));
			date.setValue(dt);

		} catch (SQLException e) {
			amount.setText(null);
			date.setValue(null);
		}
	}

	public void moveToAvailableRooms(ActionEvent event) throws IOException {
		Main.setParent("AvailableRooms", 700, 600, "Available Rooms ");
	}

	public void moveToClient(ActionEvent event) throws IOException {
		Main.setParent("clientEdit", 610, 630, "Client ");
	}

	public void moveToMainMenu(MouseEvent event) throws IOException {
		Main.setParent("recMenu", 700, 600, "Receptionist Menu ");
	}

	public void moveToBill(ActionEvent event) throws IOException {
		Main.setParent("Bill", 700, 600, "Pay Bill");
	}

	public void moveToRoom(ActionEvent event) throws IOException {
		Main.setParent("Room", 700, 600, "Room");
	}

	public void moveToReserve(ActionEvent event) throws IOException {
		Main.setParent("reserve", 700, 600, "Reserve");
	}
}
