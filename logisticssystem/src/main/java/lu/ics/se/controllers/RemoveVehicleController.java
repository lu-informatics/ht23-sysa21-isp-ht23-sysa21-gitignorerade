package lu.ics.se.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lu.ics.se.Main;
import lu.ics.se.controllers.EditVehicleController.OnCloseListener;
import lu.ics.se.models.classes.Vehicle;

public class RemoveVehicleController {

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    private Vehicle vehicle;

    private OnCloseListener onCloseListener;

     public void setOnCloseListener(OnCloseListener onCloseListener) {
            this.onCloseListener = onCloseListener;
        }


    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @FXML
    public void initialize(){
    }

    public void handleYesButton(){
        Main.companyVehicleManifest.removeVehicle(vehicle);
        ((Stage)yesButton.getScene().getWindow()).close();
        if (onCloseListener != null) {
            onCloseListener.onClose();
        }
    }
    public void handleNoButton(){
        ((Stage)noButton.getScene().getWindow()).close();
        if (onCloseListener != null) {
            onCloseListener.onClose();
        }
    }





}
