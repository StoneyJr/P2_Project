package btx.prog.project.gui;

import btx.prog.project.domain.FlightAttendant;
import btx.prog.project.domain.Person;
import btx.prog.project.domain.Pilot;
import btx.prog.project.validation.DoubleRangeValidator;
import btx.prog.project.validation.InvalidInputException;
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
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This is the class controlling the new employee page. It forwards its
 * information to the flight service class. It can save pilots and flight attendants.
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */

public class NewEmployeeController implements Initializable {

    private enum EmployeeTyp { // für die ComboBox
        PILOT, FLIGHT_ATTENDANT;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    @FXML
    private Label wrongInputLabel;
    @FXML
    private ComboBox<EmployeeTyp> chooseEmployeeTypComboBox;
    @FXML
    private ListView<FlightAttendant.Language> languageList;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private ChoiceBox<Person.Gender> genderChoiceBox;
    @FXML
    private DatePicker birthdatePicker;
    @FXML
    private DatePicker employeeDatePicker;
    @FXML
    private TextField salaryTextField;
    @FXML
    private ComboBox<Pilot.Rank> chooseRankOfPilotComboBox;

    DoubleRangeValidator doubleRangeValidator = new DoubleRangeValidator(0, 20000000);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // ChoiceBox mit Gender füllen
        this.genderChoiceBox.getItems().setAll(Person.Gender.values());
        // ComboBox mit Pilot Rank füllen
        this.chooseRankOfPilotComboBox.getItems().setAll(Pilot.Rank.values());
        this.chooseRankOfPilotComboBox.setValue(Pilot.Rank.CAPTAIN);
        // Liste mit Sprachen füllen
        this.languageList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.languageList.getItems().setAll(FlightAttendant.Language.values());
        // Combobox mit enum von Employeetypen füllen
        this.chooseEmployeeTypComboBox.getItems().setAll(EmployeeTyp.values());
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
        return this.birthdatePicker.getValue();
    }

    public LocalDate getDateOfEmployment() {
        return this.employeeDatePicker.getValue();
    }

    public double getSalary() {
        return Double.parseDouble(this.salaryTextField.getText());
    }

    public Pilot.Rank chooseRankOfPilot() {
        return this.chooseRankOfPilotComboBox.getValue();
    }

    public Set<FlightAttendant.Language> chooseLanguage() {
        Set<FlightAttendant.Language> selectedLanguages = this.languageList.getSelectionModel().getSelectedItems().
                stream().collect(Collectors.toSet());
        return selectedLanguages;
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

    public void saveEmployee(ActionEvent actionEvent) {
        try {
            this.doubleRangeValidator.validate(String.valueOf(getSalary()));
        } catch (InvalidInputException e) {
            this.wrongInputLabel.setText("incorrect salary input! Please give a salary 0-200000");
            return;
        }
        if (firstnameField.getText().isEmpty() || lastNameField.getText().isEmpty()) {
            this.wrongInputLabel.setText("Please enter a name");
            return;
        }
        if (this.chooseEmployeeTypComboBox.getValue().equals(EmployeeTyp.FLIGHT_ATTENDANT)) {
            GUI.flightService.registerFlightAttendant(getFirstName(), getLastName(), getGender(),
                    getBirthday(), getDateOfEmployment(), getSalary(), chooseLanguage());
            System.out.println("new flight attendant registered");
        } else {
            GUI.flightService.registerPilot(getFirstName(), getLastName(), getGender(), getBirthday(),
                    getDateOfEmployment(), getSalary(), chooseRankOfPilot());
            System.out.println("new pilot registered");
        }

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

    private void resetEmployeeOption() {
        this.languageList.setDisable(false);
        this.chooseRankOfPilotComboBox.setDisable(false);
    }

    public void chooseEmployeeType(ActionEvent mouseEvent) {
        resetEmployeeOption();
        if (this.chooseEmployeeTypComboBox.getValue().equals(EmployeeTyp.PILOT)) {
            this.languageList.setDisable(true);
        } else if (this.chooseEmployeeTypComboBox.getValue().equals(EmployeeTyp.FLIGHT_ATTENDANT)) {
            this.chooseRankOfPilotComboBox.setDisable(true);
        }

    }
}
