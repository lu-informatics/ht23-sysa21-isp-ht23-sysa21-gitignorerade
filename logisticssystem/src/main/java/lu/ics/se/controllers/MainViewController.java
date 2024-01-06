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
import javafx.scene.control.TableCell;

public class MainViewController implements Initializable {

    @FXML
    private Button vehicleAddButton;

    @FXML
    private Button workshopAddButton;

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
    private CheckBox primaryWorkshopCheckBox;

    @FXML
    private TableView<Vehicle> vehicleTable;

    @FXML
    private TableView<Workshop> workshopTable;

    @FXML
    private TableColumn<Workshop, String> workshopColumnName;

    @FXML
    private TableColumn<Workshop, Locations> workshopColumnLocation;

    @FXML
    private TableColumn<Workshop, Boolean> workshopColumnInternal;

    @FXML
    private TableColumn<Vehicle, String> vehicleColumnID;

    @FXML
    private TableColumn<Vehicle, String> vehicleColumnBrand;

    @FXML
    private TableColumn<Vehicle, Workshop> vehicleColumnPrimaryWorkshop;

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
    public void handleWorkshopAddButtonAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addworkshop.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add new workshop");
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

    private void setupCheckBoxListener(CheckBox checkBox, TableColumn<Vehicle, ?> column) {
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (!vehicleTable.getColumns().contains(column)) {
                    vehicleTable.getColumns().add(column);
                }
            } else {
                vehicleTable.getColumns().remove(column);
            }
            updateColumnWidths();
        });
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
        workshopColumnInternal.setCellValueFactory(new PropertyValueFactory<Workshop, Boolean>("isInternal"));
        vehicleColumnPrimaryWorkshop.setCellValueFactory(new PropertyValueFactory<Vehicle, Workshop>("primaryWorkshop"));
        workshopColumnInternal.setCellFactory(column -> {
            return new TableCell<Workshop, Boolean>() {
                @Override
                protected void updateItem(Boolean isInternal, boolean empty) {
                    super.updateItem(isInternal, empty);
                    if (isInternal == null || empty) {
                        setText(null);
                    } else if (isInternal) {
                        setText("Internal Workshop");
                    } else {
                        setText("External Workshop");
                    }
                }
            };
        });
        workshopColumnName.setCellValueFactory(new PropertyValueFactory<Workshop, String>("workshopName"));
        workshopColumnLocation.setCellValueFactory(new PropertyValueFactory<Workshop, Locations>("workshopLocation"));


        workshopTable.setItems(Main.companyWorkshopList.getWorkshopList());
        vehicleTable.setItems(Main.companyVehicleManifest.getVehicleManifest());


        vehicleTable.getColumns().remove(vehicleColumnLocation);
        vehicleTable.getColumns().remove(vehicleColumnLastMaintenance);
        vehicleTable.getColumns().remove(vehicleColumnScheduledMaintenance);
        vehicleTable.getColumns().remove(vehicleColumnName);
        vehicleTable.getColumns().remove(vehicleColumnPrimaryWorkshop);


        updateColumnWidths();
        setupCheckBoxListener(brandCheckBox, vehicleColumnBrand);
        setupCheckBoxListener(capacityCheckBox, vehicleColumnCapacity);
        setupCheckBoxListener(classCheckBox, vehicleColumnClass);
        setupCheckBoxListener(locationCheckBox, vehicleColumnLocation);
        setupCheckBoxListener(lastMaintenanceCheckBox, vehicleColumnLastMaintenance);
        setupCheckBoxListener(scheduledMaintenanceCheckBox, vehicleColumnScheduledMaintenance);
        setupCheckBoxListener(vehicleNameCheckBox, vehicleColumnName);
        setupCheckBoxListener(vehicleIdCheckBox, vehicleColumnID);
        setupCheckBoxListener(primaryWorkshopCheckBox, vehicleColumnPrimaryWorkshop);


        vehicleTable.setRowFactory(tv -> {
            TableRow<Vehicle> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem editItem = new MenuItem("Edit Vehicle");
            editItem.setOnAction(event -> {
                try {
                    
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

            MenuItem scheduleMaintenanceItem = new MenuItem("Access Maintenance History and" + "\n" +  "Schedule Maintenance");
            scheduleMaintenanceItem.setOnAction(event -> {
                try{
                Vehicle vehicle = row.getItem();
                
                Stage stage = new Stage();
                stage.setTitle("Maintenance History and Schedule Maintenance");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/servicehistoryaccesser.fxml"));
                Parent root = loader.load();

                stage.setScene(new Scene(root));
                stage.show();

                ServiceHistoryAccesserController controller = loader.getController();
                controller.setVehicle(vehicle);

                controller.setOnCloseListener(() -> {
                        vehicleTable.refresh();
                    });

            } catch (Exception e) {
                e.printStackTrace();
            }
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

                    controller.setOnCloseListener(() -> {
                        vehicleTable.refresh();
                    });
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
