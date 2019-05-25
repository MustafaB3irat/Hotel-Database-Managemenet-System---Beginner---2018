package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControlEditRooms {

	@FXML
	private JFXTextField insertRoomNum, insertRoomClass, insertRoomFloor, insertRoomCost, insertRoomSize,
			insertRoomBeds;

	@FXML
	public void insertRoom(ActionEvent event) {

		try {

			if (insertRoomBeds.getText().equals("") || insertRoomClass.getText().equals("")
					|| insertRoomCost.getText().equals("")
					|| insertRoomNum.getText().equals("") | insertRoomSize.getText().equals("")
					|| insertRoomFloor.getText().equals(""))
				throw new IOException();

			Main.db.insertRoom(insertRoomBeds.getText(), insertRoomCost.getText(), insertRoomSize.getText(),
					insertRoomFloor.getText(), insertRoomClass.getText(), "Available", insertRoomNum.getText());

			clearInsert();

			showSuccess("Added Successfully!");

		} catch (SQLIntegrityConstraintViolationException e) {
			showError("Already Exsists!");
		}

		catch (SQLException s) {

			showError("Error In Database");
		}

		catch (IOException ex) {

			showError("Please Fill in Data...");

		}
	}

	private void clearInsert() {

		insertRoomBeds.setText("");
		insertRoomClass.setText("");
		insertRoomCost.setText("");
		insertRoomFloor.setText("");
		insertRoomNum.setText("");
		insertRoomSize.setText("");
	}

	// ----------------------------------------

	@FXML
	private JFXTextField updateRoomNum, updateRoomClass, updateRoomFloor, updateRoomCost, updateRoomSize,
			updateRoomBeds;

	@FXML
	private JFXRadioButton A, U, R;

	@FXML
	public void getUpdateResult(ActionEvent e) {

		try {

			if (!updateRoomNum.getText().equals("")) {

				ResultSet r = Main.db.findRoom(updateRoomNum.getText());

				if (r.next()) {

					updateRoomBeds.setText(r.getString(2));
					updateRoomClass.setText(r.getString(6));
					updateRoomCost.setText(r.getString(3));
					updateRoomFloor.setText(r.getString(5));
					updateRoomSize.setText(r.getString(4));

					if (r.getString(7).equalsIgnoreCase("Available"))
						A.setSelected(true);

					else if (r.getString(7).equalsIgnoreCase("Reserved"))
						R.setSelected(true);

					else if (r.getString(7).equalsIgnoreCase("Under Maintenance"))
						U.setSelected(true);

				}

				else
					clearUpdate();
			}

			else
				clearUpdate();

		} catch (SQLException x) {

			showError("Not Found!");
		}
	}

	@FXML
	public void updateRoom(ActionEvent event) {

		try {

			if (updateRoomBeds.getText().equals("") || updateRoomClass.getText().equals("")
					|| updateRoomCost.getText().equals("")
					|| updateRoomNum.getText().equals("") | updateRoomSize.getText().equals("")
					|| updateRoomFloor.getText().equals(""))
				throw new Exception();

			if (A.isSelected() == true)

				Main.db.updateRoom(updateRoomBeds.getText(), updateRoomCost.getText(), updateRoomSize.getText(),
						updateRoomFloor.getText(), updateRoomClass.getText(), "Available", updateRoomNum.getText());

			else if (R.isSelected() == true)

				Main.db.updateRoom(updateRoomBeds.getText(), updateRoomCost.getText(), updateRoomSize.getText(),
						updateRoomFloor.getText(), updateRoomClass.getText(), "Reserved", updateRoomNum.getText());

			else if (U.isSelected() == true)

				Main.db.updateRoom(updateRoomBeds.getText(), updateRoomCost.getText(), updateRoomSize.getText(),
						updateRoomFloor.getText(), updateRoomClass.getText(), "Under Maintenance",
						updateRoomNum.getText());

			showSuccess("Successfully Updated!");

			clearUpdate();

		} catch (SQLException e) {

			showError("Error Try again! | Maybe Repeated Value");
		}

		catch (Exception ex) {

			showError("Please Fill in Data..");
		}
	}

	private void clearUpdate() {

		updateRoomBeds.setText("");
		updateRoomClass.setText("");
		updateRoomCost.setText("");
		updateRoomFloor.setText("");
		updateRoomSize.setText("");
		updateRoomNum.setText("");
		R.setSelected(false);
		A.setSelected(false);
		U.setSelected(false);

	}

	// --------------------Delete Rooms

	@FXML
	private JFXTextField deleteRoomNum, deleteRoomClass, deleteRoomFloor, deleteRoomCost, deleteRoomSize,
			deleteRoomBeds;

	@FXML
	public void getdeleteResult(ActionEvent e) {

		try {

			if (!deleteRoomNum.getText().equals("")) {

				ResultSet r = Main.db.findRoom(deleteRoomNum.getText());

				if (r.next()) {

					deleteRoomBeds.setText(r.getString(2));
					deleteRoomClass.setText(r.getString(6));
					deleteRoomCost.setText(r.getString(3));
					deleteRoomFloor.setText(r.getString(5));
					deleteRoomSize.setText(r.getString(4));

				}

				else
					clearDelete();
			}

			else
				clearDelete();

		} catch (SQLException x) {

			showError("Not Found!");
		}
	}

	@FXML
	public void deleteRoom(ActionEvent event) {

		try {

			if (deleteRoomBeds.getText().equals("") || deleteRoomClass.getText().equals("")
					|| deleteRoomCost.getText().equals("")
					|| deleteRoomNum.getText().equals("") | deleteRoomSize.getText().equals("")
					|| deleteRoomFloor.getText().equals(""))
				throw new Exception();

			Main.db.deleteRoom(deleteRoomNum.getText());

			showSuccess("Successfully Deleted!");

			clearDelete();

		} catch (SQLException e) {

			showError("Error Try again!");
		}

		catch (Exception ex) {

			showError("Please Fill in Data..");
		}
	}

	private void clearDelete() {

		deleteRoomBeds.setText("");
		deleteRoomClass.setText("");
		deleteRoomCost.setText("");
		deleteRoomFloor.setText("");
		deleteRoomSize.setText("");
		deleteRoomNum.setText("");
	}
	// ------------------

	private void showSuccess(String s) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initStyle(StageStyle.UNDECORATED);
		alert.setTitle("Success!");
		alert.setHeaderText(s);

		alert.showAndWait();
	}

	private void showError(String s) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initStyle(StageStyle.UNDECORATED);
		alert.setTitle("Error !!");
		alert.setHeaderText("Input Error");
		alert.setContentText(s);

		alert.showAndWait();
	}

	@FXML
	public void moveToManager(MouseEvent event) throws IOException {
		Main.setParent("Manager", 640, 400, "Manager Menu!");
	}

	@FXML
	public void minimize(ActionEvent event) {

		((Stage) ((Button) event.getSource()).getScene().getWindow()).setIconified(true);
	}

	@FXML
	public void close(ActionEvent event) throws IOException {
		Main.hide();
	}

}
