/*
 */
package softwareii.controller_view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

/**
 * FXML Controller class
 *
 * @author Jay
 */
public class MainPageController extends BaseController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
   
    
    @FXML
    protected void loadAppointmentPage(ActionEvent e) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("AppointmentPage.fxml"));
        this.switchScene(pane, e);
    }
    
    @FXML
    protected void exitProgram() {
        Platform.exit();
        System.exit(0);
    }
    
}
