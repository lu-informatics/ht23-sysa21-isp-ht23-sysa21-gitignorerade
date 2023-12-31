package lu.ics.se;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) {
        try {
            VBox vbox = new VBox();

            // Load the first FXML file
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root1 = loader1.load();
            vbox.getChildren().add(root1);

            // Set the scene to the stage with the loaded FXML layouts
            primaryStage.setScene(new Scene(vbox));
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