package lu.ics.se.models;


public class MaintenanceServiceAction {
    private String maintenanceDescription;
    private double actionCost;
    private PartsReplacedList partsReplaced;

    public MaintenanceServiceAction() {

    }
    public MaintenanceServiceAction(String maintenanceDescription, double actionCost) {
        this.maintenanceDescription = maintenanceDescription;
        this.actionCost = actionCost;
        this.partsReplaced = new PartsReplacedList();
    }

    public String getMaintenanceDescription() {
        return maintenanceDescription;
    }
    public double getActionCost() {
        return this.calculateMaintenanceServiceActionCost();
    }
    public PartsReplacedList getPartsReplaced() {
        return partsReplaced;
    }
    public void setMaintenanceDescription(String maintenanceDescription) {
        this.maintenanceDescription = maintenanceDescription;
    }
    public void setActionCost(double actionCost) {
        this.actionCost = this.calculateMaintenanceServiceActionCost();
    }
    public void setPartReplaced(PartsReplacedList partsReplaced) {
        this.partsReplaced = partsReplaced;
    }
    public double calculateMaintenanceServiceActionCost(){
        double totalCost = actionCost;
        for (VehiclePart partReplaced : partsReplaced.getPartsReplaced()) {
            totalCost += partReplaced.getPartCost();
        }
        return totalCost;
    }


}
