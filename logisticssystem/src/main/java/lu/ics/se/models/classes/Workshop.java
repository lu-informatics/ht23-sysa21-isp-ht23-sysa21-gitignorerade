package lu.ics.se.models.classes;
import lu.ics.se.models.enums.Locations;

public class Workshop {
    private String workshopName;
    private Locations workshopLocation;
    private VehicleManifest vehiclesPrimarilyServiced;
    private Boolean isInternal;
    private ServiceHistory serviceHistory;


    public Workshop(){
        this.serviceHistory = new ServiceHistory();
        this.vehiclesPrimarilyServiced = new VehicleManifest();
    }
    public Workshop(String workshopName, Locations workshopLocation, Boolean isInternal, Double hourlyRate) {
        this.workshopName = workshopName;
        this.workshopLocation = workshopLocation;
        this.isInternal = isInternal;
        this.serviceHistory = new ServiceHistory();
        this.vehiclesPrimarilyServiced = new VehicleManifest();
    }

    public String getWorkshopName() {
        return workshopName;
    }
    public Locations getWorkshopLocation() {
        return workshopLocation;
    }
    public VehicleManifest getVehiclesPrimarilyServiced() {
        return vehiclesPrimarilyServiced;
    }
    public Boolean getIsInternal() {
        return isInternal;
    }
    public ServiceHistory getServiceHistory() {
        return serviceHistory;
    }
    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }
    public void setWorkshopLocation(Locations workshopLocation) {
        this.workshopLocation = workshopLocation;
    }
    public void setVehiclesPrimarilyServiced(VehicleManifest vehiclesPrimarilyServiced) {
        this.vehiclesPrimarilyServiced = vehiclesPrimarilyServiced;
    }
    public void setIsInternal(Boolean internal) {
        isInternal = internal;
    }
    public void setServiceHistory(ServiceHistory serviceHistory) {
        this.serviceHistory = serviceHistory;
    }
    
}
