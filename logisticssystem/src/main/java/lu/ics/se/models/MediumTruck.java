package lu.ics.se.models;

public class MediumTruck extends Vehicle {
    private String vehicleIdentificationNumber;
    private String vehicleName;
    private double capacityinKg;

    public MediumTruck(){

    }
    public MediumTruck(String vehicleIdentificationNumber, String vehicleName, double capacityinKg) {
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
        this.vehicleName = vehicleName;
        this.capacityinKg = capacityinKg;
    }
    
}
