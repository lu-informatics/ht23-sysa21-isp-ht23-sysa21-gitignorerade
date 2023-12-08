package lu.ics.se.models;
import java.util.ArrayList;

public class Workshop {
    private String workshopName;
    private String workshopAddress;
    private boolean isContractor;
    private boolean servicesVans;
    private boolean servicesMediumTrucks;
    private boolean servicesLargeTrucks;
    private ArrayList<ServiceEvent> serviceRecord;

    public Workshop(){
        serviceRecord = new ArrayList<ServiceEvent>();
    }
    public Workshop(String workshopName, String workshopAddress, boolean isContractor, boolean servicesVans, boolean servicesMediumTrucks, boolean servicesLargeTrucks) {
        this.workshopName = workshopName;
        this.workshopAddress = workshopAddress;
        this.isContractor = isContractor;
        this.servicesVans = servicesVans;
        this.servicesMediumTrucks = servicesMediumTrucks;
        this.servicesLargeTrucks = servicesLargeTrucks;
        serviceRecord = new ArrayList<ServiceEvent>();
    }
    public String getWorkshopName() {
        return workshopName;
    }
    public String getWorkshopAddress() {
        return workshopAddress;
    }
    public boolean getIsContractor() {
        return isContractor;
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
    public ArrayList<ServiceEvent> getServiceRecord() {
        return serviceRecord;
    }
    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }
    public void setWorkshopAddress(String workshopAddress) {
        this.workshopAddress = workshopAddress;
    }
    public void setIsContractor(boolean isContractor) {
        this.isContractor = isContractor;
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
    public void setServiceRecord(ArrayList<ServiceEvent> serviceRecord) {
        this.serviceRecord = serviceRecord;
    }
    public void addServiceEvent(ServiceEvent serviceEvent){
        serviceRecord.add(serviceEvent);
    }
    




}
