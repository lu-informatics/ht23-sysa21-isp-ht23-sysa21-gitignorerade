package lu.ics.se.models.classes;

public class ServiceAction {
    private String actionName;
    private String actionDescription;
    private int numberOfPartsReplaced;
    private int costOfPartsReplaced;
    private int numberOfHoursWorked;

    public ServiceAction(){

    }
    public ServiceAction(String actionName, String actionDescription, int numberOfPartsReplaced, int costOfPartsReplaced, int numberOfHoursWorked) {
        this.actionName = actionName;
        this.actionDescription = actionDescription;
        this.numberOfPartsReplaced = numberOfPartsReplaced;
        this.costOfPartsReplaced = costOfPartsReplaced;
        this.numberOfHoursWorked = numberOfHoursWorked;
    }
    public String getActionName() {
        return actionName;
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
    public int getNumberOfHoursWorked() {
        return numberOfHoursWorked;
    }
    public void setActionName(String actionName) {
        this.actionName = actionName;
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
    public void setNumberOfHoursWorked(int numberOfHoursWorked) {
        this.numberOfHoursWorked = numberOfHoursWorked;
    }
    

}
