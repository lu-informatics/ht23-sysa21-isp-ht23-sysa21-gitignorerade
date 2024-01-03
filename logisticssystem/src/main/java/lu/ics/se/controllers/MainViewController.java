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




public class MainViewController implements Initializable {
    
    @FXML 
    private Button VehicleAddButton;

    @FXML
    private TableView<Vehicle> vehicleTable;

    @FXML
    private TableColumn<Vehicle, String> vehicleColumnID;

    @FXML
    private TableColumn<Vehicle, String> vehicleColumnBrand;

    @FXML
    private TableColumn<Vehicle, String> vehicleColumnCapacity;

    @FXML
    private TableColumn<Vehicle, String> vehicleColumnClass;

    @FXML
    public void handleVehicleAddButtonAction(){
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vehicleColumnID.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleIdentificationNumber"));
        vehicleColumnBrand.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleBrand"));
        vehicleColumnCapacity.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("capacityinKg"));
        vehicleColumnClass.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("vehicleClass"));
        vehicleTable.setItems(Main.companyVehicleManifest.getVehicleManifest());

        vehicleTable.setRowFactory(tv -> {
            TableRow<Vehicle> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem editItem = new MenuItem("Edit Vehicle");
            editItem.setOnAction(event -> {
                try {
                    // Handle edit vehicle here
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
                try{
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

            MenuItem scheduleMaintenanceItem = new MenuItem("Handle Maintenance");
            scheduleMaintenanceItem.setOnAction(event -> {
                Vehicle vehicle = row.getItem();
                // Handle schedule maintenance here
            });

            MenuItem setLocation = new MenuItem("Set Location");
            setLocation.setOnAction(event -> {
                try{
                    Vehicle vehicle = row.getItem();

                    Stage stage = new Stage();
                    stage.setTitle("Location Picker");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/setlocation.fxml"));
                    Parent root = loader.load();

                    stage.setScene(new Scene(root));
                    stage.show();

                    SetLocationController controller = loader.getController();
                    controller.setVehicle(vehicle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            contextMenu.getItems().addAll(editItem, removeItem, scheduleMaintenanceItem, setLocation);

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu)null)
                            .otherwise(contextMenu)
            );

            return row;
        });
    }
}
