package lu.ics.se.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import lu.ics.se.Main;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import lu.ics.se.models.classes.Vehicle;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.time.LocalDate;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.DateCell;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Platform;
import lu.ics.se.models.classes.ServiceEvents;
import lu.ics.se.models.classes.Workshop;
import lu.ics.se.models.classes.ServiceHistory;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import lu.ics.se.models.enums.VehicleClass;
import java.util.stream.Collectors;
import java.io.IOException;
import lu.ics.se.models.classes.ServiceAction;
import javafx.scene.control.Alert;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


public class ServiceHistoryAccesserController implements Initializable {

    //This sets up the listener for the updateUI event. This listener is called by the controller that created this controller.

    public interface updateUIListener {
        void updateUI();
    }
    private updateUIListener updateUIListener;

    public void setUpdateUIListener(updateUIListener updateUIListener) {
        this.updateUIListener = updateUIListener;
    }

    //This method sets the vehicle that is to be edited. It is called by the controller that created this controller.

    private Vehicle vehicle;

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;

        //First the UI is updated with the vehicle's scheduled maintenance date.

        displayDateLabel.setText(vehicle.getScheduledMaintenance().toString());

        //This reads wether or not the vehicle is a large truck, if it is, it filters out the internal workshops from the choicebox.
        //If it is not, it adds all workshops to the choicebox.
        //It also sets the default value to vehicles primary workshop, if it has one.
        //If it does not, it sets the default value to the first workshop in the list.

        if (vehicle.getVehicleClass() == VehicleClass.Largetruck) {
            List<Workshop> filteredWorkshops = Main.companyWorkshopList.getWorkshopList().stream()
                    .filter(workshop -> !workshop.getIsInternal())
                    .collect(Collectors.toList());
            selectPrimaryWorkshopChoiceBox.getItems().addAll(filteredWorkshops);
            if (!filteredWorkshops.isEmpty()) {
                selectPrimaryWorkshopChoiceBox.setValue(filteredWorkshops.get(0));
            }} else {
                selectPrimaryWorkshopChoiceBox.getItems().addAll(Main.companyWorkshopList.getWorkshopList());
            }
            if (vehicle.getPrimaryWorkshop() != null) {
                selectPrimaryWorkshopChoiceBox.setValue(vehicle.getPrimaryWorkshop());
            } else if (vehicle.getVehicleClass() == VehicleClass.Largetruck) {
                selectPrimaryWorkshopChoiceBox.setValue(Main.companyWorkshopList.getWorkshopList().stream()
                        .filter(workshop -> !workshop.getIsInternal()).findFirst().get());
            } else {
                selectPrimaryWorkshopChoiceBox.setValue(Main.companyWorkshopList.getWorkshopList().get(0));
            }
    }

    //This method updates the UI with the vehicle's service history and sets the cell value factories for the tableview.

    public void updateUI() {
        tableColumnWorkshop.setCellValueFactory(new PropertyValueFactory<ServiceEvents, String>("workshop"));
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<ServiceEvents, LocalDate>("eventDate"));
        tableColumnCost.setCellValueFactory(new PropertyValueFactory<ServiceEvents, Integer>("totalCostOfService"));
        tableColumnPartsReplaced
                .setCellValueFactory(new PropertyValueFactory<ServiceEvents, Integer>("totalPartsReplaced"));
        tableViewServiceHistory.setItems(vehicle.getServiceHistory().getServiceHistory());
    }

    //This sets up the listener for the close event. This listener is called by the controller that created this controller.

    public onCloseListener onCloseListener;

    public void setOnCloseListener(onCloseListener onCloseListener) {
        this.onCloseListener = onCloseListener;
    }

    public interface onCloseListener {
        void onClose();
    }

    @FXML
    private Button closeWindowButton;

    @FXML
    private Button setPrimaryWorkshopButton;

    @FXML
    private ChoiceBox<Workshop> selectPrimaryWorkshopChoiceBox;

    @FXML
    private Label displayDateLabel;

    @FXML
    private Button scheduleMaintenanceButton;

    @FXML
    private Button addMaintenanceButton;

    @FXML
    private DatePicker maintenanceDatePicker;

    @FXML
    private TableView<ServiceEvents> tableViewServiceHistory;

    @FXML
    private TableColumn<ServiceEvents, String> tableColumnWorkshop;

    @FXML
    private TableColumn<ServiceEvents, LocalDate> tableColumnDate;

    @FXML
    private TableColumn<ServiceEvents, Integer> tableColumnCost;

    @FXML
    private TableColumn<ServiceEvents, Integer> tableColumnPartsReplaced;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //This sets up the date picker for scheduling maintenance. It disables all dates before today,
        //and it disables the ability to select a date before today.

        maintenanceDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        maintenanceDatePicker.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null && newDate.isBefore(LocalDate.now())) {
                Platform.runLater(() -> {
                    maintenanceDatePicker.setValue(LocalDate.now());
                });
            }
        }); 

        //This sets creates a context menu for the tableview. It contains two menu items, one for deleting a service event,
        //and one for viewing the service actions of a service event.

        tableViewServiceHistory.setRowFactory(tv -> {
            TableRow <ServiceEvents> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            //This defines the action for the delete menu item. It first displays an alert 
            //asking the user if they are sure they want to delete the service event.

            MenuItem deleteItem = new MenuItem("Delete Service Event");
            deleteItem.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this service event?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Delete Service Event");
                alert.setHeaderText("Delete Service Event");
                Optional<ButtonType> result = alert.showAndWait();

                //If the user presses yes, the service event is removed from the vehicle, the company service history and the workshop service history.
                //Then the UI is updated.

                if (result.isPresent() && result.get() == ButtonType.YES) {
                ServiceEvents serviceEvents = row.getItem();
                Workshop workshop = serviceEvents.getWorkshop();
                Vehicle vehicle = serviceEvents.getVehicleServiced();
                vehicle.getServiceHistory().getServiceHistory().remove(serviceEvents);
                Main.companyServiceHistory.getServiceHistory().remove(serviceEvents);
                workshop.getServiceHistory().getServiceHistory().remove(serviceEvents);
                updateUI();
                }

                //If the user presses no, the alert is closed.

                else {
                    alert.close();
                }
            });

            //This defines the menu item for viewing the service actions of a service event. It displays an alert with a tableview
            //containing the service actions of the service event.

            MenuItem editItem = new MenuItem("View Service Actions");
            editItem.setOnAction(event -> {
                ServiceEvents serviceEvents = row.getItem();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Service Actions for selected Service Event");
                alert.setHeaderText("Service Actions for selected Service Event");
                TableView<ServiceAction> serviceActionTableView = new TableView<>();

                serviceActionTableView.setItems(serviceEvents.getServiceActions());

                //This defines the columns of the created tableview.

                TableColumn<ServiceAction, String> serviceActionDescriptionColumn = new TableColumn<>("Service Action Description");
                TableColumn<ServiceAction, Integer> serviceActionCostColumn = new TableColumn<>("Service Action Cost");
                TableColumn<ServiceAction, Integer> serviceActionPartsReplacedColumn = new TableColumn<>("Parts Replaced");

                //This sets the width of the columns to 1/3 of the width of the tableview.

                serviceActionDescriptionColumn.prefWidthProperty().bind(serviceActionTableView.widthProperty().divide(3));
                serviceActionCostColumn.prefWidthProperty().bind(serviceActionTableView.widthProperty().divide(3));
                serviceActionPartsReplacedColumn.prefWidthProperty().bind(serviceActionTableView.widthProperty().divide(3));

                //This adds the columns to a list and then adds the list to the tableview.

                List<TableColumn<ServiceAction, ?>> columns = new ArrayList<>();
                columns.add(serviceActionDescriptionColumn);
                columns.add(serviceActionCostColumn);
                columns.add(serviceActionPartsReplacedColumn);

                serviceActionTableView.getColumns().addAll(columns);

                //This sets the cell value factories for the columns.

                serviceActionDescriptionColumn.setCellValueFactory(new PropertyValueFactory<ServiceAction, String>("actionDescription"));
                serviceActionCostColumn.setCellValueFactory(new PropertyValueFactory<ServiceAction, Integer>("totalCost"));
                serviceActionPartsReplacedColumn.setCellValueFactory(new PropertyValueFactory<ServiceAction, Integer>("numberOfPartsReplaced"));

                //This displays the tableview in the alert.

                alert.getDialogPane().setContent(serviceActionTableView);

                //this creates a context menu for the tableview in the alert. It contains one menu item for deleting a service action.
                
                serviceActionTableView.setRowFactory(event1 -> {
                    TableRow<ServiceAction> editrow = new TableRow<>();
                    ContextMenu contextMenuRemove = new ContextMenu();
                    MenuItem deleteItemRemove = new MenuItem("Delete Service Action");
                
                    deleteItemRemove.setOnAction(event2 -> {
                        ServiceAction serviceAction = editrow.getItem();
                        Workshop workshop = serviceEvents.getWorkshop();

                        //This creates an iterator that goes through the service history of the vehicle, 
                        //when it finds the service event that contains the service action, it removes the service action from the service event.

                        Iterator<ServiceEvents> iterator = vehicle.getServiceHistory().getServiceHistory().iterator();
                        while(iterator.hasNext()) {
                            ServiceEvents serviceEvent = iterator.next();
                            if (serviceEvent.getServiceActions().contains(serviceAction)){
                                serviceEvent.getServiceActions().remove(serviceAction);
                                serviceEvent.setTotalCostOfService();
                                serviceEvent.setTotalPartsReplaced();
                            }
                            if (serviceEvent.getServiceActions().isEmpty()) {
                                iterator.remove();
                                
                            }
                        }

                        //This creates an iterator that goes through the service history of the company,
                        //when it finds the service event that contains the service action, it removes the service action from the service event.

                        iterator = Main.companyServiceHistory.getServiceHistory().iterator();
                        while(iterator.hasNext()) {
                            ServiceEvents serviceEvent = iterator.next();
                            if (serviceEvent.getServiceActions().contains(serviceAction)){
                                serviceEvent.getServiceActions().remove(serviceAction);
                                serviceEvent.setTotalCostOfService();
                                serviceEvent.setTotalPartsReplaced();
                            }
                            if (serviceEvent.getServiceActions().isEmpty()){
                                iterator.remove();
                            }
                        }

                        //This creates an iterator that goes through the service history of the workshop,
                        //when it finds the service event that contains the service action, it removes the service action from the service event.

                        iterator = workshop.getServiceHistory().getServiceHistory().iterator();
                        while (iterator.hasNext()) {
                            ServiceEvents serviceEvent = iterator.next();
                            if (serviceEvent.getServiceActions().contains(serviceAction)){
                                serviceEvent.getServiceActions().remove(serviceAction);
                                serviceEvent.setTotalCostOfService();
                                serviceEvent.setTotalPartsReplaced();
                            }
                            if (serviceEvent.getServiceActions().isEmpty()){
                                iterator.remove();
                            }
                        }

                        //Once the service action has been removed from all service events, the table in the alert is updated and the UI is updated.

                        serviceActionTableView.refresh();
                        if (updateUIListener != null) {
                            updateUIListener.updateUI();
                        }
                    });

                    //This adds the delete menu item to the context menu in the alert and binds the context menu to the tableview.

                    contextMenuRemove.getItems().addAll(deleteItemRemove);
                    editrow.contextMenuProperty().bind(
                            javafx.beans.binding.Bindings.when(editrow.emptyProperty())
                                    .then((ContextMenu)null)
                                    .otherwise(contextMenuRemove)
                    );
                    return editrow;
                });

                //This displays the alert.

                alert.showAndWait();


            });

            //This adds the menu items to the context menu and binds the context menu to the tableview.

            contextMenu.getItems().addAll(deleteItem, editItem);
            row.contextMenuProperty().bind(
                    javafx.beans.binding.Bindings.when(row.emptyProperty())
                            .then((ContextMenu)null)
                            .otherwise(contextMenu)
            );
            return row;
        });
    }

    //This method handles the close window button. It closes the window and calls the onCloseListener.

    public void handleCloseWindowButton() {
        ((Stage) closeWindowButton.getScene().getWindow()).close();
        if (onCloseListener != null) {
            onCloseListener.onClose();
        }

    }

    //This method handles the schedule maintenance button. It sets the scheduled maintenance date of the vehicle to the date selected in the date picker.
    //Then it updates the UI and calls the updateUIListener.

    public void handleScheduleMaintenanceButton() {
        vehicle.setScheduledMaintenance(maintenanceDatePicker.getValue());
        displayDateLabel.setText(vehicle.getScheduledMaintenance().toString());
        if (updateUIListener != null) {
            updateUIListener.updateUI();
        }

    }

    //This method handles the set primary workshop button. It sets the primary workshop of the vehicle to the workshop selected in the choicebox.

    public void handleSetPrimaryWorkshopButton() {
        Workshop selectedWorkshop = selectPrimaryWorkshopChoiceBox.getValue();
        vehicle.setPrimaryWorkshop(selectPrimaryWorkshopChoiceBox.getValue());
        selectedWorkshop.getVehiclesPrimarilyServiced().addVehicle(vehicle);
    }

    //This method handles the add maintenance button. It opens a new window where the user can add a maintenance event to the vehicle.

    public void handleAddMaintenanceButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addmaintenanceevent.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Maintenance Event");
            stage.setScene(new Scene(root));
            stage.show();

            //This sets the vehicle in the add maintenance event controller to the vehicle that is being edited.
            //It also tells the add maintenance event controller to update its UI with the vehicle's information.

            AddMaintenanceEventController addMaintenanceEventController = loader.getController();
            addMaintenanceEventController.setVehicle(vehicle);
            addMaintenanceEventController.updateUI();

            //This sets the listener in the add maintenance event controller to update the UI of this controller 
            //when the add maintenance event controller is closed.

            addMaintenanceEventController.setOnCloseListener(() -> {
                updateUI();
            });

            //This catches any exceptions occuring if the addmaintenanceevent.fxml file is not found or unable to be loaded.

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        


}

