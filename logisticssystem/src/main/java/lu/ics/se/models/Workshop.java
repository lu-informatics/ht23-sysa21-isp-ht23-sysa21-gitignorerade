package lu.ics.se.models;

public class Workshop {
    private String workshopName;
    private String workshopID;
    private int workshopIndexInWorkshopList;
    private boolean isInternalWorkshop;
    private ServiceHistory serviceHistoryofWorkshop;
    private VehicleManifest vehiclesPrimarilyServicedByWorkshop;

    public Workshop() {

    }
    public Workshop(String workshopName, String workshopID, boolean isInternalWorkshop) {
        this.workshopName = workshopName;
        this.workshopID = workshopID;
        this.workshopIndexInWorkshopList = workshopIndexInWorkshopList;
        this.serviceHistoryofWorkshop = serviceHistoryofWorkshop;
        this.vehiclesPrimarilyServicedByWorkshop = vehiclesPrimarilyServicedByWorkshop;
        this.isInternalWorkshop = isInternalWorkshop; 
    }
    public String getWorkshopName() {
        return workshopName;
    }
    public String getWorkshopID() {
        return workshopID;
    }
    public int getWorkshopIndexInWorkshopList() {
        return workshopIndexInWorkshopList;
    }
    public ServiceHistory getServiceHistoryofWorkshop() {
        return serviceHistoryofWorkshop;
    }
    public VehicleManifest getVehiclesPrimarilyServicedByWorkshop() {
        return vehiclesPrimarilyServicedByWorkshop;
    }
    public boolean getIsInternalWorkshop() {
        return isInternalWorkshop;
    }
    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }
    public void setWorkshopID(String workshopID) {
        this.workshopID = workshopID;
    }
    public void setWorkshopIndexInWorkshopList(int workshopIndexInWorkshopList) {
        this.workshopIndexInWorkshopList = workshopIndexInWorkshopList;
    }
    public void setServiceHistoryofWorkshop(ServiceHistory serviceHistoryofWorkshop) {
        this.serviceHistoryofWorkshop = serviceHistoryofWorkshop;
    }
    public void setVehiclesPrimarilyServicedByWorkshop(VehicleManifest vehiclesPrimarilyServicedByWorkshop) {
        this.vehiclesPrimarilyServicedByWorkshop = vehiclesPrimarilyServicedByWorkshop;
    }
    public void setIsInternalWorkshop(boolean isInternalWorkshop) {
        this.isInternalWorkshop = isInternalWorkshop;
    }
    
    


}
