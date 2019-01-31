package softwareii.controller_view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import softwareii.dbFunctions.CustomerDB;
import softwareii.model.Appointment;
import softwareii.model.Customer;

public class AppointmentPageController extends BaseController implements Initializable {
    
    @FXML private ChoiceBox customerDropdown;
    @FXML private CustomerDB customerDB;
    private ObservableList<Appointment> appointmentWrapper;
    @FXML private TableColumn<Customer, Integer> customerIDCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.customerDB = new CustomerDB();
        try {
            this.populateCustomers();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void populateCustomers() {
        ArrayList<Customer> customers = customerDB.getCustomers();
        ArrayList<String> custNames = new ArrayList<>();
        for (Customer customer : customers) {
            custNames.add(customer.getCustomerName() + "(" + customer.getCustomerID() + ")");
        }
        customerDropdown.setItems(FXCollections.observableArrayList(
            custNames
        ));
        /*appointmentWrapper = FXCollections.observableArrayList(
            customers
        );
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address1"));
        addressCol2.setCellValueFactory(new PropertyValueFactory<>("address2"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("cityName"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        zipCol.setCellValueFactory(new PropertyValueFactory<>("zip"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerTV.getItems().setAll(customerWrapper);*/
    }
    
    
    
}
