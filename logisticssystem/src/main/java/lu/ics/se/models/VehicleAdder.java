package lu.ics.se.models;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class VehicleAdder {
    private Map<String, Vehicle> vehiclesMap = new HashMap<String, Vehicle>();

    public VehicleAdder() {

    }
    public Vehicle createVehicle(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the vehicle identification number:");
        String vehicleIdentificationNumber = scanner.nextLine();
        System.out.println("Please enter the vehicle name:");
        String vehicleName = scanner.nextLine();
        System.out.println("Please enter the vehicle capacity:");
        double vehicleCapacity = scanner.nextDouble();
        int daysSinceLastMaintenance = 0;
        Vehicle vehicle = new Vehicle(vehicleIdentificationNumber, vehicleName, vehicleCapacity, daysSinceLastMaintenance);
        vehiclesMap.put(vehicleName, vehicle);
        System.out.println("Vehicle added successfully!");
        return vehicle;
    }

    
}

