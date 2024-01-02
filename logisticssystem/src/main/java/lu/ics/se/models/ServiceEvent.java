package lu.ics.se.models;
import java.util.Date;

import javafx.beans.Observable;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class ServiceEvent {
    private String eventName;
    private String eventDescription;
    private double eventCost;
    private Date eventDate;
    private Workshop eventWorkshop;
    private Vehicle eventVehicle;
    private ArrayList<ServiceAction> eventActions;
    private String workshopType;


    public ServiceEvent() {
        eventActions = new ArrayList<ServiceAction>();
    }
    public ServiceEvent(String eventName, String eventDescription, double eventCost, Date maintenanceDate, Workshop eventWorkshop, Vehicle eventVehicle, ArrayList<ServiceAction> eventActions) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventCost = eventCost;
        this.eventDate = maintenanceDate;
        this.eventWorkshop = eventWorkshop;
        this.eventVehicle = eventVehicle;
        this.eventActions = eventActions;
    }
    public ServiceEvent(String string, String string2, double d, LocalDate maintenanceDate, Workshop workshopByName,
            Vehicle vehicleByVIN, ArrayList arrayList) {
    }
    public String getEventName() {
        return eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setWorkshopType(String workshopType) {
        this.workshopType = workshopType;
    }

    public double getEventCost() {
        return eventCost;
    }
    public Date getEventDate() {
        return eventDate;
    }
    public Workshop getEventWorkshop() {
        return eventWorkshop;
    }
    public Vehicle getEventVehicle() {
        return eventVehicle;
    }
    public ArrayList<ServiceAction> getEventActions() {
        return eventActions;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
    public void setEventCost(double eventCost) {
        this.eventCost = eventCost;
    }
    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
    public void setEventWorkshop(Workshop eventWorkshop) {
        this.eventWorkshop = eventWorkshop;
    }
    public void setEventVehicle(Vehicle eventVehicle) {
        this.eventVehicle = eventVehicle;
    }
    public void setEventActions(ArrayList<ServiceAction> eventActions) {
        this.eventActions = eventActions;
    }
    public void add(ServiceEvent serviceEvent) {

    }
    public void remove(ServiceEvent serviceEvent) {
    }
    public void addServiceAction(ServiceAction serviceAction) {
    }
    public void addPart(Part tire) {
    }
    public String getEventVehicleName() {
        return null;
    }
    public String getEventWorkshopName() {
        return null;
    }
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder result = new StringBuilder();
        result.append("Event Name: ").append(eventName).append("\n");
        result.append("Date: ").append(dateFormat.format(eventDate)).append("\n");
        result.append("Description: ").append(eventDescription).append("\n");
        result.append("Cost: $").append(eventCost).append("\n");

        // Add information about service actions
        result.append("Service Actions:\n");
        for (ServiceAction action : eventActions) {
            result.append(action.toString());  // Use the toString method of ServiceAction
        }

        return result.toString();
    }
    public String getEventVehicleVIN() {
        return null;
    }
    public String getEventWorkshopAddress() {
        return null;
    }
    public void calculateTotalCostForWorkshop() {
    }
    public Observable eventCostProperty() {
        return null;
    }
    public Observable eventWorkshopNameProperty() {
        return null;
    }
    public Observable eventDateProperty() {
        return null;
    }
    public Observable eventVinProperty() {
        return null;
    }
    public double getCost() {
        return 0;
    }
    public Object getWorkshopType() {
        return this.workshopType;
    }
    public String getEventWorkshopType() {
        return null;
    }
    public String getVin() {
        return null;
    }
}
    

