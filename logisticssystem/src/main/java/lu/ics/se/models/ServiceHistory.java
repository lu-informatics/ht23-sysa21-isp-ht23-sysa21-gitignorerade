package lu.ics.se.models;
import java.util.ArrayList;

public class ServiceHistory {
    private ArrayList<MaintenanceEvent> serviceHistory = new ArrayList<MaintenanceEvent>();

    public ServiceHistory() {

    }
    public void addMaintenanceEvent(MaintenanceEvent maintenanceEvent){
        serviceHistory.add(maintenanceEvent);
    }
    public void removeMaintenanceEvent(MaintenanceEvent maintenanceEvent){
        serviceHistory.remove(maintenanceEvent);
    }
    public ArrayList<MaintenanceEvent> getServiceHistory(){
        return serviceHistory;
    }
    public void setServiceHistory(ArrayList<MaintenanceEvent> serviceHistory){
        this.serviceHistory = serviceHistory;
    }
    public void printServiceHistory(){
        for (MaintenanceEvent maintenanceEvent : serviceHistory) {
            System.out.println("Number: " + maintenanceEvent.getMaintenanceEventNumberInServiceHistory() + " Description: " + maintenanceEvent.getDescription() + " Cost: " + maintenanceEvent.getCost() + " Date: " + maintenanceEvent.getDate()); 
        }
    }
    public MaintenanceEvent getMaintenanceEventByNumber(Integer number){
        for (MaintenanceEvent maintenanceEvent : serviceHistory) {
            if (maintenanceEvent.getMaintenanceEventNumberInServiceHistory() == number) {
                return maintenanceEvent;
            }
        }
        return null;
    }
    
}
