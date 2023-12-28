package lu.ics.se.controllers;


import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import lu.ics.se.models.*;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.time.LocalDate;

import java.util.ArrayList;



import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.List;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import javafx.scene.layout.Pane;

public class VehicleController5 {

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

    private VehicleManifest vehicleManifest;
    private WorkshopList workshopList;

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

    private ServiceEvent selectedMaintenance;

    @FXML
    private TextField vinField;

    @FXML
    private TextField costField;

    @FXML
    private TextField workshopNameField;

    @FXML
    private ListView<String> vehicleServiceHistoryListView;



    public VehicleController5() {
        this.vehicleManifest = new VehicleManifest();
        this.workshopList = new WorkshopList();
    }

    @FXML
    private void initialize() {
        // Initialize vehicle table
        vinColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleIdentificationNumber"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleName"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        daysSinceLastServiceColumn.setCellValueFactory(new PropertyValueFactory<>("daysSinceLastService"));

        // Initialize workshop table
        workshopNameColumn.setCellValueFactory(new PropertyValueFactory<>("workshopName"));
        workshopAddressColumn.setCellValueFactory(new PropertyValueFactory<>("workshopAddress"));
        workshopTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        // Set double-click event handler for the vehicle table
        vehicleTable.setRowFactory(tv -> {
            TableRow<Vehicle> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    showVehicleDetailsDialog(row.getItem());
                }
            });
            return row;
        });

        // Load initial data into the tables
        vehicleTable.setItems(FXCollections.observableArrayList(vehicleManifest.getCompanyOwnedVehicles()));
        workshopTable.setItems(getWorkshopData());

        // Maintenance Tab
        
        initializeMaintenanceTableView();
    }

    private ObservableList<Workshop> getWorkshopData() {
        return null;
    }

    private void initializeMaintenanceTableView() {
    }

    @FXML
    private void addVehicle() {
        try {
            Vehicle newVehicle = new Vehicle(
                    generateUniqueVIN(),
                    vehicleNameField.getText(),
                    locationField.getText(),
                    Double.parseDouble(capacityField.getText()),
                    vehicleTypeField.getText(),
                    0
            );

            vehicleManifest.addVehicle(newVehicle);
            vehicleTable.setItems(FXCollections.observableArrayList(vehicleManifest.getCompanyOwnedVehicles()));
            
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Invalid input", "Numeric values are expected for capacity.");
        }
    }

    private void showAlert(String string, String string2) {
    }

    private String generateUniqueVIN() {
        return UUID.randomUUID().toString();
    }

    private void clearFields() {
        vehicleNameField.clear();
        locationField.clear();
        capacityField.clear();
        vehicleTypeField.clear();
    }

    private void showVehicleDetailsDialog(Vehicle vehicle) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Vehicle Details");
        dialog.setHeaderText(null);

        ButtonType editButtonType = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
        ButtonType deleteButtonType = new ButtonType("Delete", ButtonBar.ButtonData.APPLY);
        dialog.getDialogPane().getButtonTypes().addAll(editButtonType, deleteButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("VIN:"), 0, 0);
        grid.add(new TextField(vehicle.getVehicleIdentificationNumber()), 1, 0);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == editButtonType) {
                vehicle.setVehicleName(vehicleNameField.getText());
                vehicle.setLocation(locationField.getText());
                vehicle.setCapacityInKg(Double.parseDouble(capacityField.getText()));
                vehicle.setVehicleType(vehicleTypeField.getText());
                return ButtonType.OK;
            } else if (buttonType == deleteButtonType) {
                vehicleManifest.removeVehicle(vehicle);
                return ButtonType.OK;
            }
            return null;
        }); 

                    Optional<ButtonType> result = dialog.showAndWait();
                }
            

        @FXML
        private void initializeServiceCostTab() {
        try {
            ServiceCostController serviceCostController = new ServiceCostController(vehicleManifest);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lu/ics/se/views/ServiceCostTab.fxml"));
            loader.setController(serviceCostController);

            Parent serviceCostRoot = loader.load();

            Tab serviceCostTab = new Tab("Service Cost", serviceCostRoot);
            maintenanceTabPane.getTabs().add(serviceCostTab);
        } catch (IOException e) {
            showAlert("Error", "An error occurred while initializing the Service Cost tab.");
        }
    
        class ServiceCostController {

            @FXML
            private TextField vinTextField;
            @FXML
            private Button calculateButton;

            @FXML
            private Label resultLabel;

            private VehicleManifest vehicleManifest;

            private Stage stage;

            public void setStage(Stage stage) {
                this.stage = stage;
            }

            // Constructor to inject the VehicleManifest
            public ServiceCostController(VehicleManifest vehicleManifest) {
                this.vehicleManifest = vehicleManifest;
            }
            @FXML
            private void calculateTotalServiceCost() {
            String vin = vinTextField.getText().trim();

            if (vin.isEmpty()) {
            showAlert("Error", "VIN Empty", "Please enter a VIN.");
            return;
            }

        // Assuming you have a method to get a vehicle by VIN from your data model
        Vehicle vehicle = getVehicleByVIN(vin);

        if (vehicle == null) {
            showAlert("Error", "Vehicle Not Found", "No vehicle found with the provided VIN.");
            return;
        }

        TableView<ServiceEvent> maintenanceEventsTableView = new TableView<>();
        TableView<ServiceHistory> serviceHistoryEntriesTableView = new TableView<>();
        // Assuming you have TableView instances for maintenance and service history in your UI

        double totalCost = calculateTotalCostForEvents(maintenanceEventsTableView, serviceHistoryEntriesTableView);

        // Display the result in a pop-up window for the individual vehicle
        showTotalServiceCostForVehicle(vehicle, totalCost);
    }

    @FXML
    private void calculateTotalCost() {
        TableView<ServiceEvent> maintenanceEventsTableView = new TableView<>();
        TableView<ServiceHistory> serviceHistoryEntriesTableView = new TableView<>();
        // Assuming you have TableView instances for maintenance and service history in your UI
        double totalCost = calculateTotalCostForEvents(maintenanceEventsTableView, serviceHistoryEntriesTableView);

        // Display the result in a pop-up window for all vehicles
        showTotalServiceCostForAllVehicles(totalCost);
    }

    private double calculateTotalCostForEvents(TableView<ServiceEvent> maintenanceTableView, TableView<ServiceHistory> serviceHistoryTableView) {
        double totalCost = 0;

        // Loop through maintenance events
        for (ServiceEvent maintenanceEvent : maintenanceTableView.getItems()) {
            totalCost += maintenanceEvent.getEventCost();
        }

        // Loop through service history entries
        for (ServiceHistory serviceHistoryEntry : serviceHistoryTableView.getItems()) {
            totalCost += Double.parseDouble(serviceHistoryEntry.getCost());
        }

        return totalCost;
    }

    private void showTotalServiceCostForVehicle(Vehicle vehicle, double totalCost) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Total Service Cost for Vehicle");

        VBox vbox = new VBox(new Label("The total service cost for vehicle with VIN " + vehicle.getVin() + " is: $" + totalCost));
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> stage.close());
        vbox.getChildren().add(closeButton);

        Scene scene = new Scene(vbox, 350, 100);
        stage.setScene(scene);

        // Show the stage
        stage.show();
    }

    private void showTotalServiceCostForAllVehicles(double totalCost) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Total Cost of Services");

        VBox vbox = new VBox(new Label("The total cost of services for all vehicles is: $" + totalCost));
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> stage.close());
        vbox.getChildren().add(closeButton);

        Scene scene = new Scene(vbox, 350, 100);
        stage.setScene(scene);

        // Show the stage
        stage.show();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private Vehicle getVehicleByVIN(String vin) {
        // Implement this method to retrieve a vehicle by VIN from your data model
        return null;
    }

    // Define your ServiceEvent, ServiceHistory, and Vehicle classes as needed
}

    
    class WorkshopsVisitedController {

        @FXML
        private TextField vinTextField;

        @FXML
        private ListView<String> workshopsListView;

        private VehicleManifest vehicleManifest;  // Assuming you have a reference to the VehicleManifest

        public WorkshopsVisitedController(VehicleManifest vehicleManifest) {
            this.vehicleManifest = vehicleManifest;
        }

        @FXML
        private void listWorkshops(ActionEvent event) {
            // Get the VIN from the text field
            String vin = vinTextField.getText().trim();

            // Clear the previous list
            workshopsListView.getItems().clear();

            // Find the vehicle by VIN
            Vehicle vehicle = findVehicleByVIN(vin);

            // If the vehicle is found, list the workshops
            if (vehicle != null) {
                List<lu.ics.se.models.Workshop> workshops = vehicle.getWorkshopsServicedIn();

                // Display the workshops in the ListView
                for (lu.ics.se.models.Workshop workshop : workshops) {
                    workshopsListView.getItems().add(workshop.getWorkshopName());
                }
            } else {
                // Vehicle not found
                workshopsListView.getItems().add("Vehicle not found");
            }
        }

        @FXML
        private void clearList(ActionEvent event) {
            // Clear the ListView
            workshopsListView.getItems().clear();
        }

        // Helper method to find a vehicle by VIN
        private Vehicle findVehicleByVIN(String vin) {
            List<Vehicle> vehicles = vehicleManifest.getCompanyOwnedVehicles();
            for (Vehicle vehicle : vehicles) {
                if (vehicle.getVehicleIdentificationNumber().equals(vin)) {
                    return vehicle;
                }
            }
            return null;
        
    }   @FXML
        private void handleAddWorkshop(ActionEvent event) {
        String name = nameField.getText();
        String address = addressField.getText();
        String type = typeField.getText();

        if (name.isEmpty() || address.isEmpty() || type.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        Workshop newWorkshop = new Workshop(name, address, type);
        workshopList.addWorkshop(newWorkshop);

        workshopTable.setItems(getWorkshopData());
        clearFields();
    }

    @FXML
    private void handleDeleteWorkshop(ActionEvent event) {
        Workshop selectedWorkshop = workshopTable.getSelectionModel().getSelectedItem();

        if (selectedWorkshop != null) {
            // Show confirmation dialog
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Deletion");
            confirmationAlert.setHeaderText("Are you sure you want to delete the workshop?");
            confirmationAlert.setContentText("This action cannot be undone.");

            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // User confirmed deletion
                workshopList.removeWorkshop(selectedWorkshop);
                workshopTable.setItems(getWorkshopData());
            }
        } else {
            showAlert("Error", "Please select a workshop to delete.");
        }
    }

    @FXML
    private void handleEditWorkshop(ActionEvent event) {
        Workshop selectedWorkshop = workshopTable.getSelectionModel().getSelectedItem();

        if (selectedWorkshop != null) {
            openEditWorkshopDialog(selectedWorkshop);
        } else {
            showAlert("Error", "Please select a workshop to edit.");
        }
    }

    private void populateFields(Workshop workshop) {
        nameField.setText(workshop.getWorkshopName());
        addressField.setText(workshop.getWorkshopAddress());
        typeField.setText(workshop.getType());
    }

    private void openEditWorkshopDialog(Workshop workshop) {
        Stage editDialog = new Stage();
        editDialog.initModality(Modality.APPLICATION_MODAL);
        editDialog.setTitle("Edit Workshop");

        VBox dialogVBox = new VBox(20);
        TextField editedNameField = new TextField(workshop.getWorkshopName());
        TextField editedAddressField = new TextField(workshop.getWorkshopAddress());
        TextField editedTypeField = new TextField(workshop.getType());
        Button saveButton = new Button("Save Changes");

        saveButton.setOnAction(event -> {
            workshop.setWorkshopName(editedNameField.getText());
            workshop.setWorkshopAddress(editedAddressField.getText());
            workshop.setType(editedTypeField.getText());

            workshopTable.setItems(getWorkshopData());
            editDialog.close();
        });

        dialogVBox.getChildren().addAll(
                new Label("Name: "), editedNameField,
                new Label("Address: "), editedAddressField,
                new Label("Type: "), editedTypeField,
                saveButton
        );

        Scene dialogScene = new Scene(dialogVBox, 300, 200);
        editDialog.setScene(dialogScene);
        editDialog.show();
    }

    private ObservableList<Workshop> getWorkshopData() {
        return FXCollections.observableArrayList(workshopList.getAllWorkshops());
    }

    private void clearWorkshopFields() {
        nameField.clear();
        addressField.clear();
        typeField.clear();
    }

   
    @FXML
private void displayMostExpensiveWorkshop() {
    // Retrieve the most expensive workshop
    Workshop mostExpensiveWorkshop = findMostExpensiveWorkshop();

    // Display information on the most expensive workshop
    if (mostExpensiveWorkshop != null) {
        String mostExpensiveWorkshopMessage = "Most Expensive Workshop:\n" +
                "Workshop Name: " + mostExpensiveWorkshop.getWorkshopName() + "\n" +
                "Workshop Address: " + mostExpensiveWorkshop.getWorkshopAddress() + "\n" +
                "Total Cost: $" + calculateTotalCostForWorkshop(mostExpensiveWorkshop);
        showAlert3("Most Expensive Workshop", mostExpensiveWorkshopMessage);
    } else {
        showAlert3("No Workshops", "There are no workshops available.");
    }
}

private double calculateTotalCostForWorkshop(Workshop workshop) {
    return 0.0;
}

private Workshop findMostExpensiveWorkshop() {
    // Retrieve the list of all workshops
    ArrayList<Workshop> allWorkshops = workshopList.getAllWorkshops();

    if (allWorkshops.isEmpty()) {
        return null; // Return null if there are no workshops available
    }

    Workshop mostExpensiveWorkshop = null;
    double maxCost = Double.MIN_VALUE;

    // Iterate through all workshops to find the most expensive one
    for (Workshop workshop : allWorkshops) {
        double totalCost = calculateTotalCostForWorkshop(workshop);
        if (totalCost > maxCost) {
            maxCost = totalCost;
            mostExpensiveWorkshop = workshop;
        }
    }

    // Check the maintenanceTableView
    for (ServiceEvent event : maintenanceTableView.getItems()) {
        double totalCost = event.getEventCost();
        if (totalCost > maxCost) {
            maxCost = totalCost;
            mostExpensiveWorkshop = new Workshop(
                    event.getEventWorkshopName(),
                    event.getEventWorkshopAddress(),
                    calculateTotalCostForWorkshop(event)
            );
        }
    }
    TableView<ServiceHistory> serviceHistoryTableView = new TableView<>();
    for (ServiceHistory history : serviceHistoryTableView.getItems()) {
        double totalCost = Double.parseDouble(history.getCost());
        if (totalCost > maxCost) {
            maxCost = totalCost;
            mostExpensiveWorkshop = new Workshop(
                    history.getWorkshopName(),
                    history.getWorkshopAddress(),
                    calculateTotalCostForWorkshop2(history),
                    ""
            );
        }
    }
    return mostExpensiveWorkshop;
    
}

private String calculateTotalCostForWorkshop2(ServiceHistory history) {
    return null;
}

private Object calculateTotalCostForWorkshop(ServiceEvent event) {
    return null;
}

private void showAlert3(String title, String content) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
}


    @FXML
    private void initializeMaintenanceTableView() {
        // Set up columns for maintenance table view
        TableColumn<ServiceEvent, String> vinColumn = new TableColumn<>("VIN");
        vinColumn.setCellValueFactory(new PropertyValueFactory<>("eventVin"));

        TableColumn<ServiceEvent, LocalDate> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("eventDate"));

        TableColumn<ServiceEvent, String> workshopColumn = new TableColumn<>("Workshop");
        workshopColumn.setCellValueFactory(new PropertyValueFactory<>("eventWorkshopName"));

        TableColumn<ServiceEvent, Double> costColumn = new TableColumn<>("Cost");
        costColumn.setCellValueFactory(new PropertyValueFactory<>("eventCost"));

        // Add columns to the table view
        maintenanceTableView.getColumns().addAll(vinColumn, dateColumn, workshopColumn, costColumn);


        // Add double-click event to open the edit/delete window
        maintenanceTableView.setOnMouseClicked(this::handleMaintenanceDoubleClick);

        // Load initial data into the table
        refreshMaintenanceTable();
    }

    private void refreshMaintenanceTable() {
        maintenanceTableView.setItems(FXCollections.<ServiceEvent>observableArrayList(vehicleManifest.getAllServiceEvents()));
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

            // Parse the date from the DatePicker
            // Note: You should handle date parsing errors in a real-world application
            ServiceEvent newMaintenance = new ServiceEvent("Maintenance", "Maintenance Description",
                    0.0, DateUtil.parseStringToDate(dateString), workshopList.getWorkshopByName(workshopName),
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
            VehicleController controller = loader.getController();
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

    private void setMaintenance(ServiceEvent maintenance) {
        this.selectedMaintenance = maintenance;
        updateLabels();
    }

    private Label costLabel; // Declare costLabel


    private void updateLabels() {
        vehicleLabel.setText("VIN: " + selectedMaintenance.getEventVehicleVIN());
        dateLabel.setText("Date: " + selectedMaintenance.getEventDate());
        workshopLabel.setText("Workshop: " + selectedMaintenance.getEventWorkshopName());
        costLabel.setText("Cost: $" + selectedMaintenance.getEventCost());
    }



    @FXML
    private void handleEdit() {
        if (selectedMaintenance != null) {
            try {
                // Load the FXML file for the edit window
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/lu/ics/se/views/EditMaintenance.fxml"));
                Parent root = loader.load();

                // Set the selected maintenance in the edit controller
                EditMaintenanceController editController = loader.getController();
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

    

    private void showAlert2(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleShowAverageCost() {
        // Display information on average cost
        double averageCost = calculateAverageCost();
        String averageCostMessage = String.format("Average Cost for Maintenance: $%.2f", averageCost);
        showAlert("Average Cost", averageCostMessage);
    }

    private double calculateAverageCost() {
        return 0;
    }

    @FXML
    private void handleShowMostExpensive() {
        // Display information on the most expensive maintenance
        ServiceEvent mostExpensive = findMostExpensiveMaintenance();
        String mostExpensiveMessage = "Most Expensive Maintenance:\n" +
                "Vehicle: " + mostExpensive.getEventVehicleName() + "\n" +
                "Workshop: " + mostExpensive.getEventWorkshopName() + "\n" +
                "Cost: $" + mostExpensive.getEventCost();
        showAlert("Most Expensive Maintenance", mostExpensiveMessage);
    }

    private ServiceEvent findMostExpensiveMaintenance() {
        return null;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private ListView<String> serviceHistoryListView;

    private static List<ServiceHistory> serviceHistories;

    public static void setServiceHistories(List<ServiceHistory> histories) {
        serviceHistories = histories;
    }

    @FXML
    void loadWorkshopHistory(ActionEvent event) {
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

    @FXML
    void clearServiceHistory(ActionEvent event) {
        serviceHistoryListView.getItems().clear();
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
    void clearVehicleServiceHistory(ActionEvent event) {
        vehicleServiceHistoryListView.getItems().clear();
    }

    class ServiceHistoryController {

    @FXML
    private TextField workshopNameTextField;

    @FXML
    private ListView<String> serviceHistoryListView;

    // You need to have a reference to your WorkshopList or WorkshopService to get the workshops
    // For this example, I assume you have a WorkshopList instance in the main application class.

    // For simplicity, I'm using a static reference. In a real application, you might want to use dependency injection.
    private static WorkshopList workshopList;

    // Setter method for WorkshopList (to be set from the main application)
    public static void setWorkshopList(WorkshopList list) {
        workshopList = list;
    }

    @FXML
private void showHistory(ActionEvent event) {
    String workshopName = workshopNameTextField.getText().trim();
    Workshop workshop = workshopList.getWorkshopByName(workshopName);

    // Clear the ListView before displaying new entries
    serviceHistoryListView.getItems().clear();

    if (workshop != null) {
        // Iterate through the general service history and filter entries for the selected workshop
        for (ServiceHistory serviceHistory : serviceHistories) {
            if (serviceHistory.getWorkshopName() != null &&
                serviceHistory.getWorkshopName().equals(workshop.getWorkshopName())) {
                // Add the entry to the workshop service history ListView
                serviceHistoryListView.getItems().add(formatServiceHistory(serviceHistory));
            }
        }
    } else {
        // Handle the case when the workshop is not found
        showAlert("Workshop not found.", "Error", Alert.AlertType.ERROR);
    }

}


    // Format the service event information for display
    private String formatServiceEvent(ServiceEvent event) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder result = new StringBuilder();
        result.append("Event Name: ").append(event.getEventName()).append("\n");
        result.append("Date: ").append(dateFormat.format(event.getEventDate())).append("\n");
        result.append("Description: ").append(event.getEventDescription()).append("\n");
        result.append("Cost: $").append(event.getEventCost()).append("\n");

        // Add information about service actions
        result.append("Service Actions:\n");
        for (ServiceAction action : event.getEventActions()) {
            result.append("  - ").append(action.getActionName()).append(": ").append(action.getActionDescription()).append("\n");
        }

        return result.toString();
    }

    // Show an alert with the given message, title, and alert type
    private void showAlert(String message, String title, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    class MainController {

        @FXML
        private TextField vinField;
    
        @FXML
        private DatePicker serviceDateField;
    
        @FXML
        private TextArea descriptionField;
    
        @FXML
        private TextField costField;
    
        @FXML
        private TextField partsReplacedField;
    
        @FXML
        private TextField workshopNameField;
    
        @FXML
        private TableView<ServiceActivity> serviceActivitiesTableView;
    
        @FXML
        private TableColumn<ServiceActivity, String> activityNameColumn;

        @FXML
    private TableColumn<ServiceActivity, String> vinColumn;

    @FXML
    private TableColumn<ServiceActivity, String> descriptionColumn;

    @FXML
    private TableColumn<ServiceActivity, Double> costColumn;

    @FXML
    private TableColumn<ServiceActivity, LocalDate> serviceDateColumn;

    @FXML
    private TableColumn<ServiceActivity, String> workshopNameColumn;

    @FXML
    private TableColumn<ServiceActivity, String> partsReplacedColumn;

    @FXML
    private AnchorPane detailsPane;

    @FXML
    private TableView<ServiceHistory> serviceHistoryTableView;

    

    

    
        // Other necessary methods and event handlers...
        @FXML
        private void initialize() {
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
        private void addServiceActivity() {
            // Get values from the fields
            String vin = vinField.getText();
            LocalDate serviceDate = serviceDateField.getValue();
            String description = descriptionField.getText();
            double cost = Double.parseDouble(costField.getText());
            String partsReplaced = partsReplacedField.getText();
            String workshopName = workshopNameField.getText();
    
            // Assuming you have a method to add service activities to the table
            // (You should implement this method in your ServiceActivitiesTable class)
            ServiceActivity newActivity = new ServiceActivity(vin, description, cost, serviceDate, workshopName);
            newActivity.setPartsReplaced(partsReplaced); // Set partsReplaced
            serviceActivitiesTableView.getItems().add(newActivity);
    

            // Clear the fields after adding the service activity
            clearFields();
        }
    
        // Add other methods and event handlers as needed...
    
    private void showDetails(ServiceActivity activity) {
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

    private AnchorPane createDetailsView(ServiceActivity activity) {
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
    private void editServiceActivity() {
        ServiceActivity selectedActivity = serviceActivitiesTableView.getSelectionModel().getSelectedItem();

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

    @FXML
    private void markAsCompleted() {
        ServiceActivity selectedActivity = serviceActivitiesTableView.getSelectionModel().getSelectedItem();

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
                // TODO: Add logic to move the completed activity to the history table
                // You may need a separate table for completed activities
       


    
    }
    private void transferToServiceHistory(ServiceActivity completedActivity) {
        // Assuming you have a method to add service history entries to the table
        // (You should implement this method in your ServiceHistoryTable class)
        ServiceHistory newHistoryEntry = new ServiceHistory(
                completedActivity.getVin(),
                completedActivity.getServiceDate(),
                completedActivity.getDescription(),
                completedActivity.getCost(),
                completedActivity.getPartsReplaced(),
                completedActivity.getWorkshopName()
        );

        TableView<ServiceHistory> serviceHistoryTableView = new TableView<>(); // Initialize the variable

        serviceHistoryTableView.getItems().add(newHistoryEntry);
    }

    @FXML
    private void removeServiceActivity() {
        TableView<ServiceActivity> serviceActivitiesTableView = new TableView<>(); // Initialize the variable
        ServiceActivity selectedActivity = serviceActivitiesTableView.getSelectionModel().getSelectedItem();

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
                handleServiceHistoryDoubleClick();
            }
        });
    }

    private void handleServiceHistoryDoubleClick() {
    }

    @FXML
    private void initialize() {
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
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    // Add a method to close the popup window
    private void closePopupWindow() {
        // Code to close the window, you can use the appropriate method based on your application
        // For example, if you are using JavaFX Stage, you can use stage.close();
    }

    

}
}
        }
    
    
    
    
    
    
    
    
