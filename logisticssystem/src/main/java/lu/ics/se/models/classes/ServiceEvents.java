package lu.ics.se.models.classes;
import java.util.ArrayList;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class ServiceEvents {
    private Vehicle vehicleServiced;
    private LocalDate eventDate;
    private ObservableList<ServiceAction> serviceActions;
    private Integer totalCostOfService;
    private Integer totalPartsReplaced;
    private Workshop workshop;

    public ServiceEvents(){
        this.serviceActions = FXCollections.observableArrayList();
    }
    public ServiceEvents(Vehicle vehicleServiced, LocalDate eventDate) {
        this.vehicleServiced = vehicleServiced;
        this.eventDate = eventDate;
        this.serviceActions = FXCollections.observableArrayList();
    }
    public Vehicle getVehicleServiced() {
        return vehicleServiced;
    }
    public LocalDate getEventDate() {
        return eventDate;
    }
    public ObservableList<ServiceAction> getServiceActions() {
        return serviceActions;
    }
    public int getTotalCostOfService(){
        return totalCostOfService;
    }
    public int getTotalPartsReplaced(){
        return totalPartsReplaced;
    }
    public Workshop getWorkshop() {
        return workshop;
    }
    public void setVehicleServiced(Vehicle vehicleServiced) {
        this.vehicleServiced = vehicleServiced;
    }
    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }
    public void setServiceActions(ObservableList<ServiceAction> serviceActions) {
        this.serviceActions = serviceActions;
    }
    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }
    public void setTotalCostOfService(){
        this.totalCostOfService = 0;
        for (ServiceAction serviceAction : serviceActions) {
            this.totalCostOfService += serviceAction.getCostOfService();
            this.totalCostOfService += serviceAction.getCostOfPartsReplaced();
        }
    }
    public void setTotalPartsReplaced(){
        this.totalPartsReplaced = 0;
        for (ServiceAction serviceAction : serviceActions){
            this.totalPartsReplaced += serviceAction.getNumberOfPartsReplaced();
        }
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
