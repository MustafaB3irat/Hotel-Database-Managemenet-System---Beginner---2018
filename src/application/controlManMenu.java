package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class controlManMenu implements Initializable {

	@FXML
	private JFXHamburger ham1;

	@FXML
	private JFXDrawer drawer1;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		init1();
	}

	private void init1() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/DrawerContent1.fxml"));
			VBox box = loader.load();

			drawer1.setSidePane(box);
		} catch (IOException ex) {

		}

		HamburgerBasicCloseTransition transition = new HamburgerBasicCloseTransition(ham1);

		transition.setRate(-1);

		ham1.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
			transition.setRate(transition.getRate() * -1);
			transition.play();

			if (drawer1.isOpened()) {
				drawer1.close();
			} else {
				drawer1.open();
			}
		});
	}

	public void moveToSalary(ActionEvent event) throws IOException {
		Main.setParent("salaries", 700, 600, "salaries");
	}

	public void moveToIncome(ActionEvent event) throws IOException {
		Main.setParent("totalincome", 700, 600, "Total Income");
	}

	public void moveToMainMenu(MouseEvent event) throws IOException {
		Main.setParent("Manager", 640, 400, "Manager Menu ");
	}

	public void moveToRoom(ActionEvent event) throws IOException {
		Main.setParent("RoomMan", 700, 600, "Room");
	}

	public void moveToFinancialData(ActionEvent event) throws IOException {

		Main.setParent("FinancialData", 640, 400, "Financial Data");
	}

	public void moveToRooms(ActionEvent event) throws IOException {

		Main.setParent("Rooms", 640, 400, "Rooms");
	}

	public void moveToCleanHistory(ActionEvent event) throws IOException {

		Main.setParent("CleanHistory", 640, 400, "Clean Histroy!");
	}

}
