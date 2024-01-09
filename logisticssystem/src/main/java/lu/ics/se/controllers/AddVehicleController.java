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

        //This sets the choice box to contain the three options "Van", "Mediumtruck" and "Largetruck".

        vehicleTypeChoiceBox.getItems().addAll("Van", "Mediumtruck", "Largetruck");

        //This sets the default value of the choice box to "Van".
        vehicleTypeChoiceBox.setValue("Van");

        //This sets the slider to have a minimum value of 0 and a maximum value of 2000.
        vehicleCapacitySlider.setMin(0);
        vehicleCapacitySlider.setMax(2000);

        vehicleCapacitySlider.setMajorTickUnit(1);
        vehicleCapacitySlider.setSnapToTicks(true);

        vehicleTypeChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue.equals("Van")) {
                        vehicleCapacitySlider.setMin(0);
                        vehicleCapacitySlider.setMax(2000);
                    } else if (newValue.equals("Mediumtruck")) {
                        vehicleCapacitySlider.setMin(2000);
                        vehicleCapacitySlider.setMax(8000);
                    } else if (newValue.equals("Largetruck")) {
                        vehicleCapacitySlider.setMin(8000);
                        vehicleCapacitySlider.setMax(20000);
                    }
                    vehicleCapacitySlider.setValue(vehicleCapacitySlider.getMin());

                });
        vehicleCapacitySlider.valueProperty().addListener((observable1, oldValue1, newValue1) -> {
            vehicleCargoCapacityTextField.setText(String.valueOf((int) vehicleCapacitySlider.getValue()));

        });
        PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(0.5));
        vehicleCargoCapacityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            pause.setOnFinished(event -> {
                if (!newValue.matches("\\d*")) {
                    vehicleCargoCapacityTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }else {
                int value = Integer.parseInt(newValue);
                if (value < vehicleCapacitySlider.getMin()) {
                    vehicleCapacitySlider.setValue(vehicleCapacitySlider.getMin());
                    vehicleCargoCapacityTextField.setText(String.valueOf((int) vehicleCapacitySlider.getMin()));
                    addVehicleErrorLabel.setText("For " + vehicleTypeChoiceBox.getValue() + " the minimum capacity is "
                            + (int) vehicleCapacitySlider.getMin());
                } else if (value > vehicleCapacitySlider.getMax()) {
                    vehicleCapacitySlider.setValue(vehicleCapacitySlider.getMax());
                    addVehicleErrorLabel.setText("For " + vehicleTypeChoiceBox.getValue() + " the maximum capacity is "
                            + (int) vehicleCapacitySlider.getMax());
                    vehicleCargoCapacityTextField.setText(String.valueOf((int) vehicleCapacitySlider.getMax()));
                } else {
                    vehicleCapacitySlider.setValue(value);
                }
            }});
            pause.playFromStart();
        });
    }

    @FXML
    private void vehicleAddButtonAction() {
        Vehicle vehicle = new Vehicle();
        if (vehicleCargoCapacityTextField.getText().isEmpty() || (vehicleBrandTextField.getText().equals("0"))) {
            addVehicleErrorLabel.setText("Please fill in all fields");
        } else if (vehicleBrandTextField.getText().length() < 2) {
            addVehicleErrorLabel.setText("Vehicle brand must be at least 2 characters long");
        } else if (!vehicleBrandTextField.getText().matches("[a-zA-Z ]+")) {
            addVehicleErrorLabel.setText("Vehicle brand must contain only letters");
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
            vehicle.setVehicleIdentificationNumber(vehicle.generateVehicleIdentificationNumber());
            vehicle.setVehicleName(vehicle.generateVehicleName(vehicle.getVehicleIdentificationNumber(), vehicle.getVehicleBrand()));
            vehicle.setScheduledMaintenance(LocalDate.now().plusMonths(6));
            vehicle.setLastMaintenance();
            Main.companyVehicleManifest.addVehicle(vehicle);
            vehicle.setServiceHistory(new ServiceHistory());
            vehicleBrandTextField.clear();
            vehicleCargoCapacityTextField.clear();
            vehicleTypeChoiceBox.setValue("VAN");
            vehicleCapacitySlider.setValue(0);
            addVehicleErrorLabel.setText("");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Vehicle added");
            alert.setHeaderText("Vehicle added successfully");
            alert.setContentText("");
            alert.showAndWait();

            Stage stage = (Stage) vehicleAddButton.getScene().getWindow();
            stage.close();

        }
    }
}
