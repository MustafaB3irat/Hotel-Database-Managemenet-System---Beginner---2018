package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class controlEmpMenu {
	
	public void moveToLogIn(ActionEvent event) throws IOException{
		Main.setParent("LogIn",700,520,"Login Page");
	}

	public void moveToClient(ActionEvent event) throws IOException{
		Main.setParent("clientEdit",610,630,"Client ");
	}
	public void moveToMainMenu(MouseEvent event) throws IOException{
		Main.setParent("employeesMenu",700,600,"Employees Menu ");
	}
	
	public void moveToRoom(ActionEvent event) throws IOException{
		Main.setParent("RoomEmp",700,600,"Room");
	}
	public void moveToAvailableRooms(ActionEvent event) throws IOException{
		Main.setParent("AvailableRooms",700,600,"Available Rooms ");
	}
}
