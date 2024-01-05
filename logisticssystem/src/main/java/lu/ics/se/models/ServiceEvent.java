package lu.ics.se.models;
import java.util.Date;

import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lu.ics.se.controllers.ServiceActivityController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

 

import java.util.Date;
import java.util.ArrayList;

public class ServiceEvent {
    private String eventName;
    private String eventDescription;
    private double eventCost;
    private LocalDate eventDate;
    private String eventVin;
    private Workshop eventWorkshopName;
    private Vehicle eventVehicle;
    private ArrayList<ServiceAction> eventActions;
    private String workshopType;
    private Object eventWorkshop;

    private StringProperty eventVinProperty;
    private DoubleProperty eventCostProperty;
    private ObjectProperty<LocalDate> eventDateProperty;
    private StringProperty eventWorkshopNameProperty;

    

    public ServiceEvent(String eventVin, LocalDate eventDate, Workshop eventWorkshopName, double eventCost) {
        this.eventVin = eventVin;
        this.eventDate = eventDate;
        this.eventWorkshopName = eventWorkshopName;
        this.eventCost = eventCost;

        this.eventVinProperty = new SimpleStringProperty(eventVin);
        this.eventCostProperty = new SimpleDoubleProperty(eventCost);
        this.eventDateProperty = new SimpleObjectProperty<>(eventDate);
        this.eventWorkshopNameProperty = new SimpleStringProperty(eventWorkshopName.getName());
    }
    
    
    public ServiceEvent(String eventVin, String eventName, String eventDescription, double eventCost, LocalDate eventDate, Workshop eventWorkshopName, Vehicle eventVehicle, ArrayList<ServiceAction> eventActions) {
        this.eventVin = eventVin;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventCost = eventCost;
        this.eventDate = eventDate;
        this.eventWorkshopName = eventWorkshopName;
        this.eventVehicle = eventVehicle;
        this.eventActions = eventActions;
        
        this.eventVinProperty = new SimpleStringProperty(eventVin);
    this.eventCostProperty = new SimpleDoubleProperty(eventCost);
    this.eventDateProperty = new SimpleObjectProperty<>(eventDate);
    this.eventWorkshopNameProperty = new SimpleStringProperty(eventWorkshopName.getName());
}



    public String eventWorkshopNameProperty() {
        return eventWorkshopNameProperty();
    }

    public String getEventVin() {
        return this.eventVinProperty.get();
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setWorkshopType(String workshopType) {
        this.workshopType = workshopType;
    }

    public double getEventCost() {
        return this.eventCostProperty.get();
    }
    public LocalDate getEventDate() {
        return this.eventDateProperty.get();
    }
    public String getEventWorkshopName() {
        return this.eventWorkshopNameProperty.get();
    }

    public void setEventVin(String eventVin) {
        this.eventVinProperty.set(eventVin);
    
    }
    public void setEventdate(LocalDate eventDate) {
        this.eventDateProperty.set(eventDate);
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

    public String getEventName() {
        return this.eventName;
    }
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
    public void setEventCost(double eventCost) {
        this.eventCostProperty.set(eventCost);
    }
    public void setEventDate(LocalDate eventDate) {
        this.eventDateProperty.set(eventDate);
    }
    public void setEventWorkshopName(String eventWorkshop) {
        this.eventWorkshopNameProperty.set(eventWorkshop);
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
   
    
    public String getEventVehicleName() {
        return ((Workshop) this.eventWorkshop).getName();
    }
    
    
    public Object getWorkshopType() {
        return this.workshopType;
    }

    public void setServiceDate(LocalDate localDate) {
    }
    public void setCost(Double double1) {
    }
    
    public StringProperty eventVinProperty() {
        return eventVinProperty;
    }
    
    public DoubleProperty eventCostProperty() {
        return eventCostProperty;
    }
    
    public ObjectProperty<LocalDate> eventDateProperty() {
        return eventDateProperty;
    }
    
    
    public void addPart(Part tire) {
        // Assuming ServiceAction has a constructor that takes a Part as parameter
        this.eventActions.add(new ServiceAction(eventDescription, eventDescription, eventCost, eventDate, tire, eventDescription));
    }
    
    public String getEventVehicleVIN() {
        return this.eventVehicle.getVin();  // Assuming Vehicle has a getVin() method
    }
    
    public String getEventWorkshopAddress() {
        return ((Workshop) this.eventWorkshop).getAddress();  // Assuming Workshop has a getAddress() method
    }
    
    public void calculateTotalCostForWorkshop() {
        double totalCost = 0;
        for (ServiceAction action : eventActions) {
            totalCost += action.getCost();  // Assuming ServiceAction has a getCost() method
        }
        this.eventCost = totalCost;
    }
    
    public double getCost() {
        return this.eventCost;
    }
    
    @Override
public String toString() {
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    StringBuilder result = new StringBuilder();
    result.append("Event Name: ").append(eventVinProperty().get()).append("\n");
    result.append("Date: ").append(eventDateProperty().get().format(dateFormat)).append("\n");
    result.append("Description: ").append(getEventWorkshopName()).append("\n");
    result.append("Cost: $").append(eventCostProperty().get()).append("\n");

    // Add information about service actions
    result.append("Service Actions:\n");
    for (ServiceAction action : eventActions) {
        result.append(action.toString());  // Use the toString method of ServiceAction
    }

    return result.toString();
}


    public LocalDate getServiceDate() {
        return null;
    }


    public String getEventWorkshopType() {
        return null;
    }
}
    

