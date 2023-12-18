package lu.ics.se.models;

public class MaintenanceEvent {
    private Double cost;
    private String description;
    private String date;
    private int maintenanceEventNumberInServiceHistory;
    private Vehicle vehicleServiced;

    public MaintenanceEvent() {

    }   
    public MaintenanceEvent(Double cost, String description, String date, int maintenanceEventNumberInServiceHistory, Vehicle vehicleServiced) {
        this.cost = cost;
        this.description = description;
        this.date = date;
        this.maintenanceEventNumberInServiceHistory = maintenanceEventNumberInServiceHistory;
    }
    public Double getCost() {
        return cost;
    }
    public String getDescription() {
        return description;
    }
    public String getDate() {
        return date;
    }
    public int getMaintenanceEventNumberInServiceHistory() {
        return maintenanceEventNumberInServiceHistory;
    }
    public Vehicle getVehicleServiced() {
        return vehicleServiced;
    }
    public void setCost(Double cost) {
        this.cost = cost;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setMaintenanceEventNumberInServiceHistory(int maintenanceEventNumberInServiceHistory) {
        this.maintenanceEventNumberInServiceHistory = maintenanceEventNumberInServiceHistory;
    }
    public void setVehicleServiced(Vehicle vehicleServiced) {
        this.vehicleServiced = vehicleServiced;
    }
    
}
