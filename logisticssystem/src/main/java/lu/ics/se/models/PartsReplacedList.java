package lu.ics.se.models;
import java.util.ArrayList;

public class PartsReplacedList {
    private ArrayList<VehiclePart> partsReplaced;

    public PartsReplacedList() {
        this.partsReplaced = new ArrayList<VehiclePart>();

    }
    public void addPartReplaced(VehiclePart partReplaced){
        partsReplaced.add(partReplaced);
    }
    public void removePartReplaced(VehiclePart partReplaced){
        partsReplaced.remove(partReplaced);
    }
    public ArrayList<VehiclePart> getPartsReplaced(){
        return partsReplaced;
    }
    public void setPartsReplaced(ArrayList<VehiclePart> partsReplaced){
        this.partsReplaced = partsReplaced;
    }

}
