package btx.prog.project.gui;

import btx.prog.project.domain.Flight;
import btx.prog.project.domain.Passenger;
import btx.prog.project.domain.Person;
import btx.prog.project.domain.Seat;
import btx.prog.project.validation.EmailValidator;
import btx.prog.project.validation.InvalidInputException;
import btx.prog.project.validation.PhoneValidator;
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
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This is the class controlling the new booking page. It forwards its
 * information to the flight service class. Additionally it saves a new booking. It validates the
 * email and phonenumber input from the user.
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */

public class NewBookingsController implements Initializable {
    @FXML
    private Label wrongInputLabel;
    @FXML
    private ChoiceBox<Person.Gender> genderChoiceBox;
    @FXML
    private ComboBox<Seat> chooseSeatComboBox;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private TextField lastNameField;
    @FXML
    private ComboBox<Flight> chooseFlightComboBox;

    EmailValidator emailValidator = new EmailValidator();
    PhoneValidator phoneValidator = new PhoneValidator();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Flight> flights = GUI.flightService.getFlights();
        this.chooseFlightComboBox.getItems().setAll(flights);

        this.genderChoiceBox.getItems().setAll(Person.Gender.values());
    }

    public void refreshSeatList(ActionEvent actionEvent) {
        Map<Integer, Seat> seats = GUI.flightService
                .getFreeSeats(chooseFlightComboBox.getValue().getAirplane());
        this.chooseSeatComboBox.getItems().setAll(seats.values());
    }

    public String getFirstName() {
        return this.firstnameField.getText();
    }

    public String getLastName() {
        return this.lastNameField.getText();
    }

    public Person.Gender getGender() {
        return this.genderChoiceBox.getValue();
    }

    public LocalDate getBirthday() {
        return this.birthDatePicker.getValue();
    }

    public String getPhoneNumber() {
        return this.phoneNumberTextField.getText();
    }

    public String getEmail() {
        return this.emailTextField.getText();
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


    public void saveBooking(ActionEvent actionEvent) {


        this.wrongInputLabel.setText("");

        try {
            this.emailValidator.validate(getEmail());
        } catch (InvalidInputException e) {
            this.wrongInputLabel.setText("Incorrect email input! Please give in format: max.musterfrau@bfh.ch");
            return;
        }
        try {
            this.phoneValidator.validate(getPhoneNumber());
        } catch (InvalidInputException e) {
            this.wrongInputLabel.setText("Incorrect phone number! ");
            return;
        }
        if (firstnameField.getText().isEmpty() || lastNameField.getText().isEmpty()) {
            this.wrongInputLabel.setText("Please enter a name:");
            return;
        }

        Passenger passenger = GUI.flightService.makePassenger(getFirstName(), getLastName(), getGender(),
                getBirthday(), getPhoneNumber(), getEmail());

        GUI.flightService.registerBooking(this.chooseFlightComboBox.getValue(), this.chooseSeatComboBox.getValue(),
                passenger);
        System.out.println("new Booking register");

        URL homepage = this.getClass().getClassLoader().getResource("homepage.fxml");
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

}
