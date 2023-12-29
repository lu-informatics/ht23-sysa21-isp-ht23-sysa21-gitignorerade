package lu.ics.se;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import lu.ics.se.models.classes.*;

public class Main extends Application {

    public static VehicleManifest companyVehicleManifest = new VehicleManifest();

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/mainview.fxml"));
            primaryStage.setTitle("Logistics System");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

        public static void main (String[]args){
            launch(args);
        }
    }