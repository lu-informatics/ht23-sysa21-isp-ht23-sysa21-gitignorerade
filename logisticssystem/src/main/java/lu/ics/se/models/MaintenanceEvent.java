package lu.ics.se.models;

public class MaintenanceEvent {
    private Double cost;
    private String description;
    private String date;
    private int maintenanceEventNumberInServiceHistory;

    public MaintenanceEvent() {

    }   
    public MaintenanceEvent(Double cost, String description, String date, int maintenanceEventNumberInServiceHistory) {
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
    
}
