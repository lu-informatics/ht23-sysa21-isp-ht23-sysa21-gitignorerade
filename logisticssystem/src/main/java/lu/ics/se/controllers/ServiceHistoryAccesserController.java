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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import lu.ics.se.models.enums.VehicleClass;
import java.util.stream.Collectors;
import java.io.IOException;
import lu.ics.se.models.classes.ServiceAction;
import javafx.scene.control.Alert;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ServiceHistoryAccesserController implements Initializable {

    private Vehicle vehicle;

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        displayDateLabel.setText(vehicle.getScheduledMaintenance().toString());
        if (vehicle.getVehicleClass() == VehicleClass.Largetruck) {
            selectPrimaryWorkshopChoiceBox.getItems().addAll(
                    Main.companyWorkshopList.getWorkshopList().stream()
                            .filter(workshop -> !workshop.getIsInternal())
                            .collect(Collectors.toList()));
        } else {
            selectPrimaryWorkshopChoiceBox.getItems().addAll(Main.companyWorkshopList.getWorkshopList());
        }
        if (vehicle.getPrimaryWorkshop() != null) {
            selectPrimaryWorkshopChoiceBox.setValue(vehicle.getPrimaryWorkshop());
        } else {

            selectPrimaryWorkshopChoiceBox.setValue(Main.companyWorkshopList.getWorkshopList().get(0));
        }
    }

    public void updateUI() {
        tableColumnWorkshop.setCellValueFactory(new PropertyValueFactory<ServiceEvents, String>("workshop"));
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<ServiceEvents, LocalDate>("eventDate"));
        tableColumnCost.setCellValueFactory(new PropertyValueFactory<ServiceEvents, Integer>("totalCostOfService"));
        tableColumnPartsReplaced
                .setCellValueFactory(new PropertyValueFactory<ServiceEvents, Integer>("totalPartsReplaced"));
        tableViewServiceHistory.setItems(vehicle.getServiceHistory().getServiceHistory());
    }

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
        tableViewServiceHistory.setRowFactory(tv -> {
            TableRow <ServiceEvents> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem deleteItem = new MenuItem("Delete Service Event");
            deleteItem.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this service event?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Delete Service Event");
                alert.setHeaderText("Delete Service Event");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.YES) {
                ServiceEvents serviceEvents = row.getItem();
                Workshop workshop = serviceEvents.getWorkshop();
                Vehicle vehicle = serviceEvents.getVehicleServiced();
                vehicle.getServiceHistory().getServiceHistory().remove(serviceEvents);
                Main.companyServiceHistory.getServiceHistory().remove(serviceEvents);
                workshop.getServiceHistory().getServiceHistory().remove(serviceEvents);
                updateUI();
                }
                else {
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

                TableColumn<ServiceAction, String> serviceActionDescriptionColumn = new TableColumn<>("Service Action Description");
                TableColumn<ServiceAction, Integer> serviceActionCostColumn = new TableColumn<>("Service Action Cost");
                TableColumn<ServiceAction, Integer> serviceActionPartsReplacedColumn = new TableColumn<>("Parts Replaced");

                serviceActionDescriptionColumn.prefWidthProperty().bind(serviceActionTableView.widthProperty().divide(3));
                serviceActionCostColumn.prefWidthProperty().bind(serviceActionTableView.widthProperty().divide(3));
                serviceActionPartsReplacedColumn.prefWidthProperty().bind(serviceActionTableView.widthProperty().divide(3));

                List<TableColumn<ServiceAction, ?>> columns = new ArrayList<>();
                columns.add(serviceActionDescriptionColumn);
                columns.add(serviceActionCostColumn);
                columns.add(serviceActionPartsReplacedColumn);

                serviceActionTableView.getColumns().addAll(columns);

                serviceActionDescriptionColumn.setCellValueFactory(new PropertyValueFactory<ServiceAction, String>("actionDescription"));
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

    public void handleCloseWindowButton() {
        ((Stage) closeWindowButton.getScene().getWindow()).close();
        if (onCloseListener != null) {
            onCloseListener.onClose();
        }

    }

    public void handleScheduleMaintenanceButton() {
        vehicle.setScheduledMaintenance(maintenanceDatePicker.getValue());
        displayDateLabel.setText(vehicle.getScheduledMaintenance().toString());
    }

    public void handleSetPrimaryWorkshopButton() {
        Workshop selectedWorkshop = selectPrimaryWorkshopChoiceBox.getValue();
        vehicle.setPrimaryWorkshop(selectPrimaryWorkshopChoiceBox.getValue());
        selectedWorkshop.getVehiclesPrimarilyServiced().addVehicle(vehicle);
    }

    public void handleAddMaintenanceButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addmaintenanceevent.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Maintenance Event");
            stage.setScene(new Scene(root));
            stage.show();

            AddMaintenanceEventController addMaintenanceEventController = loader.getController();
            addMaintenanceEventController.setVehicle(vehicle);
            addMaintenanceEventController.updateUI();

            addMaintenanceEventController.setOnCloseListener(() -> {
                updateUI();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        


}

