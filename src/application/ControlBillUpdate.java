package application;

import java.sql.SQLIntegrityConstraintViolationException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ControlBillUpdate {

	@FXML
	private JFXDatePicker date_issued;

	@FXML
	private JFXButton close;

	@FXML
	private JFXTextField amount;

	public void initialize() {

		Bill s = ControlBills.UpdateBill;

		if (s != null) {

			date_issued.setValue(s.getDateIssued());
			amount.setText(s.getTotalCost() + "");
		}
	}

	@FXML
	public void updateBill(ActionEvent event) {

		try {

			Main.db.updateBill(ControlBills.UpdateBill.getBillId().toString(), date_issued.getValue(),
					amount.getText());

			AudioClip Audio = new AudioClip("file:src/application/Success!.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("Updated Successfully!");
			alert.setTitle("Done!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
					.add(new Image("/application/confirm.png"));
			alert.showAndWait();

			PauseTransition pt = new PauseTransition();
			pt.setDuration(Duration.seconds(1));

			pt.setOnFinished(ev -> {

				((Stage) close.getScene().getWindow()).close();
			});

			pt.play();

		} catch (SQLIntegrityConstraintViolationException x) {
			AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Repeated Value!");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();
		} catch (Exception e) {

			AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Ops Something Went Wrong!");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();
		}

	}

	@FXML
	public void close(ActionEvent event) {

		((Stage) close.getScene().getWindow()).close();

	}

}
