package lu.ics.se.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
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

    //This method sets the listener for the close event. This listener is called by the controller that created this controller.

     public void setOnCloseListener(OnCloseListener onCloseListener) {
            this.onCloseListener = onCloseListener;
        }

    //This method sets the vehicle that is to be removed. It is called by the controller that created this controller.

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @FXML
    public void initialize(){
    }

    //This method handles the yes button. It first checks if the vehicle has any service events in its service history. 
    //If it does, it displays an alert telling the user to remove all service events from the vehicle before removing it.
    //If it does not, it removes the vehicle from the vehicle manifest and closes the window.

    public void handleYesButton(){
        if (!vehicle.getServiceHistory().getServiceHistory().isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText("Vehicle has service events in service history");
            alert.setContentText("Please remove all service events from the vehicle befoire removing it");
            }
            else{
        Main.companyVehicleManifest.removeVehicle(vehicle);
        ((Stage)yesButton.getScene().getWindow()).close();
        if (onCloseListener != null) {
            onCloseListener.onClose();
        }
    }}
    
    //This method handles the no button. It closes the window without removing the vehicle.
    
    public void handleNoButton(){
        ((Stage)noButton.getScene().getWindow()).close();
        if (onCloseListener != null) {
            onCloseListener.onClose();
        }
    }





}
