package lu.ics.se.models;

import java.util.ArrayList;

public class Workshop {
    private String workshopName;
    private String workshopAddress;
    private String type;
    private boolean servicesVans;
    private boolean servicesMediumTrucks;
    private boolean servicesLargeTrucks;
    private ArrayList<ServiceEvent> serviceEvent;

    public Workshop(String name, String address, String type, boolean servicesVans, boolean servicesMediumTrucks, boolean servicesLargeTrucks) {
        this.workshopName = name;
        this.workshopAddress = address;
        this.type = type;
        this.servicesVans = servicesVans;
        this.servicesMediumTrucks = servicesMediumTrucks;
        this.servicesLargeTrucks = servicesLargeTrucks;
        serviceEvent = new ArrayList<ServiceEvent>();
    }

    public Workshop(String name, String address, String type){
        this.workshopName = name;
        this.workshopAddress = address;
        this.type = type;
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

    public String getWorkshopName() {
        return workshopName;
    }
    public String getWorkshopAddress() {
        return workshopAddress;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
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
    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }
    public void setWorkshopAddress(String workshopAddress) {
        this.workshopAddress = workshopAddress;
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
    public Object getName() {
        return null;
    }
    public boolean getIsInternal() {
        return false;
    }
    




}


