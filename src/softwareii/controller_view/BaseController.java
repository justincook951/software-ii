/*
 */
package softwareii.controller_view;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Jay
 */
public class BaseController {
    
    private Stage getStageInfo(ActionEvent e) {
        Stage stage = (Stage)this.getSceneInfo(e).getWindow();
        return stage;
    }
    
    protected Scene getSceneInfo(ActionEvent e) {
        Scene stage = (Scene)((Node)e.getSource()).getScene();
        return stage;
    }
    
    /*----------------------------------------
    -----------Navigation functions-----------
    -----------------------------------------*/
    @FXML
    protected void returnHome(ActionEvent e) {
        try {
            Parent pane = loadMainPane();
            switchScene(pane, e);
        } catch (IOException ex) {
            //How did you get here!?
        }
    }
    
    @FXML
    protected void loadUserPage(ActionEvent e) {
        try {
            Parent pane = FXMLLoader.load(getClass().getResource("CustomerPage.fxml"));
            this.switchScene(pane, e);     
        }
        catch (IOException ex) {
            //How did you get here!?
        }
    }
    
    protected void switchScene(Parent pane, ActionEvent e) {
        Scene switchScene = new Scene(pane);
        Stage stage = this.getStageInfo(e);
        stage.setScene(switchScene);
    }
    
    protected Parent loadMainPane() throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        return pane;
    }
    
}
