package lu.ics.se.models.classes;
import java.util.ArrayList;

public class ServiceHistory {
    private ArrayList<ServiceEvents> serviceHistory;

    public ServiceHistory(){
        this.serviceHistory = new ArrayList<>();
    }
    public ArrayList<ServiceEvents> getServiceHistory() {
        return serviceHistory;
    }
    public void setServiceHistory(ArrayList<ServiceEvents> serviceHistory) {
        this.serviceHistory = serviceHistory;
    }
    public void addServiceEvent(ServiceEvents serviceEvent){
        this.serviceHistory.add(serviceEvent);
    }
    public void removeServiceEvent(ServiceEvents serviceEvent){
        this.serviceHistory.remove(serviceEvent);
    }
    public void removeServiceEventByIndex(int index){
        this.serviceHistory.remove(index);
    }



}
