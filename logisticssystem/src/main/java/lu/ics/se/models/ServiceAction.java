package lu.ics.se.models;

import java.time.LocalDate;
import java.util.ArrayList;

public class ServiceAction {
    private String actionName;
    private String actionDescription;
    private ArrayList<Part> partsReplaced;

    public ServiceAction(String string, String description, double cost, LocalDate serviceDate, Object object, String partsReplaced2){

    }
    public ServiceAction(String actionName, String actionDescription){
        this.actionName = actionName;
        this.actionDescription = actionDescription;
        partsReplaced = new ArrayList<Part>();
    }
    public String getActionName(){
        return actionName;
    }
    public String getActionDescription(){
        return actionDescription;
    }
    public void setActionName(String actionName){
        this.actionName = actionName;
    }
    public void setActionDescription(String actionDescription){
        this.actionDescription = actionDescription;
    }

    public ArrayList<Part> getPartsReplaced() {
        return partsReplaced;
    }

    public void setPartsReplaced(ArrayList<Part> partsReplaced) {
        this.partsReplaced = partsReplaced;
    }
    @Override
    public String toString() {
        // Modify toString method to include information about parts replaced
        StringBuilder result = new StringBuilder();
        result.append("Action Name: ").append(actionName).append("\n");
        result.append("Action Description: ").append(actionDescription).append("\n");

        // Add information about parts replaced
        result.append("Parts Replaced:\n");
        for (Part part : partsReplaced) {
            result.append("  - ").append(part.getPartName()).append("\n");
        }

        return result.toString();
    }
    public void setPartsReplaced(String partsReplaced2) {
    }
    public double getCost() {
        return 0;
    }
}




