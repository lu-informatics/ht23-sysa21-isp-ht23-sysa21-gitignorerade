package lu.ics.se.models.classes;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class VehicleManifest {

    private ObservableList<Vehicle> vehicleManifest;

    public VehicleManifest() {
        this.vehicleManifest = FXCollections.observableArrayList();
    }
    public ObservableList<Vehicle> getVehicleManifest() {
        return vehicleManifest;
    }
    public void setVehicleManifest(ObservableList<Vehicle> vehicleManifest) {
        this.vehicleManifest = vehicleManifest;
    }
    public void addVehicle(Vehicle vehicle) {
        this.vehicleManifest.add(vehicle);
    }
    public void removeVehicle(Vehicle vehicle) {
        this.vehicleManifest.remove(vehicle);
    }
}
