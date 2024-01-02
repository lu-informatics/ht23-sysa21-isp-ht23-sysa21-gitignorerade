package lu.ics.se.controllers;

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
private TextField vinField;

@FXML
private TextField costField;

@FXML
private TextField workshopNameTextField;


@FXML
private Button clearVehicleServiceHistory;

@FXML
private Button clearServiceHistoryView;

@FXML
private Button loadVehicleServiceHistory;


@FXML
private Button loadWorkshopHistory;
@FXML
private ListView<String> vehicleServiceHistoryListView;

@FXML
    private ListView<String> serviceHistoryListView;

    private static List<ServiceHistory> serviceHistories;

    public static void setServiceHistories(List<ServiceHistory> histories) {
        serviceHistories = histories;
    }
    @FXML
public void vinField(ActionEvent event) {
    String vin = vinField.getText().trim();

    if (validateVIN(vin)) {
        // VIN is valid, find the vehicle
        Vehicle vehicle = findVehicleByVIN(vin);
        if (vehicle != null) {
            // Vehicle found, load the service history
            loadVehicleServiceHistory(event); // Remove the second argument 'vehicle'
        } else {
            // Vehicle not found
            showAlert("Vehicle Not Found", "No vehicle found with the entered VIN.", vin);
        }
    } else {
        // VIN is not valid
        showAlert("Invalid VIN", "The entered VIN is not valid.", vin);
    }
}
    private boolean validateVIN(String vin) {
    // Implement VIN validation logic here
    return vin.length() == 8; // Example validation: check VIN length
}
private Vehicle findVehicleByVIN(String vin) {
    VehicleManifest vehicleManifest = new VehicleManifest(); // Initialize the vehicleManifest variable
    List<Vehicle> vehicles = vehicleManifest.getCompanyOwnedVehicles();
    for (Vehicle vehicle : vehicles) {
        if (vehicle.getVehicleIdentificationNumber().equals(vin)) {
            return vehicle;
        }
    }
    return null;
}
    @FXML
    public void loadVehicleServiceHistory(ActionEvent event) {
        String vin = vinField.getText().trim();

        // Retrieve the service histories based on the VIN
        List<ServiceHistory> vehicleServiceHistories = findServiceHistoriesByVIN(vin);

        if (!vehicleServiceHistories.isEmpty()) {
            // Clear previous entries
            serviceHistoryListView.getItems().clear();

            // Iterate through service histories and populate the list view
            for (ServiceHistory serviceHistory : vehicleServiceHistories) {
                // Convert ServiceHistory object to a displayable string and add to the list view
                serviceHistoryListView.getItems().add(formatServiceHistory(serviceHistory));
            }
        } else {
            // Handle the case when no service history is found
            serviceHistoryListView.getItems().clear();
            serviceHistoryListView.getItems().add("Service history not found for the vehicle");
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
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
        // Implement this method based on your data structure
        List<ServiceHistory> matchingHistories = new ArrayList<>();

        for (ServiceHistory history : serviceHistories) {
            if (history.getVin().equalsIgnoreCase(vin)) {
                matchingHistories.add(history);
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
        public void loadHistory(ActionEvent event) {
            clearServiceHistoryView(event);
            String workshopName = workshopNameTextField.getText().trim();
    
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
    
    

    // You need to have a reference to your WorkshopList or WorkshopService to get the workshops
    // For this example, I assume you have a WorkshopList instance in the main application class.

    // For simplicity, I'm using a non-static reference. In a real application, you might want to use dependency injection.
    // Remove the duplicate field declaration
    // private WorkshopList workshopList;

    // Setter method for WorkshopList (to be set from the main application)
    
    // Existing code...

    // Add a new method to initialize the service history table with cell value factories
    private void initializeServiceHistoryTable() {
        vinColumn.setCellValueFactory(new PropertyValueFactory<>("vin"));

        TableColumn<ServiceHistory, String> serviceDateColumn = new TableColumn<>();
        serviceDateColumn.setCellValueFactory(new PropertyValueFactory<>("serviceDate"));

        TableColumn<ServiceHistory, String> descriptionColumn = new TableColumn<>();
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<ServiceHistory, String> costColumn = new TableColumn<>();
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        TableColumn<ServiceHistory, String> partsReplacedColumn = new TableColumn<>();
        partsReplacedColumn.setCellValueFactory(new PropertyValueFactory<>("partsReplaced"));

        workshopNameColumn.setCellValueFactory(new PropertyValueFactory<>("workshopName"));

        TableView<ServiceHistory> serviceHistoryTableView = new TableView<>(); // Initialize the variable

        // Add a double-click listener to the service history table
        serviceHistoryTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                handleServiceHistoryDoubleClick(serviceHistoryTableView);
            }
        });
    }

    
    @FXML
    public void clearServiceHistory(ActionEvent event) {
        serviceHistoryListView.getItems().clear();
    }


    @FXML
    public void initialize() {
        // Existing code...
        initializeServiceHistoryTable();
    }

    private void handleServiceHistoryDoubleClick(TableView<ServiceHistory> serviceHistoryTableView) {
        ServiceHistory selectedHistoryEntry = serviceHistoryTableView.getSelectionModel().getSelectedItem();
    
        if (selectedHistoryEntry != null) {
            // Show a confirmation dialog
            if (showConfirmationDialog("Remove Service History Entry",
                    "Are you sure you want to remove this service history entry?")) {
                // Remove the selected history entry from the table
                serviceHistoryTableView.getItems().remove(selectedHistoryEntry);
            }
        }
    }
    

    
    private boolean showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
    
        Optional<ButtonType> result = alert.showAndWait();
        alert.close(); // Close the dialog
    
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
    
    
    
    
    
    
    
    


