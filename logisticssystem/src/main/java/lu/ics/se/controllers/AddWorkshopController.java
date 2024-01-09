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

        //This sets the choice box to contain the two options "Yes" and "No".

        workshopInternalChoiceBox.getItems().addAll("Yes", "No");

        //This sets the default value of the choice box to "Yes".

        workshopInternalChoiceBox.setValue("Yes");

        //This sets the choice box to contain all the locations in the Locations enum.

        workshopLocationChoiceBox.getItems().addAll(Locations.values());

        //This sets the default value of the choice box to "Stockholm".

        workshopLocationChoiceBox.setValue(Locations.Stockholm);

        //This creates a textfilter that only allows the user to enter letters and spaces in the workshop name text field.

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[a-zA-Z ]*")) {
                return change;
            }
            return null;
        };

        //This applies the textfilter to the workshop name text field.

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        workshopNameTextField.setTextFormatter(textFormatter);
    }
    @FXML

    //This code runs when the add workshop button is pressed. 

    private void handleAddWorkshopButton(){

        //This checks if the workshop name text field is empty. If it is, it displays an error message telling the user to enter a name for the workshop.

        if (workshopNameTextField.getText().isEmpty()) {
            addWorkshopErrorLabel.setText("Please enter a name for the workshop");
        } else {

            //This creates a new workshop object and sets its name, location and internal status to the values entered by the user.

            Workshop workshop = new Workshop();
            workshop.setWorkshopName(workshopNameTextField.getText());
            workshop.setWorkshopLocation(workshopLocationChoiceBox.getValue());
            if (workshopInternalChoiceBox.getValue().equals("Yes")) {
                workshop.setIsInternal(true);
            } else {
                workshop.setIsInternal(false);
            }

            //This adds the workshop to the workshop list in the main class.

            Main.companyWorkshopList.addWorkshop(workshop);

            //This displays an alert telling the user that the workshop has been added.

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Workshop added");
            alert.setHeaderText("Workshop added Successfully");
            alert.setContentText("Workshop " + workshop.getWorkshopName() + " has been added to the system");
            alert.showAndWait();

            //This closes the window.
            
            Stage stage = (Stage) addWorkshopButton.getScene().getWindow();
            stage.close();
        }
    }

}
