package lu.ics.se.models;
import java.util.ArrayList;

public class ServiceAction {
    private String actionName;
    private String actionDescription;
    private ArrayList<Part> partsReplaced;

    public ServiceAction(){

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
    public void addReplacedPart(Part part){
        partsReplaced.add(part);
    }




}
