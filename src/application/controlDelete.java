package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;

public class controlDelete {
	@FXML
	private TextField textfield;

	@FXML
	private Button btn;

	public void moveToMainMenu(ActionEvent event) throws IOException {
		Main.setParent("ManagerMenu", 700, 600, "Manager Menu ");
	}

	public void clearInfo(ActionEvent event) {
		textfield.setText("");

	}

	public void deletepersoninf(ActionEvent event) {

		try {
			Integer.parseInt(textfield.getText());
			Main.db.deletePerson(textfield.getText());
			showSuccess("Person " + textfield.getText() + " was deleted successfully");
		} catch (SQLException e) {
			showError("Cannot delete receptionist employee");
		} catch (Exception e3) {
			showError("Make sure you fill all the inputs properly");
		}

	}

	private void showSuccess(String s) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Success");
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
}
