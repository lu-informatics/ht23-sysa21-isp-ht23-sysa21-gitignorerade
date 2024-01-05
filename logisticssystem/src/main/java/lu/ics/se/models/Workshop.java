package lu.ics.se.models;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Workshop {
    private boolean servicesVans;
    private boolean servicesMediumTrucks;
    private boolean servicesLargeTrucks;
    private ArrayList<ServiceEvent> serviceEvent;
    private StringProperty name;
    private StringProperty address;
    private StringProperty type;

    public Workshop(String name, String address, String type, boolean servicesVans, boolean servicesMediumTrucks, boolean servicesLargeTrucks) {
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.type = new SimpleStringProperty(type);
        this.servicesVans = servicesVans;
        this.servicesMediumTrucks = servicesMediumTrucks;
        this.servicesLargeTrucks = servicesLargeTrucks;
        serviceEvent = new ArrayList<ServiceEvent>();
    }

    
    public Workshop(String eventWorkshopName, String eventWorkshopAddress, Object calculateTotalCostForWorkshop) {
        // Add your implementation here
    }

    public Workshop(String eventWorkshopName, String eventWorkshopAddress, Object calculateTotalCostForWorkshop, int dummyParameter) {
        // Add your implementation here
    }

    public Workshop(String eventWorkshopName, String eventWorkshopAddress, Object calculateTotalCostForWorkshop, String dummyParameter) {
        // Add your implementation here
    }

    
    public boolean getServicesVans() {
        return servicesVans;
    }
    public boolean getServicesMediumTrucks() {
        return servicesMediumTrucks;
    }
    public boolean getServicesLargeTrucks() {
        return servicesLargeTrucks;
    }
    public ArrayList<ServiceEvent> getServiceEvent() {
        return serviceEvent;
    
    }

    public void setServicesVans(boolean servicesVans) {
        this.servicesVans = servicesVans;
    }
    public void setServicesMediumTrucks(boolean servicesMediumTrucks) {
        this.servicesMediumTrucks = servicesMediumTrucks;
    }
    public void setServicesLargeTrucks(boolean servicesLargeTrucks) {
        this.servicesLargeTrucks = servicesLargeTrucks;
    }
    public void setServiceEvent(ArrayList<ServiceEvent> serviceEvent) {
        this.serviceEvent = serviceEvent;
    }
    public void addServiceEvent(ServiceEvent serviceEvent){
        serviceEvent.add(serviceEvent);
    }
 
    

    public ServiceHistory[] getServiceHistory() {
        return null;
    }
    public Iterable<ServiceHistory> getServiceHistories() {
        return null;
    }
    public ServiceEvent[] getServiceEvents() {
        return null;
    }
    public boolean getIsInternal() {
        return false;
    }
    
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }
    public StringProperty getNameProperty() {
        return name;
    }
    
    public StringProperty getAddressProperty() {
        return address;
    }


    public String getWorkshopName() {
        return null;
    }



}


