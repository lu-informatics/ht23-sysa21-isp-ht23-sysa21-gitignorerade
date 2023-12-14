package lu.ics.se;

import lu.ics.se.models.VehicleAdder;
import lu.ics.se.models.VehicleManifest;
import lu.ics.se.models.Vehicle;
import java.util.Scanner;

// todo: Add methods to remove vehicles from manifest and maintenanceevents from servicehistory
public class Main {
    public static void main(String[] args) {
        VehicleAdder vehicleAdder = new VehicleAdder();
        VehicleManifest vehicleManifest = new VehicleManifest();
        Scanner myScanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("Welcome to the Logistics System!");
            System.out.println("Please enter the number of the action you want to perform:");
            System.out.println("1. Add a vehicle");
            System.out.println("2. Print the vehicle manifest: ");
            System.out.println("3. Remove a vehicle");
            System.out.println("4. Access the service history of a vehicle");
            System.out.println("5. Exit the program");

            int action = myScanner.nextInt();

            myScanner.nextLine();

            switch (action) {
                case 1:
                    vehicleManifest.addVehicle(vehicleAdder.createVehicle());
                    break;
                case 2:
                    vehicleManifest.printVehicleManifest();
                    break;
                case 3:
                    System.out.println("Please enter the name of the vehicle you want to remove:");
                    String vehicleName = myScanner.nextLine();
                    Vehicle vehicleToRemove = vehicleManifest.getVehicleByName(vehicleName);
                    vehicleManifest.removeVehicle(vehicleToRemove);
                    System.out.println("Vehicle removed successfully!");
                    break;
                case 4:
                    System.out.println("Please enter the name of the vehicle you want to access the service history of:");
                    String vehicleNameToAccess = myScanner.nextLine();
                    Vehicle vehicleToAccess = vehicleManifest.getVehicleByName(vehicleNameToAccess);
                    if (vehicleToAccess == null) {
                        System.out.println("Vehicle not found!");
                        break;
                    }
                    boolean serviceHistoryIsRunning = true;
                    while (serviceHistoryIsRunning) {
                    System.out.println("What do you want to do?");
                    System.out.println("1. Add a maintenance event");
                    System.out.println("2. Remove a maintenance event");
                    System.out.println("3. Print the service history");
                    System.out.println("4. Exit the service history");
                    int accesServiceRecordAction = myScanner.nextInt();
                    myScanner.nextLine();
                    switch (accesServiceRecordAction) {
                        case 1:
                            vehicleToAccess.addMaintenanceEventByFunction();
                            break;
                        case 2:
                            vehicleToAccess.removeMaintenanceEventByFunction();
                            break;
                        case 3:
                            vehicleToAccess.printFormattedServiceHistory();
                            break;
                        case 4:
                            serviceHistoryIsRunning = false;
                            break;
                        default:
                            System.out.println("Please enter a valid number");
                            break;
                    }
                    if (!serviceHistoryIsRunning){
                    break;
                    }
                }
                break;
                case 5:
                    isRunning = false;
                    System.out.println("Thank you for using the Logistics System!");
                    break;
                default:
                    System.out.println("Please enter a valid number");
                    break;
            }
        }
        myScanner.close();
    }
}
