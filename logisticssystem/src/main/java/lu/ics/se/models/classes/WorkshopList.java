package lu.ics.se.models.classes;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class WorkshopList {
    private ObservableList <Workshop> workshopList;

    public WorkshopList() {
        this.workshopList = FXCollections.observableArrayList();
    }
    public ObservableList<Workshop> getWorkshopList() {
        return workshopList;
    }
    public void setWorkshopList(ObservableList<Workshop> workshopList) {
        this.workshopList = workshopList;
    }
    public void addWorkshop(Workshop workshop) {
        this.workshopList.add(workshop);
    }
    public void removeWorkshop(Workshop workshop) {
        this.workshopList.remove(workshop);
    }




}
