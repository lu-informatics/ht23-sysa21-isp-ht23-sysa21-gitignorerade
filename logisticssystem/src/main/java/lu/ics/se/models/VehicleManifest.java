package lu.ics.se.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.util.Callback;

public class VehicleManifest {
    private ObservableList<Vehicle> companyOwnedVehicles;
    private static VehicleManifest instance;
    
    private VehicleManifest() {
        companyOwnedVehicles = FXCollections.observableArrayList();
    }
    
    public ObservableList<Vehicle> getAllVehicles() {
        return companyOwnedVehicles;
    }

    public static VehicleManifest getInstance() {
        if (instance == null) {
            instance = new VehicleManifest();
        }
        return instance;
    }

    public List<ServiceEvent> getAllServiceEvents() {
        List<ServiceEvent> allServiceEvents = new ArrayList<>();
        for (Vehicle vehicle : companyOwnedVehicles) {
            allServiceEvents.addAll(vehicle.getServiceEventList());
        }
        return allServiceEvents;
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
    
    public ObservableList<Vehicle> getCompanyOwnedVehicles() {
        return companyOwnedVehicles;
        
    }
    public void addVehicle(Vehicle vehicle) {
        if (isVINUnique(vehicle.getVehicleIdentificationNumber())) {
            companyOwnedVehicles.add(vehicle);
        } else {
            // Handle the case where the VIN is not unique
        }
    }
    
    
    public void removeVehicle(Vehicle vehicle) {
        companyOwnedVehicles.remove(vehicle);
    }

    
    public Vehicle getVehicleByName(String vehicleName) {
        return null;
    }


@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Vehicle vehicle = (Vehicle) obj;
    return Objects.equals(getVehicleIdentificationNumber(), vehicle.getVehicleIdentificationNumber());
}

private String getVehicleIdentificationNumber() {
    // Implement the getter method to return the vehicleIdentificationNumber field
    // You can access the field directly or use a getter method if it exists in the Vehicle class
    // For example:
    // return vehicle.getVehicleIdentificationNumber();
    // or
    // return vehicleIdentificationNumber;
    // Replace this implementation with the appropriate code based on your Vehicle class implementation
    return null;
}

public Vehicle getVehicleByVIN(String vin) {
    List<Vehicle> vehicles = VehicleManifest.getInstance().getCompanyOwnedVehicles();
    for (Vehicle vehicle : vehicles) {
        if (vehicle.getVehicleIdentificationNumber().equals(vin)) {
            return vehicle;
        }
    }
    return null;
}


public List<Vehicle> getVehicles() {
    return new ArrayList<>(companyOwnedVehicles);
}

}    




