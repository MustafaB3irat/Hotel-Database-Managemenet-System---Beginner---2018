package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControlUpdateReservation {

	@FXML
	private JFXTextField CName, CEmail, CPhone, CDocNo;

	@FXML
	private JFXRadioButton Hawya, Passport;

	@FXML
	private JFXComboBox<String> rooms;

	@FXML
	private JFXDatePicker FromDate, ToDate;

	// --------------------------------------------------------------------

	public void initialize() {

		CName.setText(ControlReservations.updateResults.get(0).getCName());
		CEmail.setText(ControlReservations.updateResults.get(0).getCEmail());
		CPhone.setText(ControlReservations.updateResults.get(0).getCPhone());
		CDocNo.setText(ControlReservations.updateResults.get(0).getCDocumentNo());
		FromDate.setValue(ControlReservations.updateResults.get(0).getFromDate());
		ToDate.setValue(ControlReservations.updateResults.get(0).getToDate());

		String status = ControlReservations.updateResults.get(0).getCDocumentType();

		if (status.equals("Passport"))
			Passport.setSelected(true);

		else if (status.equals("Hawya"))
			Hawya.setSelected(true);

		try {

			if (FromDate.getValue() != null && ToDate.getValue() != null) {

				ObservableList<String> rNums = FXCollections.observableArrayList();

				Main.db.ChangeRoomStatus(FromDate.getValue(), ToDate.getValue());
				ResultSet r = Main.db.findAvailableRooms(FromDate.getValue(), ToDate.getValue());

				if (r.next() == false)
					throw new Exception();

				while (r.next())
					rNums.add(r.getString(8));

				rooms.setValue(ControlReservations.updateResults.get(0).getRoomNum());
				rooms.setItems(rNums);

			}

		} catch (Exception x) {

			AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("No Rooms To reserve!");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();

		}

	}

	@FXML
	public void UpdateClient(ActionEvent event) {

		try {

			if (CName.getText().equals("") || CEmail.getText().equals("") || CPhone.getText().equals("")
					|| (Hawya.isSelected() == false && Passport.isSelected() == false) || CDocNo.getText().equals("")
					|| rooms.getSelectionModel().getSelectedItem() == null
					|| FromDate.getValue() == null | ToDate.getValue() == null)
				throw new Exception();

			Main.db.updateClientPerson(CName.getText(), ControlReservations.updateResults.get(0).getCEmail(),
					CPhone.getText(), CEmail.getText());

			String status = "";

			if (Hawya.isSelected())
				status = "Hawya";

			else
				status = "Passport";

			Main.db.updateClient(status, ControlReservations.updateResults.get(0).getCDocumentNo(), CDocNo.getText());

			ResultSet cid = Main.db.getCid(CDocNo.getText());

			String Cid = "";
			if (cid.next())
				Cid = cid.getString(1);

			ResultSet bNum = Main.db.getbNum(ControlReservations.updateResults.get(0).getRoomNum(),
					ControlReservations.updateResults.get(0).getFromDate(),
					ControlReservations.updateResults.get(0).getToDate(), Cid);

			String bnum = "";
			if (bNum.next())
				bnum = bNum.getString(1);

			Main.db.updateReservation(FromDate.getValue(), ToDate.getValue(),
					rooms.getSelectionModel().getSelectedItem(), bnum);

			AudioClip Audio = new AudioClip("file:src/application/Success!.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("SuccessFully Updated!");
			alert.setTitle("Success!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
					.add(new Image("/application/confirm.png"));
			alert.showAndWait();

			((Stage) ((Button) event.getSource()).getScene().getWindow()).close();

		} catch (SQLIntegrityConstraintViolationException x) {

			AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Repeated Values!");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();

		}

		catch (Exception e) {

			AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Please Fill in Data Properly! ");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();
		}
	}

	// -----------------------------

	@FXML
	public void minimize(ActionEvent event) {

		((Stage) ((Button) event.getSource()).getScene().getWindow()).setIconified(true);
	}

	@FXML
	public void close(ActionEvent event) throws IOException {

		((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
	}

}
