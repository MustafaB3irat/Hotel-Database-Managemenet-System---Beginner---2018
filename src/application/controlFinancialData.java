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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class controlFinancialData {

	@FXML
	private Label fromLabel, toLabel, totalLabel, total;

	@FXML
	private Rectangle totalBox;

	@FXML
	private JFXDatePicker from;

	@FXML
	private JFXDatePicker to;

	@FXML
	private TableView<FinancialData> totalIncomeTable;

	@FXML
	private TableColumn<FinancialData, Long> bID;
	@FXML
	private TableColumn<FinancialData, Float> amount;

	@FXML
	public void clearInfo(MouseEvent event) {

		from.setValue(null);
		to.setValue(null);

		if (totalIncomeTable.getItems() != null)
			totalIncomeTable.getItems().clear();

		fromLabel.setVisible(true);
		toLabel.setVisible(true);
		total.setVisible(false);
		totalBox.setVisible(false);
		total.setVisible(false);
		totalLabel.setVisible(false);

	}

	@FXML
	public void hideFromLabel(MouseEvent event) {

		fromLabel.setVisible(false);
	}

	@FXML
	public void hideToLabel(MouseEvent event) {

		toLabel.setVisible(false);
	}

	ObservableList<FinancialData> table1 = FXCollections.observableArrayList();

	ObservableList<Salary> table2 = FXCollections.observableArrayList();

	@FXML
	public void showTotalIncome(ActionEvent a) {

		float sum = 0;

		try {

			if ((from.getValue() == null || to.getValue() == null))
				throw new Exception();

			else {
				ResultSet r = Main.db.findTotalIncome(from.getValue(), to.getValue());

				while (r.next()) {
					sum = sum + Float.parseFloat((r.getString(3)));

					table1.add(new FinancialData(Long.parseLong((r.getString(1))), Float.parseFloat(r.getString(3))));

				}
				if (table1.size() == 0)

					throw new Exception();

				total.setText(sum + "");
				total.setVisible(true);
				totalBox.setVisible(true);
				total.setVisible(true);
				totalLabel.setVisible(true);

				totalIncomeTable.setItems(table1);
			}

		}

		catch (Exception e) {

			AudioClip iudio = new AudioClip("file:src/application/Error.mp3");
			iudio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Empty Set! , Check the Entries...");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();

		}

	}

	public void initialize() {

		totalBox.setVisible(false);
		total.setVisible(false);
		totalLabel.setVisible(false);

		bID.setCellValueFactory(new PropertyValueFactory<FinancialData, Long>("no"));
		amount.setCellValueFactory(new PropertyValueFactory<FinancialData, Float>("amount"));

		Ename.setCellValueFactory(new PropertyValueFactory<Salary, String>("Ename"));
		salary.setCellValueFactory(new PropertyValueFactory<Salary, Float>("salary"));
		job.setCellValueFactory(new PropertyValueFactory<Salary, String>("job"));

	}

	// ------------------Second Tab Pane

	@FXML
	private TableView<Salary> salaryTable;

	@FXML
	private TableColumn<Salary, String> Ename;
	@FXML
	private TableColumn<Salary, Float> salary;
	@FXML
	private TableColumn<Salary, String> job;

	@FXML
	public void clearInfoO(MouseEvent event) {
		salaryTable.getItems().clear();
	}

	@FXML
	public void showSalaries(ActionEvent a) throws SQLException {

		salaryTable.getItems().clear();

		ResultSet r = Main.db.findSalaries();

		while (r.next()) {

			table2.add(new Salary(r.getString(1), Float.parseFloat(r.getString(2)), r.getString(3)));

		}
		if (table2.size() == 0) {

			AudioClip iudio = new AudioClip("file:src/application/Error.mp3");
			iudio.play();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Empty!");
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setResizable(false);

			alert.initModality(Modality.APPLICATION_MODAL);

			((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/application/Error.png"));
			alert.showAndWait();
		}

		salaryTable.setItems(table2);

	}

	public void moveToMainMenu(MouseEvent event) throws IOException {
		Main.setParent("Manager", 640, 400, "Manager Menu ");
	}

}
