package btx.prog.project.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * This is the class controlling the homepage. It switches between scenes. Here data from the whole
 * gui can be saved to the Json-File by pressing the persist data button.
 *
 * @author Anthéa Leung
 * @author Jorma Steiner
 * @version 1.0
 */


public class PrimarySceneController {


    @FXML
    public void newFlightButtonPushed(ActionEvent actionEvent) throws IOException {
        URL flight = this.getClass().getClassLoader().getResource("newFlight.fxml");
        FXMLLoader loader = new FXMLLoader(flight);
        Parent flightView = loader.load();
        Scene flightScene = new Scene(flightView);

        Stage flightNewWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        flightNewWindow.setScene(flightScene);
        flightNewWindow.show();
    }

    @FXML
    public void newBookingButtonPushed(ActionEvent actionEvent) throws IOException {
        URL booking = this.getClass().getClassLoader().getResource("newBookings.fxml");
        FXMLLoader loader = new FXMLLoader(booking);
        Parent bookingView = loader.load();
        Scene bookingScene = new Scene(bookingView);
        Stage bookingNewWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        bookingNewWindow.setScene(bookingScene);
        bookingNewWindow.show();

    }

    @FXML
    public void newEmployeeButtonPushed(ActionEvent actionEvent) throws IOException {
        URL employee = this.getClass().getClassLoader().getResource("newEmployee.fxml");
        FXMLLoader loader = new FXMLLoader(employee);
        Parent employeeView = loader.load();
        Scene employeeScene = new Scene(employeeView);
        Stage employeeNewWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        employeeNewWindow.setScene(employeeScene);
        employeeNewWindow.show();
    }

    @FXML
    public void showLists(ActionEvent actionEvent) throws IOException {
        URL lists = this.getClass().getClassLoader().getResource("lists.fxml");
        FXMLLoader loader = new FXMLLoader(lists);
        Parent listsView = loader.load();
        Scene listScene = new Scene(listsView);
        Stage listNewWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        listNewWindow.setScene(listScene);
        listNewWindow.show();
    }

    @FXML
    public void persistDataGUI(ActionEvent actionEvent) {
        GUI.flightService.persistData();
    }
}