package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.ArrayList;

import com.jfoenix.controls.JFXDatePicker;
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
import net.sf.jasperreports.engine.JRException;

public class ControlBills {

	@FXML
	private JFXTextField CNameText, CName1, CName2, CName3;

	@FXML
	private JFXDatePicker DateIssued1, DateIssued2, DateIssued3;

	@FXML
	private TableView<Bill> PayBillTable, FindBillTable, UpdateBillTable, DeleteBillTable;

	@FXML
	private TableColumn<Bill, String> RoomNo, name, CNameColumn1, CNameColumn2, CNameColumn3, phone, CPhoneColumn1,
			CPhoneColumn2, CPhoneColumn3, dno, CDocNoColumn1, CDocNoColumn2, CDocNoColumn3, status, statusColumn1,
			statusColumn2, statusColumn3;

	@FXML
	private TableColumn<Bill, Integer> night, NightsColumn1, NightsColumn2, NightsColumn3, BillIdColumn1, BillIdColumn2,
			BillIdColumn3;

	@FXML
	private TableColumn<Bill, Float> cost, TotalCostColumn1, TotalCostColumn2, TotalCostColumn3;

	@FXML
	private TableColumn<Bill, LocalDate> FromColumn, ToColumn, DateIssuedColumn1, DateIssuedColumn2, DateIssuedColumn3;

	public void initialize() {

		FromColumn.setCellValueFactory(new PropertyValueFactory<Bill, LocalDate>("fromD"));
		ToColumn.setCellValueFactory(new PropertyValueFactory<Bill, LocalDate>("toD"));
		RoomNo.setCellValueFactory(new PropertyValueFactory<Bill, String>("room"));
		name.setCellValueFactory(new PropertyValueFactory<Bill, String>("name"));
		phone.setCellValueFactory(new PropertyValueFactory<Bill, String>("phone"));
		dno.setCellValueFactory(new PropertyValueFactory<Bill, String>("docNo"));
		status.setCellValueFactory(new PropertyValueFactory<Bill, String>("status"));
		cost.setCellValueFactory(new PropertyValueFactory<Bill, Float>("totalCost"));
		night.setCellValueFactory(new PropertyValueFactory<Bill, Integer>("nights"));

		// ---------------------------------------------

		CNameColumn1.setCellValueFactory(new PropertyValueFactory<Bill, String>("name"));
		CPhoneColumn1.setCellValueFactory(new PropertyValueFactory<Bill, String>("phone"));
		CDocNoColumn1.setCellValueFactory(new PropertyValueFactory<Bill, String>("docNo"));
		statusColumn1.setCellValueFactory(new PropertyValueFactory<Bill, String>("status"));
		TotalCostColumn1.setCellValueFactory(new PropertyValueFactory<Bill, Float>("totalCost"));
		BillIdColumn1.setCellValueFactory(new PropertyValueFactory<Bill, Integer>("billId"));
		NightsColumn1.setCellValueFactory(new PropertyValueFactory<Bill, Integer>("nights"));
		DateIssuedColumn1.setCellValueFactory(new PropertyValueFactory<Bill, LocalDate>("dateIssued"));

		// --------------------------------------------

		CNameColumn2.setCellValueFactory(new PropertyValueFactory<Bill, String>("name"));
		CPhoneColumn2.setCellValueFactory(new PropertyValueFactory<Bill, String>("phone"));
		CDocNoColumn2.setCellValueFactory(new PropertyValueFactory<Bill, String>("docNo"));
		statusColumn2.setCellValueFactory(new PropertyValueFactory<Bill, String>("status"));
		TotalCostColumn2.setCellValueFactory(new PropertyValueFactory<Bill, Float>("totalCost"));
		BillIdColumn2.setCellValueFactory(new PropertyValueFactory<Bill, Integer>("billId"));
		NightsColumn2.setCellValueFactory(new PropertyValueFactory<Bill, Integer>("nights"));
		DateIssuedColumn2.setCellValueFactory(new PropertyValueFactory<Bill, LocalDate>("dateIssued"));

		// ------------------------------------------------------

		CNameColumn3.setCellValueFactory(new PropertyValueFactory<Bill, String>("name"));
		CPhoneColumn3.setCellValueFactory(new PropertyValueFactory<Bill, String>("phone"));
		CDocNoColumn3.setCellValueFactory(new PropertyValueFactory<Bill, String>("docNo"));
		statusColumn3.setCellValueFactory(new PropertyValueFactory<Bill, String>("status"));
		TotalCostColumn3.setCellValueFactory(new PropertyValueFactory<Bill, Float>("totalCost"));
		BillIdColumn3.setCellValueFactory(new PropertyValueFactory<Bill, Integer>("billId"));
		NightsColumn3.setCellValueFactory(new PropertyValueFactory<Bill, Integer>("nights"));
		DateIssuedColumn3.setCellValueFactory(new PropertyValueFactory<Bill, LocalDate>("dateIssued"));

		UpdateBillTable.setRowFactory(tv -> {
			TableRow<Bill> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					Bill rowData = row.getItem();

					try {

						UpdateBill = rowData;

						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(Main.class.getResource("/application/UpdateBill.fxml"));
						Pane p = loader.load();
						Scene s = new Scene(p, 255, 200);
						Stage stage = new Stage();
						stage.initStyle(StageStyle.UNDECORATED);
						stage.setTitle("Update Reservation!");
						stage.setScene(s);
						stage.show();

						Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
						stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
						stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
						CName1.setText("");
						UpdateBillTable.getItems().clear();

					} catch (IOException e) {

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

	ObservableList<Bill> payBill = FXCollections.observableArrayList();

	@FXML
	public void issueBillByClientName(KeyEvent event) throws SQLException {

		if (PayBillTable.getItems() != null)
			PayBillTable.getItems().clear();

		if (!CNameText.getText().equals("")) {

			ResultSet r = Main.db.getpaymentByName(CNameText.getText());

			int nights = 0;
			float totalCost = 0f;
			String[] from = new String[3];
			String[] to = new String[3];

			while (r.next()) {

				from = r.getString(4).split("-");
				to = r.getString(5).split("-");

				nights = ((Integer.parseInt(to[0]) - Integer.parseInt(from[0])) * 360)
						+ ((Integer.parseInt(to[1]) - Integer.parseInt(from[1])) * 30)
						+ (Integer.parseInt(to[2]) - Integer.parseInt(from[2]));

				totalCost = Float.parseFloat(r.getString(6)) * nights;

				payBill.add(new Bill(r.getString(1), r.getString(3), r.getString(2), nights, totalCost, "Not Paid",
						LocalDate.parse(r.getString(4)), LocalDate.parse(r.getString(5)), r.getString(7)));

			}

			PayBillTable.setItems(payBill);

		}

	}

	@FXML
	public void payBills(ActionEvent event) {

		try {

			if (PayBillTable.getSelectionModel().getSelectedItem() == null)
				throw new Exception();

			Bill bill = PayBillTable.getSelectionModel().getSelectedItem();

			String cost = String.valueOf(bill.getTotalCost());
			String doc_no = bill.getDocNo();
			LocalDate From = bill.getFromD();
			LocalDate To = bill.getToD();
			String roomNu = bill.getRoom();

			ResultSet Cid = Main.db.getCid(doc_no);

			String cid = "";
			if (Cid.next())
				cid = Cid.getString(1);

			ResultSet r = Main.db.getbNum(roomNu, From, To, cid);

			String bnum = "";
			if (r.next())
				bnum = r.getString(1);

			Main.db.IssueBill(cost, cid, bnum);

			PayBillTable.getItems().remove(bill);
			CNameText.setText("");

		} catch (SQLIntegrityConstraintViolationException s) {

			AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("This Bill is already Paid");
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
			alert.setContentText("Please Select Bill From the Table");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();
		}
	}

	@FXML
	public void showReport(ActionEvent event) throws FileNotFoundException, JRException {

		try {

			Main.db.printReport();

		} catch (Exception e) {

			AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Please Select Bill From the Table");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();
		}
	}
	// -----Update Bill

	public static Bill UpdateBill;

	private ObservableList<Bill> update = FXCollections.observableArrayList();

	@FXML
	public void issueBillByClientNameUpdate(KeyEvent event) {

		try {

			if (UpdateBillTable.getItems() != null)
				UpdateBillTable.getItems().clear();

			if (!CName1.getText().equals("")) {

				ResultSet billId = Main.db.getpaidbills1(CName1.getText());

				ArrayList<Long> bills = new ArrayList<>();
				ArrayList<LocalDate> date_issued = new ArrayList<>();
				ArrayList<Float> paid = new ArrayList<>();

				while (billId.next()) {
					bills.add(billId.getLong(1));
					date_issued.add(LocalDate.parse(billId.getString(2)));
					paid.add(billId.getFloat(3));
				}

				ResultSet r = Main.db.getpaidbills(CName1.getText());

				int nights = 0;

				String[] from = new String[3];
				String[] to = new String[3];

				int i = 0;

				while (r.next()) {

					from = r.getString(4).split("-");
					to = r.getString(5).split("-");

					nights = ((Integer.parseInt(to[0]) - Integer.parseInt(from[0])) * 360)
							+ ((Integer.parseInt(to[1]) - Integer.parseInt(from[1])) * 30)
							+ (Integer.parseInt(to[2]) - Integer.parseInt(from[2]));

					update.add(new Bill(r.getString(1), r.getString(3), r.getString(2), bills.get(i), nights,
							paid.get(i), "Paid", date_issued.get(i)));
					i++;

				}

				UpdateBillTable.setItems(update);

			}
		} catch (Exception e) {

			AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Ops! Something Went Wrong!");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();

		}

	}

	@FXML
	public void issueBillByDateIssued(ActionEvent event) {

		try {

			if (UpdateBillTable.getItems() != null)
				UpdateBillTable.getItems().clear();

			if (DateIssued1.getValue() != null) {

				ResultSet bNum = Main.db.getbNumByDate(DateIssued1.getValue());

				ArrayList<String> bnum = new ArrayList<>();
				while (bNum.next()) {

					bnum.add(bNum.getString(1));
				}

				ArrayList<String> name = new ArrayList<>();
				ArrayList<String> doc_no = new ArrayList<>();
				ArrayList<String> phone = new ArrayList<>();
				ArrayList<LocalDate> from = new ArrayList<>();
				ArrayList<LocalDate> to = new ArrayList<>();
				ArrayList<Float> cost = new ArrayList<>();
				ArrayList<Long> rNum = new ArrayList<>();

				for (int i = 0; i < bnum.size(); i++) {

					ResultSet info1 = Main.db.getBillsByDate(bnum.get(i));

					while (info1.next()) {

						name.add(info1.getString(1));
						doc_no.add(info1.getString(2));
						phone.add(info1.getString(3));
						from.add(LocalDate.parse(info1.getString(4)));
						to.add(LocalDate.parse(info1.getString(5)));
						cost.add(info1.getFloat(6));
						rNum.add(info1.getLong(7));
					}

				}

				ResultSet r = Main.db.getBillsByDate1(DateIssued1.getValue());

				int j = 0;

				int nights = 0;

				String[] From = new String[3];
				String[] To = new String[3];

				while (r.next()) {

					From = from.get(j).toString().split("-");
					To = to.get(j).toString().split("-");

					nights = ((Integer.parseInt(To[0]) - Integer.parseInt(From[0])) * 360)
							+ ((Integer.parseInt(To[1]) - Integer.parseInt(From[1])) * 30)
							+ (Integer.parseInt(To[2]) - Integer.parseInt(From[2]));

					update.add(new Bill(name.get(j), phone.get(j), doc_no.get(j), r.getLong(1), nights,
							Float.parseFloat(r.getString(3)), "Paid", LocalDate.parse(r.getString(2))));
					j++;

				}

				UpdateBillTable.setItems(update);

			}
		} catch (Exception e) {

			AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Ops! Something Went Wrong!");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();

		}

	}

	// -------------------------------Find Bill;

	ObservableList<Bill> find = FXCollections.observableArrayList();

	@FXML
	public void issueBillByClientNameFind(KeyEvent event) {

		try {

			if (FindBillTable.getItems() != null)
				FindBillTable.getItems().clear();

			if (!CName2.getText().equals("")) {

				ResultSet billId = Main.db.getpaidbills1(CName2.getText());

				ArrayList<Long> bills = new ArrayList<>();
				ArrayList<LocalDate> date_issued = new ArrayList<>();
				ArrayList<Float> paid = new ArrayList<>();

				while (billId.next()) {
					bills.add(billId.getLong(1));
					date_issued.add(LocalDate.parse(billId.getString(2)));
					paid.add(billId.getFloat(3));
				}

				ResultSet r = Main.db.getpaidbills(CName2.getText());

				int nights = 0;

				String[] from = new String[3];
				String[] to = new String[3];

				int i = 0;

				while (r.next()) {

					from = r.getString(4).split("-");
					to = r.getString(5).split("-");

					nights = ((Integer.parseInt(to[0]) - Integer.parseInt(from[0])) * 360)
							+ ((Integer.parseInt(to[1]) - Integer.parseInt(from[1])) * 30)
							+ (Integer.parseInt(to[2]) - Integer.parseInt(from[2]));

					find.add(new Bill(r.getString(1), r.getString(3), r.getString(2), bills.get(i), nights, paid.get(i),
							"Paid", date_issued.get(i)));
					i++;

				}

				FindBillTable.setItems(find);

			}
		} catch (Exception e) {

			AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Ops! Something Went Wrong!");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();

		}

	}

	@FXML
	public void issueBillByDateIssuedFind(ActionEvent event) {

		try {

			if (FindBillTable.getItems() != null)
				FindBillTable.getItems().clear();

			if (DateIssued2.getValue() != null) {

				ResultSet bNum = Main.db.getbNumByDate(DateIssued2.getValue());

				ArrayList<String> bnum = new ArrayList<>();
				while (bNum.next()) {

					bnum.add(bNum.getString(1));
				}

				ArrayList<String> name = new ArrayList<>();
				ArrayList<String> doc_no = new ArrayList<>();
				ArrayList<String> phone = new ArrayList<>();
				ArrayList<LocalDate> from = new ArrayList<>();
				ArrayList<LocalDate> to = new ArrayList<>();
				ArrayList<Float> cost = new ArrayList<>();
				ArrayList<Long> rNum = new ArrayList<>();

				for (int i = 0; i < bnum.size(); i++) {

					ResultSet info1 = Main.db.getBillsByDate(bnum.get(i));

					while (info1.next()) {

						name.add(info1.getString(1));
						doc_no.add(info1.getString(2));
						phone.add(info1.getString(3));
						from.add(LocalDate.parse(info1.getString(4)));
						to.add(LocalDate.parse(info1.getString(5)));
						cost.add(info1.getFloat(6));
						rNum.add(info1.getLong(7));
					}

				}

				ResultSet r = Main.db.getBillsByDate1(DateIssued2.getValue());

				int j = 0;

				int nights = 0;

				String[] From = new String[3];
				String[] To = new String[3];

				while (r.next()) {

					From = from.get(j).toString().split("-");
					To = to.get(j).toString().split("-");

					nights = ((Integer.parseInt(To[0]) - Integer.parseInt(From[0])) * 360)
							+ ((Integer.parseInt(To[1]) - Integer.parseInt(From[1])) * 30)
							+ (Integer.parseInt(To[2]) - Integer.parseInt(From[2]));

					find.add(new Bill(name.get(j), phone.get(j), doc_no.get(j), r.getLong(1), nights,
							Float.parseFloat(r.getString(3)), "Paid", LocalDate.parse(r.getString(2))));
					j++;

				}

				FindBillTable.setItems(find);

			}
		} catch (Exception e) {

			AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Ops! Something Went Wrong!");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();

		}

	}

	// -------------------------------------------------------

	ObservableList<Bill> delete = FXCollections.observableArrayList();

	@FXML
	public void issueBillByClientNameDelete(KeyEvent event) {

		try {

			if (DeleteBillTable.getItems() != null)
				DeleteBillTable.getItems().clear();

			if (!CName3.getText().equals("")) {

				ResultSet billId = Main.db.getpaidbills1(CName3.getText());

				ArrayList<Long> bills = new ArrayList<>();
				ArrayList<LocalDate> date_issued = new ArrayList<>();
				ArrayList<Float> paid = new ArrayList<>();

				while (billId.next()) {
					bills.add(billId.getLong(1));
					date_issued.add(LocalDate.parse(billId.getString(2)));
					paid.add(billId.getFloat(3));
				}

				ResultSet r = Main.db.getpaidbills(CName3.getText());

				int nights = 0;

				String[] from = new String[3];
				String[] to = new String[3];

				int i = 0;

				while (r.next()) {

					from = r.getString(4).split("-");
					to = r.getString(5).split("-");

					nights = ((Integer.parseInt(to[0]) - Integer.parseInt(from[0])) * 360)
							+ ((Integer.parseInt(to[1]) - Integer.parseInt(from[1])) * 30)
							+ (Integer.parseInt(to[2]) - Integer.parseInt(from[2]));

					delete.add(new Bill(r.getString(1), r.getString(3), r.getString(2), bills.get(i), nights,
							paid.get(i), "Paid", date_issued.get(i)));
					i++;

				}

				DeleteBillTable.setItems(delete);

			}
		} catch (Exception e) {

			AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Ops! Something Went Wrong!");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();

		}

	}

	@FXML
	public void issueBillByDateIssuedDelete(ActionEvent event) {

		try {

			if (DeleteBillTable.getItems() != null)
				DeleteBillTable.getItems().clear();

			if (DateIssued3.getValue() != null) {

				ResultSet bNum = Main.db.getbNumByDate(DateIssued3.getValue());

				ArrayList<String> bnum = new ArrayList<>();
				while (bNum.next()) {

					bnum.add(bNum.getString(1));
				}

				ArrayList<String> name = new ArrayList<>();
				ArrayList<String> doc_no = new ArrayList<>();
				ArrayList<String> phone = new ArrayList<>();
				ArrayList<LocalDate> from = new ArrayList<>();
				ArrayList<LocalDate> to = new ArrayList<>();
				ArrayList<Float> cost = new ArrayList<>();
				ArrayList<Long> rNum = new ArrayList<>();

				for (int i = 0; i < bnum.size(); i++) {

					ResultSet info1 = Main.db.getBillsByDate(bnum.get(i));

					while (info1.next()) {

						name.add(info1.getString(1));
						doc_no.add(info1.getString(2));
						phone.add(info1.getString(3));
						from.add(LocalDate.parse(info1.getString(4)));
						to.add(LocalDate.parse(info1.getString(5)));
						cost.add(info1.getFloat(6));
						rNum.add(info1.getLong(7));
					}

				}

				ResultSet r = Main.db.getBillsByDate1(DateIssued3.getValue());

				int j = 0;

				int nights = 0;

				String[] From = new String[3];
				String[] To = new String[3];

				while (r.next()) {

					From = from.get(j).toString().split("-");
					To = to.get(j).toString().split("-");

					nights = ((Integer.parseInt(To[0]) - Integer.parseInt(From[0])) * 360)
							+ ((Integer.parseInt(To[1]) - Integer.parseInt(From[1])) * 30)
							+ (Integer.parseInt(To[2]) - Integer.parseInt(From[2]));

					delete.add(new Bill(name.get(j), phone.get(j), doc_no.get(j), r.getLong(1), nights,
							Float.parseFloat(r.getString(3)), "Paid", LocalDate.parse(r.getString(2))));
					j++;

				}

				DeleteBillTable.setItems(delete);

			}
		} catch (Exception e) {

			AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Ops! Something Went Wrong!");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();

		}

	}

	@FXML
	public void deleteBill(ActionEvent event) {

		try {

			if (DeleteBillTable.getSelectionModel().getSelectedItem() == null)
				throw new Exception();

			Bill Cell = DeleteBillTable.getSelectionModel().getSelectedItem();

			Main.db.deleteBill(Cell.getBillId().toString());

			DeleteBillTable.getItems().removeAll(Cell);

			AudioClip Audio = new AudioClip("file:src/application/Success!.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("SuccessFully Deleted!");
			alert.setTitle("Done!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
					.add(new Image("/application/confirm.png"));
			alert.showAndWait();
			
			

		} catch (Exception e) {

			AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
			Audio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Please Select Bill To Delete..");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();

		}
	}

	// -------------------------------

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
