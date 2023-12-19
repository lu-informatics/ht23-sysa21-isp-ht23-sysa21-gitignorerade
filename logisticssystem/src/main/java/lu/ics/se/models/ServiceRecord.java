package lu.ics.se.models;
import java.util.ArrayList;

public class ServiceRecord {
    private ArrayList<ServiceEvent> serviceRecord;

    public ServiceRecord() {
        serviceRecord = new ArrayList<ServiceEvent>();
    }
    public void addServiceEvent(ServiceEvent serviceEvent) {
        serviceRecord.add(serviceEvent);
    }
    public ArrayList<ServiceEvent> getServiceRecord() {
        return serviceRecord;
    }
    public void setServiceRecord(ArrayList<ServiceEvent> serviceRecord) {
        this.serviceRecord = serviceRecord;
    }

}
