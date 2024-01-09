package lu.ics.se.controllers;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import lu.ics.se.Main;
import lu.ics.se.models.classes.ServiceEvents;
import lu.ics.se.models.classes.Workshop;
import lu.ics.se.models.classes.Vehicle;
import lu.ics.se.models.classes.ServiceAction;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


public class WorkshopServiceHistoryAccesserController implements Initializable{

    //This sets the table refresh listener. This listener tells the controller that created this controller to refresh its table
    //when this controller calls on the listener.

    public interface TableRefreshListener{
        void refreshTable();
    }
    private TableRefreshListener tableRefreshListener;

    public void setTableRefreshListener(TableRefreshListener tableRefreshListener) {
        this.tableRefreshListener = tableRefreshListener;
    }

    //This sets the workshop that is to be accessed. It is called by the controller that created this controller.

    private Workshop workshop;

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }

    //This method updates the UI. It is called by the controller that created this controller.
    //This is done to ensure that the UI is populated with the workshop info after the initialize method is called.
    //This is because the initialize method is called before the workshop is set. Which means that the initialize method
    //will not be able to populate the UI with the workshop info. And a null pointer exception will be thrown.

    public void updateUI(){

        //This sets the table columns to the correct values.

        serviceHistoryVehicleColumn.setCellValueFactory(new PropertyValueFactory<ServiceEvents, String>("vehicleServicedName"));
        serviceHistoryDateColumn.setCellValueFactory(new PropertyValueFactory<ServiceEvents, LocalDate>("eventDate"));
        serviceHistoryCostColumn.setCellValueFactory(new PropertyValueFactory<ServiceEvents, Integer>("totalCostOfService"));
        serviceHistoryPartsReplacedColumn.setCellValueFactory(new PropertyValueFactory<ServiceEvents, Integer>("totalPartsReplaced"));

        //This sets the table to contain the service history of the workshop.

        serviceHistoryTableView.setItems(workshop.getServiceHistory().getServiceHistory());

        //This sets the workshop info label to the name of the workshop. And centers the text.

        workshopInfoLabel.setText(workshop.getWorkshopName());
        workshopInfoLabel.setAlignment(Pos.CENTER);
    }


    @FXML
    private Label workshopInfoLabel;

    @FXML
    private TableView<ServiceEvents> serviceHistoryTableView;

    @FXML
    private TableColumn<ServiceEvents, String> serviceHistoryVehicleColumn;

    @FXML
    private TableColumn<ServiceEvents, LocalDate> serviceHistoryDateColumn;

    @FXML
    private TableColumn<ServiceEvents, Integer> serviceHistoryCostColumn;

    @FXML
    private TableColumn<ServiceEvents, Integer> serviceHistoryPartsReplacedColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //This creates a context menu for the table. This context menu contains a delete item and an edit item.

        serviceHistoryTableView.setRowFactory(tv -> {
            TableRow <ServiceEvents> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            //This creates a delete item for the context menu. 

            MenuItem deleteItem = new MenuItem("Delete Service Event");
            deleteItem.setOnAction(event -> {

                //This opens an alert asking the user if they are sure they want to delete the service event.

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this service event?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Delete Service Event");
                alert.setHeaderText("Delete Service Event");
                Optional<ButtonType> result = alert.showAndWait();

                //If the user presses yes, the service event is deleted from the workshop, the vehicle and the company service history.

                if (result.get() == ButtonType.YES) {
                ServiceEvents serviceEvents = row.getItem();
                Vehicle vehicle = serviceEvents.getVehicleServiced();
                vehicle.getServiceHistory().getServiceHistory().remove(serviceEvents);
                workshop.getServiceHistory().getServiceHistory().remove(serviceEvents);
                Main.companyServiceHistory.getServiceHistory().remove(serviceEvents);

                //This updates the UI and refreshes the table of the controller that created this controller,
                //by calling the table refresh listener.

                updateUI();
                if (tableRefreshListener != null) {
                    tableRefreshListener.refreshTable();
                }
            }

                //If the user presses no, the alert is closed.

                else{
                    alert.close();
                }
            });

            //This creates defines what happens if the user presses the view service actions item in the context menu.

            MenuItem editItem = new MenuItem("View Service Actions");
            editItem.setOnAction(event -> {
                ServiceEvents serviceEvents = row.getItem();

                //This creates an alert that displays the service actions of the service event.

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Service Actions for selected Service Event");
                alert.setHeaderText("Service Actions for selected Service Event");

                //This creates a table view that contains the service actions of the service event.
                //This table view is then added to the alert.
                TableView<ServiceAction> serviceActionTableView = new TableView<>();

                serviceActionTableView.setItems(serviceEvents.getServiceActions());

                TableColumn<ServiceAction, String> serviceActionNameColumn = new TableColumn<>("Description");
                TableColumn<ServiceAction, Integer> serviceActionCostColumn = new TableColumn<>("Cost");
                TableColumn<ServiceAction, Integer> serviceActionPartsReplacedColumn = new TableColumn<>("Parts Replaced");

                serviceActionNameColumn.prefWidthProperty().bind(serviceActionTableView.widthProperty().divide(3));
                serviceActionCostColumn.prefWidthProperty().bind(serviceActionTableView.widthProperty().divide(3));
                serviceActionPartsReplacedColumn.prefWidthProperty().bind(serviceActionTableView.widthProperty().divide(3));

                List<TableColumn<ServiceAction, ?>> columns = new ArrayList<>();
                columns.add(serviceActionNameColumn);
                columns.add(serviceActionCostColumn);
                columns.add(serviceActionPartsReplacedColumn);

                serviceActionTableView.getColumns().addAll(columns);

                serviceActionNameColumn.setCellValueFactory(new PropertyValueFactory<ServiceAction, String>("actionDescription"));
                serviceActionCostColumn.setCellValueFactory(new PropertyValueFactory<ServiceAction, Integer>("totalCost"));
                serviceActionPartsReplacedColumn.setCellValueFactory(new PropertyValueFactory<ServiceAction, Integer>("numberOfPartsReplaced"));

                alert.getDialogPane().setContent(serviceActionTableView);

                serviceActionTableView.setRowFactory(event1-> {

                    //This creates a context menu for the table. This context menu contains a delete item.

                    TableRow<ServiceAction> editrow = new TableRow<>();
                    ContextMenu contextMenuRemove = new ContextMenu();
                    MenuItem deleteItemRemove = new MenuItem("Delete Service Action");

                    deleteItemRemove.setOnAction(event2 -> {

                        //If the user wants to delete an item in the table this code is executed.
                        ServiceAction serviceAction = editrow.getItem();
                        Workshop workshop = serviceEvents.getWorkshop();

                        //This creates an iterator that iterates through the events of the service history of the workshop. 
                        //If the service event contains the service action, the service action is removed from the service event.
                        Iterator<ServiceEvents> iterator = workshop.getServiceHistory().getServiceHistory().iterator();
                        while (iterator.hasNext()){
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

                        //This creates an iterator that iterates through the events of the service history of the vehicle.
                        //If the service event contains the service action, the service action is removed from the service event.

                        iterator = serviceEvents.getVehicleServiced().getServiceHistory().getServiceHistory().iterator();
                        while (iterator.hasNext()){
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

                        //This creates an iterator that iterates through the events of the service history of the company.
                        //If the service event contains the service action, the service action is removed from the service event.

                        iterator = Main.companyServiceHistory.getServiceHistory().iterator();
                            while (iterator.hasNext()){
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

                        //After the service action has been removed from the service event, the UI is updated and the table
                        //of the controller that created this controller is refreshed by calling the table refresh listener.

                    serviceActionTableView.refresh();
                    if (tableRefreshListener != null) {
                        tableRefreshListener.refreshTable();
                    }
                    });

                    //This adds the delete item to the context menu.

                    contextMenuRemove.getItems().addAll(deleteItemRemove);
                    editrow.contextMenuProperty().bind(
                            javafx.beans.binding.Bindings.when(editrow.emptyProperty())
                                    .then((ContextMenu)null)
                                    .otherwise(contextMenuRemove)
                    );
                return editrow;
            });

            //This shows the alert.

                alert.showAndWait();
            });

            //This adds the delete item and the edit item to the context menu.
            
            contextMenu.getItems().addAll(deleteItem, editItem);
            row.contextMenuProperty().bind(
                    javafx.beans.binding.Bindings.when(row.emptyProperty())
                            .then((ContextMenu)null)
                            .otherwise(contextMenu)
            );
            return row;
        });





    }
}
