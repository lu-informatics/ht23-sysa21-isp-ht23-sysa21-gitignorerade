package lu.ics.se.models;

import lu.ics.se.enums.VehicleClass;

public class Vehicle {
    private String vehicleIdentificationNumber;
    private String vehicleName;
    private double vehicleCapacity;
    private int daysSinceLastMaintenance;
    private ServiceHistory serviceHistory;
    private int VehicleNumberInFleet;
    private VehicleClass vehicleClass;

    

    public Vehicle(){
        this.serviceHistory = new ServiceHistory();

    }
    public Vehicle(String vehicleIdentificationNumber, String vehicleName, double vehicleCapacity, int daysSinceLastMaintenance, int VehicleNumberInFleet) {
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
        this.vehicleName = vehicleName;
        this.daysSinceLastMaintenance = daysSinceLastMaintenance;
        this.VehicleNumberInFleet = VehicleNumberInFleet;
        if (vehicleCapacity >= 0 && vehicleCapacity <= 6000){
            this.vehicleCapacity = vehicleCapacity;
        } else {
            throw new IllegalArgumentException(vehicleName + " has an invalid capacity!" + 
            "\n" + "Please enter a capacity between 0 and 6000!");
        }
        this.serviceHistory = new ServiceHistory();
        if (vehicleCapacity < 1500) {
            this.vehicleClass = VehicleClass.CARGOVAN;
        } else if (vehicleCapacity < 3000) {
            this.vehicleClass = VehicleClass.MEDIUMTRUCK;
        } else {
            this.vehicleClass = VehicleClass.LARGETRUCK;
        }
    }

    public String getVehicleIdentificationNumber() {
        return vehicleIdentificationNumber;
    }
    public String getVehicleName() {
        return vehicleName;
    }
    public double getVehicleCapacity() {
        return vehicleCapacity;
    }
    public int getDaysSinceLastMaintenance() {
        return daysSinceLastMaintenance;
    }
    public ServiceHistory getServiceHistory() {
        return serviceHistory;
    }
    public int getVehicleNumberInFleet() {
        return VehicleNumberInFleet;
    }
    public VehicleClass getVehicleClass() {
        return vehicleClass;
    }
    public void setVehicleIdentificationNumber(String vehicleIdentificationNumber) {
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
    }
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }
    public void setVehicleCapacity(double vehicleCapacity) {
        if (vehicleCapacity >= 0 && vehicleCapacity <= 6000){
            this.vehicleCapacity = vehicleCapacity;
        } else {
            throw new IllegalArgumentException(vehicleName + " has an invalid capacity!" + 
            "\n" + "Please enter a capacity between 0 and 6000!");
        }
    }
    public void setDaysSinceLastMaintenance(int daysSinceLastMaintenance) {
        this.daysSinceLastMaintenance = daysSinceLastMaintenance;
        }
    public void setServiceHistory(ServiceHistory serviceHistory) {
        this.serviceHistory = serviceHistory;
    }
    public void setVehicleNumberInFleet(int VehicleNumberInFleet) {
        this.VehicleNumberInFleet = VehicleNumberInFleet;
    }
    public void setVehicleClass(VehicleClass vehicleClass) {
        this.vehicleClass = vehicleClass;
    }
    public void setVehicleClassFromWeight(){
        if (this.vehicleCapacity < 1500) {
            this.vehicleClass = VehicleClass.CARGOVAN;
        } else if (this.vehicleCapacity < 3000) {
            this.vehicleClass = VehicleClass.MEDIUMTRUCK;
        } else {
            this.vehicleClass = VehicleClass.LARGETRUCK;
        }
    }
    public void addMaintenanceEvent(MaintenanceEvent maintenanceEvent){
        serviceHistory.addMaintenanceEvent(maintenanceEvent);
    }
    public void removeMaintenanceEvent(MaintenanceEvent maintenanceEvent){
        serviceHistory.removeMaintenanceEvent(maintenanceEvent);
    }
    public void printServiceHistory(){
        serviceHistory.printServiceHistory();
    }
    public void printFormattedServiceHistory(){
        for (MaintenanceEvent maintenanceEvent : serviceHistory.getServiceHistory()) {
            int i = 1;
            System.out.println("---------" +
            "\n" + "Entry no: " + i +
            "\n" + "---------" +
            "\n" + "Description: " + maintenanceEvent.getDescription() + 
            "\n" + "Cost: " + maintenanceEvent.getCost() + 
            "\n" + "Date: " + maintenanceEvent.getDate());
            i++;
        }
    }
    public void printServiceHistoryWithNumbers(){
        for (MaintenanceEvent maintenanceEvent : serviceHistory.getServiceHistory()) {
            int i = 1;
            System.out.println( i + ". " +  "Description: " + maintenanceEvent.getDescription() + " Cost: " + maintenanceEvent.getCost() + " Date: " + maintenanceEvent.getDate());
        }
    }
    public void addMaintenanceEventByFunction(){
        MaintenanceEventAdder maintenanceEventAdder = new MaintenanceEventAdder();
        int maintenanceEventNumberInServiceHistory = serviceHistory.getServiceHistory().size() + 1;
        addMaintenanceEvent(maintenanceEventAdder.createMaintenanceEvent(maintenanceEventNumberInServiceHistory, this));
        maintenanceEventAdder = null;
    }
    public void removeMaintenanceEventByFunction(){
        MaintenanceEventRemover maintenanceEventRemover = new MaintenanceEventRemover();
        maintenanceEventRemover.removeMaintenanceEvent(serviceHistory);
   }
   public boolean canBeServicedByWorkshop(Workshop workshop){
         if (this.vehicleClass != VehicleClass.LARGETRUCK & workshop.getIsInternalWorkshop() == true){
                return true;
            } else if (this.vehicleClass != VehicleClass.LARGETRUCK){
                return true;
            } else {
                return false;
            }
   }

}
