package lu.ics.se.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lu.ics.se.Main;
import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
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
import lu.ics.se.models.enums.VehicleClass;
import java.util.stream.Collectors;
import lu.ics.se.Main;
import java.io.IOException;

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
