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
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import lu.ics.se.models.*;


import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Optional;


import java.io.IOException;

import java.util.List;
import javafx.beans.Observable;
import javafx.util.Callback;
import javafx.event.ActionEvent; // Add this import statement

public class MaintenanceController {
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
    private Button displayMostExpensiveMaintenance;


    @FXML
    private TextField vinField;

    @FXML
    private TextField costField;



    @FXML
    private TextField workshopNameField;

    @FXML
    private ListView<String> vehicleServiceHistoryListView;

    // Remove the duplicate method initializeMaintenanceTableView()

    private VehicleManifest vehicleManifest;

    @FXML
    private void initializeMaintenanceTableView() {
        // Set up columns for maintenance table view
        TableColumn<ServiceEvent, String> vinColumn = new TableColumn<>("VIN");
        vinColumn.setCellValueFactory(new PropertyValueFactory<>("eventVin"));

        TableColumn<ServiceEvent, LocalDate> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));


        // ...

        TableColumn<ServiceEvent, String> workshopColumn = new TableColumn<>("Workshop");
        workshopColumn.setCellValueFactory(new PropertyValueFactory<>("eventWorkshopName"));

        TableColumn<ServiceEvent, Double> costColumn = new TableColumn<>("Cost");
        costColumn.setCellValueFactory(new PropertyValueFactory<>("eventCost"));

        // Create a list of columns
        List<TableColumn<ServiceEvent, ?>> columns = new ArrayList<>();

        columns.add(vinColumn);
        columns.add(dateColumn);
        columns.add(workshopColumn);
        columns.add(costColumn);

        // Add columns to the table view
        maintenanceTableView.getColumns().addAll(columns);

        // Add double-click event to open the edit/delete window
        maintenanceTableView.setOnMouseClicked(this::handleMaintenanceDoubleClick);

        // Load initial data into the table
        refreshMaintenanceTable();
    }

        private void refreshMaintenanceTable() {
            ObservableList<ServiceEvent> serviceEvents = FXCollections.observableArrayList((Callback<ServiceEvent, Observable[]>) param -> new Observable[]{param.eventVinProperty(), param.eventDateProperty(), param.eventWorkshopNameProperty(), param.eventCostProperty()});
            maintenanceTableView.setItems(serviceEvents);
        }

    
    @FXML
    private void handleAddMaintenance() {
        try {
            // Get input values from UI elements
            TextField vinField = new TextField(); // Replace TextField with the appropriate type for vinField
            String vehicleVin = vinField.getText();
            TextInputControl workshopNameField = new TextField(); // Replace TextField with the appropriate type for workshopNameField
            String workshopName = workshopNameField.getText();
            String dateString = maintenanceDatePicker.getEditor().getText();
            TextInputControl costField = new TextField(); // Replace TextField with the appropriate type for costField
            double costFieldValue = Double.parseDouble(costField.getText());

            // Validate inputs
            if (vehicleVin.isEmpty() || workshopName.isEmpty() || dateString.isEmpty() || String.valueOf(costFieldValue).isEmpty()) {
                showAlert("Error", "Please fill in all fields.");
                return;
            }


            // ...

            WorkshopList workshopList = new WorkshopList(); // Initialize the workshopList variable

            // Parse the date from the DatePicker
            // Note: You should handle date parsing errors in a real-world application
            LocalDate maintenanceDate = LocalDate.parse(dateString);

            ServiceEvent newMaintenance = new ServiceEvent("Maintenance", "Maintenance Description",
                0.0, maintenanceDate, workshopList.getWorkshopByName(workshopName),
                vehicleManifest.getVehicleByVIN(vehicleVin), new ArrayList<>());

            // Add the new maintenance to the vehicle's service events
            Vehicle selectedVehicle = vehicleManifest.getVehicleByVIN(vehicleVin);
            selectedVehicle.addServiceEvent(newMaintenance);

            // Refresh the maintenance table view
            refreshMaintenanceTable();




            // Clear the input fields
            clearMaintenanceFields();
        } catch (Exception e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
        }
    }

    private void handleMaintenanceDoubleClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            // Handle double-click event to open the edit/delete window
            selectedMaintenance = maintenanceTableView.getSelectionModel().getSelectedItem();
            if (selectedMaintenance != null) {
                openEditDeleteWindow();
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
    private void handleEdit() {
        if (selectedMaintenance != null) {
            try {
                // Load the FXML file for the edit window
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/lu/ics/se/views/EditMaintenance.fxml"));
                Parent root = loader.load();

                // Set the selected maintenance in the edit controller
                MaintenanceController editController = loader.getController();
                editController.setMaintenance(selectedMaintenance);

                // Create a new stage for the edit window
                Stage stage = new Stage();
                stage.setTitle("Edit Maintenance");
                stage.setScene(new Scene(root));

                // Show the edit window and wait for it to be closed
                stage.showAndWait();

                // Refresh the maintenance table view after editing
                refreshMaintenanceTable();
            } catch (IOException e) {
                showAlert("Error", "An error occurred while opening the edit window.");
            }
        }
    }

    private void setMaintenance(ServiceEvent selectedMaintenance2) {
    }

    @FXML
    private void handleDelete() {
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

    private void clearMaintenanceFields() {
        vinField.clear();
        workshopNameField.clear();
        maintenanceDatePicker.getEditor().clear();
        costField.clear();
    }

    

    @FXML
    private void handleShowAverageCost() {
        // Display information on average cost
        double averageCost = calculateAverageCost(null);
        String averageCostMessage = String.format("Average Cost for Maintenance: $%.2f", averageCost);
        showAlert("Average Cost", averageCostMessage);
    }


    public double calculateAverageCost(ActionEvent event) {
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
public void displayMostExpensiveMaintenance(ActionEvent event) {
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