package lu.ics.se.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lu.ics.se.Main;
import lu.ics.se.models.classes.Vehicle;



public class ServiceHistoryAccesserController {

    private Vehicle vehicle;

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    } 

}
