package lu.ics.se.controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;
import lu.ics.se.models.classes.Vehicle;
import lu.ics.se.models.enums.Locations;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.Collections;
import javafx.scene.control.Alert;
import javafx.stage.Stage;



public class SetLocationController implements Initializable {

    private Vehicle vehicle;

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @FXML
    private ChoiceBox<String> selectLocationChoiceBox;

    @FXML
    private Button setLocationButton;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        List<String> locations = Arrays.stream(Locations.values()).map(Enum::name).collect(Collectors.toList());
        Collections.sort(locations);
        selectLocationChoiceBox.getItems().addAll(locations);
    }
    public void handleSetLocationButton(){
        vehicle.setLocation(Locations.valueOf(selectLocationChoiceBox.getValue()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Location set");
        alert.setHeaderText("Location set succesfully!");
        alert.setContentText("The location of the vehicle has been set to " + selectLocationChoiceBox.getValue());
        alert.showAndWait();

        Stage stage = (Stage)setLocationButton.getScene().getWindow();
        stage.close();

    }

}
