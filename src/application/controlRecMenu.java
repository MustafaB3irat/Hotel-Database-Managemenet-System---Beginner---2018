package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class controlRecMenu {

	public void moveToLogIn(ActionEvent event) throws IOException{
		Main.setParent("LogIn",640,400,"Login Page");
	}

	public void moveToClient(ActionEvent event) throws IOException{
		Main.setParent("clientEdit",610,630,"Client ");
	}
	public void moveToMainMenu(MouseEvent event) throws IOException{
		Main.setParent("recMenu",700,600,"Receptionist Menu ");
	}
	public void moveToBill(ActionEvent event) throws IOException{
		Main.setParent("Bills",900,464,"Pay Bill");
	}

	public void moveToRoom(ActionEvent event) throws IOException{
		Main.setParent("Room",700,600,"Room");
	}
	
	public void moveToReserve(ActionEvent event) throws IOException{
		Main.setParent("Reservations",900,450,"Reserve");
	}
	public void moveToAvailableRooms(ActionEvent event) throws IOException{
		Main.setParent("AvailRoomsClient",640,400,"Available Rooms ");
	}
}
