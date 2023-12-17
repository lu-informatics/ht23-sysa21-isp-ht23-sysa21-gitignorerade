package lu.ics.se.models;

import java.util.ArrayList;

public class Workshop {
    private String workshopName;
    private String workshopAddress;
    private boolean isInternal;
    private boolean servicesVans;
    private boolean servicesMediumTrucks;
    private boolean servicesLargeTrucks;
    private ArrayList<ServiceEvent> serviceEvent;

    public Workshop(){
        serviceEvent = new ArrayList<ServiceEvent>();
    }
    public Workshop(String workshopName, String workshopAddress, boolean isInternal, boolean servicesVans, boolean servicesMediumTrucks, boolean servicesLargeTrucks) {
        this.workshopName = workshopName;
        this.workshopAddress = workshopAddress;
        this.isInternal = isInternal;
        this.servicesVans = servicesVans;
        this.servicesMediumTrucks = servicesMediumTrucks;
        this.servicesLargeTrucks = servicesLargeTrucks;
        serviceEvent = new ArrayList<ServiceEvent>();
    }
    public String getWorkshopName() {
        return workshopName;
    }
    public String getWorkshopAddress() {
        return workshopAddress;
    }
    public boolean getIsInternal() {
        return isInternal;
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
    public void setIsInternal(boolean isInternal) {
        this.isInternal = isInternal;
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
    




}


