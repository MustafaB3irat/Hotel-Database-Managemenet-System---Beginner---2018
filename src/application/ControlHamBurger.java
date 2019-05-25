package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ControlHamBurger {

	@FXML
	public void moveToAvailableRooms(ActionEvent event) throws IOException {
		Main.setParent("CheckRooms", 640, 440, "Edit Rooms! ");
	}

	@FXML
	public void moveToLogIn(ActionEvent event) throws IOException {
		Main.setParent("LogIn", 640, 400, "Login Page");
	}

	@FXML
	public void moveToPerson(ActionEvent event) throws IOException {
		Main.setParent("personManeger", 520, 235, "Person ");
	}
	
	@FXML
	public void moveToEmployees(ActionEvent event) throws IOException {
		
		Main.setParent("insertEmp", 700, 450, "Employees!");
	}
	
	@FXML
	public void moveToEditClean(ActionEvent event) throws IOException {

		Main.setParent("editCleanHistory", 640, 440, "Edit Cleaning History!");
	}

}
