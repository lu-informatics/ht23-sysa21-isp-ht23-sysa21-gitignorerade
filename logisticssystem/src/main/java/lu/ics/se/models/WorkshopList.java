package lu.ics.se.models;

import java.util.ArrayList;

public class WorkshopList {
    private ArrayList<Workshop> workshopList = new ArrayList<Workshop>();

    public WorkshopList() {

    }
    public void addWorkshop(Workshop workshop){
        workshopList.add(workshop);
    }
    public void removeWorkshop(Workshop workshop){
        workshopList.remove(workshop);
    }
    public ArrayList<Workshop> getWorkshopList(){
        return workshopList;
    }
    public void setWorkshopList(ArrayList<Workshop> workshopList){
        this.workshopList = workshopList;
    }



}
