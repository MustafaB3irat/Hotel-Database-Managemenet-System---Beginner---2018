package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class controlAvail implements Initializable {

	@FXML
	private ImageView gif;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		gif.setVisible(true);
		PauseTransition pt = new PauseTransition();
		pt.setDuration(Duration.seconds(1.5));

		pt.setOnFinished(ev -> {

			{
				try {
					Main.setParent("AvailRoomsClient", 640, 400, "Available Rooms!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			gif.setVisible(false);
		});
		pt.play();

	}

}
