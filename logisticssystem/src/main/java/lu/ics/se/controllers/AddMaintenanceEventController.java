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

    // This creates a new service event which is the one that will be added once the
    // controller
    // is closed.
    private ServiceEvents serviceEventToAdd;

    // This sets the created service event

    public AddMaintenanceEventController() {
        this.serviceEventToAdd = new ServiceEvents();
    }

    // This creates a listener which tells the controller opening this controller to
    // update its UI
    // When this controller is closed

    public interface onCloseListener {
        void onClose();
    }

    private onCloseListener onCloseListener;

    public void setOnCloseListener(onCloseListener onCloseListener) {
        this.onCloseListener = onCloseListener;
    }

    // This defines the the updateUI method which is used to fill in the information
    // in the window
    // depending on the information of the vehicle.

    public void updateUI() {

        // This sets the text of the label to the name of the vehicle whose
        // servicehistory is being appended.

        displayCurrentVehicleLabel.setText(vehicle.getVehicleName());

        // This checks if the vehicle is a large truck
        // if it is, a filter is created that leaves only the external workshops and
        // adds those
        // as selectable options in the choicebox.
        if (vehicle.getVehicleClass() == VehicleClass.Largetruck) {
            List<Workshop> filteredWorkshops = Main.companyWorkshopList.getWorkshopList().stream()
                    .filter(workshop -> !workshop.getIsInternal())
                    .collect(Collectors.toList());
            selectWorkshopChoiceBox.getItems().addAll(filteredWorkshops);

            // If the filtered list contains at least one element then this will be set as
            // the default
            // value in the choicebox.

            if (!filteredWorkshops.isEmpty()) {
                selectWorkshopChoiceBox.setValue(filteredWorkshops.get(0));
            }
        } else {

            // If the vehicle is not a large truck all workshops are added as options in the
            // choicebox.

            selectWorkshopChoiceBox.getItems().addAll(Main.companyWorkshopList.getWorkshopList());
        }

        // If the vehicle has a primary workshop set the default value of the workshop
        // choice box will be set to this workshop

        if (vehicle.getPrimaryWorkshop() != null) {
            selectWorkshopChoiceBox.setValue(vehicle.getPrimaryWorkshop());

            // If the vehicle is a large truck the workshop choice box is set to the first
            // external workshop.

        } else if (vehicle.getVehicleClass() == VehicleClass.Largetruck) {
            selectWorkshopChoiceBox.setValue(Main.companyWorkshopList.getWorkshopList().stream()
                    .filter(workshop -> !workshop.getIsInternal()).findFirst().get());

            // If the vehicle is another class and does not have a primary workshop set the
            // default value
            // Is set to the first workshop in the company workshop list.

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

    //This loops through all service events in the service history of the vehicle and sums the maintenance costs for it.

    public void handleAddServiceActivityButton() {
        int totalCostOfAllMaintenance = 0;
        for (ServiceEvents ServiceEvent : vehicle.getServiceHistory().getServiceHistory()) {
            totalCostOfAllMaintenance += ServiceEvent.getTotalCostOfService();
        }
        for (ServiceAction serviceAction : serviceEventToAdd.getServiceActions()) {
            totalCostOfAllMaintenance += serviceAction.getTotalCost();
        }
        totalCostOfAllMaintenance += Integer.parseInt(costOfLaborTextField.getText());

        //This loops through all service events in the service history of the vehicle and sums the number of parts that have been replaced.
        int totalNumberOfPartsReplaced = 0;
        for (ServiceAction serviceAction : serviceEventToAdd.getServiceActions()) {
            totalNumberOfPartsReplaced += serviceAction.getNumberOfPartsReplaced();
        }
        for (ServiceEvents ServiceEvent : vehicle.getServiceHistory().getServiceHistory()) {
            for (ServiceAction serviceAction : ServiceEvent.getServiceActions()) {
                totalNumberOfPartsReplaced += serviceAction.getNumberOfPartsReplaced();
            }
        }

        //This checks that all fields are filled in, if they are not, an error message is displayed to the user.

        if (descriptionTextField.getText().isEmpty() || numberOfPartsTextField.getText().isEmpty()
                || costOfLaborTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();

            //If the vehicle is a large truck and the user somehow manages to select an internal workshop in the choicebox this will
            //display an error message telling the user that Large trucks can only be serviced by external workshops

        } else if (vehicle.getVehicleClass() == VehicleClass.Largetruck
                && selectWorkshopChoiceBox.getValue().getIsInternal()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incompatible workshop");
            alert.setHeaderText("This vehicle class is incompatible with this type of workshop");
            alert.setContentText("Large trucks can only be serviced in external workshops");
            alert.showAndWait();

            //If the total number of parts replaced throughout the entire vehicles servicehistory is equal or more than 100
            //an error message is displayed to the user saying the vehicle is to be decommisioned.
            //Then the vehicle will be removed from the system.

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

            //If the total cost of maintenance for the vehicle is above 100 000 a warning will be displayed to the user
            //and they will be given a choice to disregard the warning and add the vehicle anyway.

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

            //If the user adds the event anyway the serviceactiom will be added according to the information entered.
            //It will then calculate the cost of the parts replaced depending on the class of the vehicle. With replacement
            //parts for heavier vehicles costs more.

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

                //This adds the serviceaction to the service event.

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

        //If the user tries to add the service event without having added any service actions to it an alert
        //will be displayed telling the user that no changes were made and the service event will be set to null.
        //The window will then close.

        if (serviceEventToAdd.getServiceActions().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Changes Made");
            alert.setHeaderText(null);
            alert.setContentText("No changes were made to the service history");
            alert.showAndWait();

            serviceEventToAdd = null;

            Stage stage = (Stage) saveAndExitButton.getScene().getWindow();
            stage.close();

            //If no date for the maintenance has been set the an alert will be shown asking the user to select
            //a date before saving the maintenance event.

        } else if (maintenanceDatePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please select a date");
            alert.showAndWait();

            //If no errors are discovered the service event is set and added to the three servicehistories.
            //The window is then closed and a listener is called telling the 
            //service history accesser to update it's table
            
        } else {
            serviceEventToAdd.setVehicleServiced(vehicle);
            serviceEventToAdd.setEventDate(maintenanceDatePicker.getValue());
            serviceEventToAdd.setTotalCostOfService();
            serviceEventToAdd.setTotalPartsReplaced();
            serviceEventToAdd.setWorkshop(selectWorkshopChoiceBox.getValue());
            Main.companyServiceHistory.addServiceEvent(serviceEventToAdd);
            vehicle.getServiceHistory().addServiceEvent(serviceEventToAdd);
            vehicle.setLastMaintenance();
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

    //This code sets up the columns of the tableview and the datepicker in the window.

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

        //This creates a text filter that only allows the user to enter numbers into the textfield.

        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };

        //This applies the textfilter on the text field.
        numberOfPartsTextField.setTextFormatter(new TextFormatter<>(integerFilter));
        costOfLaborTextField.setTextFormatter(new TextFormatter<>(integerFilter));

    }
}
