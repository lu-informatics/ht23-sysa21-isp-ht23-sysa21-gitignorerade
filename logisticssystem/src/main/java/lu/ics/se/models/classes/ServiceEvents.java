package lu.ics.se.models.classes;
import java.util.ArrayList;
import java.time.LocalDate;

public class ServiceEvents {
    private Vehicle vehicleServiced;
    private LocalDate eventDate;
    private ArrayList<ServiceAction> serviceActions;

    public ServiceEvents(){
        this.serviceActions = new ArrayList<>();
    }
    public ServiceEvents(Vehicle vehicleServiced, LocalDate eventDate) {
        this.vehicleServiced = vehicleServiced;
        this.eventDate = eventDate;
        this.serviceActions = new ArrayList<>();
    }
    public Vehicle getVehicleServiced() {
        return vehicleServiced;
    }
    public LocalDate getEventDate() {
        return eventDate;
    }
    public ArrayList<ServiceAction> getServiceActions() {
        return serviceActions;
    }
    public void setVehicleServiced(Vehicle vehicleServiced) {
        this.vehicleServiced = vehicleServiced;
    }
    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }
    public void setServiceActions(ArrayList<ServiceAction> serviceActions) {
        this.serviceActions = serviceActions;
    }
    public void addServiceAction(ServiceAction serviceAction){
        this.serviceActions.add(serviceAction);
    }
    public void removeServiceAction(ServiceAction serviceAction){
        this.serviceActions.remove(serviceAction);
    }
    public void removeServiceActionByIndex(int index){
        this.serviceActions.remove(index);
    }
    



}
