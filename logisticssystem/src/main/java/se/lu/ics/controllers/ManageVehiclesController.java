
package se.lu.ics.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ManageVehiclesController {

    // Handle button actions
    @FXML
    private void addVehicle() {
        showAlert("Add Vehicle", "Add Vehicle functionality not implemented.");
    }

    @FXML
    private void removeVehicle() {
        showAlert("Remove Vehicle", "Remove Vehicle functionality not implemented.");
    }

    @FXML
    private void updateVehicle() {
        showAlert("Update Vehicle", "Update Vehicle functionality not implemented.");
    }

    
    

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}


