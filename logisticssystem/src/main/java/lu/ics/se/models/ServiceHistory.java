package lu.ics.se.models;
import java.util.ArrayList;

public class ServiceHistory {
    private ArrayList<ServiceEvent> serviceEvent;
    private String vin;
    private String serviceDate;
    private String description;
    private String cost;
    private String partsReplaced;
    private String workshopName;

    public ServiceHistory() {
        serviceEvent = new ArrayList<ServiceEvent>();
    }
public ServiceHistory (String vin, Object object, String description, Object object2, Object object3, Object object4) {
    this.vin = vin;
    this.serviceDate = serviceDate;
    this.description = description;
    this.cost = cost;
    this.partsReplaced = partsReplaced;
    this.workshopName = workshopName;
    serviceEvent = new ArrayList<ServiceEvent>();
}

    public class LargeTruckInternalServiceException extends Exception {
        public LargeTruckInternalServiceException(String message) {
            super(message);
        }
    }

    public void addServiceEvent(ServiceEvent serviceEvent) throws LargeTruckInternalServiceException {
        // Check if the vehicle type is "Large Truck" and the workshop is internal
        if (isLargeTruck(serviceEvent.getEventVehicle()) && isInternalWorkshop(serviceEvent.getEventWorkshop())) {
            throw new LargeTruckInternalServiceException("Large trucks cannot be serviced at internal workshops.");
        }

        this.serviceEvent.add(serviceEvent);
    }

    private boolean isInternalWorkshop(Workshop eventWorkshop) {
        return eventWorkshop != null && eventWorkshop.getIsInternal();
    }
    private boolean isLargeTruck(Vehicle eventVehicle) {
        return eventVehicle != null && eventVehicle.isLargeTruck();
    
    }
    public void removeServiceEvent(ServiceEvent serviceEvent) {
        serviceEvent.remove(serviceEvent);
    }

    public ArrayList<ServiceEvent> getAllServiceEvents() {
        return serviceEvent;
    }
    public void addMaintenanceEvent(ServiceEvent maintenanceEvent) {
        serviceEvent.add(maintenanceEvent);
    }

    public Workshop getMaintenanceWorkshopForVehicle(Vehicle vehicle) {
        for (ServiceEvent event : serviceEvent) {
            if (event.getEventVehicle().equals(vehicle)) {
                return event.getEventWorkshop();
            }
        }
        return null; // Handle the case when no maintenance event is found
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setPartsReplaced(String partsReplaced) {
        this.partsReplaced = partsReplaced;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    public String getVin() {
        return vin;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public String getDescription() {
        return description;
    }

    public String getCost() {
        return cost;
    }

    public String getPartsReplaced() {
        return partsReplaced;
    }

    public String getWorkshopName() {
        return workshopName;
    }
    public String calculateTotalCostForWorkshop() {
        return null;
    }
    public String getWorkshopAddress() {
        return null;
    }

    
}

    