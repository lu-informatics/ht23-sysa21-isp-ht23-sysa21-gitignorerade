package lu.ics.se;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the main FXML file which contains the TabPane
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainViewVikingExpress.fxml"));
            Parent root = loader.load();

            // Set the scene to the stage with the loaded FXML layout
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Viking Express"); // Set the title of the window
            primaryStage.show(); // Display the stage
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
