package lu.ics.se.models;
import java.util.Scanner;

public class MaintenanceEventAdder {
    public MaintenanceEventAdder() {
    }
    public MaintenanceEvent createMaintenanceEvent(int numberOfMaintenanceEventsInServiceHistory){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the maintenance event description:");
        String description = scanner.nextLine();
        System.out.println("Please enter the maintenance event cost:");
        double cost = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Please enter the maintenance event date:");
        String date = scanner.nextLine();
        MaintenanceEvent maintenanceEvent = new MaintenanceEvent(cost, description, date, numberOfMaintenanceEventsInServiceHistory);
        System.out.println("Maintenance event added successfully!");
        return maintenanceEvent;
        
    }
    


}
