package lu.ics.se;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Image image = new Image(getClass().getResourceAsStream("/firstPicture1.png"));
            Parent root = FXMLLoader.load(getClass().getResource("FirstPage.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
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