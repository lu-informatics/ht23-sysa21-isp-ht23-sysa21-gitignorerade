package lu.ics.se.controllers;

import javafx.fxml.Initializable;
import javafx.geometry.Pos;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import lu.ics.se.models.enums.Locations;
import lu.ics.se.models.classes.Workshop;



public class EditWorkshopController implements Initializable{


    public interface OnCloseListener{
        void onClose();
    }
    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.onCloseListener = onCloseListener;
    }
    private OnCloseListener onCloseListener;

    private Workshop workshop;

    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
        String workshopType = workshop.getIsInternal() ? "Internal" : "External";
        displayWorkshopInfoLabel.setText("Current Workshop: " + "\n" + "Workshop Name: " + workshop.getWorkshopName() + "\n" + "Workshop Location: " + workshop.getWorkshopLocation() + "\n" + "Internal or External: " + workshopType);
        displayWorkshopInfoLabel.setAlignment(Pos.CENTER);
        
        typeOfEditChoiceBox.getItems().addAll("Change Name", "Change Location", "Change Internal/External");
        typeOfEditChoiceBox.setValue("Change Name");

        editIsInternalChoiceBox.setVisible(false);
        editIsInternalChoiceBox.getItems().addAll("Internal", "External");
        editIsInternalChoiceBox.setValue(workshopType);

        selectLocationChoiceBox.getItems().addAll(Locations.values());
        selectLocationChoiceBox.setValue(workshop.getWorkshopLocation());
        selectLocationChoiceBox.setVisible(false);
    }

    @FXML
    private ChoiceBox<Locations> selectLocationChoiceBox;

    @FXML
    private ChoiceBox<String> typeOfEditChoiceBox;

    @FXML
    private ChoiceBox<String> editIsInternalChoiceBox;

    @FXML
    private TextField nameTextField;

    @FXML
    private Label displayWorkshopInfoLabel;

    @FXML
    private Button saveAndApplyChangesButton;

    public void handleSaveAndApplyChangesButton(){
        if (typeOfEditChoiceBox.getValue().equals("Change Name")) {
            workshop.setWorkshopName(nameTextField.getText());
        } else if (typeOfEditChoiceBox.getValue().equals("Change Location")) {
            workshop.setWorkshopLocation(selectLocationChoiceBox.getValue());
        } else if (typeOfEditChoiceBox.getValue().equals("Change Internal/External")) {
            if (editIsInternalChoiceBox.getValue().equals("Internal")) {
                workshop.setIsInternal(true);
            } else {
                workshop.setIsInternal(false);
            }
        }
        String workshopType = workshop.getIsInternal() ? "Internal" : "External";
        displayWorkshopInfoLabel.setText("Current Workshop: " + "\n" + "Workshop Name: " + workshop.getWorkshopName() + "\n" + "Workshop Location: " + workshop.getWorkshopLocation() + "\n" + "Internal or External: " + workshopType);
        if (onCloseListener != null) {
            onCloseListener.onClose();
        }
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeOfEditChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Change Name")) {
                nameTextField.setVisible(true);
                editIsInternalChoiceBox.setVisible(false);
                selectLocationChoiceBox.setVisible(false);
            } else if (newValue.equals("Change Location")) {
                nameTextField.setVisible(false);
                editIsInternalChoiceBox.setVisible(false);
                selectLocationChoiceBox.setVisible(true);
            } else if (newValue.equals("Change Internal/External")) {
                nameTextField.setVisible(false);
                editIsInternalChoiceBox.setVisible(true);
                selectLocationChoiceBox.setVisible(false);
            }
        });


    }



}
