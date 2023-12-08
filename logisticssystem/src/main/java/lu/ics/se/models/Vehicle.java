package lu.ics.se.models;

import java.util.ArrayList;

public class Vehicle {
    private String vehicleIdentificationNumber;
    private String vehicleName;
    private String location;
    private double capacityinKg;
    private String vehicleType;
    private double daysSinceLastService;
    private ArrayList<ServiceEvent> serviceRecord;

    public Vehicle(){
    }
    public Vehicle(String vehicleIdentificationNumber, String vehicleName, String location, double capacityinKg, String vehicleType, double daysSinceLastService) {
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
        this.vehicleName = vehicleName;
        this.location = location;
        this.capacityinKg = capacityinKg;
        this.vehicleType = vehicleType;
        this.daysSinceLastService = daysSinceLastService;
        this.serviceRecord = new ArrayList<ServiceEvent>();

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
    public String getVehicleType() {
        return vehicleType;
    }
    public double getDaysSinceLastService() {
        return daysSinceLastService;
    }
    public ArrayList<ServiceEvent> getServiceRecord() {
        return serviceRecord;
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
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
    public void setDaysSinceLastService(double daysSinceLastService) {
        this.daysSinceLastService = daysSinceLastService;
    }
    public void setServiceRecord(ArrayList<ServiceEvent> serviceRecord) {
        this.serviceRecord = serviceRecord;
    }
    public void addServiceEvent(ServiceEvent serviceEvent){
        this.serviceRecord.add(serviceEvent);
    }
    public void removeServiceEvent(ServiceEvent serviceEvent){
        this.serviceRecord.remove(serviceEvent);
    }

}

