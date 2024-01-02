package lu.ics.se.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private Button clearWorkshopList;

    @FXML
    private Button addWorkshopButton;
    @FXML
    private Button editWorkshopButton;
    @FXML
    private Button deleteWorkshopButton;

    @FXML
    private TableView<ServiceEvent> maintenanceTableView;

    @FXML
    private TableView<ServiceHistory> serviceHistoryTableView;

    

    private WorkshopList workshopList;
    // The value of the field WorkshopController.vehicleManifest is not used
    // If you don't need to use the vehicleManifest field, you can remove it to avoid the warning
    // If you do need to use it, make sure to utilize it in your code


    // Remove the unused field
    // private VehicleManifest vehicleManifest;

    public WorkshopController(VehicleManifest vehicleManifest, WorkshopList workshopList) {
      
        this.workshopList = workshopList;
    }
    
        public WorkshopController() {
            // constructor body
        }
    
        // rest of the class
    

    @FXML
    public void initialize() {

        initializeWorkshopList();
        // Initialize workshop table
        workshopNameColumn.setCellValueFactory(new PropertyValueFactory<>("workshopName"));
        workshopAddressColumn.setCellValueFactory(new PropertyValueFactory<>("workshopAddress"));
        workshopTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        workshopTable.setItems(getWorkshopData());
    }

    @FXML
    public void clearWorkshopList(ActionEvent event) {
        // Show confirmation dialog
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Clear");
        confirmationAlert.setHeaderText("Are you sure you want to clear the workshop list?");
        confirmationAlert.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User confirmed clearing the workshop list
            workshopList.clearWorkshopList();
            workshopTable.setItems(getWorkshopData());
        }
    }
    private void clearWorkshopColumns() {
        nameField.clear();
        addressField.clear();
        typeField.clear();
    }

    
    private void initializeWorkshopList() {
        // Initialize workshopList here. This might involve creating a new instance
        // or fetching it from another source depending on your application's design.
        workshopList = new WorkshopList();

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
public void handleDeleteWorkshop(ActionEvent event) {
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
public void handleEditWorkshop(ActionEvent event) {
Workshop selectedWorkshop = workshopTable.getSelectionModel().getSelectedItem();

if (selectedWorkshop != null) {
    openEditWorkshopDialog(selectedWorkshop);
} else {
    showAlert("Error", "Please select a workshop to edit.");
}
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

    // If your TableView is bound to an ObservableList, this line might not be necessary
    // workshopTable.setItems(getWorkshopData());

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