package lu.ics.se.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.AnchorPane;

import lu.ics.se.models.*;


import java.time.LocalDate;
import javafx.scene.layout.Pane;

public class ServiceActivityController {


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

 @FXML
 private Button clearServiceActivityFields;

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
 private TextField vinField;

 @FXML
 private TextField costField;

 @FXML
 private TextField workshopNameField;

 @FXML
 private ListView<String> vehicleServiceHistoryListView;

 
        @FXML
        private DatePicker serviceDateField;
    
        @FXML
        private TextField descriptionField;
    

    
        @FXML
        private TextField partsReplacedField;
    
       
    
        @FXML
        private TableView<ServiceActivityController> serviceActivitiesTableView;

        @FXML
        private Button clearServiceActivitiesTable;

        @FXML
        private TableColumn<ServiceActivityController, String> activityNameColumn;

        
    @FXML
    private TableColumn<ServiceActivityController, String> descriptionColumn;

    @FXML
    private TableColumn<ServiceActivityController, Double> costColumn;

    @FXML
    private TableColumn<ServiceActivityController, LocalDate> serviceDateColumn;



    @FXML
    private TableColumn<ServiceActivityController, String> partsReplacedColumn;

    @FXML
    private AnchorPane detailsPane;

    @FXML
    private TableView<ServiceHistory> serviceHistoryTableView;

    private VehicleManifest vehicleManifest;

    public ServiceActivityController(String vin, String description, double cost, LocalDate serviceDate, String workshopName) {
        this.vinField.setText(vin);
        this.descriptionField.setText(description);
        this.costField.setText(String.valueOf(cost));
        this.serviceDateField.setValue(serviceDate);
        this.workshopNameField.setText(workshopName);
    }
    
        public ServiceActivityController(String vin, String description, double cost, LocalDate serviceDate, Object name,
            String partsReplaced) {
    }

    // Other necessary methods and event handlers...
    @FXML
    public void initialize() {
    // Initialize columns
    vinColumn.setCellValueFactory(new PropertyValueFactory<>("vin"));
    descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
    serviceDateColumn.setCellValueFactory(new PropertyValueFactory<>("serviceDate"));
    workshopNameColumn.setCellValueFactory(new PropertyValueFactory<>("workshopName"));
    partsReplacedColumn.setCellValueFactory(new PropertyValueFactory<>("partsReplaced"));

    // Set the selection listener for the table
    serviceActivitiesTableView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showDetails(newValue));
}

    @FXML
    public void clearServiceActivitiesTable() {
    serviceActivitiesTableView.getItems().clear();
    }

    @FXML
public void addServiceActivity() {
    try {
        // Get values from the fields
        String vin = vinField.getText();
        String workshopName = workshopNameField.getText();
        String partsReplaced = partsReplacedField.getText();
        String description = descriptionField.getText();
        double cost = Double.parseDouble(costField.getText());
        LocalDate serviceDate = serviceDateField.getValue();

        // Validate inputs
        if (vin.isEmpty() || workshopName.isEmpty() || partsReplaced.isEmpty() || description.isEmpty() || String.valueOf(cost).isEmpty() || serviceDate == null) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        WorkshopList workshopList = new WorkshopList(); // Initialize the workshopList variable

        Workshop selectedWorkshop = workshopList.getWorkshopByName(workshopName);
        if (selectedWorkshop == null) {
            showAlert("Error", "No workshop found with the entered name.");
            return;
        }

        Vehicle selectedVehicle = vehicleManifest.getVehicleByVIN(vin);
        if (selectedVehicle == null) {
            showAlert("Error", "No vehicle found with the entered VIN.");
            return;
        }
        ServiceActivityController newActivity = new ServiceActivityController(selectedVehicle.getVin(), description, cost, serviceDate, selectedWorkshop.getName(), partsReplaced); // Add the new service activity to the service activities table view
        serviceActivitiesTableView.getItems().add(newActivity);

        // Clear the input fields
        clearServiceActivityFields();
    } catch (Exception e) {
        showAlert("Error", "An error occurred: " + e.getMessage());
    }
}
        // Add other methods and event handlers as needed...
    
    
    private void showDetails(ServiceActivityController activity) {
        if (activity != null) {
            // Create and populate the details view
            AnchorPane detailsView = createDetailsView(activity);

            // Set the details view in the placeholder
            ((Pane) detailsPane).getChildren().setAll(detailsView);
        } else {
            // Clear the details view if no service activity is selected
            ((Pane) detailsPane).getChildren().clear();
        }
    }

    @FXML 
    public void clearServiceActivityFields() {
        vinField.clear();
        serviceDateField.setValue(null);
        descriptionField.clear();
        costField.clear();
        partsReplacedField.clear();
        workshopNameField.clear();

        }
        private AnchorPane createDetailsView(ServiceActivityController activity) {
        // Create a new AnchorPane for details view
        AnchorPane detailsView = new AnchorPane();

        // Populate details view with labels and buttons
        // ... (similar to your previous createDetailsView method, but with partsReplaced)

        // Set the details in the labels
        Label vinLabel = new Label("VIN: " + activity.getVin());
        vinLabel.setLayoutX(26.0);
        vinLabel.setLayoutY(36.0);

        // ... (similar labels for other fields, including partsReplaced)

        // Add labels and buttons to the details view
        detailsView.getChildren().addAll(vinLabel/* Other labels *//* Buttons */);

        return detailsView;

        
    }
    @FXML
    public void editServiceActivity() {
        ServiceActivityController selectedActivity = serviceActivitiesTableView.getSelectionModel().getSelectedItem();

        if (selectedActivity != null) {
            // Open a simple TextInputDialog for editing
            TextInputDialog editDialog = new TextInputDialog(selectedActivity.getDescription());
            editDialog.setTitle("Edit Service Activity");
            editDialog.setHeaderText(null);
            editDialog.setContentText("Enter new description:");

            // Show the dialog and wait for user response
            editDialog.showAndWait().ifPresent(newDescription -> {
                // Update the selected activity
                selectedActivity.setDescription(newDescription);
                // Refresh the table to reflect the changes
                serviceActivitiesTableView.refresh();
            });
        } else {
            showAlert("Edit Error", "No service activity selected.");
        }
    }

    private void setDescription(String newDescription) {
    }
    @FXML
    public void markAsCompleted() {
        ServiceActivityController selectedActivity = serviceActivitiesTableView.getSelectionModel().getSelectedItem();

        if (selectedActivity != null) {
            // Show a confirmation dialog
            if (showConfirmationDialog("Mark as Completed Confirmation", "Mark this service activity as completed?")) {
                // Remove the selected activity from the table
                serviceActivitiesTableView.getItems().remove(selectedActivity);

                // Transfer the completed activity to the service history table
            transferToServiceHistory(selectedActivity);
        }
    } else {
        showAlert("Mark as Completed Error", "No service activity selected.");
    }
}
               


    
    
    private void transferToServiceHistory(ServiceActivityController completedActivity) {
        // Assuming you have a method to add service history entries to the table
        // (You should implement this method in your ServiceHistoryTable class)
        ServiceHistory newHistoryEntry = new ServiceHistory(
                completedActivity.getVin(),
                completedActivity.getServiceDate(),
                completedActivity.getDescription(),
                completedActivity.getClass(),
                completedActivity.getPartsReplaced(),
                completedActivity.getWorkshopName()
        );

        TableView<ServiceHistory> serviceHistoryTableView = new TableView<>(); // Initialize the variable

        serviceHistoryTableView.getItems().add(newHistoryEntry);
    }

    private Object getWorkshopName() {
        return null;
    }
    private Object getPartsReplaced() {
        return null;
    }
    private String getDescription() {
        return null;
    }
    private Object getServiceDate() {
        return null;
    }
    private String getVin() {
        return null;
    }
    @FXML
    public void removeServiceActivity() {
        TableView<ServiceActivityController> serviceActivitiesTableView = new TableView<>(); // Initialize the variable
        ServiceActivityController selectedActivity = serviceActivitiesTableView.getSelectionModel().getSelectedItem();

        if (selectedActivity != null) {
            // Show a confirmation dialog
            if (showConfirmationDialog("Remove Confirmation", "Are you sure you want to remove this service activity?")) {
                // Remove the selected activity from the table
                serviceActivitiesTableView.getItems().remove(selectedActivity);
            }
        } else {
            showAlert("Remove Error", "No service activity selected.");
        }
    }

    private boolean showConfirmationDialog(String title, String content) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle(title);
        confirmation.setHeaderText(null);
        confirmation.setContentText(content);

        return confirmation.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}


