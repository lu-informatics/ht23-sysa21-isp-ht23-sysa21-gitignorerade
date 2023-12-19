package lu.ics.se;

import lu.ics.se.models.Vehicle;

public class Main {
    public static void main(String[] args) {
        Vehicle Ford1 = new Vehicle("F87341", "Ford87", "Avesta", 1600, 140);
        Ford1.vehicleMaintenance();
        System.out.println(Ford1.getServiceRecord());

    }
}
