package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControlReservations {

	@FXML
	private JFXTextField CName, CEmail, CPhone, DocNo;

	@FXML
	private JFXDatePicker FromDate, ToDate, CDOB;

	@FXML
	private ComboBox<String> rNum;

	@FXML
	private JFXRadioButton Passport, Hawya;

	// -----------------------------

	@FXML
	public void showRooms(MouseEvent e) throws SQLException {

		try {

			if (FromDate.getValue() != null && ToDate.getValue() != null) {

				ObservableList<String> rNums = FXCollections.observableArrayList();

				Main.db.ChangeRoomStatus(FromDate.getValue(), ToDate.getValue());
				ResultSet r = Main.db.findAvailableRooms(FromDate.getValue(), ToDate.getValue());

				if (r.next() == false)
					throw new Exception();

				while (r.next())
					rNums.add(r.getString(8));

				rNum.setValue("Room Number");
				rNum.setItems(rNums);

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

	// Make Reservation.........

	@FXML
	public void insertReservation(ActionEvent event) throws SQLException {

		try {

			if (CName.getText().equals("") || CEmail.getText().equals("") || CPhone.getText().equals("")
					|| (Passport.isSelected() == false && Hawya.isSelected() == false) || CDOB.getValue() == null
					|| FromDate.getValue() == null || ToDate.getValue() == null
					|| rNum.getSelectionModel().getSelectedItem() == null)
				throw new Exception();

			Main.db.insertPerson(CEmail.getText(), CName.getText(), CDOB.getValue(), CPhone.getText());

			ResultSet pid = Main.db.getPidEmail(CEmail.getText());

			String docType = "";

			if (Passport.isSelected())
				docType = "Passport";
			else if (Hawya.isSelected())
				docType = "Hawya";

			if (pid.next())
				Main.db.insertClient(pid.getString(1), docType, DocNo.getText());

			ResultSet cid = Main.db.getClientId(DocNo.getText());

			if (cid.next())
				Main.db.insertReservation(cid.getString(1), controlLogIn.Eid,
						rNum.getSelectionModel().getSelectedItem(), FromDate.getValue(), ToDate.getValue());

			Main.db.UpdateRoomStatus(rNum.getSelectionModel().getSelectedItem());

			AudioClip Audio = new AudioClip("file:src/application/Success!.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("Reserved Successfully!");
			alert.setTitle("Done!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
					.add(new Image("/application/confirm.png"));
			alert.showAndWait();

			this.clearInfo();

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

	private void clearInfo() {

		rNum.getItems().clear();
		CName.setText("");
		CPhone.setText("");
		CEmail.setText("");
		CDOB.setValue(null);
		FromDate.setValue(null);
		ToDate.setValue(null);
		Passport.setSelected(false);
		Hawya.setSelected(false);
		DocNo.setText("");
	}

	// Get Reservations

	@FXML
	private TableView<Reservations> FindReservationsTable, UpdateReservationsTable, DeleteReservationsTable;

	@FXML
	private TableColumn<Reservations, String> CNameColumn, CNameColumn1, CNameColumn2, CEmailColumn, CEmailColumn1,
			CEmailColumn2, CPhoneColumn, CPhoneColumn1, CPhoneColumn2, CDocumentType, CDocumentType1, CDocumentType2,
			CDocumentNo, CDocumentNo1, CDocumentNo2, RoomNumberColumn, RoomNumberColumn1, RoomNumberColumn2,
			RecNameColumn, RecNameColumn1, RecNameColumn2;

	@FXML
	private TableColumn<Reservations, LocalDate> FromDateColumn, FromDateColumn1, FromDateColumn2, ToDateColumn,
			ToDateColumn1, ToDateColumn2;

	@FXML
	private JFXTextField FindReservationsText, DeleteReservationsText, UpdateReservationsText;

	static ArrayList<Reservations> updateResults = new ArrayList<>();

	public void initialize() {

		CNameColumn.setCellValueFactory(new PropertyValueFactory<Reservations, String>("CName"));
		CEmailColumn.setCellValueFactory(new PropertyValueFactory<Reservations, String>("CEmail"));
		CPhoneColumn.setCellValueFactory(new PropertyValueFactory<Reservations, String>("CPhone"));
		CDocumentType.setCellValueFactory(new PropertyValueFactory<Reservations, String>("CDocumentType"));
		CDocumentNo.setCellValueFactory(new PropertyValueFactory<Reservations, String>("CDocumentNo"));
		RoomNumberColumn.setCellValueFactory(new PropertyValueFactory<Reservations, String>("RoomNum"));
		RecNameColumn.setCellValueFactory(new PropertyValueFactory<Reservations, String>("RecName"));
		FromDateColumn.setCellValueFactory(new PropertyValueFactory<Reservations, LocalDate>("FromDate"));
		ToDateColumn.setCellValueFactory(new PropertyValueFactory<Reservations, LocalDate>("ToDate"));

		// -----------------------------------------------------------------------------------------

		CNameColumn1.setCellValueFactory(new PropertyValueFactory<Reservations, String>("CName"));
		CEmailColumn1.setCellValueFactory(new PropertyValueFactory<Reservations, String>("CEmail"));
		CPhoneColumn1.setCellValueFactory(new PropertyValueFactory<Reservations, String>("CPhone"));
		CDocumentType1.setCellValueFactory(new PropertyValueFactory<Reservations, String>("CDocumentType"));
		CDocumentNo1.setCellValueFactory(new PropertyValueFactory<Reservations, String>("CDocumentNo"));
		RoomNumberColumn1.setCellValueFactory(new PropertyValueFactory<Reservations, String>("RoomNum"));
		RecNameColumn1.setCellValueFactory(new PropertyValueFactory<Reservations, String>("RecName"));
		FromDateColumn1.setCellValueFactory(new PropertyValueFactory<Reservations, LocalDate>("FromDate"));
		ToDateColumn1.setCellValueFactory(new PropertyValueFactory<Reservations, LocalDate>("ToDate"));

		// ------------------------------------------------------------------------------------------------

		CNameColumn2.setCellValueFactory(new PropertyValueFactory<Reservations, String>("CName"));
		CEmailColumn2.setCellValueFactory(new PropertyValueFactory<Reservations, String>("CEmail"));
		CPhoneColumn2.setCellValueFactory(new PropertyValueFactory<Reservations, String>("CPhone"));
		CDocumentType2.setCellValueFactory(new PropertyValueFactory<Reservations, String>("CDocumentType"));
		CDocumentNo2.setCellValueFactory(new PropertyValueFactory<Reservations, String>("CDocumentNo"));
		RoomNumberColumn2.setCellValueFactory(new PropertyValueFactory<Reservations, String>("RoomNum"));
		RecNameColumn2.setCellValueFactory(new PropertyValueFactory<Reservations, String>("RecName"));
		FromDateColumn2.setCellValueFactory(new PropertyValueFactory<Reservations, LocalDate>("FromDate"));
		ToDateColumn2.setCellValueFactory(new PropertyValueFactory<Reservations, LocalDate>("ToDate"));

		// --------------------------------------------------------------------------------------------------

		UpdateReservationsTable.setRowFactory(tv -> {
			TableRow<Reservations> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					Reservations rowData = row.getItem();

					try {

						updateResults.clear();

						ResultSet r = Main.db.getReservationsByDocNo(rowData.getCDocumentNo());

						if (r.next()) {

							updateResults.add(new Reservations(r.getString(3), r.getString(2), r.getString(5),
									r.getString(8), r.getString(9), r.getString(13), r.getString(12),
									LocalDate.parse(r.getString(14)), LocalDate.parse(r.getString(15))));
						}

						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(Main.class.getResource("/application/UpdateReservation.fxml"));
						Pane p = loader.load();
						Scene s = new Scene(p, 710, 464);
						Stage stage = new Stage();
						stage.initStyle(StageStyle.UNDECORATED);
						stage.setTitle("Update Reservation!");
						stage.setScene(s);
						stage.show();

						Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
						stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
						stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
						UpdateReservationsTable.getItems().clear();
						UpdateReservationsText.setText("");

					} catch (SQLException | IOException e) {

						AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
						Audio.play();

						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("Ops Something Went Wrong!");
						alert.setTitle("Error!");
						alert.setHeaderText(null);
						alert.setResizable(false);

						alert.initModality(Modality.APPLICATION_MODAL);

						((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
								.add(new Image("/application/Error.png"));
						alert.showAndWait();

					}

				}
			});
			return row;
		});

	}

	ObservableList<Reservations> find = FXCollections.observableArrayList(),
			update = FXCollections.observableArrayList(), delete = FXCollections.observableArrayList();

	@FXML
	public void getReservationsFind(KeyEvent event) {

		find.clear();
		if (FindReservationsTable.getItems() != null)
			FindReservationsTable.getItems().clear();

		if (!FindReservationsText.getText().equals("")) {

			try {

				ArrayList<String> Email = new ArrayList<>();
				ArrayList<String> Name = new ArrayList<>();
				ArrayList<String> Phone = new ArrayList<>();
				ArrayList<String> DocType = new ArrayList<>();
				ArrayList<String> DocNo = new ArrayList<>();
				ArrayList<String> RoomNum = new ArrayList<>();
				ArrayList<String> Eid = new ArrayList<>();
				ArrayList<LocalDate> From = new ArrayList<>();
				ArrayList<LocalDate> To = new ArrayList<>();

				ResultSet r = Main.db.getReservationsByClients(FindReservationsText.getText());

				while (r.next()) {
					Email.add(r.getString(2));
					Name.add(r.getString(3));
					Phone.add(r.getString(5));
					DocType.add(r.getString(8));
					DocNo.add(r.getString(9));
					RoomNum.add(r.getString(13));
					Eid.add(r.getString(12));

					From.add(LocalDate.parse(r.getString(14)));

					To.add(LocalDate.parse(r.getString(15)));

				}

				for (int i = 0; i < Email.size(); i++) {

					ResultSet s = Main.db.getEmpById(Eid.get(i));

					if (s.next()) {

						find.add(new Reservations(Name.get(i), Email.get(i), Phone.get(i), DocType.get(i), DocNo.get(i),
								RoomNum.get(i), s.getString(3), From.get(i), To.get(i)));

					}

				}

				FindReservationsTable.setItems(find);

			} catch (Exception e) {

				AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
				Audio.play();

				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Ops Something Went Wrong!");
				alert.setTitle("Error!");
				alert.setHeaderText(null);
				alert.setResizable(false);

				alert.initModality(Modality.APPLICATION_MODAL);

				((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
						.add(new Image("/application/Error.png"));
				alert.showAndWait();

			}

		}
	}

	// ------------Get Reservations for delete .....

	@FXML
	public void getReservationsDelete(KeyEvent event) {

		find.clear();
		if (DeleteReservationsTable.getItems() != null)
			DeleteReservationsTable.getItems().clear();

		if (!DeleteReservationsText.getText().equals("")) {

			try {

				ArrayList<String> Email = new ArrayList<>();
				ArrayList<String> Name = new ArrayList<>();
				ArrayList<String> Phone = new ArrayList<>();
				ArrayList<String> DocType = new ArrayList<>();
				ArrayList<String> DocNo = new ArrayList<>();
				ArrayList<String> RoomNum = new ArrayList<>();
				ArrayList<String> Eid = new ArrayList<>();
				ArrayList<LocalDate> From = new ArrayList<>();
				ArrayList<LocalDate> To = new ArrayList<>();

				ResultSet r = Main.db.getReservationsByClients(DeleteReservationsText.getText());

				while (r.next()) {
					Email.add(r.getString(2));
					Name.add(r.getString(3));
					Phone.add(r.getString(5));
					DocType.add(r.getString(8));
					DocNo.add(r.getString(9));
					RoomNum.add(r.getString(13));
					Eid.add(r.getString(12));
					From.add(LocalDate.parse(r.getString(14)));
					To.add(LocalDate.parse(r.getString(15)));

				}

				for (int i = 0; i < Email.size(); i++) {

					ResultSet s = Main.db.getEmpById(Eid.get(i));

					if (s.next()) {

						delete.add(new Reservations(Name.get(i), Email.get(i), Phone.get(i), DocType.get(i),
								DocNo.get(i), RoomNum.get(i), s.getString(3), From.get(i), To.get(i)));

					}

				}

				DeleteReservationsTable.setItems(delete);

			} catch (Exception e) {

				AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
				Audio.play();

				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Ops Something Went Wrong!");
				alert.setTitle("Error!");
				alert.setHeaderText(null);
				alert.setResizable(false);

				alert.initModality(Modality.APPLICATION_MODAL);

				((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
						.add(new Image("/application/Error.png"));
				alert.showAndWait();

			}

		}
	}

	@FXML
	public void deleteReservation(ActionEvent event) {

		try {

			if (DeleteReservationsTable.getItems() != null) {

				Reservations s = DeleteReservationsTable.getSelectionModel().getSelectedItem();

				Main.db.deleteReservation(s.getRoomNum(), s.getFromDate(), s.getToDate());

				String doc_no = DeleteReservationsTable.getSelectionModel().getSelectedItem().getCDocumentNo();

				ResultSet cid = Main.db.getCid(doc_no);

				String Cid = "";
				if (cid.next())
					Cid = cid.getString(1);

				Main.db.deleteClient(Cid);

				DeleteReservationsTable.getItems().removeAll(s);
				DeleteReservationsText.setText("");

				AudioClip Audio = new AudioClip("file:src/application/Success!.mp3");
				Audio.play();

				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Deleted Successfully!");
				alert.setTitle("Success!");
				alert.setHeaderText(null);
				alert.setResizable(false);

				alert.initModality(Modality.APPLICATION_MODAL);

				((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
						.add(new Image("/application/confirm.png"));
				alert.showAndWait();
			}

		} catch (Exception e) {

			AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("There's a bill related to this client!");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();

		}
	}

	// ------------Update Reservations

	@FXML
	public void getReservationsUpdate(KeyEvent event) {

		find.clear();
		if (UpdateReservationsTable.getItems() != null)
			UpdateReservationsTable.getItems().clear();

		if (!UpdateReservationsText.getText().equals("")) {

			try {

				ArrayList<String> Email = new ArrayList<>();
				ArrayList<String> Name = new ArrayList<>();
				ArrayList<String> Phone = new ArrayList<>();
				ArrayList<String> DocType = new ArrayList<>();
				ArrayList<String> DocNo = new ArrayList<>();
				ArrayList<String> RoomNum = new ArrayList<>();
				ArrayList<String> Eid = new ArrayList<>();
				ArrayList<LocalDate> From = new ArrayList<>();
				ArrayList<LocalDate> To = new ArrayList<>();

				ResultSet r = Main.db.getReservationsByClients(UpdateReservationsText.getText());

				while (r.next()) {
					Email.add(r.getString(2));
					Name.add(r.getString(3));
					Phone.add(r.getString(5));
					DocType.add(r.getString(8));
					DocNo.add(r.getString(9));
					RoomNum.add(r.getString(13));
					Eid.add(r.getString(12));
					From.add(LocalDate.parse(r.getString(14)));
					To.add(LocalDate.parse(r.getString(15)));

				}

				for (int i = 0; i < Email.size(); i++) {

					ResultSet s = Main.db.getEmpById(Eid.get(i));

					if (s.next()) {

						update.add(new Reservations(Name.get(i), Email.get(i), Phone.get(i), DocType.get(i),
								DocNo.get(i), RoomNum.get(i), s.getString(3), From.get(i), To.get(i)));

					}

				}

				UpdateReservationsTable.setItems(update);

			} catch (Exception e) {

				AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
				Audio.play();

				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Ops Something Went Wrong!");
				alert.setTitle("Error!");
				alert.setHeaderText(null);
				alert.setResizable(false);

				alert.initModality(Modality.APPLICATION_MODAL);

				((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
						.add(new Image("/application/Error.png"));
				alert.showAndWait();

			}

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
	public void moveToRec(MouseEvent event) throws IOException {

		Main.setParent("ReceptioniestMenu", 640, 400, "Receptioniest!");
	}

}
