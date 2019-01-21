package softwareii.controller_view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jay
 */
public class SoftwareII extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent pane = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        
        Scene scene = new Scene(pane);
        
        stage.setScene(scene);
        stage.setTitle("Schedule Manager");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
