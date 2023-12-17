package lu.ics.se.models;
import java.util.ArrayList;

public class ServiceRecord {
    private ArrayList<ServiceEvent> serviceEvent;

    public ServiceRecord() {
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

    
    

}

    


