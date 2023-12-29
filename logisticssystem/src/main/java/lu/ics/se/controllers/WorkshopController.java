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
    private Button addWorkshopButton;
    @FXML
    private Button editWorkshopButton;
    @FXML
    private Button deleteWorkshopButton;

    

    private WorkshopList workshopList;
    // The value of the field WorkshopController.vehicleManifest is not used
    // If you don't need to use the vehicleManifest field, you can remove it to avoid the warning
    // If you do need to use it, make sure to utilize it in your code


    // Remove the unused field
    // private VehicleManifest vehicleManifest;

    public WorkshopController(VehicleManifest vehicleManifest, WorkshopList workshopList) {
      
        this.workshopList = workshopList;
    }

    @FXML
    private void initialize() {
        // Initialize workshop table
        workshopNameColumn.setCellValueFactory(new PropertyValueFactory<>("workshopName"));
        workshopAddressColumn.setCellValueFactory(new PropertyValueFactory<>("workshopAddress"));
        workshopTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        workshopTable.setItems(getWorkshopData());
    }

    // Remove the duplicate method declaration
    // private ObservableList<Workshop> getWorkshopData() {
    //     return FXCollections.observableArrayList(workshopList.getAllWorkshops());
    // }

   @FXML
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
clearWorkshopColumns();
}

private void clearWorkshopColumns() {
}

private void showAlert(String string, String string2) {
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
TableView<ServiceEvent> maintenanceTableView = new TableView<>(); // Declare and initialize maintenanceTableView

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
}
