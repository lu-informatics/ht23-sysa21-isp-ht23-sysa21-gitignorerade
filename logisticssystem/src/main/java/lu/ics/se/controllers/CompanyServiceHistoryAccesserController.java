package lu.ics.se.controllers;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import lu.ics.se.models.classes.ServiceAction;
import lu.ics.se.models.classes.ServiceEvents;
import lu.ics.se.models.classes.Vehicle;
import lu.ics.se.models.classes.Workshop;

import java.time.LocalDate;
import lu.ics.se.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CompanyServiceHistoryAccesserController implements Initializable {

    public interface TableRefreshListener{
        void refreshTable();
    }
    private TableRefreshListener tableRefreshListener;

    public void setTableRefreshListener(TableRefreshListener tableRefreshListener) {
        this.tableRefreshListener = tableRefreshListener;
    }
    @FXML
    private TableView<ServiceEvents> serviceHistoryTable;

    @FXML
    private TableColumn<ServiceEvents, String> serviceHistoryColumnVehicleName;

    @FXML
    private TableColumn<ServiceEvents, String> serviceHistoryColumnWorkshop;

    @FXML
    private TableColumn<ServiceEvents, LocalDate> serviceHistoryColumnDate;

    @FXML
    private TableColumn<ServiceEvents, Integer> serviceHistoryColumnPartsReplaced;

    @FXML
    private TableColumn<ServiceEvents, Integer> serviceHistoryColumnCost;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        serviceHistoryColumnVehicleName.setCellValueFactory(new PropertyValueFactory<ServiceEvents, String>("vehicleServicedName"));
        serviceHistoryColumnWorkshop.setCellValueFactory(new PropertyValueFactory<ServiceEvents, String>("workshopName"));
        serviceHistoryColumnDate.setCellValueFactory(new PropertyValueFactory<ServiceEvents, LocalDate>("eventDate"));
        serviceHistoryColumnPartsReplaced.setCellValueFactory(new PropertyValueFactory<ServiceEvents, Integer>("totalPartsReplaced"));
        serviceHistoryColumnCost.setCellValueFactory(new PropertyValueFactory<ServiceEvents, Integer>("totalCostOfService"));
        serviceHistoryTable.setItems(Main.companyServiceHistory.getServiceHistory());

        serviceHistoryTable.setRowFactory(tv -> {
            TableRow <ServiceEvents> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem deleteItem = new MenuItem("Delete Service Event");
            deleteItem.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this service event?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Delete Service Event");
                alert.setHeaderText("Delete Service Event");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.YES) {
                ServiceEvents serviceEvents = row.getItem();
                Vehicle vehicle = serviceEvents.getVehicleServiced();
                Workshop workshop = serviceEvents.getWorkshop();
                vehicle.getServiceHistory().getServiceHistory().remove(serviceEvents);
                workshop.getServiceHistory().getServiceHistory().remove(serviceEvents);
                Main.companyServiceHistory.getServiceHistory().remove(serviceEvents);
                serviceHistoryTable.refresh();
                if (tableRefreshListener != null) {
                    tableRefreshListener.refreshTable();
                }
            }
                else{
                    alert.close();
                }
            });
            MenuItem editItem = new MenuItem("View Service Actions");
            editItem.setOnAction(event -> {
                ServiceEvents serviceEvents = row.getItem();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Service Actions for selected Service Event");
                alert.setHeaderText("Service Actions for selected Service Event");
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
                alert.showAndWait();

            });
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

