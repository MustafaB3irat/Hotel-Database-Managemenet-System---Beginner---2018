package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class controlCleanHistory {

	@FXML
	private ComboBox<String> CleanerName, RoomNum;

	@FXML
	private JFXDatePicker deleteDate, date;

	@FXML
	private JFXTextField deleteNum, deleteName;

	@FXML
	private TableView<CleanHistory> deleteCleanTable;

	@FXML
	private TableColumn<CleanHistory, String> deleteNameColumn, deleteNumColumn;

	@FXML
	private TableColumn<CleanHistory, LocalDate> deleteDateColumn;

	ObservableList<String> names = FXCollections.observableArrayList();
	ObservableList<String> rooms = FXCollections.observableArrayList();

	public void initialize() throws SQLException {

		deleteNameColumn.setCellValueFactory(new PropertyValueFactory<CleanHistory, String>("Name"));
		deleteNumColumn.setCellValueFactory(new PropertyValueFactory<CleanHistory, String>("Room"));
		deleteDateColumn.setCellValueFactory(new PropertyValueFactory<CleanHistory, LocalDate>("date"));

		ResultSet r = Main.db.getCleanerNames();

		while (r.next()) {
			names.add(r.getString(1));
		}

		ResultSet r1 = Main.db.getRooms();

		while (r1.next()) {
			rooms.add(r1.getString(1));
		}

		CleanerName.setValue("Cleaner Name");
		CleanerName.setItems(names);

		RoomNum.setValue("Room Number");
		RoomNum.setItems(rooms);

	}

	@FXML
	public void addCleanHistory(ActionEvent event) {

		try {

			if (CleanerName.getSelectionModel().getSelectedItem().equals("")
					|| RoomNum.getSelectionModel().getSelectedItem().equals("") || date.getValue() == null)
				throw new Exception();

			ResultSet r = Main.db.getEidOfName(CleanerName.getSelectionModel().getSelectedItem());

			String eid = "";
			if (r.next()) {
				eid = r.getString(1);
			}

			Main.db.insertCleanHistory(eid, RoomNum.getSelectionModel().getSelectedItem(), date.getValue());

			AudioClip Audio = new AudioClip("file:src/application/Success!.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("Successfully Added!");
			alert.setTitle("Done!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
					.add(new Image("/application/confirm.png"));
			alert.showAndWait();

			date.setValue(null);
			CleanerName.getItems().clear();
			RoomNum.getItems().clear();

		} catch (Exception e) {
			AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Please Fill in Data Properly!");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();
		}

	}

	// --------Delete History

	ObservableList<CleanHistory> clean = FXCollections.observableArrayList();

	@FXML
	public void getCleanHistory(ActionEvent e) throws SQLException {

		try {

			if (deleteCleanTable.getItems() != null)
				deleteCleanTable.getItems().clear();

			clean.clear();

			if (deleteDate.getValue() != null && deleteNum.getText().equals("") && deleteName.getText().equals("")) {

				ResultSet r = Main.db.getCleanHistory(deleteDate.getValue());
				ArrayList<String> eids = new ArrayList<>();
				ArrayList<String> rNums = new ArrayList<>();
				ArrayList<LocalDate> dates = new ArrayList<>();

				while (r.next()) {

					eids.add(r.getString(1));
					rNums.add(r.getString(2));
					dates.add(LocalDate.parse(r.getString(3)));

				}

				for (int i = 0; i < eids.size(); i++) {

					ResultSet res = Main.db.nameOfeid(eids.get(i));

					if (res.next()) {

						clean.add(new CleanHistory(res.getString(1), rNums.get(i), dates.get(i)));

					}
				}

			}

			else if (deleteDate.getValue() != null && !deleteNum.getText().equals("")
					&& deleteName.getText().equals("")) {

				ResultSet r = Main.db.getCleanByRoomNum(deleteNum.getText(), deleteDate.getValue());

				if (r.next()) {

					String eid = r.getString(1);
					String rNum = r.getString(2);
					LocalDate date = LocalDate.parse(r.getString(3));

					ResultSet name = Main.db.nameOfeid(eid);

					String Name = "";

					if (name.next()) {
						Name = name.getString(1);
						clean.add(new CleanHistory(Name, rNum, date));

					}
				}

			}

			else if (deleteDate.getValue() != null && !deleteName.getText().equals("")
					&& deleteNum.getText().equals("")) {

				ResultSet eid = Main.db.getEidOfName(deleteName.getText());

				String Eid = "";
				if (eid.next()) {
					Eid = eid.getString(1);
				}

				ResultSet r = Main.db.getCleanByeid(Eid);

				while (r.next()) {

					clean.add(new CleanHistory(deleteName.getText(), r.getString(2), LocalDate.parse(r.getString(3))));
				}

			}

			else if (deleteDate.getValue() == null && !deleteNum.getText().equals("")
					&& deleteName.getText().equals("")) {

				ResultSet r = Main.db.getCleanHistoryFromRoom(deleteNum.getText());

				while (r.next()) {
					clean.add(new CleanHistory(r.getString(1), r.getString(2), LocalDate.parse(r.getString(3))));
				}

			}

			else if (deleteDate.getValue() == null && deleteNum.getText().equals("")
					&& !deleteName.getText().equals("")) {

				ResultSet r = Main.db.getCleanHistoryFromName(deleteName.getText());

				while (r.next()) {

					clean.add(new CleanHistory(r.getString(1), r.getString(2), LocalDate.parse(r.getString(3))));

				}

			}

			deleteCleanTable.setItems(clean);

		} catch (Exception ex) {

			AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Please Fill in Data Properly!");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();
		}
	}

	@FXML
	public void deleteHistory(ActionEvent event) {

		try {

			if (deleteCleanTable.getItems() == null)
				throw new Exception();

			CleanHistory sample = deleteCleanTable.getSelectionModel().getSelectedItem();

			ResultSet r = Main.db.getEidOfName(sample.getName());

			String eid = "";

			if (r.next())
				eid = r.getString(1);

			Main.db.deleteCleanHistory(eid, sample.getRoom(), sample.getDate());

			AudioClip Audio = new AudioClip("file:src/application/Success!.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("Successfully Deleted!");
			alert.setTitle("Done!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
					.add(new Image("/application/confirm.png"));
			alert.showAndWait();

			deleteCleanTable.getItems().removeAll(sample);

		} catch (Exception e) {

			AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error!");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();

		}
	}

	// -------------------------------------

	@FXML
	public void minimize(ActionEvent event) {

		((Stage) ((Button) event.getSource()).getScene().getWindow()).setIconified(true);
	}

	@FXML
	public void close(ActionEvent event) throws IOException {
		Main.hide();
	}

	@FXML
	public void moveToManager(MouseEvent event) throws IOException {

		Main.setParent("Manager", 640, 400, "Manager!");
	}

}
