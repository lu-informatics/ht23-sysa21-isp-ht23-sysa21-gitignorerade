package lu.ics.se.controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;



import lu.ics.se.models.classes.Vehicle;
import lu.ics.se.models.enums.VehicleClass;




public class EditVehicleController implements Initializable {

        @FXML
        private ChoiceBox<String> selectKindOfEditChoiceBox;

        @FXML
        private Label displayCurrentVehicleLabel;

        private Vehicle vehicle;

        public void setVehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
            displayCurrentVehicleLabel.setText("Current Vehicle: " + "\n" + "Brand: " + vehicle.getVehicleBrand() + "\n" + "Capacity: " + vehicle.getCapacityinKg() + "\n" + "Vehicle Type: " + vehicle.getVehicleClass() + "\n" + "Vehicle ID: " + vehicle.getVehicleIdentificationNumber());
        }

        @FXML
        private TextField changesToBeMadeTextField;

        @FXML
        private Button applyChangesButton;

        @FXML
        private Button closeEditorButton;

        @FXML
        private ChoiceBox<VehicleClass> selectVehicleClassChoiceBox;

        @Override
        public void initialize(URL url, ResourceBundle rb) {
            selectKindOfEditChoiceBox.getItems().addAll("Edit Brand", "Edit Capacity", "Edit Vehicle Type");
                selectKindOfEditChoiceBox.setValue("Edit Brand");
                selectVehicleClassChoiceBox.getItems().addAll(VehicleClass.VAN, VehicleClass.MEDIUMTRUCK, VehicleClass.LARGETRUCK);
                selectVehicleClassChoiceBox.setVisible(false);
                selectKindOfEditChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if ("Edit Vehicle Type".equals(newValue)){
                        selectVehicleClassChoiceBox.setVisible(true);
                        changesToBeMadeTextField.setVisible(false);
                    }
                    else {
                        selectVehicleClassChoiceBox.setVisible(false);
                        changesToBeMadeTextField.setVisible(true);
                    }
                });
            }
            public void handleApplyChangesButtonAction(){
            if (selectKindOfEditChoiceBox.getValue().equals("Edit Brand")){
                if (!changesToBeMadeTextField.getText().matches("[a-zA-Z ]+")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid input");
                    alert.setContentText("Brand name can only contain letters and spaces!");
                    alert.showAndWait();
                }
                else {
                    vehicle.setvehicleBrand(changesToBeMadeTextField.getText());
                    vehicle.generateVehicleIdentificationNumber();
                    setVehicle(vehicle);
            }
            }
            else if (selectKindOfEditChoiceBox.getValue().equals("Edit Capacity")){
                double enteredCapacity = Double.parseDouble(changesToBeMadeTextField.getText());
                double[] allowedRange = vehicle.getAllowedCapacityRange();
                if (!changesToBeMadeTextField.getText().matches("[0-9]+")){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid input");
                    alert.setContentText("Please enter a number!");
                    alert.showAndWait();
                }
                else if (Double.parseDouble(changesToBeMadeTextField.getText()) < 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid input");
                    alert.setContentText("Please enter a positive number!");
                    alert.showAndWait();
                }
                else if (Double.parseDouble(changesToBeMadeTextField.getText()) > 20000){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid input");
                    alert.setContentText("Vehicles cannot have capacity exceeding 20 000");
                    alert.showAndWait();
                }
                else if (enteredCapacity < allowedRange[0] || enteredCapacity > allowedRange[1]){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid input");
                    alert.setContentText("Vehicles of this type can only have capacity between " + allowedRange[0] + " and " + allowedRange[1]);
                    alert.showAndWait();
                }
                else {
                    vehicle.setCapacityinKg(Double.parseDouble(changesToBeMadeTextField.getText()));
                    setVehicle(vehicle);
                }
            }
            else if (selectKindOfEditChoiceBox.getValue().equals("Edit Vehicle Type")){
                vehicle.setVehicleClass(selectVehicleClassChoiceBox.getValue());
                vehicle.setCapacityinKg(vehicle.getAllowedCapacityRange()[0]);
                vehicle.generateVehicleIdentificationNumber();
                setVehicle(vehicle);
            }
            
        }
        public void handleCloseEditorButtonAction(){
            Stage stage = (Stage) closeEditorButton.getScene().getWindow();
            stage.close();
        }
        


}
