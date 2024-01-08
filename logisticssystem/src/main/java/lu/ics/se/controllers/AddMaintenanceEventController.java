package lu.ics.se.controllers;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import lu.ics.se.Main;
import lu.ics.se.models.classes.Vehicle;
import lu.ics.se.models.classes.Workshop;
import lu.ics.se.models.classes.WorkshopList;
import lu.ics.se.models.classes.ServiceEvents;
import lu.ics.se.models.classes.ServiceAction;
import lu.ics.se.models.enums.VehicleClass;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
import javafx.scene.control.DateCell;
import javafx.scene.control.ButtonType;

import java.util.List;
import java.util.Optional;

public class AddMaintenanceEventController implements Initializable {
    private ServiceEvents serviceEventToAdd;

    public AddMaintenanceEventController() {
        this.serviceEventToAdd = new ServiceEvents();
    }

    public interface onCloseListener {
        void onClose();
    }

    private onCloseListener onCloseListener;

    public void setOnCloseListener(onCloseListener onCloseListener) {
        this.onCloseListener = onCloseListener;
    }

    public void updateUI() {
        displayCurrentVehicleLabel.setText(vehicle.getVehicleName());
        if (vehicle.getVehicleClass() == VehicleClass.Largetruck) {
            List<Workshop> filteredWorkshops = Main.companyWorkshopList.getWorkshopList().stream()
                    .filter(workshop -> !workshop.getIsInternal())
                    .collect(Collectors.toList());
            selectWorkshopChoiceBox.getItems().addAll(filteredWorkshops);
            if (!filteredWorkshops.isEmpty()) {
                selectWorkshopChoiceBox.setValue(filteredWorkshops.get(0));
            }} else {
                selectWorkshopChoiceBox.getItems().addAll(Main.companyWorkshopList.getWorkshopList());
            }
            if (vehicle.getPrimaryWorkshop() != null) {
                selectWorkshopChoiceBox.setValue(vehicle.getPrimaryWorkshop());
            } else if (vehicle.getVehicleClass() == VehicleClass.Largetruck) {
                selectWorkshopChoiceBox.setValue(Main.companyWorkshopList.getWorkshopList().stream()
                        .filter(workshop -> !workshop.getIsInternal()).findFirst().get());
            } else {
                selectWorkshopChoiceBox.setValue(Main.companyWorkshopList.getWorkshopList().get(0));
            }
        }

    private Vehicle vehicle;

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @FXML
    private Label displayCurrentVehicleLabel;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private ChoiceBox<Workshop> selectWorkshopChoiceBox;

    @FXML
    private TextField numberOfPartsTextField;

    @FXML
    private TextField costOfLaborTextField;

    @FXML
    private Button addServiceActivityButton;

    @FXML
    private Button saveAndExitButton;

    @FXML
    private TableView<ServiceAction> serviceActionTable;

    @FXML
    private TableColumn<ServiceAction, String> serviceActionColumnDescription;

    @FXML
    private TableColumn<ServiceAction, Integer> serviceActionColumnPartsReplaced;

    @FXML
    private TableColumn<ServiceAction, Integer> serviceActionColumnCost;

    @FXML
    private DatePicker maintenanceDatePicker;

    @FXML
    public void handleAddServiceActivityButton() {
        int totalCostOfAllMaintenance = 0;
        for (ServiceEvents ServiceEvent : vehicle.getServiceHistory().getServiceHistory()) {
            totalCostOfAllMaintenance += ServiceEvent.getTotalCostOfService();
        }
        for (ServiceAction serviceAction : serviceEventToAdd.getServiceActions()) {
            totalCostOfAllMaintenance += serviceAction.getTotalCost();
        }
        totalCostOfAllMaintenance += Integer.parseInt(costOfLaborTextField.getText());

        int totalNumberOfPartsReplaced = 0;
        for (ServiceAction serviceAction : serviceEventToAdd.getServiceActions()) {
            totalNumberOfPartsReplaced += serviceAction.getNumberOfPartsReplaced();
        }
        for (ServiceEvents ServiceEvent : vehicle.getServiceHistory().getServiceHistory()) {
            for (ServiceAction serviceAction : ServiceEvent.getServiceActions()) {
                totalNumberOfPartsReplaced += serviceAction.getNumberOfPartsReplaced();
            }
        }
        if (descriptionTextField.getText().isEmpty() || numberOfPartsTextField.getText().isEmpty()
                || costOfLaborTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
        } else if (vehicle.getVehicleClass() == VehicleClass.Largetruck
                && selectWorkshopChoiceBox.getValue().getIsInternal()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incompatible workshop");
            alert.setHeaderText("This vehicle class is incompatible with this type of workshop");
            alert.setContentText("Large trucks can only be serviced in external workshops");
            alert.showAndWait();
        } else if (totalNumberOfPartsReplaced + Integer.parseInt(numberOfPartsTextField.getText()) >= 100) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Vehicle should be decommissioned");
            alert.setHeaderText("Vehicle should be decommissioned");
            alert.setContentText(
                    "The vehicle has exceeded 100 parts replaced." + "\n" + "Vehicle will be removed from system");
            alert.showAndWait();
            Main.companyVehicleManifest.removeVehicle(vehicle);
            Stage stage = (Stage) saveAndExitButton.getScene().getWindow();
            stage.close();

        } else if (totalCostOfAllMaintenance >= 100000) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Exceedingly high maintenance cost");
            alert.setHeaderText("Exceedingly high maintenance cost");
            alert.setContentText("The vehicle has exceeded 100,000 crowns in maintenance costs." + "\n"
                    + "Consider decommissioning the vehicle");

            ButtonType buttonTypeGoAheadAnyway = new ButtonType("Add event anyway");
            ButtonType buttonTypeCancel = new ButtonType("Cancel");
            alert.getButtonTypes().setAll(buttonTypeGoAheadAnyway, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeGoAheadAnyway) {
                ServiceAction serviceActionToAdd = new ServiceAction();
                serviceActionToAdd.setActionDescription(descriptionTextField.getText());
                serviceActionToAdd.setNumberOfPartsReplaced(Integer.parseInt(numberOfPartsTextField.getText()));
                serviceActionToAdd.setCostOfService(Integer.parseInt(costOfLaborTextField.getText()));
                if (vehicle.getVehicleClass() == VehicleClass.Van) {
                    serviceActionToAdd.setCostOfPartsReplaced(100 * serviceActionToAdd.getNumberOfPartsReplaced());
                } else if (vehicle.getVehicleClass() == VehicleClass.Mediumtruck) {
                    serviceActionToAdd.setCostOfPartsReplaced(200 * serviceActionToAdd.getNumberOfPartsReplaced());
                } else if (vehicle.getVehicleClass() == VehicleClass.Largetruck) {
                    serviceActionToAdd.setCostOfPartsReplaced(500 * serviceActionToAdd.getNumberOfPartsReplaced());
                }
                serviceActionToAdd
                        .setTotalCost(
                                serviceActionToAdd.getCostOfPartsReplaced() + serviceActionToAdd.getCostOfService());

                serviceEventToAdd.getServiceActions().add(serviceActionToAdd);
            } else {
            }

        } else {
            ServiceAction serviceActionToAdd = new ServiceAction();
            serviceActionToAdd.setActionDescription(descriptionTextField.getText());
            serviceActionToAdd.setNumberOfPartsReplaced(Integer.parseInt(numberOfPartsTextField.getText()));
            serviceActionToAdd.setCostOfService(Integer.parseInt(costOfLaborTextField.getText()));
            if (vehicle.getVehicleClass() == VehicleClass.Van) {
                serviceActionToAdd.setCostOfPartsReplaced(100 * serviceActionToAdd.getNumberOfPartsReplaced());
            } else if (vehicle.getVehicleClass() == VehicleClass.Mediumtruck) {
                serviceActionToAdd.setCostOfPartsReplaced(200 * serviceActionToAdd.getNumberOfPartsReplaced());
            } else if (vehicle.getVehicleClass() == VehicleClass.Largetruck) {
                serviceActionToAdd.setCostOfPartsReplaced(500 * serviceActionToAdd.getNumberOfPartsReplaced());
            }
            serviceActionToAdd
                    .setTotalCost(serviceActionToAdd.getCostOfPartsReplaced() + serviceActionToAdd.getCostOfService());

            serviceEventToAdd.getServiceActions().add(serviceActionToAdd);
        }
    }

    @FXML
    public void handleSaveAndExitButton() {
        if (serviceEventToAdd.getServiceActions().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Changes Made");
            alert.setHeaderText(null);
            alert.setContentText("No changes were made to the service history");
            alert.showAndWait();

            serviceEventToAdd = null;

            Stage stage = (Stage) saveAndExitButton.getScene().getWindow();
            stage.close();

        } else if (maintenanceDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please select a date");
            alert.showAndWait();
        } else {
            serviceEventToAdd.setVehicleServiced(vehicle);
            serviceEventToAdd.setEventDate(maintenanceDatePicker.getValue());
            serviceEventToAdd.setTotalCostOfService();
            serviceEventToAdd.setTotalPartsReplaced();
            serviceEventToAdd.setWorkshop(selectWorkshopChoiceBox.getValue());
            Main.companyServiceHistory.addServiceEvent(serviceEventToAdd);
            vehicle.getServiceHistory().addServiceEvent(serviceEventToAdd);
            Workshop workshop = selectWorkshopChoiceBox.getValue();
            for (Workshop workshopToFind : Main.companyWorkshopList.getWorkshopList()) {
                if (workshopToFind.equals(workshop)) {
                    workshopToFind.getServiceHistory().addServiceEvent(serviceEventToAdd);
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Service Event Added");
            alert.setHeaderText(null);
            alert.setContentText("Service Event has been added to the system");
            alert.showAndWait();

            Stage stage = (Stage) saveAndExitButton.getScene().getWindow();
            stage.close();
            if (onCloseListener != null) {
                onCloseListener.onClose();
            }
        }
    }

    public void initialize(URL url, ResourceBundle rb) {
        serviceActionColumnDescription
                .setCellValueFactory(new PropertyValueFactory<ServiceAction, String>("actionDescription"));
        serviceActionColumnPartsReplaced
                .setCellValueFactory(new PropertyValueFactory<ServiceAction, Integer>("numberOfPartsReplaced"));
        serviceActionColumnCost.setCellValueFactory(new PropertyValueFactory<ServiceAction, Integer>("totalCost"));
        serviceActionTable.setItems(serviceEventToAdd.getServiceActions());

        LocalDate currentDate = LocalDate.now();

        maintenanceDatePicker.setDayCellFactory(datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isAfter(currentDate));
            }
        });
        maintenanceDatePicker.setValue(currentDate);

        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };

        numberOfPartsTextField.setTextFormatter(new TextFormatter<>(integerFilter));
        costOfLaborTextField.setTextFormatter(new TextFormatter<>(integerFilter));

    }
}
