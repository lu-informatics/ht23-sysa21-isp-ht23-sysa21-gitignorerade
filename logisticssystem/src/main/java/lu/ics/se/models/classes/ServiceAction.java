package lu.ics.se.models.classes;

public class ServiceAction {
    private String actionDescription;
    private int numberOfPartsReplaced;
    private int costOfPartsReplaced;
    private int costOfService;
    private int totalCost;

    public ServiceAction(){

    }
    public ServiceAction(String actionDescription, int numberOfPartsReplaced, int costOfPartsReplaced, int numberOfHoursWorked) {
        this.actionDescription = actionDescription;
        this.numberOfPartsReplaced = numberOfPartsReplaced;
        this.costOfPartsReplaced = costOfPartsReplaced;
        this.costOfService= numberOfHoursWorked;
    }
    public String getActionDescription() {
        return actionDescription;
    }
    public int getNumberOfPartsReplaced() {
        return numberOfPartsReplaced;
    }
    public int getCostOfPartsReplaced() {
        return costOfPartsReplaced;
    }
    public int getCostOfService() {
        return costOfService;
    }
    public int getTotalCost() {
        return totalCost;
    }
    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }
    public void setNumberOfPartsReplaced(int numberOfPartsReplaced) {
        this.numberOfPartsReplaced = numberOfPartsReplaced;
    }
    public void setCostOfPartsReplaced(int costOfPartsReplaced) {
        this.costOfPartsReplaced = costOfPartsReplaced;
    }
    public void setCostOfService(int numberOfHoursWorked) {
        this.costOfService = numberOfHoursWorked;
    }
    public void setTotalCost(int totalCost) {
        this.totalCost = costOfPartsReplaced + costOfService;
    }

    

}
