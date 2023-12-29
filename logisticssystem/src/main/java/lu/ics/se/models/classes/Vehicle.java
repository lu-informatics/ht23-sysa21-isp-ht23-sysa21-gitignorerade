package lu.ics.se.models.classes;
import lu.ics.se.models.enums.VehicleClass;
import java.util.Random;

public class Vehicle {
    private String vehicleIdentificationNumber;
    private double capacityinKg;
    private String vehicleBrand;
    private VehicleClass vehicleClass;

    public Vehicle(){

    }
    public Vehicle(String vehicleBrand, double capacityinKg, VehicleClass vehicleClass) {
        this.vehicleBrand = vehicleBrand;
        this.capacityinKg = capacityinKg;
        this.vehicleClass = vehicleClass;
    }
    public String getVehicleIdentificationNumber() {
        return vehicleIdentificationNumber;
    }
    public String getVehicleBrand() {
        return vehicleBrand;
    }
    public double getCapacityinKg() {
        return capacityinKg;
    }
    public VehicleClass getVehicleClass() {
        return vehicleClass;
    }
    public void setVehicleIdentificationNumber(String vehicleIdentificationNumber) {
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
    }
    public void setvehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }
    public void setCapacityinKg(double capacityinKg) {
        this.capacityinKg = capacityinKg;
    }
    public void setVehicleClass(VehicleClass vehicleClass) {
        this.vehicleClass = vehicleClass;
    }
    public String generateVehicleIdentificationNumber() {
        String vehicleIdentificationNumber = "";
        if (this.vehicleClass == VehicleClass.VAN) {
            vehicleIdentificationNumber = vehicleIdentificationNumber + "VN";
        } else if (this.vehicleClass == VehicleClass.MEDIUMTRUCK) {
            vehicleIdentificationNumber = "MT";
        } else if (this.vehicleClass == VehicleClass.LARGETRUCK) {
            vehicleIdentificationNumber = "LT";
        }
        vehicleIdentificationNumber = vehicleIdentificationNumber + "-";
        vehicleIdentificationNumber = vehicleIdentificationNumber + this.vehicleBrand.substring(0, 2).toUpperCase();

        Random randomNumber = new Random();
        Integer num1 = randomNumber.nextInt(10);
        Integer num2 = randomNumber.nextInt(10);
        Integer num3 = randomNumber.nextInt(10);
        Integer num4 = randomNumber.nextInt(10);
        Integer num5 = randomNumber.nextInt(10);
        Integer num6 = randomNumber.nextInt(10);

        vehicleIdentificationNumber = vehicleIdentificationNumber + "-"  + num1.toString() + num2.toString() + num3.toString() + num4.toString() + num5.toString() + num6.toString();

        return vehicleIdentificationNumber;

    }
}
