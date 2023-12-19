package lu.ics.se.models;

public class VehiclePart {
    private String partName;
    private double partCost;

    public VehiclePart() {

    }
    public VehiclePart(String partName, double partCost) {
        this.partName = partName;
        this.partCost = partCost;
    }
    public void setPartName(String partName) {
        this.partName = partName;
    }
    public void setPartCost(double partCost) {
        this.partCost = partCost;
    }
    public String getPartName() {
        return partName;
    }
    public double getPartCost() {
        return partCost;
    }


}
