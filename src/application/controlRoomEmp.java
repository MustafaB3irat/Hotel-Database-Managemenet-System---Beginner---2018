//package application;
//
//import java.io.IOException;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.TextField;
//import javafx.scene.control.Alert.AlertType;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.input.MouseEvent;
//
//public class controlRoomEmp {
//
//	@FXML
//	private TextField rId, price, beds, floor, size, class1, status;
//
//	public void clearInfo(ActionEvent event) {
//		rId.setText("");
//		price.setText("");
//		beds.setText("");
//		floor.setText("");
//		size.setText("");
//		class1.setText("");
//		status.setText("");
//	}
//
//	@FXML
//	public void update(ActionEvent a) throws SQLException {
//		ResultSet r = Main.db.findRoom(rId.getText());
//		if (r.first()) {
//			Main.db.updateRoom(rId.getText(), beds.getText(), price.getText(), size.getText(), floor.getText(),
//					class1.getText(), status.getText());
//		} else {
//			Alert alert = new Alert(AlertType.ERROR);
//			alert.setTitle("Error !");
//			alert.setHeaderText("Room does not exist");
//
//			alert.showAndWait();
//		}
//	}
//
//	@FXML
//	public void insert(ActionEvent a) throws SQLException {
//		ResultSet r = Main.db.findRoom(rId.getText());
//		if (!r.first()) {
//			Main.db.insertRoom(rId.getText(), beds.getText(), price.getText(), size.getText(), floor.getText(),
//					class1.getText(), status.getText());
//		} else {
//			Alert alert = new Alert(AlertType.ERROR);
//			alert.setTitle("Error !");
//			alert.setHeaderText("there is room with same id");
//
//			alert.showAndWait();
//		}
//	}
//
//	@FXML
//	public void delete(ActionEvent a) throws SQLException {
//		ResultSet r = Main.db.findRoom(rId.getText());
//		if (r.first()) {
//			Main.db.deleteRoom(rId.getText());
//		} else {
//			Alert alert = new Alert(AlertType.ERROR);
//			alert.setTitle("Error !");
//			alert.setHeaderText("Room does not exist");
//
//			alert.showAndWait();
//		}
//	}
//
//	public void handleRidChange(KeyEvent k) {
//		try {
//			ResultSet r = Main.db.findRoom(rId.getText());
//			r.first();
//			price.setText(r.getString(3));
//			beds.setText(r.getString(2));
//			floor.setText(r.getString(4));
//			size.setText(r.getString(5));
//			class1.setText(r.getString(6));
//			status.setText(r.getString(7));
//
//		} catch (SQLException | NullPointerException e) {
//			price.setText(null);
//			beds.setText(null);
//			floor.setText(null);
//			size.setText(null);
//			class1.setText(null);
//			status.setText(null);
//		}
//	}
//
//	public void moveToClient(ActionEvent event) throws IOException {
//		Main.setParent("clientEdit", 610, 630, "Client ");
//	}
//
//	public void moveToMainMenu(MouseEvent event) throws IOException {
//		Main.setParent("employeesMenu", 700, 600, "Employees Menu ");
//	}
//
//	public void moveToRoom(ActionEvent event) throws IOException {
//		Main.setParent("RoomEmp", 700, 600, "Room");
//	}
//
//	public void moveToAvailableRooms(ActionEvent event) throws IOException {
//		Main.setParent("AvailableRooms", 700, 600, "Available Rooms ");
//	}
//
//}
