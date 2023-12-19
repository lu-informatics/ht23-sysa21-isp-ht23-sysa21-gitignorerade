package lu.ics.se.models;

import java.util.ArrayList;
import java.util.Scanner;

public class Vehicle {
    private String vehicleIdentificationNumber;
    private String vehicleName;
    private String location;
    private double capacityinKg;
    private String vehicleType;
    private double daysSinceLastService;
    private ArrayList<ServiceEvent> serviceRecord;

    public Vehicle(){
        this.serviceRecord = new ArrayList<ServiceEvent>();
    }
    public Vehicle(String vehicleIdentificationNumber, String vehicleName, String location, double capacityinKg, double daysSinceLastService) {
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
        this.vehicleName = vehicleName;
        this.location = location;
        this.capacityinKg = capacityinKg;
        this.daysSinceLastService = daysSinceLastService;
        this.serviceRecord = new ArrayList<ServiceEvent>();
        if (capacityinKg < 1500) {
            this.vehicleType = "Van";
        } else if (capacityinKg < 2500) {
            this.vehicleType = "Medium Truck";
        } else {
            this.vehicleType = "Large Truck";
        }

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
    public String getLocation() {
        return location;
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
    public void setLocation(String location) {
        this.location = location;
    }
    public void addVehicleServiceEvent (ServiceEvent serviceEvent){
        this.serviceRecord.add(serviceEvent);
    }
    public void vehicleMaintenance(){
        Scanner myScanner = new Scanner(System.in);
        Boolean userWantsProgramToContinue = true;
        System.out.println("Vehicle is in workshop");
        while (userWantsProgramToContinue){
        System.out.println("What would you like to do?" + "\n" + "1. Add service action" + "\n" + "2. Exit");
        ServiceEvent serviceEventforMaintenance = new ServiceEvent();
        
            int userChoice = myScanner.nextInt();
            myScanner.nextLine();
            switch (userChoice){
                case 1:
                    ServiceAction serviceActionToAdd = new ServiceAction();
                    System.out.println("Please enter the name of the service action: ");
                    String serviceActionName = myScanner.nextLine();
                    serviceActionToAdd.setActionName(serviceActionName);
                    System.out.println("Please enter the description of the service action: ");
                    String serviceActionDescription = myScanner.nextLine();
                    serviceActionToAdd.setActionDescription(serviceActionDescription);
                    System.out.println("Service action added");
                    break;
                case 2:
                    System.out.println("Exiting");
                    userWantsProgramToContinue = false;
                    break;
            }    
        }
    }
}
        



