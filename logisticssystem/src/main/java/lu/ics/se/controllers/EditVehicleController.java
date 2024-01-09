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

        //This defines a listener that can be used to detect when the window is closed.
        public interface OnCloseListener{
            void onClose();
        }

        @FXML
        private ChoiceBox<String> selectKindOfEditChoiceBox;

        @FXML
        private Label displayCurrentVehicleLabel;

        private OnCloseListener onCloseListener;

        //This allows the controller opening this controller to set the controller

        public void setOnCloseListener(OnCloseListener onCloseListener) {
            this.onCloseListener = onCloseListener;
        }

        private Vehicle vehicle;


        //This allows the controller opening this controller to set the vehicle that is to be edited.
        //In this case, it is the MainViewController that sets the vehicle.
        //It also sets the label to display the current vehicle's information.

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

            //This sets the kind of edit choice box to contain the different kinds of edits that can be made.

            selectKindOfEditChoiceBox.getItems().addAll("Edit Brand", "Edit Capacity", "Edit Vehicle Type");

            //This sets the default value of the kind of edit choice box to "Edit Brand".

                selectKindOfEditChoiceBox.setValue("Edit Brand");

                //This adds the different vehicle classes to the vehicle class choice box.

                selectVehicleClassChoiceBox.getItems().addAll(VehicleClass.Van, VehicleClass.Mediumtruck, VehicleClass.Largetruck);

                //This sets the select vehicle class choice box to be invisible.

                selectVehicleClassChoiceBox.setVisible(false);

                //This creates a listener for the kind of edit choice box. It makes the vehicle class choice box visible 
                //if the user selects "Edit Vehicle Type". And makes the changes to be made text field visible if the user selects
                //"Edit Brand" or "Edit Capacity".

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

            //This checks if the kind of edit choice box is set to "Edit Brand". If it is, it checks if the changes to be made text field
            //contains only letters and spaces. If it does, it sets the vehicle's brand to the text in the changes to be made text field
            //when the apply changes button is pressed. If it does not, it displays an alert telling the user that the brand can only contain
            //letters and spaces.

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

            //This checks if the kind of edit choice box is set to "Edit Capacity". If it is, it checks if the changes to be made text field
            //contains only numbers. If it does, it checks if the number is within the allowed range for the vehicle type. If it is, it sets
            //the vehicle's capacity to the number in the changes to be made text field when the apply changes button is pressed. If it does not,
            //it displays an alert telling the user that the capacity must be within the allowed range for the vehicle type.

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
            
            //This checks if the kind of edit choice box is set to "Edit Vehicle Type". If it is, it sets the vehicle's vehicle class to the
            //vehicle class selected in the select vehicle class choice box when the apply changes button is pressed.

            else if (selectKindOfEditChoiceBox.getValue().equals("Edit Vehicle Type")){
                vehicle.setVehicleClass(selectVehicleClassChoiceBox.getValue());
                vehicle.setCapacityinKg(vehicle.getAllowedCapacityRange()[0]);
                setVehicle(vehicle);
            }
            
        }
        
        //This closes the window when the close editor button is pressed. It also sets the vehicle's vehicle identification number and vehicle name
        //to new values from the edits made. It also calls the onClose method of the listener.

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
