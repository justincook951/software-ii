/*
 */
package softwareii.controller_view;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import softwareii.dbFunctions.AppointmentDB;
import softwareii.dbFunctions.CustomerDB;
import softwareii.initializer.Initializer;
import softwareii.model.Appointment;
import softwareii.model.Customer;

/**
 * FXML Controller class
 *
 * @author Jay
 */
public class MainPageController extends BaseController implements Initializable {
    
    private CustomerDB customerDB;
    private ObservableList<Customer> customerWrapper;
    @FXML private TableColumn<Customer, Integer> customerIDColCust;
    @FXML private TableColumn<Customer, String> customerNameCol;
    @FXML private TableView<Customer> customerTV;
    private AppointmentDB appointmentDB;
    private ObservableList<Appointment> appointmentWrapper;
    @FXML private TableView<Appointment> appointmentTV;
    @FXML private TableColumn<Customer, Integer> customerIDColAppt;
    @FXML private TableColumn<Customer, Integer> apptDateCol;
    @FXML private TableColumn<Customer, Integer> apptType;
    @FXML private Label warningLabel;
    @FXML private Label comingApptLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        warningLabel.setText("");
        this.customerDB = Initializer.customerdb;
        this.appointmentDB = Initializer.appointmentdb;
        this.populateCustomers();
        this.populateAppointments();
        if (!this.hasUpcomingAppointment()) {
            this.comingApptLabel.setText(props.getProperty("nofifteenMinutes"));
        }
        else {
            this.comingApptLabel.setText(props.getProperty("fifteenMinutes"));
        }
    }
    
    protected void populateCustomers() {
        customerWrapper = FXCollections.observableArrayList(
            customerDB.getCustomers()
        );
        customerIDColCust.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerTV.getItems().setAll(customerWrapper);
    }
    
    protected void populateAppointments() {
        appointmentWrapper = FXCollections.observableArrayList(
            appointmentDB.getAppointments()
        );
        customerIDColAppt.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptDateCol.setCellValueFactory(new PropertyValueFactory<>("apptDate"));
        apptType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentTV.getItems().setAll(appointmentWrapper);
    }
    
    @FXML
    protected void exitProgram() {
        Platform.exit();
        System.exit(0);
    }
    
    @FXML
    protected void deleteCustomer() {
        warningLabel.setText("");
        Customer selectedCustomer = customerTV.getSelectionModel().getSelectedItem();
        int selectedID = 0;
        try {
           selectedID = selectedCustomer.getCustomerID(); 
           try {
                customerDB.deleteCustomer(selectedID);
                //Refresh the view
                populateCustomers();
            }
            catch (SQLException exception) {
                warningLabel.setText("SQL Error on deleteCustomer.");
            }
        }
        catch (NullPointerException exception) {
            warningLabel.setText(props.getProperty("noCustomer"));
        }
    }
    
    @FXML
    protected void deleteAppointment() {
        Appointment selectedAppt = appointmentTV.getSelectionModel().getSelectedItem();
        int selectedID = 0;
        try {
           selectedID = selectedAppt.getAppointmentId(); 
           try {
                appointmentDB.deleteAppointment(selectedID);
                //Refresh the view
                populateAppointments();
            }
            catch (SQLException exception) {
                warningLabel.setText("SQL Error on deleteAppointment.");
            }
        }
        catch (NullPointerException exception) {
            warningLabel.setText(props.getProperty("noAppointment"));
        }
    }
    
    protected boolean hasUpcomingAppointment() {
        return (appointmentDB.getUpcomingAppointmentCount() > 0);
    }
    
}
