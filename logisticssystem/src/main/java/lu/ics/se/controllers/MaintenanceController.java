package lu.ics.se.controllers;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import lu.ics.se.models.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;


import java.io.IOException;

import java.util.List;
import java.util.Map;

import javafx.beans.Observable;
import javafx.util.Callback;
import javafx.event.ActionEvent; // Add this import statement

public class MaintenanceController {
    // Maintenance Tab
    @FXML
    private TabPane maintenanceTabPane;

    private WorkshopList workshopList;
    @FXML
    private TextField vehicleNameTextField;
    @FXML
    private DatePicker maintenanceDatePickerM;
    
    @FXML
    private Button handleAddMaintenance;

    @FXML
    private TableView<ServiceEvent> maintenanceTableView;

    @FXML
    private Button clearMaintenanceList;

    
    @FXML
    private TableColumn<ServiceEvent, String> vinColumnM;

    @FXML
    private TableColumn<ServiceEvent, String> workshopNameColumnM;

    @FXML
    private TableColumn<ServiceEvent, String> maintenanceDateColumnM;


    @FXML
    private TableColumn<ServiceEvent, String> costColumnM;

    @FXML
    private Label averageCostLabel;
    @FXML
    private Label mostExpensiveLabel;

    @FXML
    private Button clearMaintenanceFields;

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
    private Button displayMostExpensiveMaintenance;


    @FXML
    private TextField vinFieldM;

    @FXML
    private TextField costFieldM;



    @FXML
    private TextField workshopNameFieldM;

    @FXML
    private ListView<String> vehicleServiceHistoryListView;

    // Remove the duplicate method initializeMaintenanceTableView()

    private VehicleManifest vehicleManifest;

    public MaintenanceController() {
        vehicleManifest = VehicleManifest.getInstance();
    }

    @FXML
public void initializeMaintenanceTableView() {
    // Set up columns for maintenance table view
    TableColumn<ServiceEvent, String> vinColumnM = new TableColumn<>("VIN");
    vinColumnM.setCellValueFactory(new PropertyValueFactory<>("eventVin"));

    TableColumn<ServiceEvent, LocalDate> maintenanceDateColumnM = new TableColumn<>("Date");
    maintenanceDateColumnM.setCellValueFactory(new PropertyValueFactory<>("eventDate"));

    TableColumn<ServiceEvent, String> workshopNameColumn = new TableColumn<>("Workshop");
    workshopNameColumn.setCellValueFactory(new PropertyValueFactory<>("eventWorkshopName"));

    TableColumn<ServiceEvent, Double> costColumnM = new TableColumn<>("Cost");
    costColumnM.setCellValueFactory(new PropertyValueFactory<>("eventCost"));

    // Add columns to the table view
    maintenanceTableView.getColumns().addAll(vinColumnM, maintenanceDateColumnM, workshopNameColumnM, costColumnM);

    // Add double-click event to open the edit/delete window
    maintenanceTableView.setOnMouseClicked(this::handleMaintenanceDoubleClick);

    // Load initial data into the table
    refreshMaintenanceTable();
}

private void refreshMaintenanceTable() {
    ObservableList<ServiceEvent> serviceEvents = FXCollections.observableArrayList();

    for (Vehicle vehicle : vehicleManifest.getVehicles()) {
        serviceEvents.addAll(vehicle.getServiceEvents());
    }

    maintenanceTableView.setItems(serviceEvents);
}
    
    @FXML
public void handleAddMaintenance() {
    try {
        // Get input values from UI elements
        String vehicleVin = vinFieldM.getText();
        String workshopName = workshopNameFieldM.getText();
        String dateString = maintenanceDatePickerM.getEditor().getText();
        double costFieldValue = Double.parseDouble(costFieldM.getText());

        // Validate inputs
        if (vehicleVin.isEmpty() || workshopName.isEmpty() || dateString.isEmpty() || String.valueOf(costFieldValue).isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        // Check if the vehicle is decommissioned
        Vehicle selectedVehicle = vehicleManifest.getVehicleByVIN(vehicleVin);
        if (selectedVehicle == null) {
            showAlert("Error", "No vehicle found with the entered VIN.");
            return;
        }
        if (selectedVehicle.isDecommissioned()) {
            showAlert("Error", "The selected vehicle is decommissioned and cannot receive maintenance.");
            return;
        }

        WorkshopList workshopList = WorkshopList.getInstance();

        // Parse the date from the DatePicker
        LocalDate maintenanceDate = LocalDate.parse(dateString);

        Workshop selectedWorkshop = workshopList.getWorkshopByName(workshopName);
        if (selectedWorkshop == null) {
            showAlert("Error", "No workshop found with the entered name.");
            return;
        }

        ServiceEvent newMaintenance = new ServiceEvent("Maintenance", "Maintenance Description",
            costFieldValue, maintenanceDate, selectedWorkshop,
            selectedVehicle, new ArrayList<>());

        // Add the new maintenance to the vehicle's service events
        selectedVehicle.addServiceEvent(newMaintenance);

        // Add the new maintenance to the maintenance table view
        maintenanceTableView.getItems().add(newMaintenance);
        
        refreshMaintenanceTable();
        
        // Clear the input fields
        clearMaintenanceFields();
    } catch (Exception e) {
        showAlert("Error", "An error occurred: " + e.getMessage());
    }
}
    @FXML
    public void clearMaintenanceFields() {
        vinFieldM.clear();
        workshopNameFieldM.clear();
        maintenanceDatePickerM.getEditor().clear();
        costFieldM.clear();
    }

    private void handleMaintenanceDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            // Handle double-click event to open the edit/delete window
            ServiceEvent serviceEvent = maintenanceTableView.getSelectionModel().getSelectedItem();
            if (serviceEvent != null) {
                setMaintenance(serviceEvent);
                openEditDeleteWindow();
            } else {
                showAlert("Error", "No maintenance selected.");
            }
        }
    }

    private void openEditDeleteWindow() {
        try {
            // Load the FXML file for the edit/delete window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lu/ics/se/views/EditDeleteMaintenance.fxml"));
            Parent root = loader.load();

            // Set the selected maintenance in the controller
            MaintenanceController controller = loader.getController();
            controller.setMaintenance(selectedMaintenance);

            // Create a new stage for the edit/delete window
            Stage stage = new Stage();
            stage.setTitle("Edit/Delete Maintenance");
            stage.setScene(new Scene(root));

            // Show the window and wait for it to be closed
            stage.showAndWait();

            // Refresh the maintenance table view after editing or deleting
            refreshMaintenanceTable();
        } catch (IOException e) {
            showAlert("Error", "An error occurred while opening the edit/delete window.");
        }
    }

    private ServiceEvent selectedMaintenance; // Declare selectedMaintenance field


    // Remove the unused costLabel field


    // Remove the unused method updateLabels()


    @FXML
    public void handleEdit() {
    ServiceEvent selectedMaintenance = maintenanceTableView.getSelectionModel().getSelectedItem(); // replace 'maintenanceTableView' with your TableView's ID
    if (selectedMaintenance != null) {
        Dialog<Map<String, Object>> dialog = new Dialog<>();
        dialog.setTitle("Edit Maintenance");
        dialog.setHeaderText("Edit Maintenance Details");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        DatePicker serviceDatePicker = new DatePicker(selectedMaintenance.getServiceDate());
        TextField costField = new TextField(String.valueOf(selectedMaintenance.getCost()));

        grid.add(new Label("Service Date:"), 0, 0);
        grid.add(serviceDatePicker, 1, 0);
        grid.add(new Label("Cost:"), 0, 1);
        grid.add(costField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                LocalDate serviceDate = serviceDatePicker.getValue();
                String costText = costField.getText();

                // Validate inputs
                if (serviceDate == null || costText.isEmpty()) {
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
                results.put("serviceDate", serviceDate);
                results.put("cost", cost);
                return results;
            }
            return null;
        });

        Optional<Map<String, Object>> result = dialog.showAndWait();
        result.ifPresent(maintenanceDetails -> {
            selectedMaintenance.setServiceDate((LocalDate) maintenanceDetails.get("serviceDate"));
            selectedMaintenance.setCost((Double) maintenanceDetails.get("cost"));
            maintenanceTableView.refresh(); // replace 'maintenanceTableView' with your TableView's ID
        });
    } else {
        showAlert("Edit Error", "No maintenance selected.");
    }
}


    private void setMaintenance(ServiceEvent selectedMaintenance2) {
        this.selectedMaintenance = selectedMaintenance2;
    }

    @FXML
    public void handleDelete() {
        if (selectedMaintenance != null) {
            // Ask for confirmation before deleting
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Maintenance");
            alert.setContentText("Are you sure you want to delete this maintenance?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Remove the maintenance object from the vehicle's service events
                Vehicle vehicle = selectedMaintenance.getEventVehicle();
                vehicle.removeServiceEvent(selectedMaintenance);

                // Refresh the maintenance table view after deleting
                refreshMaintenanceTable();
            }
        }
    }

@FXML
public void handleShowAverageCost() {
    // Display information on average cost
    double averageCost = calculateAverageCost();
    String averageCostMessage = String.format("Average Cost for Maintenance: $%.2f", averageCost);
    showAlert("Average Cost", averageCostMessage);
}

public double calculateAverageCost() {
    ObservableList<ServiceEvent> serviceEvents = maintenanceTableView.getItems();
    if (serviceEvents.isEmpty()) {
        return 0.0;
    }

    double totalCost = 0.0;
    for (ServiceEvent serviceEvent : serviceEvents) {
        totalCost += serviceEvent.getEventCost();
    }

    return totalCost / serviceEvents.size();
}

@FXML
public void displayMostExpensiveMaintenance() {
    ObservableList<ServiceEvent> serviceEvents = maintenanceTableView.getItems();

    if (serviceEvents.isEmpty()) {
        showAlert("Most Expensive Maintenance", "No maintenance records available.");
        return;
    }

    ServiceEvent mostExpensiveEvent = findMostExpensiveMaintenance();

    if (mostExpensiveEvent != null) {
        String mostExpensiveMessage = "Most Expensive Maintenance:\n" +
            "Vehicle: " + mostExpensiveEvent.getEventVehicleName() + "\n" +
            "Workshop: " + mostExpensiveEvent.getEventWorkshopName() + "\n" +
            "Cost: $" + mostExpensiveEvent.getEventCost();
        showAlert("Most Expensive Maintenance", mostExpensiveMessage);
    } else {
        showAlert("Most Expensive Maintenance", "No maintenance records found.");
    }
}

private ServiceEvent findMostExpensiveMaintenance() {
    ObservableList<ServiceEvent> serviceEvents = maintenanceTableView.getItems();

    if (serviceEvents.isEmpty()) {
        return null;
    }

    ServiceEvent mostExpensiveEvent = null;
    double highestCost = Double.MIN_VALUE;

    for (ServiceEvent serviceEvent : serviceEvents) {
        if (serviceEvent.getEventCost() > highestCost) {
            highestCost = serviceEvent.getEventCost();
            mostExpensiveEvent = serviceEvent;
        }
    }

    return mostExpensiveEvent;
}

private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
}
