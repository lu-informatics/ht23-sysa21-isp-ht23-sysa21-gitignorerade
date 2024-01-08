package lu.ics.se.controllers;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import lu.ics.se.Main;
import lu.ics.se.models.enums.Locations;
import javafx.scene.control.TextFormatter;
import java.util.function.UnaryOperator;
import javafx.scene.control.Label;
import lu.ics.se.models.classes.Workshop;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


public class AddWorkshopController implements Initializable {

    @FXML
    private Button addWorkshopButton;

    @FXML
    private ChoiceBox<String> workshopInternalChoiceBox;

    @FXML
    private ChoiceBox<Locations> workshopLocationChoiceBox;

    @FXML
    private TextField workshopNameTextField;

    @FXML
    private Label addWorkshopErrorLabel; 

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        workshopInternalChoiceBox.getItems().addAll("Yes", "No");
        workshopInternalChoiceBox.setValue("Yes");

        workshopLocationChoiceBox.getItems().addAll(Locations.values());
        workshopLocationChoiceBox.setValue(Locations.Stockholm);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[a-zA-Z ]*")) {
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        workshopNameTextField.setTextFormatter(textFormatter);
    }
    @FXML
    private void handleAddWorkshopButton(){
        if (workshopNameTextField.getText().isEmpty()) {
            addWorkshopErrorLabel.setText("Please enter a name for the workshop");
        } else {
            Workshop workshop = new Workshop();
            workshop.setWorkshopName(workshopNameTextField.getText());
            workshop.setWorkshopLocation(workshopLocationChoiceBox.getValue());
            if (workshopInternalChoiceBox.getValue().equals("Yes")) {
                workshop.setIsInternal(true);
            } else {
                workshop.setIsInternal(false);
            }
            Main.companyWorkshopList.addWorkshop(workshop);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Workshop added");
            alert.setHeaderText("Workshop added Successfully");
            alert.setContentText("Workshop " + workshop.getWorkshopName() + " has been added to the system");
            alert.showAndWait();

            Stage stage = (Stage) addWorkshopButton.getScene().getWindow();
            stage.close();
        }
    }

}
