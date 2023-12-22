package lu.ics.se.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javafx.beans.value.ObservableValue;

public class Vehicle {
    private String vehicleIdentificationNumber;
    private String vehicleName;
    private String location;
    private double capacityInKg;
    private String vehicleType;
    private double daysSinceLastService;
    private ArrayList<ServiceEvent> serviceEvent;
    private ServiceHistory serviceHistory;
    private int numberOfPartsReplaced;
    
    private static final int MAX_PARTS_REPLACED = 100;

    private static final double TOTAL_COST_THRESHOLD = 100000;   


    public Vehicle(String string, String string2, String string3, double d, String string5){
   }
    public Vehicle(String vehicleIdentificationNumber, String vehicleName, String location, double capacityinKg, String vehicleType, double daysSinceLastService) {
        this.vehicleIdentificationNumber = generateUniqueVIN();
        this.vehicleName = vehicleName;
        this.location = location;
        this.capacityInKg = capacityinKg;
        this.daysSinceLastService = daysSinceLastService;
        this.serviceEvent = new ArrayList<ServiceEvent>();
        this.serviceHistory = new ServiceHistory(vehicleType, daysSinceLastService, vehicleType, daysSinceLastService, daysSinceLastService, daysSinceLastService);

        

        // Check if the provided type is valid
        if (isValidType(vehicleType)) {
            this.vehicleType = vehicleType;
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

    public List<Workshop> getWorkshopsServicedIn() {
        List<Workshop> workshops = new ArrayList<>();

        for (ServiceEvent event : serviceEvent) {
            Workshop workshop = event.getEventWorkshop();
            if (workshop != null && !workshops.contains(workshop)) {
                workshops.add(workshop);
            }
        }

        return workshops;
    }


    private String generateUniqueVIN() {
        return UUID.randomUUID().toString();
    }

    public boolean isLargeTruck() {
        return "Large truck".equalsIgnoreCase(vehicleType);
    }

    public void setCapacityInKg(double capacityInKg) {
        if (capacityInKg >= 0) {
            this.capacityInKg = capacityInKg;
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
            Workshop workshop = latestEvent.getEventWorkshop();

            return workshop != null && !workshop.getIsInternal();
        }
        return false;
    }

    ServiceEvent getLatestServiceEvent() {
        if (!serviceEvent.isEmpty()) {
            return serviceEvent.get(serviceEvent.size() - 1);
        }
        return null;
    }


    private void decommissionVehicle() {
        isDecommissioned = true;
        // You may add additional logic for decommissioning the vehicle
    }


    public boolean isDecommissioned() {
        return isDecommissioned;
    }

    public String getVehicleIdentificationNumber() {
        return vehicleIdentificationNumber;
    }

    // Define the getServiceEvents() method in the ServiceHistory class
    public ArrayList<ServiceEvent> getServiceEvents() {
        return serviceEvent;
    }

    public void setVehicleIdentificationNumber(String vehicleIdentificationNumber) {
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
    }

    public double getDaysSinceLastService() {
        return daysSinceLastService;
    }

    public void setDaysSinceLastService(double daysSinceLastService) {
        this.daysSinceLastService = daysSinceLastService;
    }

    public ArrayList<ServiceEvent> getServiceEvent() {
        return serviceEvent;
    }

    public void setServiceEvent(ArrayList<ServiceEvent> serviceEvent) {
        this.serviceEvent = serviceEvent;
    }

    public ServiceHistory getServiceHistory() {
        return serviceHistory;
    }

    public void setServiceHistory(ServiceHistory serviceHistory) {
        this.serviceHistory = serviceHistory;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName){
        this.vehicleName = vehicleName;
    }   

    public String getLocation() {
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public double getCapacityInKg() {
        return capacityInKg;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType){
        this.vehicleType = vehicleType;
    }

    public void addServiceEvent(ServiceEvent serviceEvent){
        this.serviceEvent.add(serviceEvent);
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
    public ObservableValue<String> vehicleNameProperty() {
        return null;
    }
    public Object vehicleIdentificationNumberProperty() {
        return null;
    }
    public Object getCapacity() {
        return null;
    }
    public ObservableValue<String> locationProperty() {
        return null;
    }
    public Object capacityProperty() {
        return null;
    }
    public Object daysSinceLastServiceProperty() {
        return null;
    }
   
}
