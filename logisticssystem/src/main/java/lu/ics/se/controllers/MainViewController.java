package lu.ics.se.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import javafx.scene.control.Alert;
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
import java.util.ArrayList;
import java.util.List;

public class MainViewController implements Initializable {

    @FXML
    private Button vehicleAddButton;

    @FXML
    private Button workshopAddButton;

    @FXML
    private Button averageMaintenanceCostButton;

    @FXML
    private Button displayServiceHistoryForAllButton;

    @FXML
    private Button mostExpensiveMaintenanceButton;

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
    private CheckBox totalCostCheckBox;

    @FXML
    private CheckBox partsReplacedCheckBox;

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
    private TableColumn<Workshop, Integer> workshopColumnTotalCost;

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
    private TableColumn<Vehicle, Integer> vehicleColumnTotalCostOfService;

    @FXML
    private TableColumn<Vehicle, Integer> vehicleColumnPartsReplaced;

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

    public void handleAverageMaintenanceCostButton() {
        try {
            int averageMaintenanceCost = 0;
            int totalCostOfAllMaintenance = 0;
            int totalNumberOfMaintenanceEvents = 0;
            for (Vehicle vehicle : Main.companyVehicleManifest.getVehicleManifest()) {
                for (ServiceEvents event : vehicle.getServiceHistory().getServiceHistory()) {
                    totalCostOfAllMaintenance += event.getTotalCostOfService();
                    totalNumberOfMaintenanceEvents++;
                }
            }
            averageMaintenanceCost = totalCostOfAllMaintenance / totalNumberOfMaintenanceEvents;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Average Maintenance Cost");
            alert.setHeaderText("The average maintenance cost has been calculated!");
            alert.setContentText("The average maintenance cost is:" + "\n" + averageMaintenanceCost + "SEK");
            alert.showAndWait();
        } catch (ArithmeticException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Average Maintenance Cost");
            alert.setHeaderText("The average maintenance cost has been calculated!");
            alert.setContentText("There are no maintenance events to calculate the average maintenance cost from");
            alert.showAndWait();
        }
    }

    public void handleMostExpensiveMaintenanceButton() {
        try {
            int mostExpensiveMaintenance = 0;
            ServiceEvents mostExpensiveMaintenanceEvent = null;
            for (ServiceEvents event : Main.companyServiceHistory.getServiceHistory()) {
                if (event.getTotalCostOfService() > mostExpensiveMaintenance) {
                    mostExpensiveMaintenance = event.getTotalCostOfService();
                    mostExpensiveMaintenanceEvent = event;
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Most Expensive Maintenance Event");
            alert.setHeaderText("The most expensive maintenance" + "\n" + "event was carried out by: "
                    + mostExpensiveMaintenanceEvent.getWorkshop().getWorkshopName() + " On "
                    + mostExpensiveMaintenanceEvent.getVehicleServiced().getVehicleName() + "\n" + "On "
                    + mostExpensiveMaintenanceEvent.getEventDate() + "\n"
                    + "The total cost of the maintenance event was: "
                    + mostExpensiveMaintenanceEvent.getTotalCostOfService() + "SEK");
            TableView<ServiceAction> table = new TableView<>();

            table.setItems(FXCollections.observableArrayList(mostExpensiveMaintenanceEvent.getServiceActions()));

            TableColumn<ServiceAction, String> serviceActionDescriptionColumn = new TableColumn<>("Description");
            TableColumn<ServiceAction, Integer> serviceActionCostColumn = new TableColumn<>("Cost");
            TableColumn<ServiceAction, Integer> serviceActionPartsReplacedColumn = new TableColumn<>("Parts Replaced");

            serviceActionDescriptionColumn.prefWidthProperty().bind(table.widthProperty().divide(3));
            serviceActionCostColumn.prefWidthProperty().bind(table.widthProperty().divide(3));
            serviceActionPartsReplacedColumn.prefWidthProperty().bind(table.widthProperty().divide(3));

            List<TableColumn<ServiceAction, ?>> columns = new ArrayList<>();
            columns.add(serviceActionDescriptionColumn);
            columns.add(serviceActionCostColumn);
            columns.add(serviceActionPartsReplacedColumn);

            table.getColumns().addAll(columns);

            serviceActionDescriptionColumn
                    .setCellValueFactory(new PropertyValueFactory<ServiceAction, String>("actionDescription"));
            serviceActionCostColumn.setCellValueFactory(new PropertyValueFactory<ServiceAction, Integer>("totalCost"));
            serviceActionPartsReplacedColumn
                    .setCellValueFactory(new PropertyValueFactory<ServiceAction, Integer>("numberOfPartsReplaced"));

            alert.getDialogPane().setContent(table);
            alert.showAndWait();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Most Expensive Maintenance Event");
            alert.setHeaderText(
                    "There are no maintenance events to calculate the most expensive maintenance event from");
            alert.showAndWait();
        }
    }

    public void handleDisplayServiceHistoryForAllButton() {
        try {
            Stage stage = new Stage();
            stage.setTitle("Service History for all vehicles");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/companyservicehistoryaccesser.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.show();

            CompanyServiceHistoryAccesserController controller = loader.getController();
            controller.setTableRefreshListener(() -> {
                vehicleTable.refresh();
                workshopTable.refresh();
            });
        } catch (Exception e) {
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
        vehicleColumnPrimaryWorkshop
                .setCellValueFactory(new PropertyValueFactory<Vehicle, Workshop>("primaryWorkshop"));
        vehicleColumnTotalCostOfService
                .setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("totalCostOfService"));
        vehicleColumnPartsReplaced
                .setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("totalPartsReplaced"));
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
        workshopColumnTotalCost
                .setCellValueFactory(new PropertyValueFactory<Workshop, Integer>("totalCostOfAllService"));

        workshopTable.setItems(Main.companyWorkshopList.getWorkshopList());
        vehicleTable.setItems(Main.companyVehicleManifest.getVehicleManifest());

        vehicleTable.getColumns().remove(vehicleColumnLocation);
        vehicleTable.getColumns().remove(vehicleColumnLastMaintenance);
        vehicleTable.getColumns().remove(vehicleColumnScheduledMaintenance);
        vehicleTable.getColumns().remove(vehicleColumnName);
        vehicleTable.getColumns().remove(vehicleColumnPrimaryWorkshop);
        vehicleTable.getColumns().remove(vehicleColumnTotalCostOfService);
        vehicleTable.getColumns().remove(vehicleColumnPartsReplaced);

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
        setupCheckBoxListener(totalCostCheckBox, vehicleColumnTotalCostOfService);
        setupCheckBoxListener(partsReplacedCheckBox, vehicleColumnPartsReplaced);

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
                        workshopTable.refresh();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            MenuItem scheduleMaintenanceItem = new MenuItem(
                    "Access Maintenance History and" + "\n" + "Schedule Maintenance");
            scheduleMaintenanceItem.setOnAction(event -> {
                try {
                    Vehicle vehicle = row.getItem();

                    Stage stage = new Stage();
                    stage.setTitle("Maintenance History and Schedule Maintenance");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/servicehistoryaccesser.fxml"));
                    Parent root = loader.load();

                    stage.setScene(new Scene(root));
                    stage.show();

                    ServiceHistoryAccesserController controller = loader.getController();
                    controller.setVehicle(vehicle);
                    controller.updateUI();

                    controller.setOnCloseListener(() -> {
                        vehicleTable.refresh();
                        workshopTable.refresh();
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
                        workshopTable.refresh();
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
        workshopTable.setRowFactory(tv -> {
            TableRow<Workshop> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem viewServiceHistory = new MenuItem("View Service History");

            viewServiceHistory.setOnAction(event -> {
                try {
                    Workshop workshop = row.getItem();

                    Stage stage = new Stage();
                    stage.setTitle("Service History");

                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/fxml/workshopservicehistoryaccesser.fxml"));
                    Parent root = loader.load();

                    stage.setScene(new Scene(root));
                    stage.show();

                    WorkshopServiceHistoryAccesserController controller = loader.getController();
                    controller.setWorkshop(workshop);
                    controller.updateUI();
                    controller.setTableRefreshListener(() -> {
                        vehicleTable.refresh();
                        workshopTable.refresh();
                    });
                    

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            contextMenu.getItems().addAll(viewServiceHistory);
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu));
            return row;

        });

    }
}
