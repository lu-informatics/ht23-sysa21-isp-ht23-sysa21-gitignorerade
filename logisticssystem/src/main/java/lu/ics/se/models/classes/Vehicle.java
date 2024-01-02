package lu.ics.se.models.classes;
import lu.ics.se.models.enums.VehicleClass;
import lu.ics.se.models.enums.Locations;
import java.util.Random;

public class Vehicle {
    private String vehicleIdentificationNumber;
    private double capacityinKg;
    private String vehicleBrand;
    private VehicleClass vehicleClass;
    private Locations location;
    private Locations destination;
    private String vehicleName;

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
    public Locations getLocation() {
        return location;
    }
    public String getVehicleName() {
        return vehicleName;
    }
    public Locations getDestination() {
        return destination;
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
    public void setLocation(Locations location) {
        this.location = location;
    }
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }
    public void setDestination(Locations destination) {
        this.destination = destination;
    }
    public String generateVehicleIdentificationNumber() {
        String vehicleIdentificationNumber = "";
        if (this.vehicleClass == VehicleClass.Van) {
            vehicleIdentificationNumber = vehicleIdentificationNumber + "VN";
        } else if (this.vehicleClass == VehicleClass.Mediumtruck) {
            vehicleIdentificationNumber = "MT";
        } else if (this.vehicleClass == VehicleClass.Largetruck) {
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
    public String generateVehicleName(String vehicleIdentificationNumber, String vehicleBrand){
        String vehicleName = "";
        vehicleName = vehicleName + vehicleBrand + vehicleIdentificationNumber.substring(7, 9);
        return vehicleName;
    }
    public double[] getAllowedCapacityRange(){
        double[] allowedCapacityRange = new double[2];
        if (this.vehicleClass == VehicleClass.Van){
            allowedCapacityRange[0] = 0;
            allowedCapacityRange[1] = 2000;
        }
        else if (this.vehicleClass == VehicleClass.Mediumtruck){
            allowedCapacityRange[0] = 2000;
            allowedCapacityRange[1] = 8000;
        }
        else if (this.vehicleClass == VehicleClass.Largetruck){
            allowedCapacityRange[0] = 8000;
            allowedCapacityRange[1] = 20000;
        }
        return allowedCapacityRange;
    }

}
