package lu.ics.se.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.Date;

public class TestForModelIntegration {

    public static void main(String[] args) {
        // Create workshops
        Workshop internalWorkshop = new Workshop("Internal Workshop", "Address 1", "Internal", true, true, false);
        Workshop externalWorkshop = new Workshop("External Workshop", "Address 2", "External", false, true, true);
        // Create vehicles
        Vehicle largeTruck = new Vehicle("VIN123", "Volvo FH16", "Location 1", 5000, "Large truck", 30);
        Vehicle mediumTruck = new Vehicle("VIN456", "Ford F150", "Location 2", 3000, "Medium truck", 20);
        Vehicle van = new Vehicle("VIN789", "Mercedes Sprinter", "Location 3", 2000, "Van", 15);

        // Create service actions
        ServiceAction replaceTire = new ServiceAction("Replace Tire", "Replace the old tire");

        Part tire = new Part("Tire");

        // Add parts to events

        class ServiceEvent {
            private String eventName;
            private Date eventDate;

            public ServiceEvent(String eventName, Date eventDate) {
                this.eventName = eventName;
                this.eventDate = eventDate;
            }

            public lu.ics.se.models.ServiceEvent getEventName() {
                return null;
            }

            public Object getEventDate() {
                return null;
            }

            // Getters and setters
        }

        // Rest of the code...

        // Initialize service history
        ServiceHistory serviceHistory = new ServiceHistory();

        // Add service events to service history
        try {
            ServiceEvent maintenanceEvent1 = new ServiceEvent("Maintenance Event 1", new Date());
            ServiceEvent maintenanceEvent2 = new ServiceEvent("Maintenance Event 2", new Date());
            serviceHistory.addServiceEvent(maintenanceEvent1.getEventName(), maintenanceEvent1.getEventDate());
            serviceHistory.addServiceEvent(maintenanceEvent2.getEventName(), maintenanceEvent2.getEventDate());
        } catch (ServiceHistory.LargeTruckInternalServiceException e) {
            e.printStackTrace();
        }

        // Set service history for vehicles
        largeTruck.setServiceHistory(serviceHistory);
        mediumTruck.setServiceHistory(serviceHistory);
        van.setServiceHistory(serviceHistory);

        // Create a vehicle manifest
        VehicleManifest vehicleManifest = VehicleManifest.getInstance();
        vehicleManifest.addVehicle(largeTruck);
        vehicleManifest.addVehicle(mediumTruck);
        vehicleManifest.addVehicle(van);

        // Display information
        System.out.println("=== Vehicle Manifest ===");
        for (Vehicle vehicle : vehicleManifest.getCompanyOwnedVehicles()) {
            System.out.println("Vehicle: " + vehicle.getVehicleName());
            System.out.println("Type: " + vehicle.getVehicleType());
            System.out.println("Location: " + vehicle.getLocation());
            System.out.println("Capacity: " + vehicle.getCapacityInKg());
            System.out.println("Days since last service: " + vehicle.getDaysSinceLastService());
            System.out.println("Workshops serviced in: " + vehicle.getWorkshopsServicedIn());
            System.out.println("Number of parts replaced: " + vehicle.getNumberOfPartsReplaced());
            System.out.println("Total cost of service: " + vehicle.calculateTotalServiceCost());
            System.out.println("Is decommissioned: " + vehicle.isDecommissioned());
            System.out.println("=========================");
        }

        System.out.println("=== Workshops ===");
        WorkshopList workshopList = new WorkshopList();
        workshopList.addWorkshop(internalWorkshop);
        workshopList.addWorkshop(externalWorkshop);
        for (Workshop workshop : workshopList.getAllWorkshops()) {
            System.out.println("Workshop: " + workshop.getName());
            System.out.println("Address: " + workshop.getAddress());
            System.out.println("Is internal: " + workshop.getIsInternal());
            System.out.println("Services vans: " + workshop.getServicesVans());
            System.out.println("Services medium trucks: " + workshop.getServicesMediumTrucks());
            System.out.println("Services large trucks: " + workshop.getServicesLargeTrucks());
            System.out.println("=========================");
        }

        System.out.println("=== Most Expensive Workshop ===");
        Workshop mostExpensiveWorkshop = workshopList.getMostExpensiveWorkshop();
        if (mostExpensiveWorkshop != null) {
            System.out.println("Most expensive workshop: " + mostExpensiveWorkshop.getName());
            System.out.println("Total cost: " + workshopList.calculateTotalCostForWorkshop(mostExpensiveWorkshop));
        } else {
            System.out.println("No workshops available.");
        }
    }
}



