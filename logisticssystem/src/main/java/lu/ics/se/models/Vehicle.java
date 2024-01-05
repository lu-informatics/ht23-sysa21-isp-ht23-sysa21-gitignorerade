package lu.ics.se.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Vehicle {
    private SimpleStringProperty vehicleIdentificationNumber;
    private SimpleStringProperty vehicleName;
    private SimpleStringProperty location;
    private SimpleDoubleProperty capacityInKg;
    private SimpleStringProperty vehicleType;
    private SimpleDoubleProperty daysSinceLastService;
    private ObservableList<ServiceEvent> serviceEvent; // Changed to ObservableList
    private ServiceHistory serviceHistory;
    private int numberOfPartsReplaced;
    private List<ServiceEvent> serviceEvents;



    private static final int MAX_PARTS_REPLACED = 100;
    private static final double TOTAL_COST_THRESHOLD = 100000;

    public Vehicle(String vehicleIdentificationNumber, String vehicleName, String location, double capacityInKg, String vehicleType, double daysSinceLastService) {
        this.vehicleIdentificationNumber = new SimpleStringProperty(vehicleIdentificationNumber);
        this.vehicleName = new SimpleStringProperty(vehicleName);
        this.location = new SimpleStringProperty(location);
        this.capacityInKg = new SimpleDoubleProperty(capacityInKg);
        this.daysSinceLastService = new SimpleDoubleProperty(daysSinceLastService);
        this.serviceEvent = FXCollections.observableArrayList(); // Initialize as ObservableList
        this.serviceHistory = new ServiceHistory(); // Assuming a constructor exists for ServiceHistory
        this.serviceEvents = new ArrayList<ServiceEvent>();
        
        // Check if the provided type is valid
        if (isValidType(vehicleType)) {
            this.vehicleType = new SimpleStringProperty(vehicleType);
        } else {
            throw new IllegalArgumentException("Invalid vehicle type: " + vehicleType);
        }
    }

    // Method to check if a given type is valid
    private boolean isValidType(String vehicleType) {
        // List of allowed vehicle types
        String[] allowedTypes = {"Large truck", "Medium truck", "Van"};

        // Check if the given type is in the allowed types
        for (String allowedType : allowedTypes) {
            if (allowedType.equalsIgnoreCase(vehicleType)) {
                return true;
            }
        }

        return false;
    }

    public List<String> getWorkshopsServicedIn() {
        List<String> workshops = new ArrayList<>();

        for (ServiceEvent event : serviceEvent) {
            String workshop = event.getEventWorkshopName();
            if (workshop != null && !workshops.contains(workshop)) {
                workshops.add(workshop);
            }
        }

        return workshops;
    }

    public List<ServiceEvent> getServiceEvents() {
        return serviceEvents;
    }
    
    private String generateUniqueVIN() {
    // Create a new instance of Random
    Random random = new Random();

    // Generate a random 8-digit number
    int number = random.nextInt(90000000) + 10000000;

    // Convert the number to a string and return it
    return Integer.toString(number);
}
    

    public boolean isLargeTruck() {
        return "Large truck".equalsIgnoreCase(vehicleType.get());
    }

    public void setCapacityInKg(double capacityInKg) {
        if (capacityInKg >= 0) {
            this.capacityInKg.set(capacityInKg);
        } else {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }
    }



    public int getNumberOfPartsReplaced() {
        int numberOfPartsReplaced = 0;
        for (ServiceEvent event : serviceEvent) {
            for (ServiceAction action : event.getEventActions()) {
                // Assuming each action represents a part replacement
                numberOfPartsReplaced++;
            }
        }
        return numberOfPartsReplaced;
    }

    public void checkTotalServiceCost() {
        double totalCost = calculateTotalServiceCost();

        if (totalCost > TOTAL_COST_THRESHOLD) {
            System.out.println("Warning: Total cost of servicing for vehicle " + vehicleIdentificationNumber +
                    " exceeds " + TOTAL_COST_THRESHOLD + "."); }

        else {
            System.out.println("Total cost of servicing for vehicle " + vehicleIdentificationNumber +
                    " is within the limit.");
        }
    }
    

    public double calculateAverageMaintenanceCost() {
        if (serviceEvent.isEmpty()) {
            return 0; // Return 0 if there are no service events to avoid division by zero
        }

        double totalCost = 0;

        for (ServiceEvent event : serviceEvent) {
            totalCost += event.getEventCost();
        }

        return totalCost / serviceEvent.size();
    }

    public double calculateTotalServiceCost() {
        double totalCost = 0;

        for (ServiceEvent event : serviceEvent) {
            totalCost += event.getEventCost();
        }

        return totalCost;
    }

    private boolean isDecommissioned = false;
    private Object getServiceEventList;

    public void replacePart() {
        if (getNumberOfPartsReplaced() < MAX_PARTS_REPLACED) {
            // Replace the part
            // (You may add the logic to replace the part here)

            // Update the count of parts replaced
            ServiceEvent latestEvent = serviceEvent.get(serviceEvent.size() - 1);
            latestEvent.addServiceAction(new ServiceAction("Replace Part", "Description")); // Example action
        } else {
            // If the limit is exceeded, decommission the vehicle
            decommissionVehicle();
            System.out.println("Vehicle decommissioned due to exceeding the maximum number of parts replaced.");
        }
    }
    public boolean isLargeTruckServicedExternally() {
        if (isLargeTruck()) {
            ServiceEvent latestEvent = getLatestServiceEvent();
            String workshop = latestEvent.getEventWorkshopName();

            return workshop != null && !workshop.equals("internal");
        }
        return false;
    }

    ServiceEvent getLatestServiceEvent() {
        if (!serviceEvent.isEmpty()) {
            return serviceEvent.get(serviceEvent.size() - 1);
        }
        return null;
    }

    public void addServiceEvent(ServiceEvent serviceEvent) {
        try {
            this.serviceEvent.add(serviceEvent);
        } catch (Exception e) {
            System.out.println("Exception occurred while adding service event: " + e.getMessage());
        }
    }

    public ObservableList<ServiceEvent> getServiceEventList() {
        return FXCollections.observableArrayList(this.serviceEvent);
    }

   


    private void decommissionVehicle() {
        isDecommissioned = true;
        // You may add additional logic for decommissioning the vehicle
    }


    public boolean isDecommissioned() {
        return isDecommissioned;
    }

    // Define the getServiceEvents() method in the ServiceHistory class
    

   

    public ServiceHistory getServiceHistory() {
        return serviceHistory;
    }

    public void setServiceHistory(ServiceHistory serviceHistory) {
        this.serviceHistory = serviceHistory;
    }

    

    public void removeServiceEvent(ServiceEvent serviceEvent){
        this.serviceEvent.remove(serviceEvent);
    }

    public void getNumberOfPartsReplaced(int numberOfPartsReplaced){
        this.numberOfPartsReplaced = numberOfPartsReplaced;
    }

    public void setNumberOfPartsReplaced(int numberOfPartsReplaced){
        this.numberOfPartsReplaced = numberOfPartsReplaced;
    }
   
   
    
    public Object getCapacity() {
        return null;
    }
   
   
    
    public String getVin() {
        return null;
    }
    public void setDecommissioned(boolean b) {
    }
    public String getVehicleIdentificationNumber() {
        return vehicleIdentificationNumber.get();
    }

    public String getVehicleName() {
        return vehicleName.get();
    }

    public String getLocation() {
        return location.get();
    }

    public double getCapacityInKg() {
        return capacityInKg.get();
    }

    public String getVehicleType() {
        return vehicleType.get();
    }

    public double getDaysSinceLastService() {
        return daysSinceLastService.get();
    }

    // Standard setters
    public void setVehicleIdentificationNumber(String vin) {
        this.vehicleIdentificationNumber.set(vin);
    }

    public void setVehicleName(String name) {
        this.vehicleName.set(name);
    }

    public void setLocation(String loc) {
        this.location.set(loc);
    }

    public void setVehicleType(String type) {
        this.vehicleType.set(type);
    }

    public void setDaysSinceLastService(double days) {
        this.daysSinceLastService.set(days);
    }
    public ObservableValue<String> vehicleIdentificationNumberProperty() {
        return vehicleIdentificationNumber;
    }

    public ObservableValue<String> vehicleNameProperty() {
        return vehicleName;
    }

    public ObservableValue<String> locationProperty() {
        return location;
    }

    public ObservableValue<Number> capacityProperty() {
        return capacityInKg;
    }

    public ObservableValue<String> vehicleTypeProperty() {
        return vehicleType;
    }

    public ObservableValue<Number> daysSinceLastServiceProperty() {
        return daysSinceLastService;
    }

    @Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Vehicle vehicle = (Vehicle) obj;
    return Objects.equals(vehicleIdentificationNumber, vehicle.vehicleIdentificationNumber);
}
@Override
public int hashCode() {
    return Objects.hash(vehicleIdentificationNumber.get());
}
}
