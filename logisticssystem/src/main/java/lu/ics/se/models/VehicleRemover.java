package lu.ics.se.models;
import java.util.Scanner;

public class VehicleRemover {

    public VehicleRemover() {
    }
    public void removeVehicle(VehicleManifest vehicleList){
        Scanner scanner = new Scanner(System.in);
        vehicleList.printVehicleManifest();
        System.out.println("Please enter the number of the vehicle you want to remove:");
        int vehicleNumber = scanner.nextInt();
        Vehicle vehicleToRemove = vehicleList.getVehicleByNumber(vehicleNumber);
        vehicleList.removeVehicle(vehicleToRemove);
        vehicleList.renumberVehicles();
        System.out.println("Vehicle removed successfully!");
        }


}
