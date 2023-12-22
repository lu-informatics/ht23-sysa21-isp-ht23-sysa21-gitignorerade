package lu.ics.se.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Part {
    private String partName;
    private ArrayList<Part> partsReplaced;

public Part(String partName) {
    this.partName = partName;
    partsReplaced = new ArrayList<Part>();
}

public String getPartName() {
    return partName;
}

public void setPartName(String partName) {
    this.partName = partName;
}

public ArrayList<Part> getPartsReplaced() {
    return partsReplaced;
}

public void setPartsReplaced(ArrayList<Part> partsReplaced) {
    this.partsReplaced = partsReplaced;
}

public void addPart(Part part) {
    partsReplaced.add(part);
}

public void removePart(Part part) {
    partsReplaced.remove(part);
}

}
