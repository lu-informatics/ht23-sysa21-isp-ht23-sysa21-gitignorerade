package lu.ics.se.models;

public class Workshop {
    private String workshopName;
    private String workshopID;
    private int workshopIndexInWorkshopList;
    private ServiceHistory serviceHistoryofWorkshop;
    private VehicleManifest vehiclesPrimarilyServicedByWorkshop;

    public Workshop() {

    }
    public Workshop(String workshopName, String workshopID) {
        this.workshopName = workshopName;
        this.workshopID = workshopID;
        this.workshopIndexInWorkshopList = workshopIndexInWorkshopList;
        this.serviceHistoryofWorkshop = serviceHistoryofWorkshop;
        this.vehiclesPrimarilyServicedByWorkshop = vehiclesPrimarilyServicedByWorkshop;
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
    


}
