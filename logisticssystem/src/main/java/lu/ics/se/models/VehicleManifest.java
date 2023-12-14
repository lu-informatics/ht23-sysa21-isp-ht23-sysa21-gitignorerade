package lu.ics.se.models;
import java.util.ArrayList;

public class VehicleManifest {
    private ArrayList<Vehicle> vehicleManifest = new ArrayList<Vehicle>();

    public VehicleManifest() {

    }
    public void addVehicle(Vehicle vehicle){
        vehicleManifest.add(vehicle);
    }
    public void removeVehicle(Vehicle vehicle){
        vehicleManifest.remove(vehicle);
    }
    public ArrayList<Vehicle> getVehicleManifest(){
        return vehicleManifest;
    }
    public void setVehicleManifest(ArrayList<Vehicle> vehicleManifest){
        this.vehicleManifest = vehicleManifest;
    }
    public void printVehicleManifest(){
        for (Vehicle vehicle : vehicleManifest) {
            System.out.println("Vehicle name: " + vehicle.getVehicleName());
            System.out.println("Vehicle identification number: " + vehicle.getVehicleIdentificationNumber());
            System.out.println("Vehicle capacity: " + vehicle.getVehicleCapacity());
            System.out.println("Days since last maintenance: " + vehicle.getDaysSinceLastMaintenance());
        }
    }
    public Vehicle getVehicleByName(String vehicleName){
        for (Vehicle vehicle : vehicleManifest) {
            if (vehicle.getVehicleName().equals(vehicleName)) {
                return vehicle;
            }
        }
        return null;
    }
    




}
