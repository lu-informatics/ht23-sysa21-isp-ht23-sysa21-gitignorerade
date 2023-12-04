package lu.ics.se.models;

public class LargeTruck extends Vehicle{
    private String vehicleIdentificationNumber;
    private String vehicleName;
    private double capacityinKg;

    public LargeTruck(){

    }
    public LargeTruck(String vehicleIdentificationNumber, String vehicleName, double capacityinKg) {
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
        this.vehicleName = vehicleName;
        this.capacityinKg = capacityinKg;
    }
}
