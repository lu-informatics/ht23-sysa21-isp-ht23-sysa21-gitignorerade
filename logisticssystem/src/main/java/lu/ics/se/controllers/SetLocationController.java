package lu.ics.se.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;
import lu.ics.se.models.classes.Vehicle;
import lu.ics.se.models.enums.Locations;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.Collections;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class SetLocationController implements Initializable {

    // This allows the controller opening this controller to set the vehicle that is
    // to be edited.
    // IIn this case, it is the MainViewController that sets the vehicle.

    private Vehicle vehicle;

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    // This allows the controller opening this controller to set a listener for the
    // close event.
    // In this case, it is the MainViewController that sets the listener.

    public interface OnCloseListener {
        void onClose();
    }

    private OnCloseListener onCloseListener;

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.onCloseListener = onCloseListener;
    }

    @FXML
    private ChoiceBox<String> selectLocationChoiceBox;

    @FXML
    private Button setLocationButton;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        // This sets the choice box to contain all the locations in the Locations enum.

        List<String> locations = Arrays.stream(Locations.values()).map(Enum::name).collect(Collectors.toList());
        Collections.sort(locations);
        selectLocationChoiceBox.getItems().addAll(locations);
    }

    // This method handles the set location button. It sets the location of the
    // vehicle to the location selected in the choice box.
    public void handleSetLocationButton() {
        vehicle.setLocation(Locations.valueOf(selectLocationChoiceBox.getValue()));

        // This displays an alert telling the user that the location has been set.

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Location set");
        alert.setHeaderText("Location set succesfully!");
        alert.setContentText("The location of the vehicle has been set to " + selectLocationChoiceBox.getValue());
        alert.showAndWait();

        // This closes the window and calls the onClose method of the listener.

        Stage stage = (Stage) setLocationButton.getScene().getWindow();
        if (onCloseListener != null) {
            onCloseListener.onClose();
        }
        stage.close();

    }

}
