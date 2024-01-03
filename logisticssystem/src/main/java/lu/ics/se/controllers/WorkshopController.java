package lu.ics.se.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lu.ics.se.models.*;

import java.util.ArrayList;
import java.util.Optional;




public class WorkshopController {

    // Workshop Tab
    @FXML
    private TableView<Workshop> workshopTable;
    @FXML
    private TableColumn<Workshop, String> workshopNameColumnW;
    @FXML
    private TableColumn<Workshop, String> workshopAddressColumnW;
    @FXML
    private TableColumn<Workshop, String> workshopTypeColumnW;

    @FXML
    private TextField nameFieldW;
    @FXML
    private TextField addressFieldW;

    @FXML
    private Button clearWorkshopList;

    @FXML
    private Button addWorkshopButton;
    @FXML
    private Button editWorkshopButton;
    @FXML
    private Button deleteWorkshopButton;

    @FXML
    private ComboBox<String> workshopTypeField;
    @FXML
    private TableView<ServiceEvent> maintenanceTableView;

    @FXML
    private TableView<ServiceHistory> serviceHistoryTableView;

    

    private WorkshopList workshopList;
    // The value of the field WorkshopController.vehicleManifest is not used
    // If you don't need to use the vehicleManifest field, you can remove it to avoid the warning
    // If you do need to use it, make sure to utilize it in your code
    private VehicleManifest vehicleManifest;


    // Remove the unused field
    // private VehicleManifest vehicleManifest;

    public WorkshopController(VehicleManifest vehicleManifest, WorkshopList workshopList) {
        this.vehicleManifest = vehicleManifest;
        this.workshopList = WorkshopList.getInstance();
    }
    
        public WorkshopController() {
            // constructor body
        }
    
        // rest of the class
    

        
        public void initialize() {
            initializeWorkshopList();
        
            // Initialize workshop table
            workshopNameColumnW.setCellValueFactory(new PropertyValueFactory<>("workshopName"));
            workshopAddressColumnW.setCellValueFactory(new PropertyValueFactory<>("workshopAddress"));
            workshopTypeColumnW.setCellValueFactory(new PropertyValueFactory<>("type"));
        
            workshopTable.setItems(getWorkshopData());
        
            // Initialize workshop type ComboBox
            workshopTypeField.setItems(FXCollections.observableArrayList("Internal", "External"));

            workshopTable.setRowFactory(tv -> {
                TableRow<Workshop> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        handleWorkshopActions(null);
                    }
                });
                return row;
            });
        }
        
        private void clearWorkshopColumns() {
            nameFieldW.clear();
            addressFieldW.clear();
            workshopTypeField.setValue(null); // Clear the ComboBox selection
        }
        
        private void initializeWorkshopList() {
            // Initialize workshopList here. This might involve creating a new instance
            // or fetching it from another source depending on your application's design.
            workshopList  = WorkshopList.getInstance();
        
            // If your WorkshopList requires some initial data or setup, do it here
            // For example, you might load workshops from a database or a file
        }
        
        private ObservableList<Workshop> getWorkshopData() {
            if (workshopList != null) {
                return FXCollections.observableArrayList(workshopList.getAllWorkshops());
            } else {
                // Handle the case where workshopList is null
                return FXCollections.observableArrayList();
            }
        }
        @FXML
        public void handleAddWorkshop(ActionEvent event) {
            String name = nameFieldW.getText();
            String address = addressFieldW.getText();
            String type = workshopTypeField.getValue(); // Get the selected value from the ComboBox
        
            if (name.isEmpty() || address.isEmpty() || type == null) {
                showAlert("Error", "Please fill in all fields.");
                return;
            }
        
            Workshop newWorkshop = new Workshop(name, address, type);
            workshopList.addWorkshop(newWorkshop);
        
            workshopTable.setItems(getWorkshopData());
            clearWorkshopColumns();
        }

private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

@FXML
public void workshopTypeField(ActionEvent event) {
    String selectedType = workshopTypeField.getValue();
    // Do something with selectedType...
}

@FXML
public void handleWorkshopActions(ActionEvent event) {
    Workshop selectedWorkshop = workshopTable.getSelectionModel().getSelectedItem();

    if (selectedWorkshop != null) {
        showWorkshopDetailsDialog(selectedWorkshop);
    } else {
        showAlert("Error", "Please select a workshop.");
    }
}

private void showWorkshopDetailsDialog(Workshop workshop) {
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setTitle("Workshop Details");
    dialog.setHeaderText(null);

    ButtonType editButtonType = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
    ButtonType deleteButtonType = new ButtonType("Delete", ButtonBar.ButtonData.APPLY);
    dialog.getDialogPane().getButtonTypes().addAll(editButtonType, deleteButtonType, ButtonType.CANCEL);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    TextField workshopNameField = new TextField(workshop.getWorkshopName());
    TextField workshopAddressField = new TextField(workshop.getWorkshopAddress());
    Label workshopTypeLabel = new Label(workshop.getType());

    grid.add(new Label("Name:"), 0, 0);
    grid.add(workshopNameField, 1, 0);
    grid.add(new Label("Address:"), 0, 1);
    grid.add(workshopAddressField, 1, 1);
    grid.add(new Label("Type:"), 0, 2);
    grid.add(workshopTypeLabel, 1, 2);

    dialog.getDialogPane().setContent(grid);

  dialog.setResultConverter(buttonType -> {
        if (buttonType == editButtonType) {
            String name = workshopNameField.getText();
            String address = workshopAddressField.getText();

            if (name.isEmpty() || address.isEmpty()) {
                showAlert("Invalid input", "All fields must be filled.");
                return null;
            }

            workshop.setWorkshopName(name);
            workshop.setWorkshopAddress(address);
            return ButtonType.OK;

        } else if (buttonType == deleteButtonType) {
            workshopList.removeWorkshop(workshop);
            workshopTable.setItems(getWorkshopData());
            return ButtonType.OK;
        }
        return null;
    });

    Optional<ButtonType> result = dialog.showAndWait();
    if (result.isPresent() && (result.get() == editButtonType || result.get() == deleteButtonType)) {
        workshopTable.refresh(); // Refresh the TableView
    }
}

@FXML
public void displayMostExpensiveWorkshop() {
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
    double totalCost = 0.0;

    for (ServiceEvent event : workshop.getServiceEvents()) {
        // Assuming ServiceEvent has a method getCost that returns the cost of the event
        totalCost += event.getCost();
    }

    return totalCost;
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
// Check the maintenanceTableView
// Check the maintenanceTableView
for (ServiceEvent event : maintenanceTableView.getItems()) {
    double totalCost = event.getEventCost();
    if (totalCost > maxCost) {
        maxCost = totalCost;
        mostExpensiveWorkshop = new Workshop(
                event.getEventWorkshopName(),
                event.getEventWorkshopAddress(),
                calculateTotalCostForWorkshop(event),
                event.getEventWorkshopType()
        );
    }
}


// Check the serviceHistoryTableView
for (ServiceHistory history : serviceHistoryTableView.getItems()) {
    double totalCost = Double.parseDouble(history.getCost());
    if (totalCost > maxCost) {
        maxCost = totalCost;
        mostExpensiveWorkshop = new Workshop(
            history.getWorkshopName(),
            history.getWorkshopAddress(),
            calculateTotalCostForWorkshop2(history),
            history.getWorkshopType()
        );
    }
}

return mostExpensiveWorkshop;
}

private double calculateTotalCostForWorkshop2(ServiceHistory history) {
    // Assuming ServiceHistory has a method getCost that returns the cost of the history
    return Double.parseDouble(history.getCost());
}

private double calculateTotalCostForWorkshop(ServiceEvent event) {
    // Assuming ServiceEvent has a method getCost that returns the cost of the event
    return event.getCost();
}


private void showAlert3(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
}
}