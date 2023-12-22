
package lu.ics.se.controllers;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lu.ics.se.models.ServiceEvent;
import lu.ics.se.models.Vehicle;
import lu.ics.se.models.VehicleManifest;
import lu.ics.se.models.Workshop;
import lu.ics.se.models.WorkshopList;

import java.util.Optional;
import java.util.UUID;

public class VehicleController {

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

    private VehicleManifest vehicleManifest;

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

    public VehicleController() {
        this.vehicleManifest = new VehicleManifest();
        this.workshopList = new WorkshopList();
    }

    @FXML
    private TableColumn<Workshop, String> addressColumn;

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
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Invalid input", "Numeric values are expected for capacity.");
        }
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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setMaintenance(ServiceEvent selectedMaintenance) {
    }
}
