package lu.ics.se.models;
import java.util.Scanner;

public class MaintenanceEventRemover {
    public MaintenanceEventRemover() {

    }
    public void removeMaintenanceEvent(ServiceHistory serviceHistory){
        Scanner scanner = new Scanner(System.in);
        serviceHistory.printServiceHistory();
        System.out.println("Please enter the number of the maintenance event you want to remove:");
        int maintenanceEventNumber = scanner.nextInt();
        MaintenanceEvent maintenanceEventToRemove = serviceHistory.getMaintenanceEventByNumber(maintenanceEventNumber);
        serviceHistory.removeMaintenanceEvent(maintenanceEventToRemove);
        System.out.println("Maintenance event removed successfully!");
        }
    }
