package lu.ics.se.controllers;




import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Random;
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
    private TextField locationFieldV;
    @FXML
    private TextField capacityFieldV;
    @FXML
    private TextField daysSinceLastServiceField;
    @FXML
    private TextField vehicleTypeField;

    @FXML
    private TableView<Vehicle> vehicleTable;
    @FXML
    private TableColumn<Vehicle, String> vinColumnV;
    @FXML
    private TableColumn<Vehicle, String> nameColumnV;
    @FXML
    private TableColumn<Vehicle, String> locationColumnV;
    @FXML
    private TableColumn<Vehicle, Double> capacityColumnV;
    @FXML
    private TableColumn<Vehicle, String> typeColumnV;
    @FXML
    private TableColumn<Vehicle, Double> daysSinceLastServiceColumn;
    @FXML
    private Button calculateButton;
    @FXML
    private Label resultLabel;

    private VehicleManifest vehicleManifest;

    @FXML
    private TextField vinFieldV;

    @FXML
    private TextField vinFieldV1;

    @FXML
    private Button listworkshops;
    
    @FXML
    private Button addVehicle;
    

    @FXML
    private ListView<String> workshopsListView;

    @FXML
    private Button clearVehicleList;

    @FXML
    private Button clearVehicleFields;

    @FXML
    private TextField vehicleNameFieldV;
        
    private ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList();

    public VehicleController1() {
        this.vehicleManifest = VehicleManifest.getInstance();
    }

    @FXML
    public void initialize() {
    // Initialize vehicle table
    vinColumnV.setCellValueFactory(new PropertyValueFactory<>("vehicleIdentificationNumber"));
    nameColumnV.setCellValueFactory(new PropertyValueFactory<>("vehicleName"));
    locationColumnV.setCellValueFactory(new PropertyValueFactory<>("location"));
    capacityColumnV.setCellValueFactory(new PropertyValueFactory<>("capacity"));
    typeColumnV.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
    daysSinceLastServiceColumn.setCellValueFactory(new PropertyValueFactory<>("daysSinceLastService"));

    vehicleTable.setItems(FXCollections.observableArrayList(vehicleManifest.getCompanyOwnedVehicles()));

    vehicleTable.setRowFactory(tv -> {
        TableRow<Vehicle> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                showVehicleDetailsDialog(row.getItem());
            }
        });
        return row;
    });

    vehicleTypeComboBox.setItems(FXCollections.observableArrayList("Large truck", "Medium truck", "Van"));
}
    
    @FXML
    public void vehicleTypeField(ActionEvent event) {
    String selectedType = vehicleTypeComboBox.getValue();
    // Do something with selectedType...
}
    @FXML
public void addVehicle() {
    String vehicleName = vehicleNameFieldV.getText();
    String location = locationFieldV.getText();
    String capacityStr = capacityFieldV.getText();
    String vehicleType = vehicleTypeComboBox.getValue(); // Get the selected value from the ComboBox

    if (vehicleName.isEmpty() || location.isEmpty() || capacityStr.isEmpty() || vehicleType == null) {
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

    @FXML
    public void clearVehicleFields() {
        vehicleNameFieldV.clear();
        locationFieldV.clear();
        capacityFieldV.clear();
        vehicleTypeComboBox.setValue(null);
    }
    @FXML
    private ComboBox<String> vehicleTypeComboBox;

    public void initialize1() {
        vehicleTypeComboBox.setItems(FXCollections.observableArrayList("Large truck", "Medium truck", "Van"));
}
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private String generateUniqueVIN() {
    // Create a new instance of Random
    Random random = new Random();

    // Generate a random 8-digit number
    int number = random.nextInt(90000000) + 10000000;

    // Convert the number to a string and return it
    return Integer.toString(number);
}
    
    

    private void showVehicleDetailsDialog(Vehicle vehicle) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Vehicle Details");
        dialog.setHeaderText(null);
    
        ButtonType editButtonType = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
        ButtonType deleteButtonType = new ButtonType("Delete", ButtonBar.ButtonData.APPLY);
        ButtonType decommissionButtonType = new ButtonType("Decommission", ButtonBar.ButtonData.APPLY);
        dialog.getDialogPane().getButtonTypes().addAll(editButtonType, deleteButtonType, decommissionButtonType, ButtonType.CANCEL);
    
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
    
        TextField vehicleNameField = new TextField(vehicle.getVehicleName());
        TextField locationField = new TextField(vehicle.getLocation());
        TextField capacityField = new TextField(String.valueOf(vehicle.getCapacityInKg()));
        Label vehicleTypeLabel = new Label(vehicle.getVehicleType());
    
        grid.add(new Label("VIN:"), 0, 0);
        grid.add(new Label(vehicle.getVehicleIdentificationNumber()), 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(vehicleNameField, 1, 1);
        grid.add(new Label("Location:"), 0, 2);
        grid.add(locationField, 1, 2);
        grid.add(new Label("Capacity:"), 0, 3);
        grid.add(capacityField, 1, 3);
        grid.add(new Label("Type:"), 0, 4);
        grid.add(vehicleTypeLabel, 1, 4);
        
        dialog.getDialogPane().setContent(grid);

    dialog.setResultConverter(buttonType -> {
        if (buttonType == editButtonType) {
            String vehicleName = vehicleNameField.getText();
            String location = locationField.getText();
            String capacityStr = capacityField.getText();

            if (vehicleName.isEmpty() || location.isEmpty() || capacityStr.isEmpty()) {
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
            return ButtonType.OK;

        } else if (buttonType == deleteButtonType) {
            vehicleManifest.removeVehicle(vehicle);
            vehicleTable.setItems(FXCollections.observableArrayList(vehicleManifest.getCompanyOwnedVehicles()));
            return ButtonType.OK;
}
        
        else if (buttonType == decommissionButtonType) {
            vehicle.setDecommissioned(true);
            return ButtonType.OK;
        }
        return null;
    });

    Optional<ButtonType> result = dialog.showAndWait();
    if (result.isPresent() && (result.get() == editButtonType || result.get() == deleteButtonType || result.get() == decommissionButtonType)) {
        vehicleTable.refresh(); // Refresh the TableView
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



// Implement validateVIN(), findVehicleByVIN(), and calculateTotalServiceCost() methods here


    

@FXML
private TableView<ServiceEvent> maintenanceTableView;
@FXML
private TableView<ServiceHistory> serviceHistoryTableView;

@FXML
public void calculateTotalServiceCost(ActionEvent event) {
    String vin = vinFieldV1.getText().trim(); // Use the appropriate field for VIN input

    if (vin.isEmpty()) {
        showAlert("Error", "VIN Empty", "Please enter a VIN.");
        return;
    }

    try {
        if (validateVIN(vin)) {
            // VIN is valid, find the vehicle
            Vehicle vehicle = getVehicleByVIN(vin);
            if (vehicle != null) {
                // Vehicle found, calculate the total service cost
                double totalCost = calculateTotalCostForEvents(maintenanceTableView, serviceHistoryTableView);

                // Display the result in a pop-up window for the individual vehicle
                showTotalServiceCostForVehicle(vehicle, totalCost);
            } else {
                // Vehicle not found
                showAlert("Error", "Vehicle Not Found", "No vehicle found with the provided VIN.");
            }
        } else {
            // VIN is not valid
            showAlert("Invalid VIN", "The entered VIN is not valid.");
        }
    } catch (Exception e) {
        showAlert("Error", "An error occurred: " + e.getMessage());
    }
}

// Implement getVehicleByVIN(), calculateTotalCostForEvents(), and showTotalServiceCostForVehicle() methods here

@FXML
public void calculateTotalCost() {
    // Use the existing TableView instances for maintenance and service history from your UI
    double totalCost = calculateTotalCostForEvents(maintenanceTableView, serviceHistoryTableView);

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





private boolean validateVIN(String vin) {
    // Implement VIN validation logic here
    return vin.length() == 8; // Example validation: check VIN length
}


@FXML
public void listWorkshops(ActionEvent event) {
    workshopsListView.getItems().clear();
    String vin = vinFieldV.getText().trim();

    if (validateVIN(vin)) {
        Vehicle vehicle = getVehicleByVIN(vin);
        if (vehicle != null) {
            List<String> workshops = vehicle.getWorkshopsServicedIn();
            for (String workshop : workshops) {
                workshopsListView.getItems().add(workshop);
            }
        } else {
            workshopsListView.getItems().add("Vehicle not found");
        }
    } else {
        showAlert("Invalid VIN", "The entered VIN is not valid.");
    }
}

// Rest of the methods remain unchanged


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