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



import lu.ics.se.models.classes.Vehicle;
import lu.ics.se.models.enums.VehicleClass;




public class EditVehicleController implements Initializable {

        public interface OnCloseListener{
            void onClose();
        }

        @FXML
        private ChoiceBox<String> selectKindOfEditChoiceBox;

        @FXML
        private Label displayCurrentVehicleLabel;

        private OnCloseListener onCloseListener;

        public void setOnCloseListener(OnCloseListener onCloseListener) {
            this.onCloseListener = onCloseListener;
        }

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
                selectVehicleClassChoiceBox.getItems().addAll(VehicleClass.Van, VehicleClass.Mediumtruck, VehicleClass.Largetruck);
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
                    setVehicle(vehicle);
            }
            }
            else if (selectKindOfEditChoiceBox.getValue().equals("Edit Capacity")){
                try {
                    double enteredCapacity = Double.parseDouble(changesToBeMadeTextField.getText());
                    double[] allowedRange = vehicle.getAllowedCapacityRange();
            
                    if (enteredCapacity < 0){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Invalid input");
                        alert.setContentText("Please enter a positive number!");
                        alert.showAndWait();
                    }
                    else if (enteredCapacity > 20000){
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
                        alert.setContentText("Please enter a number within the allowed range!");
                        alert.showAndWait();
                    }
                    else {
                        vehicle.setCapacityinKg(enteredCapacity);
                        setVehicle(vehicle);
                    }
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid input");
                    alert.setContentText("Please enter only numbers!");
                    alert.showAndWait();
                }
            }
            else if (selectKindOfEditChoiceBox.getValue().equals("Edit Vehicle Type")){
                vehicle.setVehicleClass(selectVehicleClassChoiceBox.getValue());
                vehicle.setCapacityinKg(vehicle.getAllowedCapacityRange()[0]);
                setVehicle(vehicle);
            }
            
        }
        public void handleCloseEditorButtonAction(){
            Stage stage = (Stage) closeEditorButton.getScene().getWindow();
            vehicle.setVehicleIdentificationNumber(vehicle.generateVehicleIdentificationNumber());
            vehicle.setVehicleName(vehicle.generateVehicleName(vehicle.getVehicleIdentificationNumber(), vehicle.getVehicleBrand()));
            stage.close();
            if (onCloseListener != null){
                onCloseListener.onClose();
        }



}
}
