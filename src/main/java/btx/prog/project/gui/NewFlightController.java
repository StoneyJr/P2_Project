package btx.prog.project.gui;

import btx.prog.project.domain.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * This is the class controlling the new flight page. It forwards its
 * information to the flight service class. It saves all the information to create a flight.
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */

public class NewFlightController implements Initializable {

    @FXML
    private ChoiceBox<Pilot> choosePilotChoiceBox;
    @FXML
    private ListView<FlightAttendant> flightAttendantList;
    @FXML
    private ComboBox<Airport> pickDestinationComboBox;
    @FXML
    private ComboBox<Airport> startingPointComboBox;
    @FXML
    private DatePicker departureDatePicker;
    @FXML
    private TextField departureTimeTextField;
    @FXML
    private ComboBox<Airplane> pickPlaneComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // füllt ComboBox mit AirplaneIds
        List<Airplane> airplanes = GUI.flightService.getPlanes();
        this.pickPlaneComboBox.getItems().addAll(airplanes);

        // füllt die ComboBox mit StartingPoints und Destination
        List<Airport> airports = GUI.flightService.getAirports();
        this.startingPointComboBox.getItems().addAll(airports);
        this.pickDestinationComboBox.getItems().addAll(airports);

        // füllt die Liste mit Flight Attendants und gibt an das man mehrere wählen kann
        List<FlightAttendant> flightAttendant = GUI.flightService.getFlightAttendants();
        this.flightAttendantList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.flightAttendantList.getItems().addAll(flightAttendant);

        // füllt die ChoiceBox mit Piloten
        List<Pilot> pilots = GUI.flightService.getPilots();
        this.choosePilotChoiceBox.getItems().setAll(pilots);
    }

    public Airplane getAirplane(ActionEvent actionEvent) {
        return this.pickPlaneComboBox.getValue();
    }

    public Airport getDestination() {
        return this.pickDestinationComboBox.getValue();
    }

    public Airport getStartingPoint() {
        return this.startingPointComboBox.getValue();
    }

    public LocalDate getDepartureDate(ActionEvent actionEvent) {
        return this.departureDatePicker.getValue();
    }

    public String getDepartureTime(ActionEvent actionEvent) {
        return this.departureTimeTextField.getText();
    }

    public List<Employee> chooseFlightAttendant() {
        return new ArrayList<Employee>(this.flightAttendantList.getSelectionModel().getSelectedItems());
    }

    public void saveFlight(ActionEvent actionEvent) {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.addAll(chooseFlightAttendant());
        employeeList.add(this.choosePilotChoiceBox.getValue());
        List<UUID> uuidList = new ArrayList<>();
        for (Employee employee : employeeList) {
            uuidList.add(employee.getFullPersonId());
        }
        GUI.flightService.registerFlight(uuidList, this.pickPlaneComboBox.getValue(), getStartingPoint(),
                getDestination(), putDepartureDateTogether());

        URL homepage = this.getClass().getResource("homepage.fxml");
        FXMLLoader loader = new FXMLLoader(homepage);
        Parent homeView;
        try {
            homeView = loader.load();
            Scene homepageScene = new Scene(homeView);
            Stage homeWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            homeWindow.setScene(homepageScene);
            homeWindow.show();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    public LocalDateTime putDepartureDateTogether() {
        LocalDate departure = this.departureDatePicker.getValue();
        LocalTime departureHourMinute = LocalTime.parse(this.departureTimeTextField.getText());
        LocalDateTime allTogether = departure.atTime(departureHourMinute);
        return allTogether;
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
