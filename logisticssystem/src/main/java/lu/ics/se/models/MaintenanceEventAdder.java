package lu.ics.se.models;

import java.util.Scanner;

public class MaintenanceEventAdder {
    public MaintenanceEventAdder() {
    }

    public MaintenanceEvent createMaintenanceEvent(int numberOfMaintenanceEventsInServiceHistory, Vehicle vehicleServiced){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the maintenance event description:");
        String description = scanner.nextLine();
        System.out.println("Please enter the maintenance event date:");
        String date = scanner.nextLine();
        MaintenanceEvent maintenanceEvent = new MaintenanceEvent(description, date, numberOfMaintenanceEventsInServiceHistory, vehicleServiced);
        System.out.println("Maintenance event added successfully!");

        
        boolean serviceActionAdderIsRunning = true;
        while (serviceActionAdderIsRunning){
        System.out.println("What do you want to do now?" + 
        "\n" + "1. Add an action to this maintenance event" +
        "\n" + "2. Save maintenance event and return back to the main menu");

        int maintenanceActionChoice = scanner.nextInt();
        scanner.nextLine();
        while (serviceActionAdderIsRunning){
            switch (maintenanceActionChoice) {
                
                case 1:
                System.out.println("Please enter the maintenance action description:");
                String maintenanceActionDescription = scanner.nextLine();
                System.out.println("Please enter the maintenance action cost:");
                Double maintenanceActionCost = scanner.nextDouble();
                scanner.nextLine();
                MaintenanceServiceAction maintenanceServiceAction = new MaintenanceServiceAction(maintenanceActionDescription, maintenanceActionCost);
                System.out.println("Where any parts replaced?");
                System.out.println("1. Yes" + 
                "\n" + "2. No");
                int partsReplacedChoice = scanner.nextInt();
                scanner.nextLine();
                
                switch (partsReplacedChoice){
                    case 1:
                    int addAnotherPartChoice = 1;
                    do{
                    System.out.println("Please enter the part description:");
                    String partDescription = scanner.nextLine();
                    System.out.println("Please enter the part cost:");
                    Double partCost = scanner.nextDouble();
                    scanner.nextLine();
                    VehiclePart vehiclePart = new VehiclePart(partDescription, partCost);
                    maintenanceServiceAction.getPartsReplaced().addPartReplaced(vehiclePart);
                    System.out.println("Do you want to add another part?");
                    System.out.println("1. Yes" + 
                    "\n" + "2. No");
                    addAnotherPartChoice = scanner.nextInt();
                    scanner.nextLine();
                    } while (addAnotherPartChoice == 1);
                    maintenanceEvent.getMaintenanceServiceActions().add(maintenanceServiceAction);
                    
                    case 2:
                    break;
                    
                    default:
                    System.out.println("Invalid input!");
                }
                break;
                case 2:
                serviceActionAdderIsRunning = false;
                System.out.println("Maintenance event saved successfully!");
                break;

                default:
                System.out.println("Invalid input!");
                break;
            }


}
        
}
return maintenanceEvent;
}
}
