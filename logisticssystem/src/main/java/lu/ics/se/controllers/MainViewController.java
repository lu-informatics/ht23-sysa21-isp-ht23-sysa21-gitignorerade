package lu.ics.se.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import java.net.URL;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import java.io.IOException;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lu.ics.se.models.classes.*;
import lu.ics.se.models.enums.*;
import java.util.ResourceBundle;
import lu.ics.se.Main;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableRow;
import javafx.scene.control.MenuItem;
import javafx.beans.binding.Bindings;
import java.time.LocalDate;
import javafx.scene.control.CheckBox;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.ObservableList;

public class MainViewController implements Initializable {

    @FXML
    private Button vehicleAddButton;

    @FXML
    private CheckBox vehicleIdCheckBox;

    @FXML
    private CheckBox brandCheckBox;

    @FXML
    private CheckBox capacityCheckBox;

    @FXML
    private CheckBox classCheckBox;

    @FXML
    private CheckBox scheduledMaintenanceCheckBox;

    @FXML
    private CheckBox lastMaintenanceCheckBox;

    @FXML
    private CheckBox locationCheckBox;

    @FXML
    private CheckBox vehicleNameCheckBox;

    @FXML
    private TableView<Vehicle> vehicleTable;

    @FXML
    private TableColumn<Vehicle, String> vehicleColumnID;

    @FXML
    private TableColumn<Vehicle, String> vehicleColumnBrand;

    @FXML
    private TableColumn<Vehicle, String> vehicleColumnCapacity;

    @FXML
    private TableColumn<Vehicle, String> vehicleColumnClass;

    @FXML
    private TableColumn<Vehicle, String> vehicleColumnLocation;

    @FXML
    private TableColumn<Vehicle, LocalDate> vehicleColumnLastMaintenance;

    @FXML
    private TableColumn<Vehicle, LocalDate> vehicleColumnScheduledMaintenance;

    @FXML
    private TableColumn<Vehicle, String> vehicleColumnName;

    @FXML
    public void handleVehicleAddButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addvehicle.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add new vehicle");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void updateColumnWidths() {
        ObservableList<TableColumn<Vehicle, ?>> columns = vehicleTable.getColumns();

        DoubleBinding columnWidth = vehicleTable.widthProperty().divide(columns.size());

        for (TableColumn<Vehicle, ?> column : columns) {
            column.prefWidthProperty().bind(columnWidth);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vehicleColumnID.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleIdentificationNumber"));
        vehicleColumnBrand.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleBrand"));
        vehicleColumnCapacity.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("capacityinKg"));
        vehicleColumnClass.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleClass"));
        vehicleColumnLocation.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("location"));
        vehicleColumnLastMaintenance
                .setCellValueFactory(new PropertyValueFactory<Vehicle, LocalDate>("lastMaintenance"));
        vehicleColumnScheduledMaintenance
                .setCellValueFactory(new PropertyValueFactory<Vehicle, LocalDate>("scheduledMaintenance"));
        vehicleColumnName.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleName"));
        vehicleTable.setItems(Main.companyVehicleManifest.getVehicleManifest());

        vehicleTable.getColumns().remove(vehicleColumnLocation);
        vehicleTable.getColumns().remove(vehicleColumnLastMaintenance);
        vehicleTable.getColumns().remove(vehicleColumnScheduledMaintenance);
        vehicleTable.getColumns().remove(vehicleColumnName);

        updateColumnWidths();

        vehicleIdCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (!vehicleTable.getColumns().contains(vehicleColumnID)) {
                    vehicleTable.getColumns().add(vehicleColumnID);
                }
            } else {
                vehicleTable.getColumns().remove(vehicleColumnID);
            }
            updateColumnWidths();
        });
        brandCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (!vehicleTable.getColumns().contains(vehicleColumnBrand)) {
                    vehicleTable.getColumns().add(vehicleColumnBrand);
                }
            } else {
                vehicleTable.getColumns().remove(vehicleColumnBrand);
            }
            updateColumnWidths();
        });
        capacityCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (!vehicleTable.getColumns().contains(vehicleColumnCapacity)) {
                    vehicleTable.getColumns().add(vehicleColumnCapacity);
                }
            } else {
                vehicleTable.getColumns().remove(vehicleColumnCapacity);
            }
            updateColumnWidths();
        });
        classCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (!vehicleTable.getColumns().contains(vehicleColumnClass)) {
                    vehicleTable.getColumns().add(vehicleColumnClass);
                }
            } else {
                vehicleTable.getColumns().remove(vehicleColumnClass);
            }
            updateColumnWidths();
        });
        scheduledMaintenanceCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (!vehicleTable.getColumns().contains(vehicleColumnScheduledMaintenance)) {
                    vehicleTable.getColumns().add(vehicleColumnScheduledMaintenance);
                }
            } else {
                vehicleTable.getColumns().remove(vehicleColumnScheduledMaintenance);
            }
            updateColumnWidths();
        });
        lastMaintenanceCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (!vehicleTable.getColumns().contains(vehicleColumnLastMaintenance)) {
                    vehicleTable.getColumns().add(vehicleColumnLastMaintenance);
                }
            } else {
                vehicleTable.getColumns().remove(vehicleColumnLastMaintenance);
            }
            updateColumnWidths();
        });
        locationCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (!vehicleTable.getColumns().contains(vehicleColumnLocation)) {
                    vehicleTable.getColumns().add(vehicleColumnLocation);
                }
            } else {
                vehicleTable.getColumns().remove(vehicleColumnLocation);
            }
            updateColumnWidths();
        });
        vehicleNameCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (!vehicleTable.getColumns().contains(vehicleColumnName)) {
                    vehicleTable.getColumns().add(vehicleColumnName);
                }
            } else {
                vehicleTable.getColumns().remove(vehicleColumnName);
            }
            updateColumnWidths();
        });

        vehicleTable.setRowFactory(tv -> {
            TableRow<Vehicle> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem editItem = new MenuItem("Edit Vehicle");
            editItem.setOnAction(event -> {
                try {
                    // Handle edit vehicle here
                    Vehicle vehicle = row.getItem();

                    Stage stage = new Stage();
                    stage.setTitle("Vehicle Editor");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editvehicle.fxml"));
                    Parent root = loader.load();

                    stage.setScene(new Scene(root));
                    stage.show();

                    EditVehicleController controller = loader.getController();
                    controller.setVehicle(vehicle);

                    controller.setOnCloseListener(() -> {
                        vehicleTable.refresh();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            MenuItem removeItem = new MenuItem("Remove Vehicle");
            removeItem.setOnAction(event -> {
                try {
                    Vehicle vehicle = row.getItem();

                    Stage stage = new Stage();
                    stage.setTitle("Vehicle Remover");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/removevehicle.fxml"));
                    Parent root = loader.load();

                    stage.setScene(new Scene(root));
                    stage.show();

                    RemoveVehicleController controller = loader.getController();
                    controller.setVehicle(vehicle);

                    controller.setOnCloseListener(() -> {
                        vehicleTable.refresh();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            MenuItem scheduleMaintenanceItem = new MenuItem("Handle Maintenance");
            scheduleMaintenanceItem.setOnAction(event -> {
                Vehicle vehicle = row.getItem();
                // Handle schedule maintenance here
            });

            MenuItem setLocation = new MenuItem("Set Location");
            setLocation.setOnAction(event -> {
                try {
                    Vehicle vehicle = row.getItem();

                    Stage stage = new Stage();
                    stage.setTitle("Location Picker");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/setlocation.fxml"));
                    Parent root = loader.load();

                    stage.setScene(new Scene(root));
                    stage.show();

                    SetLocationController controller = loader.getController();
                    controller.setVehicle(vehicle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            contextMenu.getItems().addAll(editItem, removeItem, scheduleMaintenanceItem, setLocation);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu));

            return row;
        });
    }
}
