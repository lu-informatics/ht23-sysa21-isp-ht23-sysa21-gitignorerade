package lu.ics.se.controllers;




import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import lu.ics.se.models.*;
import javafx.stage.StageStyle;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.io.IOException;
import javafx.event.ActionEvent; // Add this import statement

public class VehicleController1 {
    // Vehicle Tab
    @FXML
    private TextField vehicleNameField;


@FXML
private ListView<String> workshopListView;
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

    private VehicleManifest vehicleManifest;

    @FXML
    private TextField vinField;

    @FXML
    private Button listworkshops;
    
    @FXML
    private Button addVehicle;
    @FXML
    private TextField vinTextField;

    @FXML
    private ListView<String> workshopsListView;

    @FXML
    private Button clearVehicleList;

    @FXML
    private Button clearVehicleFields;

    
        
    
    public VehicleController1() {
        this.vehicleManifest = new VehicleManifest();
    }

    @FXML
    public void initialize() {
        // Initialize vehicle table
        vinColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleIdentificationNumber"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleName"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        daysSinceLastServiceColumn.setCellValueFactory(new PropertyValueFactory<>("daysSinceLastService"));

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
    }

    @FXML
    public void clearVehicleList() {
        vehicleTable.getItems().clear();
    }

    @FXML
    public void addVehicle() {
        String vehicleName = vehicleNameField.getText();
        String location = locationField.getText();
        String capacityStr = capacityField.getText();
        String vehicleType = vehicleTypeField.getText();
    
        if (vehicleName.isEmpty() || location.isEmpty() || capacityStr.isEmpty() || vehicleType.isEmpty()) {
            showAlert("Invalid input", "All fields must be filled.");
            return;
        }
    
        double capacity;
        try {
            capacity = Double.parseDouble(capacityStr);
        } catch (NumberFormatException e) {
            showAlert("Invalid input", "Capacity must be a number.");
            return;
        }
    
        try {
            Vehicle newVehicle = new Vehicle(
                    generateUniqueVIN(),
                    vehicleName,
                    location,
                    capacity,
                    vehicleType,
                    0
            );
    
            vehicleManifest.addVehicle(newVehicle);
            vehicleTable.setItems(FXCollections.observableArrayList(vehicleManifest.getCompanyOwnedVehicles()));
    
            clearVehicleFields();
        } catch (IllegalArgumentException e) {
            showAlert("Invalid input", e.getMessage());
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private String generateUniqueVIN() {
        // Generate a random UUID
        String uuid = UUID.randomUUID().toString();
    
        // Remove hyphens from the UUID
        String uuidWithoutHyphens = uuid.replaceAll("-", "");
    
        // Take the first 8 characters
        return uuidWithoutHyphens.substring(0, 8);
    }
    
    private void clearVehicleFields() {
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
    
        TextField vehicleNameField = new TextField(vehicle.getVehicleName());
        TextField locationField = new TextField(vehicle.getLocation());
        TextField capacityField = new TextField(String.valueOf(vehicle.getCapacityInKg()));
        TextField vehicleTypeField = new TextField(vehicle.getVehicleType());
    
        grid.add(new Label("VIN:"), 0, 0);
        grid.add(new TextField(vehicle.getVehicleIdentificationNumber()), 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(vehicleNameField, 1, 1);
        grid.add(new Label("Location:"), 0, 2);
        grid.add(locationField, 1, 2);
        grid.add(new Label("Capacity:"), 0, 3);
        grid.add(new Label("Type:"), 0, 4);
    grid.add(vehicleTypeField, 1, 4);

    dialog.getDialogPane().setContent(grid);

    dialog.setResultConverter(buttonType -> {
        if (buttonType == editButtonType) {
            String vehicleName = vehicleNameField.getText();
            String location = locationField.getText();
            String capacityStr = capacityField.getText();
            String vehicleType = vehicleTypeField.getText();

            if (vehicleName.isEmpty() || location.isEmpty() || capacityStr.isEmpty() || vehicleType.isEmpty()) {
                showAlert("Invalid input", "All fields must be filled.");
                return null;
            }

            double capacity;
            try {
                capacity = Double.parseDouble(capacityStr);
            } catch (NumberFormatException e) {
                showAlert("Invalid input", "Capacity must be a number.");
                return null;
            }

            vehicle.setVehicleName(vehicleName);
            vehicle.setLocation(location);
            vehicle.setCapacityInKg(capacity);
            vehicle.setVehicleType(vehicleType);
            return ButtonType.OK;
        } else if (buttonType == deleteButtonType) {
            vehicleManifest.removeVehicle(vehicle);
            return ButtonType.OK;
        }
        return null;
    });

    Optional<ButtonType> result = dialog.showAndWait();
    if (result.isPresent() && (result.get() == editButtonType || result.get() == deleteButtonType)) {
        vehicleTable.setItems(FXCollections.observableArrayList(vehicleManifest.getCompanyOwnedVehicles()));
    }
}

    @FXML
    private TabPane vehicleTabPane; // Declare vehicleTabPane

    @FXML
public void initializeServiceCostTab() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lu/ics/se/views/ServiceCostTab.fxml"));
        Parent serviceCostRoot = loader.load();
        Tab serviceCostTab = new Tab("Service Cost", serviceCostRoot);

        vehicleTabPane.getTabs().add(serviceCostTab); // Access vehicleTabPane and add serviceCostTab
    } catch (IOException e) {
        showAlert("Error", "An error occurred while initializing the Service Cost tab.");
    }
}

@FXML
public void vinField(ActionEvent event) {
    String vin = vinField.getText().trim();

    try {
        if (validateVIN(vin)) {
            // VIN is valid, find the vehicle
            Vehicle vehicle = getVehicleByVIN(vin);
            if (vehicle != null) {
                // Vehicle found, calculate the total service cost
                calculateTotalServiceCost(vehicle);
            } else {
                // Vehicle not found
                showAlert("Vehicle Not Found", "No vehicle found with the entered VIN.");
            }
        } else {
            // VIN is not valid
            showAlert("Invalid VIN", "The entered VIN is not valid.");
        }
    } catch (Exception e) {
        showAlert("Error", "An error occurred: " + e.getMessage());
    }
}



// Implement validateVIN(), findVehicleByVIN(), and calculateTotalServiceCost() methods here


    

@FXML
private TableView<ServiceEvent> maintenanceEventsTableView;
@FXML
private TableView<ServiceHistory> serviceHistoryEntriesTableView;

private void calculateTotalServiceCost(Vehicle vehicle) {
    // Implement this method
}

@FXML
public void calculateTotalServiceCost(ActionEvent event) {
    String vin = vinField.getText().trim();

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

    double totalCost = calculateTotalCostForEvents(maintenanceEventsTableView, serviceHistoryEntriesTableView);

    // Display the result in a pop-up window for the individual vehicle
    showTotalServiceCostForVehicle(vehicle, totalCost);
}

// Implement getVehicleByVIN(), calculateTotalCostForEvents(), and showTotalServiceCostForVehicle() methods here

@FXML
public void calculateTotalCost() {
    // Use the existing TableView instances for maintenance and service history from your UI
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

// Define your ServiceEvent, ServiceHistory, and Vehicle classes as needed
 // Make sure this fx:id matches with your FXML

@FXML
public void vinTextField(ActionEvent event) {
    String vin = vinTextField.getText().trim();

    if (validateVIN(vin)) {
        // VIN is valid, find the vehicle
        Vehicle vehicle = getVehicleByVIN(vin);
        if (vehicle != null) {
            // Vehicle found, list the workshops visited
            listWorkshops(vehicle);
        } else {
            // Vehicle not found
            showAlert("Vehicle Not Found", "No vehicle found with the entered VIN.");
            workshopListView.getItems().clear(); // Clear the list as no vehicle is found
        }
    } else {
        // VIN is not valid
        showAlert("Invalid VIN", "The entered VIN is not valid.");
        workshopListView.getItems().clear(); // Clear the list as VIN is invalid
    }
}

private void listWorkshops(Vehicle vehicle) {
}

private boolean validateVIN(String vin) {
    // Implement VIN validation logic here
    return vin.length() == 8; // Example validation: check VIN length
}


@FXML
public void listWorkshops(ActionEvent event) {
    workshopsListView.getItems().clear();
    Vehicle vehicle = getVehicleByVIN(vinTextField.getText().trim());
    if (vehicle != null) {
        List<lu.ics.se.models.Workshop> workshops = vehicle.getWorkshopsServicedIn();
        for (lu.ics.se.models.Workshop workshop : workshops) {
            workshopsListView.getItems().add(workshop.getWorkshopName());
        }
    } else {
        workshopsListView.getItems().add("Vehicle not found");
    }
}

@FXML
public void clearList(ActionEvent event) {
    // Clear the ListView
    workshopsListView.getItems().clear();
}

// Helper method to find a vehicle by VIN
private Vehicle getVehicleByVIN(String vin) {
    List<Vehicle> vehicles = vehicleManifest.getCompanyOwnedVehicles();
    for (Vehicle vehicle : vehicles) {
        if (vehicle.getVehicleIdentificationNumber().equals(vin)) {
            return vehicle;
        }
    }
    return null;
}

public void setMaintenance(ServiceEvent selectedMaintenance) {
}
}