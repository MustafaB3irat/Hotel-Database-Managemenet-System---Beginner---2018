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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ControlCheckCleanHistory {

	@FXML
	private Label dateLabel;

	@FXML
	private JFXDatePicker date;

	@FXML
	private JFXTextField CleanerName, roomNumber;

	@FXML
	private TableView<CleanHistory> cleanHistoryTable;
	@FXML
	private TableColumn<CleanHistory, String> Name;
	@FXML
	private TableColumn<CleanHistory, String> Room;
	@FXML
	private TableColumn<CleanHistory, LocalDate> CleanDate;

	public void initialize() {
		Name.setCellValueFactory(new PropertyValueFactory<CleanHistory, String>("Name"));
		Room.setCellValueFactory(new PropertyValueFactory<CleanHistory, String>("Room"));
		CleanDate.setCellValueFactory(new PropertyValueFactory<CleanHistory, LocalDate>("Date"));
	}

	ObservableList<CleanHistory> clean = FXCollections.observableArrayList();

	@FXML
	public void getCleanHistory(ActionEvent e) throws SQLException {

		try {

			if (cleanHistoryTable.getItems() != null)
				cleanHistoryTable.getItems().clear();

			clean.clear();

			if (date.getValue() != null && roomNumber.getText().equals("") && CleanerName.getText().equals("")) {

				ResultSet r = Main.db.getCleanHistory(date.getValue());
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

			else if (date.getValue() != null && !roomNumber.getText().equals("")
					&& CleanerName.getText().equals("")) {

				ResultSet r = Main.db.getCleanByRoomNum(roomNumber.getText(), date.getValue());

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

			else if (date.getValue() != null && !CleanerName.getText().equals("") && roomNumber.getText().equals("")) {

				ResultSet eid = Main.db.getEidOfName(CleanerName.getText());

				String Eid = "";
				if (eid.next()) {
					Eid = eid.getString(1);
				}

				ResultSet r = Main.db.getCleanByeid(Eid);

				while (r.next()) {

					clean.add(new CleanHistory(CleanerName.getText(), r.getString(2), LocalDate.parse(r.getString(3))));
				}

			}

			else if (date.getValue() == null && !roomNumber.getText().equals("") && CleanerName.getText().equals("")) {

				ResultSet r = Main.db.getCleanHistoryFromRoom(roomNumber.getText());

				while (r.next()) {
					clean.add(new CleanHistory(r.getString(1), r.getString(2), LocalDate.parse(r.getString(3))));
				}

			}

			else if (date.getValue() == null && roomNumber.getText().equals("") && !CleanerName.getText().equals("")) {

				ResultSet r = Main.db.getCleanHistoryFromName(CleanerName.getText());

				while (r.next()) {

					clean.add(new CleanHistory(r.getString(1), r.getString(2), LocalDate.parse(r.getString(3))));

				}

			}

			cleanHistoryTable.setItems(clean);

		} catch (Exception ex) {
			System.out.println("Error!");
		}
	}

	// ----------------------------------

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

	@FXML
	public void hideLabel1() {

		dateLabel.setVisible(false);

	}

	@FXML
	public void clearInfo(MouseEvent event) {

		if (cleanHistoryTable.getItems() != null)
			cleanHistoryTable.getItems().clear();

		dateLabel.setVisible(true);

		date.setValue(null);
		CleanerName.setText("");
		roomNumber.setText("");
	}

}
