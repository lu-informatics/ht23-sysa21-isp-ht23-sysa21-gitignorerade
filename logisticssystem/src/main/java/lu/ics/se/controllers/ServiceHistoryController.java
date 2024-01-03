package lu.ics.se.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lu.ics.se.models.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



import java.text.SimpleDateFormat;

public class ServiceHistoryController {
// Vehicle Tab
@FXML
private TextField vehicleNameField;
@FXML
private TextField locationField;
@FXML
private TextField capacityField;
@FXML
private TextField daysSinceLastServiceField;
@FXML
private TextField vehicleTypeField;

@FXML
private TableView<Vehicle> vehicleTable;
@FXML
private TableColumn<Vehicle, String> vinColumn;
@FXML
private TableColumn<Vehicle, String> nameColumn;
@FXML
private TableColumn<Vehicle, String> locationColumn;
@FXML
private TableColumn<Vehicle, Double> capacityColumn;
@FXML
private TableColumn<Vehicle, String> typeColumn;
@FXML
private TableColumn<Vehicle, Double> daysSinceLastServiceColumn;
@FXML
private Button calculateButton;
@FXML
private Label resultLabel;

  




// Workshop Tab
@FXML
private TableView<Workshop> workshopTable;
@FXML
private TableColumn<Workshop, String> workshopNameColumn;
@FXML
private TableColumn<Workshop, String> workshopAddressColumn;
@FXML
private TableColumn<Workshop, String> workshopTypeColumn;

@FXML
private TextField nameField;
@FXML
private TextField addressField;
@FXML
private TextField typeField;

@FXML
private Button addWorkshopButton;
@FXML
private Button editWorkshopButton;
@FXML
private Button deleteWorkshopButton;



// Maintenance Tab
@FXML
private TabPane maintenanceTabPane;

@FXML
private TextField vehicleNameTextField;
@FXML
private DatePicker maintenanceDatePicker;

@FXML
private Button addMaintenanceButton;

@FXML
private TableView<ServiceEvent> maintenanceTableView;
@FXML
private TableColumn<ServiceEvent, String> vehicleColumn;
@FXML
private TableColumn<ServiceEvent, String> dateColumn;
@FXML
private TableColumn<ServiceEvent, String> workshopColumn;

@FXML
private Label averageCostLabel;
@FXML
private Label mostExpensiveLabel;

@FXML
private Label vehicleLabel;
@FXML
private Label dateLabel;
@FXML
private Label workshopLabel;
@FXML
private Button editButton;
@FXML
private Button deleteButton;
@FXML
private Button showAverageCostButton;
@FXML
private Button showMostExpensiveButton;

@FXML
private Button clearServiceHistory;


@FXML
private TextField vinFieldSH;

@FXML
private TextField costField;

@FXML
private TextField workshopNameTextFieldSH;


@FXML
private Button clearVehicleServiceHistory;

@FXML
private Button clearServiceHistoryView;

@FXML
private Button loadVehicleServiceHistory;

@FXML
private VehicleManifest vehicleManifest;

@FXML
private Button loadWorkshopHistory;
@FXML
private ListView<String> vehicleServiceHistoryListView;
private static List<ServiceHistory> serviceHistories;

@FXML
    private ListView<String> serviceHistoryListView;



    public ServiceHistoryController() {
        this.serviceHistories = new ArrayList<>();
        this.vehicleManifest = VehicleManifest.getInstance();
    }

    public void setServiceHistories(List<ServiceHistory> histories) {
        serviceHistories = histories;
    }

    
    
    private Vehicle getVehicleByVIN(String vin) {
        List<Vehicle> vehicles = vehicleManifest.getCompanyOwnedVehicles();
        if (vehicles != null) {
            for (Vehicle vehicle : vehicles) {
                if (vehicle.getVehicleIdentificationNumber().equals(vin)) {
                    return vehicle;
                }
            }
        }
        return null;
    }

    @FXML
public void loadVehicleServiceHistory(ActionEvent event) {
    String vin = vinFieldSH.getText();
    if (vin != null && !vin.trim().isEmpty()) {
        vin = vin.trim();
        if (validateVIN(vin)) {
            Vehicle vehicle = getVehicleByVIN(vin);
            if (vehicle != null) {
                List<ServiceHistory> vehicleServiceHistories = findServiceHistoriesByVIN(vin);
                vehicleServiceHistoryListView.getItems().clear();
                if (vehicleServiceHistories != null && !vehicleServiceHistories.isEmpty()) {
                    for (ServiceHistory serviceHistory : vehicleServiceHistories) {
                        vehicleServiceHistoryListView.getItems().add(formatServiceHistory(serviceHistory));
                    }
                } else {
                    vehicleServiceHistoryListView.getItems().add("Service history not found for the vehicle");
                }
            } else {
                showAlert("Vehicle Not Found", "No vehicle found with the entered VIN.", vin);
            }
        } else {
            showAlert("Invalid VIN", "The entered VIN is not valid.", vin);
        }
    }
}


    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private boolean validateVIN(String vin) {
        // Add more validation logic if needed
        return vin.length() == 8;
    }

        
  
    private String formatServiceHistory(ServiceHistory serviceHistory) {
        // Customize the format based on your needs
        return serviceHistory.getServiceDate() + " | " +
               serviceHistory.getDescription() + " | " +
               serviceHistory.getCost() + " | " +
               serviceHistory.getWorkshopName() + " | " +
               serviceHistory.getPartsReplaced();
    }

    private List<ServiceHistory> findServiceHistoriesByVIN(String vin) {
        List<ServiceHistory> matchingHistories = new ArrayList<>();
        if (serviceHistories != null) {
            for (ServiceHistory history : serviceHistories) {
                if (history.getVin().equals(vin)) {
                    matchingHistories.add(history);
                }
            }
        }
        return matchingHistories;
    }
    @FXML
    public void clearVehicleServiceHistory(ActionEvent event) {
        vehicleServiceHistoryListView.getItems().clear();
    }

    

       
        private WorkshopList workshopList;
    
        // Setter for workshopList
        public void setWorkshopList(WorkshopList list) {
            this.workshopList = list;
        }
    
        // Handler for showing workshop history
        @FXML
        public void loadWorkshopHistory(ActionEvent event) {
            clearServiceHistoryView(event);
            String workshopName = workshopNameTextFieldSH.getText().trim();
    
            Workshop workshop = workshopList.getWorkshopByName(workshopName);
            if (workshop == null) {
                showAlert("Workshop not found", "Error", Alert.AlertType.ERROR);
                return;
            }
    
            updateServiceHistoryView(workshop);
        }
    
        // Method to clear the service history view
         @FXML
        public void clearServiceHistoryView(ActionEvent event) {
            serviceHistoryListView.getItems().clear();
        }
    
        // Method to update the service history view
        private void updateServiceHistoryView(Workshop workshop) {
            workshop.getServiceHistories().forEach(history -> 
                serviceHistoryListView.getItems().add(formatServiceHistory(history))
            );
        }
    
        // Method to format a single service history entry
        private String formatWorkshopServiceHistory(ServiceHistory history) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            StringBuilder historyBuilder = new StringBuilder();
    
            historyBuilder.append("Event Name: ").append(history.getEventName()).append("\n")
                          .append("Date: ").append(dateFormat.format(history.getEventDate())).append("\n")
                          .append("Description: ").append(history.getEventDescription()).append("\n")
                          .append("Cost: $").append(history.getEventCost()).append("\n");
    
            history.getServiceActions().forEach(action -> 
                historyBuilder.append("  - ").append(action.getActionName())
                              .append(": ").append(action.getActionDescription()).append("\n")
            );
    
            return historyBuilder.toString();
        }
    
        // Method to show alert dialog
        private void showAlert(String message, String title, Alert.AlertType alertType) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    
    @FXML
    private TableView<ServiceHistory> serviceHistoryTableView; // Assuming this is linked to your FXML file

    @FXML
    private TableColumn<ServiceHistory, String> workshopNameColumnSH; // Assuming this is linked to your FXML file
    
    @FXML
    private TableColumn<ServiceHistory, String> workshopTypeColumnSH; // Assuming this is linked to your FXML file

    @FXML
    private TableColumn<ServiceHistory, String> vinColumnSH; // Assuming this is linked to your FXML file

    @FXML
    private TableColumn<ServiceHistory, String> serviceDateColumnSH; // Assuming this is linked to your FXML file

    @FXML
    private TableColumn<ServiceHistory, String> descriptionColumnSH; // Assuming this is linked to your FXML file

    private void initializeServiceHistoryTable() {
    // Define columns
    TableColumn<ServiceHistory, String> vinColumnSH = new TableColumn<>("VIN");
    vinColumnSH.setCellValueFactory(new PropertyValueFactory<>("vin"));

    TableColumn<ServiceHistory, String> serviceDateColumnSH = new TableColumn<>("Service Date");
    serviceDateColumnSH.setCellValueFactory(new PropertyValueFactory<>("serviceDate"));

    TableColumn<ServiceHistory, String> descriptionColumnSH = new TableColumn<>("Description");
    descriptionColumnSH.setCellValueFactory(new PropertyValueFactory<>("description"));

    TableColumn<ServiceHistory, String> costColumnSH = new TableColumn<>("Cost");
    costColumnSH.setCellValueFactory(new PropertyValueFactory<>("cost"));

    TableColumn<ServiceHistory, String> partsReplacedColumnSH = new TableColumn<>("Parts Replaced");
    partsReplacedColumnSH.setCellValueFactory(new PropertyValueFactory<>("partsReplaced"));

    TableColumn<ServiceHistory, String> workshopNameColumnSH = new TableColumn<>("Workshop Name");
    workshopNameColumnSH.setCellValueFactory(new PropertyValueFactory<>("workshopName"));
    // Add columns to table
    serviceHistoryTableView.getColumns().setAll(vinColumnSH, serviceDateColumnSH, descriptionColumnSH, costColumnSH, partsReplacedColumnSH, workshopNameColumnSH);

    // Set items to serviceHistories
    serviceHistoryTableView.setItems(FXCollections.observableArrayList(serviceHistories));

    // Handle double click
    serviceHistoryTableView.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2) {
            handleServiceHistoryDoubleClick();
        }
    });
}
@FXML
public void initialize() {
    // Existing code...
    initializeServiceHistoryTable();
}

private void handleServiceHistoryDoubleClick() {
    ServiceHistory selectedHistoryEntry = serviceHistoryTableView.getSelectionModel().getSelectedItem();

    if (selectedHistoryEntry != null) {
        if (showConfirmationDialog("Remove Service History Entry", "Are you sure you want to remove this service history entry?")) {
            serviceHistories.remove(selectedHistoryEntry); // Assuming serviceHistories is your data source
            serviceHistoryTableView.getItems().remove(selectedHistoryEntry);
        }
    }
}

private boolean showConfirmationDialog(String title, String message) {
    return new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK, ButtonType.CANCEL)
            .showAndWait()
            .filter(response -> response == ButtonType.OK)
            .isPresent();
}
   
}
    

    
    


