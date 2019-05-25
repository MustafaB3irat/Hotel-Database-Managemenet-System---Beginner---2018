package application;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ControlLogInConfirmation {

	@FXML
	private JFXTextField USERNAME;

	public static int flag = 0;

	@FXML
	private JFXPasswordField PASSWORD;

	@FXML
	private Label incorrect;

	int counter = 1;

	@FXML
	public void confirmLogIn(ActionEvent event) {

		try {

			Boolean c = Main.db.confirmLogin(USERNAME.getText(), PASSWORD.getText());

			if (counter > 3) {

				Node s = (Node) event.getSource();
				((Stage) s.getScene().getWindow()).close();

				Main.setParent("LogIn", 640, 400, "Log In!");

			}

			if (c == false)
				throw new Exception();

			incorrect.setVisible(false);
			flag = 1;
			Node s = (Node) event.getSource();
			((Stage) s.getScene().getWindow()).close();

		} catch (Exception e) {

			USERNAME.setText("");
			PASSWORD.setText("");

			counter++;
			incorrect.setVisible(true);

		}

	}

}
