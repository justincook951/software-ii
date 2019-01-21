package softwareii.controller_view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import softwareii.dbFunctions.LoginDB;
import java.sql.Timestamp;
import softwareii.model.BaseClass;

/**
 * FXML Controller class
 *
 * @author Jay
 */
public class LoginPageController extends BaseController implements Initializable {

    @FXML private Label errorLabel;
    @FXML private PasswordField loginPasswordField;
    @FXML private TextField usernameField;
    private LoginDB logindb;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.logindb = new LoginDB();
    }
    
    @FXML
    private void attemptLogin(ActionEvent e) {
        String attemptedUser = usernameField.getText();
        String attemptedPassword = loginPasswordField.getText();
        if (logindb.isValidUser(attemptedUser, attemptedPassword)) {
            logLoginAttempt(attemptedUser, true);
            BaseClass.setLoggedInUser(attemptedUser);
            this.returnHome(e);
        }
        else {
            logLoginAttempt(attemptedUser, false);
            String frenchWarning = "Nom d'utilisateur et mot de passe ne correspondent pas.";
            String englishWarning = "Username and password do not match.";
            errorLabel.setText(frenchWarning);
            errorLabel.setVisible(true);
        }
    }
    
    private void logLoginAttempt(String user, boolean successful) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String successString = "unsuccessful";
        if (successful) {
            successString = "successful";
        }
        String directoryLoc = new File("src/logfiles/").getAbsolutePath();
        try {
            File directory = new File(directoryLoc);
            if (! directory.exists()){
                directory.mkdir();
            }
            File logfile = new File(directoryLoc + "/login.log");
            logfile.createNewFile(); // Does nothing if file exists
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logfile,true))) {
                writer.write("Attempted login was " + successString + " by user " + user + " at " + timestamp.toString() + ".");
                writer.newLine();
            }
        } catch (IOException ex) {
            System.out.println("Threw error");
            Logger.getLogger(LoginPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
