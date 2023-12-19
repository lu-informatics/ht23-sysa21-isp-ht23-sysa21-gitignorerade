package lu.ics.se.models;
import java.util.ArrayList;

public class MaintenanceEvent {
    private Double cost;
    private String description;
    private String date;
    private int maintenanceEventNumberInServiceHistory;
    private Vehicle vehicleServiced;
    private ArrayList<MaintenanceServiceAction> maintenanceServiceActions;

    public MaintenanceEvent() {

    }   
    public MaintenanceEvent(String description, String date, int maintenanceEventNumberInServiceHistory, Vehicle vehicleServiced) {
        this.description = description;
        this.date = date;
        this.maintenanceEventNumberInServiceHistory = maintenanceEventNumberInServiceHistory;
        this.maintenanceServiceActions = new ArrayList<MaintenanceServiceAction>();
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
    public ArrayList<MaintenanceServiceAction> getMaintenanceServiceActions() {
        return maintenanceServiceActions;
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
    public void setMaintenanceServiceActions(ArrayList<MaintenanceServiceAction> maintenanceServiceActions) {
        this.maintenanceServiceActions = maintenanceServiceActions;
    }
    public void calculateMaintenanceEventCost(){
        double totalCost = 0;
        for (MaintenanceServiceAction maintenanceServiceAction : maintenanceServiceActions) {
            totalCost += maintenanceServiceAction.calculateMaintenanceServiceActionCost();
        }
        this.cost = totalCost;
    }
    
}
