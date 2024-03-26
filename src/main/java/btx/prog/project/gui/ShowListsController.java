package btx.prog.project.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import btx.prog.project.domain.Airplane;
import btx.prog.project.domain.Booking;
import btx.prog.project.domain.Employee;
import btx.prog.project.domain.Flight;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * This is the class controlling the list page. It gets all its information
 * from flight service. All the new data entered from the other forms can be printed here.
 *
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */

public class ShowListsController implements Initializable {
	@FXML
	private ListView<Employee> employeeList;
	@FXML
	private ListView<Flight> flightList;
	@FXML
	private ListView<Airplane> airplaneList;
	@FXML
	private ComboBox<Flight> pickAFlightComboBox;
	@FXML
	private ListView<Booking> bookingList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<Flight> flights = GUI.flightService.getFlights();
		this.flightList.getItems().addAll(flights);
		this.pickAFlightComboBox.getItems().setAll(GUI.flightService.getFlights());
	}

	public void pickFlight(ActionEvent actionEvent) {
		Flight flight = this.pickAFlightComboBox.getValue();
		List<Booking> booking = GUI.flightService.getBooking(flight);
		this.bookingList.getItems().setAll(booking);
	}

	public void changeToAirplaneList(Event event) {
		List<Airplane> airplane = GUI.flightService.getPlanes();
		this.airplaneList.getItems().setAll(airplane);
	}

	public void changeToEmployeeList(Event event) {
		List<Employee> employee = new ArrayList<>();
		employee.addAll(GUI.flightService.getFlightAttendants());
		employee.addAll(GUI.flightService.getPilots());
		this.employeeList.getItems().setAll(employee);
	}

	public void returnToHomepage(ActionEvent actionEvent) throws IOException {
		URL homepage = this.getClass().getClassLoader().getResource("homepage.fxml");
		FXMLLoader loader = new FXMLLoader(homepage);
		Parent homeView = loader.load();
		Scene homepageScene = new Scene(homeView);
		Stage homeWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		homeWindow.setScene(homepageScene);
		homeWindow.show();
	}

}
