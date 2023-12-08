package lu.ics.se.models;

public class ServiceAction {
    private String actionName;
    private String actionDescription;

    public ServiceAction(){

    }
    public ServiceAction(String actionName, String actionDescription){
        this.actionName = actionName;
        this.actionDescription = actionDescription;
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
    



}
