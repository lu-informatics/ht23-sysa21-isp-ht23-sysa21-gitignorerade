package lu.ics.se.models;
import java.util.Scanner;

public class MaintenanceEventAdder {
    public MaintenanceEventAdder() {
    }
    public MaintenanceEvent createMaintenanceEvent(int numberOfMaintenanceEventsInServiceHistory, Vehicle vehicleServiced){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the maintenance event description:");
        String description = scanner.nextLine();
        System.out.println("Please enter the maintenance event cost:");
        double cost = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Please enter the maintenance event date:");
        String date = scanner.nextLine();
        MaintenanceEvent maintenanceEvent = new MaintenanceEvent(cost, description, date, numberOfMaintenanceEventsInServiceHistory, vehicleServiced);
        System.out.println("Maintenance event added successfully!");
        return maintenanceEvent;
        
    }
    


}
