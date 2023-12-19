package lu.ics.se.models;

public class Vehicle {
    private String vehicleIdentificationNumber;
    private String vehicleName;
    private double vehicleCapacity;
    private int daysSinceLastMaintenance;
    private ServiceHistory serviceHistory;
    private int VehicleNumberInFleet;

    

    public Vehicle(){
        this.serviceHistory = new ServiceHistory();

    }
    public Vehicle(String vehicleIdentificationNumber, String vehicleName, double vehicleCapacity, int daysSinceLastMaintenance, int VehicleNumberInFleet) {
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
        this.vehicleName = vehicleName;
        this.vehicleCapacity = vehicleCapacity;
        this.daysSinceLastMaintenance = daysSinceLastMaintenance;
        this.VehicleNumberInFleet = VehicleNumberInFleet;
        this.serviceHistory = new ServiceHistory();
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
    public void setVehicleIdentificationNumber(String vehicleIdentificationNumber) {
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
    }
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }
    public void setVehicleCapacity(double vehicleCapacity) {
        this.vehicleCapacity = vehicleCapacity;
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
}
