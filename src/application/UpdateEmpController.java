package application;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class UpdateEmpController {

	public void initialize() {

		UpdateEmp s = controlEmployees.updateResults.get(0);

		recUsername.setText(s.getUsername());
		recPassword.setText(s.getPassword());
		recEmail.setText(s.getEmail());
		recAddress.setText(s.getAddress());
		recEndDate.setValue(s.getEndDate());
		recStartDate.setValue(s.getStartDate());
		recSalary.setText("" + s.getSalary());
		recJobField.setText(s.getJobField());
		recPhone.setText(s.getPhone());
		recRating.setText(s.getRating() + "");
	}

	// update

	// recUpdate
	@FXML
	private JFXTextField recUsername, recPassword, recPhone, recRating, recAddress, recSalary, recJobField, recEmail;

	@FXML
	private JFXDatePicker recStartDate, recEndDate;

	@FXML
	private JFXButton update;

	@FXML
	public void updaterec(ActionEvent event) {

		try {

			if (recRating.getText().equals("") || recAddress.getText().equals("") || recSalary.getText().equals("")
					|| recUsername.getText().equals("") || recPassword.getText().equals("")
					|| recStartDate.getValue() == null || recPhone.getText().equals("") || recEmail.getText().equals("")
					|| recJobField.getText().equals(""))
				showError("Please Fill in the Data..");

			else {

				Main.db.updatePerson(recEmail.getText(), recPhone.getText(),
						controlEmployees.updateResults.get(0).getUsername(), recRating.getText(), recAddress.getText(),
						recSalary.getText(), recPassword.getText(), recStartDate.getValue(), recEndDate.getValue(),
						recJobField.getText(), recUsername.getText());

				showSuccess("Successfully Updated!");

				PauseTransition pt = new PauseTransition();
				pt.setDuration(Duration.seconds(1));

				pt.setOnFinished(ev -> {

					((Stage) update.getScene().getWindow()).close();

				});
				pt.play();

			}

		} catch (SQLIntegrityConstraintViolationException x) {

			showError("Repeated Value!");
		} catch (SQLException e) {

			showError("Ops! something went wrong!");

		}
	}

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
	public void minimize(ActionEvent event) {

		((Stage) ((Button) event.getSource()).getScene().getWindow()).setIconified(true);
	}

	@FXML
	private JFXButton close;

	@FXML
	public void close(ActionEvent event) throws IOException {
		Stage s = (Stage) close.getScene().getWindow();
		s.close();
	}

}
