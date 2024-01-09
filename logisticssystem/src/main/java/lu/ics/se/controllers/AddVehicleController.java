package lu.ics.se.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;
import lu.ics.se.Main;
import lu.ics.se.models.classes.*;
import lu.ics.se.models.enums.*;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;

public class AddVehicleController implements Initializable {

    @FXML
    private ChoiceBox<String> vehicleTypeChoiceBox;

    @FXML
    private Slider vehicleCapacitySlider;

    @FXML
    private TextField vehicleCargoCapacityTextField;

    @FXML
    private TextField vehicleBrandTextField;

    @FXML
    private Button vehicleAddButton;

    @FXML
    private Label addVehicleErrorLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // This sets the choice box to contain the three options "Van", "Mediumtruck"
        // and "Largetruck".

        vehicleTypeChoiceBox.getItems().addAll("Van", "Mediumtruck", "Largetruck");

        // This sets the default value of the choice box to "Van".

        vehicleTypeChoiceBox.setValue("Van");

        // This sets the slider to have a minimum value of 0 and a maximum value of
        // 2000.

        vehicleCapacitySlider.setMin(0);
        vehicleCapacitySlider.setMax(2000);

        // This sets the slider to have a major tick unit of 1 and to snap to ticks.
        // This means that the slider will only be able to have integer values.
        // This also means that the slider will only be able to have values that are
        // multiples of 1.

        vehicleCapacitySlider.setMajorTickUnit(1);
        vehicleCapacitySlider.setSnapToTicks(true);

        vehicleTypeChoiceBox.getSelectionModel().selectedItemProperty()

                // This sets a listener for the choice box. When the user selects a different
                // value in the choice box, the slider will change accordingly.

                .addListener((observable, oldValue, newValue) -> {

                    // This sets the slider to have a minimum value of 0 and a maximum value of 2000
                    // if the user selects "Van".

                    if (newValue.equals("Van")) {
                        vehicleCapacitySlider.setMin(0);
                        vehicleCapacitySlider.setMax(2000);

                        // This sets the slider to have a minimum value of 2000 and a maximum value of
                        // 8000 if the user selects "Mediumtruck".
                    } else if (newValue.equals("Mediumtruck")) {
                        vehicleCapacitySlider.setMin(2000);
                        vehicleCapacitySlider.setMax(8000);

                        // This sets the slider to have a minimum value of 8000 and a maximum value of
                        // 20000 if the user selects "Largetruck".
                    } else if (newValue.equals("Largetruck")) {
                        vehicleCapacitySlider.setMin(8000);
                        vehicleCapacitySlider.setMax(20000);
                    }

                    // This sets the slider to the minimum value if the user selects a vehicle class
                    // in the choicebox

                    vehicleCapacitySlider.setValue(vehicleCapacitySlider.getMin());

                });

        // This sets the vehicle cargo capacity text field to display the value of the
        // slider.

        vehicleCapacitySlider.valueProperty().addListener((observable1, oldValue1, newValue1) -> {
            vehicleCargoCapacityTextField.setText(String.valueOf((int) vehicleCapacitySlider.getValue()));

        });

        // This defines a pause transition that ensures that the text entered is not
        // changed until the user has stopped typing for 0.5 seconds.

        PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(0.5));

        // This creates a listener that checks the user input in the vehicle cargo
        // capacity text field.

        vehicleCargoCapacityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            pause.setOnFinished(event -> {

                // This checks if the user enters a value that is not an integer. If the user
                // does, the text field will be cleared.

                if (!newValue.matches("\\d*")) {
                    vehicleCargoCapacityTextField.setText(newValue.replaceAll("[^\\d]", ""));
                } else {

                    // This checks if the user enters a value that is less than the minimum value of
                    // the slider. If the user
                    // does, the value of the slider will be set to the minimum value of the slider
                    // and the text field will be updated accordingly.

                    int value = Integer.parseInt(newValue);
                    if (value < vehicleCapacitySlider.getMin()) {
                        vehicleCapacitySlider.setValue(vehicleCapacitySlider.getMin());
                        vehicleCargoCapacityTextField.setText(String.valueOf((int) vehicleCapacitySlider.getMin()));
                        addVehicleErrorLabel
                                .setText("For " + vehicleTypeChoiceBox.getValue() + " the minimum capacity is "
                                        + (int) vehicleCapacitySlider.getMin());

                        // This checks if the user enters a value that is greater than the maximum value
                        // of the slider. If the user
                        // does, the value of the slider will be set to the maximum value of the slider
                        // and the text field will be updated accordingly.

                    } else if (value > vehicleCapacitySlider.getMax()) {
                        vehicleCapacitySlider.setValue(vehicleCapacitySlider.getMax());
                        addVehicleErrorLabel
                                .setText("For " + vehicleTypeChoiceBox.getValue() + " the maximum capacity is "
                                        + (int) vehicleCapacitySlider.getMax());
                        vehicleCargoCapacityTextField.setText(String.valueOf((int) vehicleCapacitySlider.getMax()));

                        // If the entered value is within the allowed range, the value of the slider
                        // will be set to the entered value.

                    } else {
                        vehicleCapacitySlider.setValue(value);
                    }
                }
            });

            // This starts the pause transition.

            pause.playFromStart();
        });
    }

    @FXML

    //This code runs when the add vehicle button is pressed.

    private void vehicleAddButtonAction() {

        //This creates a new vehicle object

        Vehicle vehicle = new Vehicle();

        //This checks if the cargo capacity field is empty or if the user has not selected a vehicle brand.
        //If it is, it displays an error message telling the user to fill in all fields.

        if (vehicleCargoCapacityTextField.getText().isEmpty() || (vehicleBrandTextField.getText().equals("0"))) {
            addVehicleErrorLabel.setText("Please fill in all fields");

            //This checks if the vehicle brand is less than 2 characters long. If it is, it displays an error message.

        } else if (vehicleBrandTextField.getText().length() < 2) {
            addVehicleErrorLabel.setText("Vehicle brand must be at least 2 characters long");

            //This checks if the vehicle brand contains only letters. If it does not, it displays an error message.
        } else if (!vehicleBrandTextField.getText().matches("[a-zA-Z ]+")) {
            addVehicleErrorLabel.setText("Vehicle brand must contain only letters");

            //This sets the vehicle's brand, capacity, and class to the entered values.

        } else {
            vehicle.setvehicleBrand(vehicleBrandTextField.getText());
            vehicle.setCapacityinKg(Double.parseDouble(vehicleCargoCapacityTextField.getText()));
            if (vehicleTypeChoiceBox.getValue().equals("Van")) {
                vehicle.setVehicleClass(VehicleClass.Van);
            } else if (vehicleTypeChoiceBox.getValue().equals("Mediumtruck")) {
                vehicle.setVehicleClass(VehicleClass.Mediumtruck);
            } else if (vehicleTypeChoiceBox.getValue().equals("Largetruck")) {
                vehicle.setVehicleClass(VehicleClass.Largetruck);
            }

            //This sets the vehicle's identification number using the generateVehicleIdentificationNumber method in the vehicle class.

            vehicle.setVehicleIdentificationNumber(vehicle.generateVehicleIdentificationNumber());

            //This sets the vehicle's name using the generateVehicleName method in the vehicle class.

            vehicle.setVehicleName(
                    vehicle.generateVehicleName(vehicle.getVehicleIdentificationNumber(), vehicle.getVehicleBrand()));

                    //This sets the the scheduled maintenance date to 6 months from the current date.

            vehicle.setScheduledMaintenance(LocalDate.now().plusMonths(6));

            //This sets the vehicle's last maintenance date to the current date.

            vehicle.setLastMaintenance();

            //This adds the vehicle to the vehicle manifest and sets its service history to a new service history object.

            Main.companyVehicleManifest.addVehicle(vehicle);
            vehicle.setServiceHistory(new ServiceHistory());

            //This clears the text fields and choice boxes and sets the slider to 0.

            vehicleBrandTextField.clear();
            vehicleCargoCapacityTextField.clear();
            vehicleTypeChoiceBox.setValue("VAN");
            vehicleCapacitySlider.setValue(0);
            addVehicleErrorLabel.setText("");

            //This displays an alert telling the user that the vehicle has been added.

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Vehicle added");
            alert.setHeaderText("Vehicle added successfully");
            alert.setContentText("");
            alert.showAndWait();

            //This closes the window.
            
            Stage stage = (Stage) vehicleAddButton.getScene().getWindow();
            stage.close();

        }
    }
}
