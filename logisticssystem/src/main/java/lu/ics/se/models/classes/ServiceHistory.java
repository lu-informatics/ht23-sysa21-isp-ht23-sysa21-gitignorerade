package lu.ics.se.models.classes;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class ServiceHistory {
    private ObservableList<ServiceEvents> serviceHistory;

    public ServiceHistory(){
        this.serviceHistory = FXCollections.observableArrayList();
    }
    public ServiceHistory(ObservableList<ServiceEvents> serviceHistory) {
        this.serviceHistory = serviceHistory;
    }
    public void setServiceHistory(ObservableList<ServiceEvents> serviceHistory) {
        this.serviceHistory = serviceHistory;
    }
    public ObservableList<ServiceEvents> getServiceHistory() {
        return serviceHistory;
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
