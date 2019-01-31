/*
 */
package softwareii.controller_view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import softwareii.dbFunctions.CustomerDB;
import softwareii.model.Customer;

/**
 * FXML Controller class
 *
 * @author Jay
 */
public class MainPageController extends BaseController implements Initializable {
    
    private CustomerDB customerDB;
    private ObservableList<Customer> customerWrapper;
    @FXML private TableColumn<Customer, Integer> customerIDCol;
    @FXML private TableColumn<Customer, String> customerNameCol;
    @FXML private TableView<Customer> customerTV;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.customerDB = new CustomerDB();
        customerWrapper = FXCollections.observableArrayList(
            customerDB.getCustomers()
        );
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerTV.getItems().setAll(customerWrapper);
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
