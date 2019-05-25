package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import databaseAPI.DBHandler.login_type;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class controlEmployees {

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

	// --------------------------------------

	@FXML
	private JFXTextField name, email, address, rating, phone, salary, username;

	@FXML
	private JFXDatePicker DOB, startDate, endDate;

	@FXML
	private JFXPasswordField password;

	@FXML
	private JFXRadioButton rece, cleaner;

	@FXML
	public void insert(ActionEvent event) throws SQLException {
		try {

			if (rating.getText().equals("") || address.getText().equals("") || salary.getText().equals("")
					|| username.getText().equals("") || password.getText().equals("") || startDate.getValue() == null
					|| phone.getText().equals("") || email.getText().equals("") || name.getText().equals("")
					|| (rece.isSelected() == false && cleaner.isSelected() == false)) {
				showError("Please Fill in Data..");

			} else {

				Main.db.insertPerson(email.getText(), name.getText(), DOB.getValue(), phone.getText());

				ResultSet r = Main.db.findIdPerson(email.getText());

				String pid = "";
				if (r.next()) {
					pid = r.getString(1);
				}

				if (rece.isSelected()) {
					Main.db.insertEmployee(pid, rating.getText(), address.getText(), salary.getText(),
							username.getText(), password.getText(), startDate.getValue(), login_type.RECEPTIONIST);
				}

				else if (cleaner.isSelected()) {
					Main.db.insertEmployee(pid, rating.getText(), address.getText(), salary.getText(),
							username.getText(), password.getText(), startDate.getValue(), login_type.Cleaner);
				}

				// Main.db.insertReceptionist(Eid.getText(), pId.getText(), clients.getText());

				showSuccess("the insert was done successfully");
				clearInfo();
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			showError("Repeated value!");
		}

	}

	// Update Emp

	@FXML
	private TableView<UpdateEmp> updateTable;

	@FXML
	private TableColumn<UpdateEmp, String> updateName, updateAddress, updatePhone, updateEmail;

	@FXML
	private TableColumn<UpdateEmp, Float> updateRating, updateSalary;

	@FXML
	private TableColumn<UpdateEmp, Long> updateId;

	@FXML
	private TableColumn<UpdateEmp, LocalDate> updateStart, updateEnd;

	ObservableList<UpdateEmp> update = FXCollections.observableArrayList();

	@FXML
	private JFXTextField name1;

	@FXML
	public void getEmployee(KeyEvent event) {

		updateTable.getItems().clear();

		if (!name1.getText().equals("")) {

			try {

				ResultSet r = Main.db.getEmployee(name1.getText());

				while (r.next()) {

					if (r.getString(14) == null)
						update.add(new UpdateEmp(r.getString(3), Long.parseLong(r.getString(6)), r.getString(2),
								r.getString(5), r.getFloat(8), r.getString(9), r.getFloat(10),
								LocalDate.parse(r.getString(13))));

					else
						update.add(new UpdateEmp(r.getString(3), Long.parseLong(r.getString(6)), r.getString(2),
								r.getString(5), r.getFloat(8), r.getString(9), r.getFloat(10),
								LocalDate.parse(r.getString(13)), LocalDate.parse(r.getString(14))));

				}

				updateTable.setItems(update);

			} catch (SQLException e) {

				showError("Not Found!");

			}

		}

	}

	// ....Rec

	@FXML
	private TableView<EmployeeRec> deleteTable;

	@FXML
	private TableColumn<EmployeeRec, String> deleteName, deleteAddress, deletePhone, deleteEmail;

	@FXML
	private TableColumn<EmployeeRec, Float> deleteRating, deleteSalary;

	@FXML
	private TableColumn<EmployeeRec, Long> deleteId;

	@FXML
	private TableColumn<EmployeeRec, LocalDate> deleteStartDate, deleteEndDate;

	@FXML
	private JFXTextField deleteName1;

	public void initialize() {

		deleteEmail.setCellValueFactory(new PropertyValueFactory<EmployeeRec, String>("email"));
		deleteName.setCellValueFactory(new PropertyValueFactory<EmployeeRec, String>("name"));
		deletePhone.setCellValueFactory(new PropertyValueFactory<EmployeeRec, String>("phone"));
		deleteId.setCellValueFactory(new PropertyValueFactory<EmployeeRec, Long>("id"));
		deleteAddress.setCellValueFactory(new PropertyValueFactory<EmployeeRec, String>("address"));
		deleteRating.setCellValueFactory(new PropertyValueFactory<EmployeeRec, Float>("rating"));
		deleteSalary.setCellValueFactory(new PropertyValueFactory<EmployeeRec, Float>("salary"));
		deleteStartDate.setCellValueFactory(new PropertyValueFactory<EmployeeRec, LocalDate>("startDate"));
		deleteEndDate.setCellValueFactory(new PropertyValueFactory<EmployeeRec, LocalDate>("endDate"));

		findEmail.setCellValueFactory(new PropertyValueFactory<EmployeeRec, String>("email"));
		findName.setCellValueFactory(new PropertyValueFactory<EmployeeRec, String>("name"));
		findPhone.setCellValueFactory(new PropertyValueFactory<EmployeeRec, String>("phone"));
		JOB.setCellValueFactory(new PropertyValueFactory<EmployeeRec, String>("job"));
		findId.setCellValueFactory(new PropertyValueFactory<EmployeeRec, Long>("id"));
		findAddress.setCellValueFactory(new PropertyValueFactory<EmployeeRec, String>("address"));
		findRating.setCellValueFactory(new PropertyValueFactory<EmployeeRec, Float>("rating"));
		findSalary.setCellValueFactory(new PropertyValueFactory<EmployeeRec, Float>("salary"));
		findStartDate.setCellValueFactory(new PropertyValueFactory<EmployeeRec, LocalDate>("startDate"));
		findEndDate.setCellValueFactory(new PropertyValueFactory<EmployeeRec, LocalDate>("endDate"));

		updateEmail.setCellValueFactory(new PropertyValueFactory<UpdateEmp, String>("email"));
		updateName.setCellValueFactory(new PropertyValueFactory<UpdateEmp, String>("name"));
		updatePhone.setCellValueFactory(new PropertyValueFactory<UpdateEmp, String>("phone"));
		updateId.setCellValueFactory(new PropertyValueFactory<UpdateEmp, Long>("id"));
		updateAddress.setCellValueFactory(new PropertyValueFactory<UpdateEmp, String>("address"));
		updateRating.setCellValueFactory(new PropertyValueFactory<UpdateEmp, Float>("rating"));
		updateSalary.setCellValueFactory(new PropertyValueFactory<UpdateEmp, Float>("salary"));
		updateStart.setCellValueFactory(new PropertyValueFactory<UpdateEmp, LocalDate>("startDate"));
		updateEnd.setCellValueFactory(new PropertyValueFactory<UpdateEmp, LocalDate>("endDate"));

		updateTable.setRowFactory(tv -> {
			TableRow<UpdateEmp> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					UpdateEmp rowData = row.getItem();

					try {

						updateResults.clear();

						ResultSet r = Main.db.getEmpById(rowData.getId() + "");

						if (r.next()) {

							if (r.getString(14) == null)

								updateResults.add(new UpdateEmp(r.getString(11), r.getString(12), r.getString(2),
										r.getString(5), r.getFloat(8), r.getString(9), r.getFloat(10),
										LocalDate.parse(r.getString(13)), r.getString(15)));

							else
								updateResults.add(new UpdateEmp(r.getString(11), r.getString(12), r.getString(2),
										r.getString(5), r.getFloat(8), r.getString(9), r.getFloat(10),
										LocalDate.parse(r.getString(13)), LocalDate.parse(r.getString(14)),
										r.getString(15)));

							FXMLLoader loader = new FXMLLoader();
							loader.setLocation(Main.class.getResource("/application/UpdateEmp.fxml"));
							Pane p = loader.load();
							Scene s = new Scene(p, 710, 464);
							Stage stage = new Stage();
							stage.initStyle(StageStyle.UNDECORATED);
							stage.setTitle("Update Emp");
							stage.setScene(s);
							stage.show();

							Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
							stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
							stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

							updateTable.getItems().clear();
							name1.setText("");

						}

					} catch (SQLException | IOException e) {
						showError("Error!");
					}

				}
			});
			return row;
		});

	}

	static ArrayList<UpdateEmp> updateResults = new ArrayList<>();

	ObservableList<EmployeeRec> rec = FXCollections.observableArrayList();

	@FXML
	public void showDetails(KeyEvent event) {

		try {

			deleteTable.getItems().clear();

			if (!deleteName1.getText().equals("")) {

				ResultSet r = Main.db.getEmployee(deleteName1.getText());

				if (r == null)
					throw new SQLException();

				while (r.next()) {

					if (r.getString(14) == null) {

						rec.add(new EmployeeRec(r.getString(3), Long.parseLong(r.getString(6)), r.getString(2),
								r.getString(5), Float.parseFloat(r.getString(8)), r.getString(9),
								Float.parseFloat(r.getString(10)), LocalDate.parse(r.getString(13)), null,
								r.getString(15)));

					}

					else {
						rec.add(new EmployeeRec(r.getString(3), Long.parseLong(r.getString(6)), r.getString(2),
								r.getString(5), Float.parseFloat(r.getString(8)), r.getString(9),
								Float.parseFloat(r.getString(10)), LocalDate.parse(r.getString(13)),
								LocalDate.parse(r.getString(14)), r.getString(15)));
					}

				}

			}

			deleteTable.setItems(rec);

		} catch (SQLException e) {
			showError("Not Found!");
		}

	}

	private void showlogInConfirmation() throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/application/confirmLogin.fxml"));
		Pane p = loader.load();
		Scene s = new Scene(p, 309, 200);
		Stage stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setTitle("Confirm Login!");
		stage.setScene(s);
		stage.show();

		Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
		stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

	}

	@FXML
	public void deleteEmp(ActionEvent event) {

		try {

			if (ControlLogInConfirmation.flag != 1)
				showlogInConfirmation();

			if (ControlLogInConfirmation.flag != 0) {

				if (deleteTable.getSelectionModel().getSelectedItem() != null) {

					ResultSet r = Main.db.findIdPerson(deleteTable.getSelectionModel().getSelectedItem().getEmail());
					EmployeeRec temp = deleteTable.getSelectionModel().getSelectedItem();

					if (r.next()) {

						Main.db.deletePerson(r.getString(1));
					}

					showSuccess("Deleted Successfully!");

					deleteName1.setText("");
					deleteTable.getItems().removeAll(temp);

				}

				else
					throw new SQLException();

			}

		} catch (SQLException | IOException e) {

			showError("Please Select Row... Or Check that this employee is not associated in a Reservation!");
		}

	}

	// Find

	@FXML
	private TableView<EmployeeRec> findTable;

	@FXML
	private TableColumn<EmployeeRec, String> findName, findAddress, findPhone, findEmail, JOB;

	@FXML
	private TableColumn<EmployeeRec, Float> findRating, findSalary;

	@FXML
	private TableColumn<EmployeeRec, Long> findId;

	@FXML
	private TableColumn<EmployeeRec, LocalDate> findStartDate, findEndDate;

	@FXML
	private JFXTextField findName1;

	ObservableList<EmployeeRec> rec1 = FXCollections.observableArrayList();

	@FXML
	public void showFindResult(KeyEvent event) {

		try {

			findTable.getItems().clear();

			if (!findName1.getText().equals("")) {

				ResultSet r = Main.db.getEmployee(findName1.getText());

				if (r == null)
					throw new SQLException();

				while (r.next()) {

					if (r.getString(14) == null) {

						rec1.add(new EmployeeRec(r.getString(3), Long.parseLong(r.getString(6)), r.getString(2),
								r.getString(5), Float.parseFloat(r.getString(8)), r.getString(9),
								Float.parseFloat(r.getString(10)), LocalDate.parse(r.getString(13)), null,
								r.getString(15)));

					}

					else {
						rec1.add(new EmployeeRec(r.getString(3), Long.parseLong(r.getString(6)), r.getString(2),
								r.getString(5), Float.parseFloat(r.getString(8)), r.getString(9),
								Float.parseFloat(r.getString(10)), LocalDate.parse(r.getString(13)),
								LocalDate.parse(r.getString(14)), r.getString(15)));
					}

				}

			}

			findTable.setItems(rec1);

		} catch (SQLException e) {
			showError("Not Found!");
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
	public void clearInfo(ActionEvent event) {

		email.setText("");
		phone.setText("");
		rating.setText("");
		name.setText("");
		username.setText("");
		password.setText("");
		address.setText("");
		salary.setText("");
		DOB.setValue(null);
		startDate.setValue(null);
		rece.setSelected(false);
		cleaner.setSelected(false);

	}

	public void clearInfo() {
		email.setText("");
		phone.setText("");
		rating.setText("");
		name.setText("");
		username.setText("");
		password.setText("");
		address.setText("");
		salary.setText("");
		DOB.setValue(null);
		startDate.setValue(null);
		rece.setSelected(false);
		cleaner.setSelected(false);
	}

}
