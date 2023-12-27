
package lu.ics.se.models;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.util.Callback;

public class VehicleManifest {
    private ArrayList<Vehicle> companyOwnedVehicles;
    
    public VehicleManifest() {
        companyOwnedVehicles = new ArrayList<Vehicle>();
    }

    private boolean isVINUnique(String vin) {
        for (Vehicle vehicle : companyOwnedVehicles) {
            if (vehicle.getVehicleIdentificationNumber().equals(vin)) {
                return false; // VIN already exists
            }
        }
        return true; // VIN is unique
    }
    public double calculateTotalServiceCostForAllVehicles() {
        double totalCost = 0;

        for (Vehicle vehicle : companyOwnedVehicles) {
            totalCost += vehicle.calculateTotalServiceCost();
        }

        return totalCost;
    }
    
    public ArrayList<Vehicle> getCompanyOwnedVehicles() {
        return companyOwnedVehicles;
        
    }
    public void addVehicle(Vehicle vehicle) {
        if (isVINUnique(vehicle.getVehicleIdentificationNumber())) {
            companyOwnedVehicles.add(vehicle);
        } else {
            System.out.println("Error: Vehicle with VIN " + vehicle.getVehicleIdentificationNumber() + " already exists.");
        }
    }
    
    
    public void removeVehicle(Vehicle vehicle) {
        companyOwnedVehicles.remove(vehicle);
    }

    public Callback getAllServiceEvents() {
        return null;
    }

    public Vehicle getVehicleByName(String vehicleName) {
        return null;
    }

    public Vehicle getVehicleByVIN(String vehicleVin) {
        return null;
    } 
}
    




