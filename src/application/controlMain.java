package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class controlMain {

	@FXML
	public void moveToLoginPage(MouseEvent e) throws IOException {

		Main.setParent("LogIn", 640, 400, "Login Page");
	}

	@FXML
	public void moveToClientMenu(MouseEvent e) throws IOException {
		Main.setParent("AvailableRooms", 640, 400, "Client Menu");
	}

	@FXML
	public void exit(ActionEvent event) throws IOException {
		Main.hide();
	}

	@FXML
	public void minimize(ActionEvent event) {

		((Stage) ((Button) event.getSource()).getScene().getWindow()).setIconified(true);
	}
}
