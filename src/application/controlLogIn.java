package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import databaseAPI.DBHandler.login_type;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class controlLogIn {

	@FXML
	private TextField username;

	public static String Eid;

	@FXML
	private PasswordField password;

	@FXML
	private ImageView loading;

	@FXML
	private Button login;

	@FXML
	public void exit(ActionEvent event) throws IOException {
		Main.hide();
	}

	@FXML
	public void handleMinimizeBTAction(ActionEvent event) {
		((Stage) ((Button) event.getSource()).getScene().getWindow()).setIconified(true);
	}

	@FXML
	public void doLogin(ActionEvent a) throws IOException {

		login.setVisible(false);

		loading.setVisible(true);
		username.setEditable(false);

		PauseTransition pt = new PauseTransition();
		pt.setDuration(Duration.seconds(3));

		pt.setOnFinished(ev -> {

			{

				try {
					username.setEditable(true);
					login_type t = Main.db.getLoginType(username.getText(), password.getText());
					ResultSet s = Main.db.getEidFromUserName(username.getText());

					if (s.next())
						Eid = s.getString(1);

					switch (t) {
					case MANAGER:
						Main.setParent("Manager", 640, 400, "Manager Menu");
						break;
					case RECEPTIONIST: {

						Main.setParent("ReceptioniestMenu", 640, 400, "recMenu");
					}
						break;
					case Cleaner: {
						AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
						Audio.play();

						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("You Don't Have Access To the Program!");
						alert.setTitle("Error!");
						alert.setHeaderText(null);
						alert.setResizable(false);

						alert.initModality(Modality.APPLICATION_MODAL);

						((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
								.add(new Image("/application/Error.png"));
						alert.showAndWait();
					}
						break;
					default:
						break;
					}

				}catch (SQLException e) {
					AudioClip Audio = new AudioClip("file:src/application/Error.mp3");
					Audio.play();
					pt.stop();
					loading.setVisible(false);
					login.setVisible(true);

					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Wrong Password or Uername!");
					alert.setTitle("Error!");
					alert.setHeaderText(null);
					alert.setResizable(false);

					alert.initModality(Modality.APPLICATION_MODAL);

					((Stage) alert.getDialogPane().getScene().getWindow()).getIcons()
							.add(new Image("/application/Error.png"));
					alert.show();

				} catch (IOException e) {

				}

			}

			loading.setVisible(false);
			login.setVisible(true);
		});
		pt.play();

	}

	@FXML
	public void moveToMain(MouseEvent event) throws IOException {

		Main.setParent("Main", 640, 400, "Royal Hotel!");

	}

}
