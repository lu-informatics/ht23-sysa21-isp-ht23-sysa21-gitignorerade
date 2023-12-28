package lu.ics.se;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("MainViewVikingExpress.fxml"));
        Parent root = loader.load();

        
        
        Scene scene = new Scene(root, 800, 600); // Set your preferred width and height

        primaryStage.setTitle("Viking express");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
