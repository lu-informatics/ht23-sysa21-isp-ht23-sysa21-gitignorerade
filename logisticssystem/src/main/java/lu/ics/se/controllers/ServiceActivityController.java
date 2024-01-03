package lu.ics.se.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import lu.ics.se.models.*;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

 private String description;
 private Double cost;
 private LocalDate serviceDate;
 private String partsReplaced;

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
 private TextField vinFieldSA;

 @FXML
 private TextField costFieldSA;

 @FXML
 private TextField workshopNameFieldSA;

 
        @FXML
        private DatePicker serviceDateFieldSA;
    
        @FXML
        private TextField descriptionFieldSA;
    

    
        @FXML
        private TextField partsReplacedFieldSA;
    
       
    
        @FXML
        private TableView<ServiceActivityController> serviceActivitiesTableView;

        @FXML
        private TableColumn<ServiceActivityController, String> vinColumnSA;
        
        @FXML
        private TableColumn<ServiceActivityController, String> workshopNameColumnSA;

        
    @FXML
    private TableColumn<ServiceActivityController, String> descriptionColumnSA;

    @FXML
    private TableColumn<ServiceActivityController, Double> costColumnSA;

    @FXML
    private TableColumn<ServiceActivityController, LocalDate> serviceDateColumnSA;



    @FXML
    private TableColumn<ServiceActivityController, String> partsReplacedColumnSA;

    @FXML
    private AnchorPane detailsPane;

    @FXML
    private TableView<ServiceHistory> serviceHistoryTableView;

    private VehicleManifest vehicleManifest;

    public ServiceActivityController(String vin, String description, double cost, LocalDate serviceDate, String workshopName, String partsReplaced) {
        this.vinFieldSA.setText(vin);
        this.descriptionFieldSA.setText(description);
        this.costFieldSA.setText(String.valueOf(cost));
        this.serviceDateFieldSA.setValue(serviceDate);
        this.workshopNameFieldSA.setText(workshopName);
        this.partsReplacedFieldSA.setText(partsReplaced);
        this.vehicleManifest = VehicleManifest.getInstance();
    }
    
        public ServiceActivityController(String vin, String description, double cost, LocalDate serviceDate, Object name,
            String partsReplaced) {
    }
    public ServiceActivityController() {
        // no-arg constructor
    }
    // Other necessary methods and event handlers...
    @FXML
    public void initialize() {
    // Initialize columns
    vinColumnSA.setCellValueFactory(new PropertyValueFactory<>("vin"));
    descriptionColumnSA.setCellValueFactory(new PropertyValueFactory<>("description"));
    costColumnSA.setCellValueFactory(new PropertyValueFactory<>("cost"));
    serviceDateColumnSA.setCellValueFactory(new PropertyValueFactory<>("serviceDate"));
    workshopNameColumnSA.setCellValueFactory(new PropertyValueFactory<>("workshopName"));
    partsReplacedColumnSA.setCellValueFactory(new PropertyValueFactory<>("partsReplaced"));

    // Set the selection listener for the table
    serviceActivitiesTableView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showDetails(newValue));

    // Add double-click event handler
    serviceActivitiesTableView.setRowFactory(tv -> {
        TableRow<ServiceActivityController> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (!row.isEmpty())) {
                editServiceActivity();
            }
        });
        return row;
    });
}
    

    @FXML
    public void clearServiceActivitiesTable() {
    serviceActivitiesTableView.getItems().clear();
    }

    @FXML
public void addServiceActivity() {
    try {
        // Get values directly from the fields
        String vin = vinFieldSA.getText();
        String workshopName = workshopNameFieldSA.getText();
        String partsReplaced = partsReplacedFieldSA.getText();
        String description = descriptionFieldSA.getText();
        double cost = Double.parseDouble(costFieldSA.getText());
        LocalDate serviceDate = serviceDateFieldSA.getValue();

        // Validate inputs
        if (vin.isEmpty() || workshopName.isEmpty() || partsReplaced.isEmpty() || description.isEmpty() || String.valueOf(cost).isEmpty() || serviceDate == null) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        // Check if the vehicle is decommissioned
        Vehicle selectedVehicle = vehicleManifest.getVehicleByVIN(vin);
        if (selectedVehicle == null) {
            showAlert("Error", "No vehicle found with the entered VIN.");
            return;
        }
        if (selectedVehicle.isDecommissioned()) {
            showAlert("Error", "The selected vehicle is decommissioned and cannot receive service.");
            return;
        }

        WorkshopList workshopList = new WorkshopList(); // Initialize the workshopList variable

        Workshop selectedWorkshop = workshopList.getWorkshopByName(workshopName);
        if (selectedWorkshop == null) {
            showAlert("Error", "No workshop found with the entered name.");
            return;
        }

        ServiceActivityController newActivity = new ServiceActivityController(vin, description, cost, serviceDate, workshopName, partsReplaced); // Add the new service activity to the service activities table view
        serviceActivitiesTableView.getItems().add(newActivity);

        // Clear the input fields
        clearServiceActivityFields();
    } catch (Exception e) {
        showAlert("Error", "An error occurred: " + e.getMessage());
    }
}
        // Add other methods and event handlers as needed...
    @FXML 
    public void clearServiceActivityFields() {
        vinFieldSA.clear();
        serviceDateFieldSA.setValue(null);
        descriptionFieldSA.clear();
        costFieldSA.clear();
        partsReplacedFieldSA.clear();
        workshopNameFieldSA.clear();
    }
    
    @FXML
public void showDetails(ServiceActivityController activity) {
    if (activity != null) {
        AnchorPane detailsView = createDetailsView(activity);
        detailsPane.getChildren().setAll(detailsView);
    } else {
        detailsPane.getChildren().clear();
    }
}

private AnchorPane createDetailsView(ServiceActivityController activity) {
    AnchorPane detailsView = new AnchorPane();

    // Create and add labels for each detail
    Label vinLabel = new Label("VIN: " + activity.getVin());
    vinLabel.setLayoutX(26.0);
    vinLabel.setLayoutY(36.0);

    Label descriptionLabel = new Label("Description: " + activity.getDescription());
    Label costLabel = new Label("Cost: " + activity.getCost());
    Label dateLabel = new Label("Service Date: " + activity.getServiceDate());
    Label workshopLabel = new Label("Workshop: " + activity.getWorkshopName());
    Label partsReplacedLabel = new Label("Parts Replaced: " + activity.getPartsReplaced());

    detailsView.getChildren().addAll(vinLabel, descriptionLabel, costLabel, dateLabel, workshopLabel, partsReplacedLabel);
    return detailsView;
}

@FXML
public void editServiceActivity() {
    ServiceActivityController selectedActivity = serviceActivitiesTableView.getSelectionModel().getSelectedItem();
    if (selectedActivity != null) {
        Dialog<Map<String, Object>> dialog = new Dialog<>();
        dialog.setTitle("Edit Service Activity");
        dialog.setHeaderText("Edit Service Activity Details");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField descriptionField = new TextField(selectedActivity.getDescription());
        TextField costField = new TextField(String.valueOf(selectedActivity.getCost()));
        DatePicker serviceDatePicker = new DatePicker(selectedActivity.getServiceDate());
        TextField partsReplacedField = new TextField(selectedActivity.getPartsReplaced());

        grid.add(new Label("Description:"), 0, 0);
        grid.add(descriptionField, 1, 0);
        grid.add(new Label("Cost:"), 0, 1);
        grid.add(costField, 1, 1);
        grid.add(new Label("Service Date:"), 0, 2);
        grid.add(serviceDatePicker, 1, 2);
        grid.add(new Label("Parts Replaced:"), 0, 3);
        grid.add(partsReplacedField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                String description = descriptionField.getText();
                String costText = costField.getText();
                LocalDate serviceDate = serviceDatePicker.getValue();
                String partsReplaced = partsReplacedField.getText();

                // Validate inputs
                if (description.isEmpty() || costText.isEmpty() || serviceDate == null || partsReplaced.isEmpty()) {
                    showAlert("Error", "Please fill in all fields.");
                    return null;
                }

                double cost;
                try {
                    cost = Double.parseDouble(costText);
                } catch (NumberFormatException e) {
                    showAlert("Error", "Cost must be a number.");
                    return null;
                }

                Map<String, Object> results = new HashMap<>();
                results.put("description", description);
                results.put("cost", cost);
                results.put("serviceDate", serviceDate);
                results.put("partsReplaced", partsReplaced);
                return results;
            }
            return null;
        });

        Optional<Map<String, Object>> result = dialog.showAndWait();
        result.ifPresent(activityDetails -> {
            selectedActivity.setDescription((String) activityDetails.get("description"));
            selectedActivity.setCost((Double) activityDetails.get("cost"));
            selectedActivity.setServiceDate((LocalDate) activityDetails.get("serviceDate"));
            selectedActivity.setPartsReplaced((String) activityDetails.get("partsReplaced"));
            serviceActivitiesTableView.refresh();
        });
    } else {
        showAlert("Edit Error", "No service activity selected.");
    }
}
       
    private void setDescription(String description) {
        this.description = description;
    }
    
    private void setCost(Double cost) {
        this.cost = cost;
    }
    
    private void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }
    
    private void setPartsReplaced(String partsReplaced) {
        this.partsReplaced = partsReplaced;
    }


private String getVin() {
    return vinFieldSA.getText();
}

private String getWorkshopName() {
    return workshopNameFieldSA.getText();
}

private String getPartsReplaced() {
    return partsReplacedFieldSA.getText();
}

private String getDescription() {
    return descriptionFieldSA.getText();
}

private double getCost() {
    return Double.parseDouble(costFieldSA.getText());
}

private LocalDate getServiceDate() {
    return serviceDateFieldSA.getValue();
}

@FXML
public void markAsCompleted() {
    ServiceActivityController selectedActivity = serviceActivitiesTableView.getSelectionModel().getSelectedItem();
    if (selectedActivity != null) {
        if (showConfirmationDialog("Mark as Completed", "Mark this service activity as completed?")) {
            serviceActivitiesTableView.getItems().remove(selectedActivity);
            transferToServiceHistory(selectedActivity);
        }
    } else {
        showAlert("Completion Error", "No service activity selected.");
    }
}

private void transferToServiceHistory(ServiceActivityController completedActivity) {
    ServiceHistory newHistoryEntry = new ServiceHistory(
        completedActivity.getVin(),
        completedActivity.getServiceDate(),
        completedActivity.getDescription(),
        completedActivity.getCost(),
        completedActivity.getPartsReplaced(),
        completedActivity.getWorkshopName()
    );

    serviceHistoryTableView.getItems().add(newHistoryEntry);
}


@FXML
public void removeServiceActivity() {
    ServiceActivityController selectedActivity = serviceActivitiesTableView.getSelectionModel().getSelectedItem();
    if (selectedActivity != null) {
        if (showConfirmationDialog("Remove Activity", "Remove this service activity?")) {
            serviceActivitiesTableView.getItems().remove(selectedActivity);
        }
    } else {
        showAlert("Removal Error", "No service activity selected.");
    }
}

private boolean showConfirmationDialog(String title, String content) {
    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
    confirmation.setTitle(title);
    confirmation.setContentText(content);
    return confirmation.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
}

private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setContentText(content);
    alert.showAndWait();
}
}

// Other methods (markAsCompleted, transferToServiceHistory, removeServiceActivity, showAlert, showConfirmationDialog) remain unchanged.
