package lu.ics.se.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import java.io.IOException;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
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
import java.util.Optional;

public class MainViewController implements Initializable {

    // Define all the FXML elements here

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
    private Button calculateAllMaintenanceCostButton;

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

    // Code is run when the add vehicle button is pressed.
    // This loads the addvehicle.fxml file and opens it in a new window.

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

    // Code is run when the add workshop button is pressed.
    // This loads the addworkshop.fxml file and opens it in a new window.

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

    // Code is run when the calculate all maintenance cost button is pressed.

    public void handleCalculateAllMaintenanceCostButton() {
        try {

            // This iterates over the service history for all vehicles in the system
            // and adds the total cost of all maintenance events to the
            // totalCostOfAllMaintenance variable.

            int totalCostOfAllMaintenance = 0;
            for (Vehicle vehicle : Main.companyVehicleManifest.getVehicleManifest()) {
                for (ServiceEvents event : vehicle.getServiceHistory().getServiceHistory()) {
                    totalCostOfAllMaintenance += event.getTotalCostOfService();
                }
            }

            // This displays the total cost of all maintenance events in a popup window.

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Total Maintenance Cost");
            alert.setHeaderText("The total maintenance cost has been calculated!");
            alert.setContentText("The total maintenance cost is:" + "\n" + totalCostOfAllMaintenance + "SEK");
            alert.showAndWait();

            // This catches the NullPointerException that is thrown if there are no
            // maintenance events to calculate the total maintenance cost from.
            // And displays a popup window with an error message.

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Total Maintenance Cost");
            alert.setHeaderText("The total maintenance cost has been calculated!");
            alert.setContentText("There are no maintenance events to calculate the total maintenance cost from");
            alert.showAndWait();
        }
    }
    // This code is run when the average maintenance cost button is pressed.

    public void handleAverageMaintenanceCostButton() {
        try {

            // This iterates over the service history for all vehicles in the system
            // and stores the total cost and total number of maintenance events in
            // variables.
            // It then calculates the average maintenance cost and stores it in the
            // averageMaintenanceCost variable.

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

            // This displays the average maintenance cost in a popup window.

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Average Maintenance Cost");
            alert.setHeaderText("The average maintenance cost has been calculated!");
            alert.setContentText("The average maintenance cost is:" + "\n" + averageMaintenanceCost + "SEK");
            alert.showAndWait();

            // This catches the ArithmeticException that is thrown if there are no
            // maintenance events to calculate the average maintenance cost from.
            // And displays a popup window with an error message.
            // Otherwise a division by 0 exception would be thrown.

        } catch (ArithmeticException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Average Maintenance Cost");
            alert.setHeaderText("The average maintenance cost has not been calculated!");
            alert.setContentText("There are no maintenance events to calculate the average maintenance cost from");
            alert.showAndWait();
        }
    }

    // This code is run when the most expensive maintenance button is pressed.
    // It iterates over the service history for all vehicles in the system and
    // checks each maintenance event to see if it is more expensive than the
    // previous most expensive maintenance event.
    // If it is, it stores the new most expensive maintenance event in the
    // mostExpensiveMaintenanceEvent variable.
    // It then displays the most expensive maintenance event in a popup window.

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

            // This creates a table view that takes service actions.

            TableView<ServiceAction> table = new TableView<>();

            // This sets the tableview to take the service actions from the most expensive
            // maintenance event.

            table.setItems(FXCollections.observableArrayList(mostExpensiveMaintenanceEvent.getServiceActions()));

            // This creates the columns for the table view.

            TableColumn<ServiceAction, String> serviceActionDescriptionColumn = new TableColumn<>("Description");
            TableColumn<ServiceAction, Integer> serviceActionCostColumn = new TableColumn<>("Cost");
            TableColumn<ServiceAction, Integer> serviceActionPartsReplacedColumn = new TableColumn<>("Parts Replaced");

            // This ensures that the column width is exactly 1/3 of the table view width.

            serviceActionDescriptionColumn.prefWidthProperty().bind(table.widthProperty().divide(3));
            serviceActionCostColumn.prefWidthProperty().bind(table.widthProperty().divide(3));
            serviceActionPartsReplacedColumn.prefWidthProperty().bind(table.widthProperty().divide(3));

            // This adds the columns to a list and then adds the columns to the table view.

            List<TableColumn<ServiceAction, ?>> columns = new ArrayList<>();
            columns.add(serviceActionDescriptionColumn);
            columns.add(serviceActionCostColumn);
            columns.add(serviceActionPartsReplacedColumn);

            table.getColumns().addAll(columns);

            // This sets the cell value factory for each column. The cell value factory
            // determines what data is displayed in each column.
            // It does this by calling the get method for the corresponding property in the
            // ServiceAction class.

            serviceActionDescriptionColumn
                    .setCellValueFactory(new PropertyValueFactory<ServiceAction, String>("actionDescription"));
            serviceActionCostColumn.setCellValueFactory(new PropertyValueFactory<ServiceAction, Integer>("totalCost"));
            serviceActionPartsReplacedColumn
                    .setCellValueFactory(new PropertyValueFactory<ServiceAction, Integer>("numberOfPartsReplaced"));

            // This displays the table view in the popup window. And makes the popup window
            // wait for the user to close it before continuing.

            alert.getDialogPane().setContent(table);
            alert.showAndWait();

            // This catches the NullPointerException that is thrown if there are no
            // maintenance events to calculate the most expensive maintenance event from.
            // And displays a popup window with an error message.

        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Most Expensive Maintenance Event");
            alert.setHeaderText(
                    "There are no maintenance events to calculate the most expensive maintenance event from");
            alert.showAndWait();
        }
    }

    // This code is run when the display service history for all button is pressed.
    // It loads the companyservicehistoryaccesser.fxml file and opens it in a new
    // window.

    public void handleDisplayServiceHistoryForAllButton() {
        try {
            Stage stage = new Stage();
            stage.setTitle("Service History for all vehicles");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/companyservicehistoryaccesser.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.show();

            // This sets up a listener in the companyservicehistoryaccessercontroller class
            // that refreshes the table views
            // in the mainviewcontroller class when the
            // companyservicehistoryaccessercontroller class calls the refresh listener.

            CompanyServiceHistoryAccesserController controller = loader.getController();
            controller.setTableRefreshListener(() -> {
                vehicleTable.refresh();
                workshopTable.refresh();
            });

            // This catches the IOException that is thrown if the
            // companyservicehistoryaccesser.fxml file cannot be loaded.

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // This defines the method that is used to update the column widths for the
    // vehicle table view.
    // It is used to ensure that the columns are set to the correct width when the
    // user selects or deselects columns to display.

    private void updateColumnWidths() {
        ObservableList<TableColumn<Vehicle, ?>> columns = vehicleTable.getColumns();

        DoubleBinding columnWidth = vehicleTable.widthProperty().divide(columns.size());

        for (TableColumn<Vehicle, ?> column : columns) {
            column.prefWidthProperty().bind(columnWidth);
        }
    }

    // This defines the method that is used to setup the checkbox listener for the
    // vehicle table view.
    // It is used to ensure that the columns are displayed or hidden when the user
    // selects or deselects columns to display.

    private void setupCheckBoxListener(CheckBox checkBox, TableColumn<Vehicle, ?> column) {
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {

            // This checks if the checkbox is selected or not and adds or removes the column
            // from the table view accordingly.

            if (newValue) {
                if (!vehicleTable.getColumns().contains(column)) {
                    vehicleTable.getColumns().add(column);
                }
            } else {
                vehicleTable.getColumns().remove(column);
            }
            // Once the column has been added or removed from the table view,
            // the updateColumnWidths method is called to ensure that the columns are set to
            // the correct width.

            updateColumnWidths();
        });
    }

    @Override
    // This code is run when the mainview.fxml file is loaded.

    public void initialize(URL url, ResourceBundle rb) {

        // This sets up the value to be displayed in the vehicle and workshop table
        // views.

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

        // This code ensures that the workshop internal column displays the text
        // Internal Workshop or External Workshop
        // Instead of true or false.

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

        // This sets up the rest of the values in the workshop table columns.

        workshopColumnName.setCellValueFactory(new PropertyValueFactory<Workshop, String>("workshopName"));
        workshopColumnLocation.setCellValueFactory(new PropertyValueFactory<Workshop, Locations>("workshopLocation"));
        workshopColumnTotalCost
                .setCellValueFactory(new PropertyValueFactory<Workshop, Integer>("totalCostOfAllService"));

        // This tells the vehicle table view to populate the table view with the data
        // from the vehicle manifest in main.
        // And tells the workshop table view to populate the table view with the data
        // from the workshop list in main.

        workshopTable.setItems(Main.companyWorkshopList.getWorkshopList());
        vehicleTable.setItems(Main.companyVehicleManifest.getVehicleManifest());

        // This removes the columns that are unchecked from the vehicle table view.

        vehicleTable.getColumns().remove(vehicleColumnLocation);
        vehicleTable.getColumns().remove(vehicleColumnLastMaintenance);
        vehicleTable.getColumns().remove(vehicleColumnScheduledMaintenance);
        vehicleTable.getColumns().remove(vehicleColumnName);
        vehicleTable.getColumns().remove(vehicleColumnPrimaryWorkshop);
        vehicleTable.getColumns().remove(vehicleColumnTotalCostOfService);
        vehicleTable.getColumns().remove(vehicleColumnPartsReplaced);

        // This calls the updateColumnWidths method to ensure that the columns are set
        // to the correct width.
        // And then sets up the checkbox listener for each checkbox in the vehicle table
        // view and links it to the corresponding column.

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

        // This sets up the context menu for the vehicle and workshop table views.
        // The context menu is the menu that appears when the user right clicks on a row
        // in the table view.

        vehicleTable.setRowFactory(tv -> {
            TableRow<Vehicle> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            // This defines the contextmenu item edit vehicle and sets up the code that is
            // run when the user clicks on it.
            // It loads the editvehicle.fxml file and opens it in a new window.

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

                    // This sets the vehicle to be edited in the editvehicle.fxml file.
                    EditVehicleController controller = loader.getController();
                    controller.setVehicle(vehicle);

                    // This sets up a listener in the editvehiclecontroller class that refreshes the
                    // table views when
                    // the editvehiclecontroller class calls the refresh listener.

                    controller.setOnCloseListener(() -> {
                        vehicleTable.refresh();
                    });

                    // This catches the IOException that is thrown if the editvehicle.fxml file
                    // cannot be loaded.

                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

            // This defines the contextmenu item remove vehicle and sets up the code that is
            // run when the user clicks on it.
            // It loads the removevehicle.fxml file and opens it in a new window.

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

                    // This sets the vehicle to be removed in the removevehicle.fxml file.

                    RemoveVehicleController controller = loader.getController();
                    controller.setVehicle(vehicle);

                    // This sets up a listener in the removevehiclecontroller class that refreshes
                    // the table views when
                    // the removevehiclecontroller class calls the refresh listener.

                    controller.setOnCloseListener(() -> {
                        vehicleTable.refresh();
                        workshopTable.refresh();
                    });

                    // This catches the IOException that is thrown if the removevehicle.fxml file
                    // cannot be loaded.

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // This defines the contextmenu item schedule maintenance and sets up the code
            // that is run when the user clicks on it.
            // It loads the servicehistoryaccesser.fxml file and opens it in a new window.

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

                    //This sets the vehicle that is being accessed in the servicehistoryaccesser.fxml file.
                    //It also calls on the updateUI method in the servicehistoryaccessercontroller class which ensures
                    //that the table view is populated with the correct data.
                    //It also sets up two listeners in the servicehistoryaccessercontroller class.
                    //Both do the same thing but one is meant to be called when the user closes the window
                    //and the other is meant to be called when the servicehistoryaccessercontroller calls it.


                    ServiceHistoryAccesserController controller = loader.getController();
                    controller.setVehicle(vehicle);
                    controller.updateUI();
                    controller.setUpdateUIListener(() -> {
                        vehicleTable.refresh();
                        workshopTable.refresh();
                    });

                    controller.setOnCloseListener(() -> {
                        vehicleTable.refresh();
                        workshopTable.refresh();
                    });

                    // This catches the IOException that is thrown if the servicehistoryaccesser.fxml file is
                    // unable to be loaded.

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // This defines the contextmenu item set location and sets up the code that is
            // run when the user clicks on it.
            // It loads the setlocation.fxml file and opens it in a new window.

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

                    //This sets the vehicle that is being accessed in the setlocation.fxml file.
                    //It also sets up a listener in the setlocationcontroller class that refreshes the table views in the
                    //mainviewcontroller when the setlocationcontroller class calls the refresh listener by being cloesd.

                    SetLocationController controller = loader.getController();
                    controller.setVehicle(vehicle);

                    controller.setOnCloseListener(() -> {
                        vehicleTable.refresh();
                        workshopTable.refresh();
                    });

                    //This catches the IOException that is thrown if the setlocation.fxml file is unable to be loaded.

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            //This adds all the defined contextmenu items to the contextmenu.

            contextMenu.getItems().addAll(editItem, removeItem, scheduleMaintenanceItem, setLocation);

            //This binds the contextmenu to the row. And ensures that the contextmenu is only displayed when the clicked row is not empty.

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu));
            return row;
        });

        //This sets up the context menu for the workshop table view.

        workshopTable.setRowFactory(tv -> {
            TableRow<Workshop> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();
            
            //This defines the context menu item view service history and sets up the code that is run when the user clicks on it.
            //It loads the workshopservicehistoryaccesser.fxml file and opens it in a new window.

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

                    //This sets the workshop that is being accessed in the workshopservicehistoryaccesser.fxml file.
                    //It also calls on the updateUI method in the workshopservicehistoryaccessercontroller class which ensures
                    //that the table view is populated with the correct data.
                    //It also sets up a listener in the workshopservicehistoryaccessercontroller class that refreshes the table views in the
                    //mainviewcontroller when the workshopservicehistoryaccessercontroller class calls the refresh listener.

                    WorkshopServiceHistoryAccesserController controller = loader.getController();
                    controller.setWorkshop(workshop);
                    controller.updateUI();
                    controller.setTableRefreshListener(() -> {
                        vehicleTable.refresh();
                        workshopTable.refresh();
                    });
                    
                    //This catches the IOException that is thrown if the workshopservicehistoryaccesser.fxml file is unable to be loaded.

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            //This defines the context menu item delete workshop and sets up the code that is run when the user clicks on it.
            //This opens a confirmation dialog that asks the user if they are sure they want to delete the workshop.
            MenuItem deleteWorkshop = new MenuItem("Delete Workshop");

            deleteWorkshop.setOnAction(event -> {
                Workshop workshop = row.getItem();
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setHeaderText("Confirmation");
                alert.setContentText("Are you sure you want to delete the workshop?");
                alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

                //This sets the program to wait for the user to click on the yes or no button in the confirmation dialog.

                Optional<ButtonType> result = alert.showAndWait();

                //If the user clicks on the yes button the workshop is deleted from the workshop list in main. But only
                //if the workshop does not have any service events in its service history.
                //If it does have service events in its service history a warning dialog is displayed that tells the user
                //to remove all service events from the workshop before deleting it.

                if (result.isPresent() && result.get() == ButtonType.YES) {
                    if (workshop.getServiceHistory().getServiceHistory().isEmpty()) {
                        Main.companyWorkshopList.removeWorkshop(workshop);
                        workshopTable.refresh();
                    } else {
                        Alert warningAlert = new Alert(AlertType.WARNING,
                                "Remove all service events before removing the workshop", ButtonType.OK);
                        warningAlert.showAndWait();
                    }

                    //If the user clicks on the no button the confirmation dialog is closed. 
                    //And no further action is taken.

                } else if (result.isPresent() && result.get() == ButtonType.NO) {
                    alert.close();
                }
            });
            
            //This adds all the defined contextmenu items to the contextmenu.

            contextMenu.getItems().addAll(viewServiceHistory, deleteWorkshop);

            //This ensures that the contextmenu is only displayed when the clicked row is not empty.
            
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu));
            return row;

        });

    }
}
