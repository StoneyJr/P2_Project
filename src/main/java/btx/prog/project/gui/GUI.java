package btx.prog.project.gui;

import java.io.File;
import java.net.URL;

import btx.prog.project.service.FlightService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the class which launches our graphic user interface. The main stage
 * is created in this class. Besides launching the homepage, it saves the data from the
 * graphic user interface into a Json-file.
 *
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */

public class GUI extends Application {

	protected static FlightService flightService;

	private static String ROOT_DOCUMENTS = System.getProperty("user.home") + File.separator + "documents";
	private final static String PLANES_FILE_NAME = "planes.json";
	private final static String PLANES_LOCATION = ROOT_DOCUMENTS + File.separator + PLANES_FILE_NAME;

	private final static String FLIGHATTENDANTS_FILE_NAME = "flightattendants.json";
	private final static String FLIGHATTENDANTS_LOCATION = ROOT_DOCUMENTS + File.separator + FLIGHATTENDANTS_FILE_NAME;

	private final static String PILOTS_FILE_NAME = "pilots.json";
	private final static String PILOTS_LOCATION = ROOT_DOCUMENTS + File.separator + PILOTS_FILE_NAME;

	private final static String FLIGHTS_FILE_NAME = "flights.json";
	private final static String FLIGHTS_LOCATION = ROOT_DOCUMENTS + File.separator + FLIGHTS_FILE_NAME;

	// Homepage wird geladen aus fxml -File
	@Override
	public void start(Stage stage) throws Exception {

		URL url = this.getClass().getClassLoader().getResource("homepage.fxml");

		System.out.println(url.toString()); // drin lassen so kann ich testen ob es das File findet
		FXMLLoader loader = new FXMLLoader(url);
		Parent root = loader.load();
		Scene s = new Scene(root);
		stage.setScene(s);
		stage.setTitle("Welcome to Little Fly");
		stage.show();

	}

	public static void main(String[] args) {
		try {
			GUI.flightService = new FlightService(GUI.PLANES_LOCATION, GUI.PILOTS_LOCATION,
					GUI.FLIGHATTENDANTS_LOCATION, GUI.FLIGHTS_LOCATION);
			launch(args);
		} catch (Exception ex) {
			System.err.println("Cannot initialize application: ");
			System.err.println(ex.getCause());
			System.err.println("Close application");
			return;
		}

	}

}
