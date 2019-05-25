package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class controlAvaiRooms {

	@FXML
	private JFXDatePicker fromD, toD;

	@FXML
	private Label l1, l2;

	@FXML
	private TableView<AvailableRooms> AvailRooms;

	@FXML
	private TableColumn<AvailableRooms, String> roomNum, status;

	@FXML
	private TableColumn<AvailableRooms, Integer> floor, beds;

	@FXML
	private TableColumn<AvailableRooms, Float> cost, size, C;

	@FXML
	public void clearInfo(MouseEvent event) {

		if (AvailRooms.getItems() != null)
			AvailRooms.getItems().clear();

		l1.setVisible(true);
		l2.setVisible(true);
		fromD.setValue(null);
		toD.setValue(null);
	}

	public void initialize() {

		l1.setVisible(true);
		l2.setVisible(true);

		roomNum.setCellValueFactory(new PropertyValueFactory<AvailableRooms, String>("roomNum"));
		beds.setCellValueFactory(new PropertyValueFactory<AvailableRooms, Integer>("numOfBeds"));
		cost.setCellValueFactory(new PropertyValueFactory<AvailableRooms, Float>("Cost"));
		size.setCellValueFactory(new PropertyValueFactory<AvailableRooms, Float>("size"));
		C.setCellValueFactory(new PropertyValueFactory<AvailableRooms, Float>("Cl"));
		floor.setCellValueFactory(new PropertyValueFactory<AvailableRooms, Integer>("floor"));
		status.setCellValueFactory(new PropertyValueFactory<AvailableRooms, String>("Status"));

	}

	ObservableList<AvailableRooms> l = FXCollections.observableArrayList();

	@FXML
	public void hideLabel1() {

		l1.setVisible(false);

	}

	@FXML
	public void rotate(ActionEvent event) {
		
		fromD.setScaleX(fromD.getScaleX() * -1);
	}

	@FXML
	public void hideLabel2() {

		l2.setVisible(false);
	}

	@FXML
	public void showAvailableRooms(ActionEvent a) throws SQLException {

		try {

			AvailRooms.getItems().clear();

			if (fromD.getValue() == null || toD.getValue() == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error !");
				alert.setHeaderText("You must specify all input fields");

				alert.showAndWait();
			} else {
				
				
				Main.db.ChangeRoomStatus(fromD.getValue(), toD.getValue());
				ResultSet r = Main.db.findAvailableRooms(fromD.getValue(), toD.getValue());
				

				while (r.next()) {

					l.add(new AvailableRooms(r.getString(8), Integer.parseInt(r.getString(2)),
							Float.parseFloat(r.getString(3)), Float.parseFloat(r.getString(4)),
							Integer.parseInt(r.getString(5)), Float.parseFloat(r.getString(6)), r.getString(7)));

				}
				if (l.size() == 0)
					l.add(null);

				AvailRooms.setItems(l);

			}

		}

		catch (Exception e) {
			AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Something went wrong!");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();
		}
	}

	public void moveToMainMenu(MouseEvent event) throws IOException {
		if (Main.back == 1)
			Main.setParent("Main", 640, 400, "Royal Hotel!");
		else if (Main.back == 2)
			Main.setParent("ReceptioniestMenu", 640, 400, "Receptionist Menu! ");
		else if (Main.back == 3)
			Main.setParent("employeesMenu", 640, 400, "Employees Menu ");
		else if (Main.back == 4)
			Main.setParent("Manager", 640, 400, "Manager Menu ");
	}

	@FXML
	public void close(ActionEvent event) throws IOException {

		Main.hide();
	}

	@FXML
	public void minimize(ActionEvent event) {

		((Stage) ((Button) event.getSource()).getScene().getWindow()).setIconified(true);
	}

}
