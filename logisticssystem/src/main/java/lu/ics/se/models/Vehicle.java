package lu.ics.se.models;

public class Vehicle {
    private String vehicleIdentificationNumber;
    private String vehicleName;
    private double capacityinKg;

    public Vehicle(){

    }
    public Vehicle(String vehicleIdentificationNumber, String vehicleName, double capacityinKg) {
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
        this.vehicleName = vehicleName;
        this.capacityinKg = capacityinKg;
    }
    public String getVehicleIdentificationNumber() {
        return vehicleIdentificationNumber;
    }
    public String getvehicleName() {
        return vehicleName;
    }
    public double getCapacityinKg() {
        return capacityinKg;
    }
    public void setVehicleIdentificationNumber(String vehicleIdentificationNumber) {
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
    }
    public void setvehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }
    public void setCapacityinKg(double capacityinKg) {
        this.capacityinKg = capacityinKg;
    }
    
}
