package softwareii.controller_view;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import softwareii.model.Customer;
import softwareii.dbFunctions.CustomerDB;
import softwareii.dbFunctions.CityDB;
import softwareii.dbFunctions.CountryDB;
import softwareii.dbFunctions.AddressDB;

public class CustomerPageController extends BaseController implements Initializable {
    
    private CustomerDB customerDB;
    private CityDB cityDB;
    private CountryDB countryDB;
    private AddressDB addressDB;

    @FXML protected TextField customerID;
    @FXML protected TextField customerName;
    @FXML protected TextField address;
    @FXML protected TextField address2;
    @FXML protected ChoiceBox<String> city;
    @FXML protected ChoiceBox<String> country;
    @FXML protected TextField zip;
    @FXML protected TextField phone;
    @FXML protected boolean performUpdate;
    
    private ArrayList<String> countryWrapper;
    private List<Integer> countryInts;
    private ArrayList<String> cityWrapper;
    private List<Integer> cityInts;
    
    private ObservableList<Customer> customerWrapper;
    @FXML private TableColumn<Customer, Integer> customerIDCol;
    @FXML private TableColumn<Customer, String> customerNameCol;
    @FXML private TableColumn<Customer, String> addressCol;
    @FXML private TableColumn<Customer, String> addressCol2;    
    @FXML private TableColumn<Customer, String> cityCol;
    @FXML private TableColumn<Customer, String> countryCol;
    @FXML private TableColumn<Customer, Integer> zipCol;
    @FXML private TableColumn<Customer, String> phoneCol;
    @FXML private TableView<Customer> customerTV;
    @FXML private Label warningLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.performUpdate = false;
        this.customerDB = new CustomerDB();
        this.cityDB = new CityDB();
        this.countryDB = new CountryDB();
        this.addressDB = new AddressDB();
        try {
            this.populateCustomers();
            this.populateCountries();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        warningLabel.setText("");
    }
    
    @FXML protected void saveCustomer(ActionEvent e) {
        warningLabel.setText("");
        String nameVal = customerName.getText();
        String addressVal = address.getText();
        String address2Val = address2.getText();
        String cityStr = city.getValue();
        int cityVal = cityDB.getCityId(cityStr);
        String countryStr = country.getValue();
        int countryVal = countryDB.getCountryId(countryStr);
        int zipVal = 0;
        try {
            zipVal = (int)Integer.parseInt(zip.getText());
        }
        catch (Exception ex) {
            //If value is blank, it'll be handled by our custom validator to throw meaningful exceptions
        }
        String phoneVal = phone.getText();
        if (this.performUpdate) {
            //Update existing customer value
            int idVal = (int)Integer.parseInt(customerID.getText());
            try {
                customerDB.updateCustomer(idVal, nameVal, addressVal, address2Val, cityVal, countryVal, zipVal, phoneVal);  
                //Clear input and refresh the view
                this.loadUserPage(e);
            }
            catch (Exception ex) {
                //Test code pls ignore
                warningLabel.setText(ex.getMessage());
            }
        }
        else {
            //Create new customer using provided values
            try {
                customerDB.newCustomer(nameVal, addressVal, address2Val, cityVal, countryVal, zipVal, phoneVal);    
                //Clear input and refresh the view
                this.loadUserPage(e);
            }
            catch (Exception ex) {
                    //Test code pls ignore
                warningLabel.setText(ex.getMessage());
            }
        }
    }
    
    @FXML protected void editCustomerClicked() {
        warningLabel.setText("");
        Customer selectedCustomer = customerTV.getSelectionModel().getSelectedItem();
        customerID.setText(Integer.toString(selectedCustomer.getCustomerID()));
        customerName.setText(selectedCustomer.getCustomerName());
        address.setText(selectedCustomer.getAddress1());
        address2.setText(selectedCustomer.getAddress2());
        country.setValue(selectedCustomer.getCountryName());
        updateCitiesCB();
        city.setValue(selectedCustomer.getCityName());     
        zip.setText(Integer.toString(selectedCustomer.getZip()));
        phone.setText(selectedCustomer.getPhone());
        this.performUpdate = true;
    }
    
    protected void populateCustomers() {
        customerWrapper = FXCollections.observableArrayList(
            customerDB.getCustomers()
        );
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address1"));
        addressCol2.setCellValueFactory(new PropertyValueFactory<>("address2"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("cityName"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        zipCol.setCellValueFactory(new PropertyValueFactory<>("zip"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerTV.getItems().setAll(customerWrapper);
    }
    
    protected void populateCountries() {
        this.countryWrapper = new ArrayList();
        country.setItems(FXCollections.observableArrayList(
            countryDB.getCountries()
        ));
    }
    
    @FXML protected void updateCitiesCB() {
        //Clear previous iteration
        this.cityWrapper = null;
        this.cityWrapper = new ArrayList();
        String selectedCountry = (String)country.getSelectionModel().getSelectedItem();
        city.setItems(FXCollections.observableArrayList(
            countryDB.getCountryCities(selectedCountry)
        ));
    }
    
    @FXML protected void deleteCustomer(ActionEvent e) {
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
            warningLabel.setText("No customer selected.");
        }
    }
    
}
