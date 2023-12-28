package lu.ics.se;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args); // Pass command-line arguments to the application
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file. Adjust the path if your FXML file is in a different package
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lu/ics/se/MainViewVikingExpress.fxml"));
            Parent root = loader.load();

            // Create the scene with the loaded root node and set the window size
            Scene scene = new Scene(root, 800, 600); // Adjust the size as per your layout requirements

            // Set the title and scene, then show the primary stage
            primaryStage.setTitle("Viking Express");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions here (e.g., show an alert to the user)
        }
    }
}

