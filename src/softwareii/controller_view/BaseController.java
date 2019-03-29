/*
 */
package softwareii.controller_view;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import softwareii.initializer.Initializer;

/**
 *
 * @author Jay
 */
public class BaseController {
    
    protected static String delim = java.io.File.separator;
    protected static Locale locale = Initializer.locale;
    protected final Properties props = getProps(locale);
    
    private Stage getStageInfo(ActionEvent e) {
        Stage stage = (Stage)this.getSceneInfo(e).getWindow();
        return stage;
    }
    
    protected Scene getSceneInfo(ActionEvent e) {
        Scene stage = (Scene)((Node)e.getSource()).getScene();
        return stage;
    }
    
    protected String rootPath() {
        return new File("").getAbsolutePath();
    }
    
    protected String resourcePath() {
        return new File("src/resources/").getAbsolutePath();
    }
    
    protected String logPath() {
        return new File("src/logfiles/").getAbsolutePath();
    }
    
    protected Properties getProps(Locale language) {
        Properties returnProps = new Properties();
        try {
            ResourceBundle resources = ResourceBundle.getBundle("resources/softwareii", language);
            //Allows us to intuitively find and set the resource results in the properties files for later use
            resources.keySet().stream().forEach(k -> returnProps.put(k, resources.getString(k)));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return returnProps;
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
    
    @FXML
    protected void loadAppointmentPage(ActionEvent e) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("AppointmentPage.fxml"));
        this.switchScene(pane, e);
    }
    
    @FXML
    protected void loadReportPage(ActionEvent e) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("ReportingPage.fxml"));
        this.switchScene(pane, e);
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
