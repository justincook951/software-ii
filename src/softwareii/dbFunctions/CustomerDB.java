/*
 */
package softwareii.dbFunctions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import softwareii.model.Customer;
import softwareii.model.BaseClass;
import softwareii.validator.Validator;

/**
 *
 * @author Jay
 */
public class CustomerDB extends DB_Base {
    
        protected static List<String> columnsNew = Arrays.asList(new String[]{
            "customerName", 
            "addressId",
            "active",
            "createdBy",
            "lastUpdateBy"
        });
        
        protected static List<String> columnsUpdate = Arrays.asList(new String[]{
            "customerName", 
            "active",
            "lastUpdateBy",
            "key"
        });
        protected static List<String> requiredFields = Arrays.asList(new String[] {
            "customerName",
            "active",
            "addressId",
            "createdBy",
            "lastUpdateBy",
            "customerId"
        });
    
    public ArrayList<Customer> getCustomers() {
        String selectString = "SELECT customerId, customerName, address, address2, cityId, city, countryId, country, postalCode, phone "
                + "FROM customer "
                + "INNER JOIN address USING (addressId) "
                + "INNER JOIN city USING (cityId) "
                + "INNER JOIN country USING (countryId) ";
        ArrayList<Customer> resultList = new ArrayList();
        try {
            ResultSet results = this.execute(selectString, new HashMap<>());
            while(results.next()) {
                int customerId = results.getInt("customerId");
                String newcustomerName = results.getString("customerName");
                String customerAddress1 = results.getString("address");
                String customerAddress2 = results.getString("address2");
                int customerCity = results.getInt("cityId");
                int customerCountry = results.getInt("countryId");
                int customerZip = results.getInt("postalCode");
                String customerPhone = results.getString("phone");
                Customer nextCustomer = new Customer(customerId, newcustomerName, customerAddress1, customerAddress2, customerCity, customerCountry, customerZip, customerPhone);
                String countryName = results.getString("country");
                nextCustomer.setCountryName(countryName);
                String cityName = results.getString("city");
                nextCustomer.setCityName(cityName);
                resultList.add(nextCustomer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultList;
    }
    
    public void newCustomer(String nameVal, String addressVal, String address2Val, int cityVal, int countryVal, int zipVal, String phoneVal) throws Exception {
        AddressDB addressdb = new AddressDB();
        Validator validator = new Validator();
        int existingAddress = addressdb.handleAddress(addressVal, address2Val, phoneVal, zipVal, cityVal, "create", 0);
        HashMap<String, String> custMap = new HashMap<>();
   
        custMap.put("customerName", nameVal);
        custMap.put("addressId", Integer.toString(existingAddress));
        custMap.put("active", "1");
        custMap.put("createdBy", BaseClass.getLoggedInUser());
        custMap.put("lastUpdateBy", BaseClass.getLoggedInUser());
        
        ArrayList<String> requiredList = new ArrayList<>(requiredFields);
        
        if (validator.containsNoEmpties(custMap, requiredList)) {
            String queryString = "INSERT INTO customer "
                    + "(customerName, addressId, active, createDate, createdBy, lastUpdateBy) "
                    + "VALUES"
                    + "(:customerName, :addressId, :active, now(), :createdBy, :lastUpdateBy)";
            this.run(queryString, custMap);
        }
        else {
            throw new Exception("Invalid input received. Please ensure all of your input is valid.");
        } 
    }
    
    public void updateCustomer(int customerID, String nameVal, String addressVal, String address2Val, int cityVal, int countryVal, int zipVal, String phoneVal) throws Exception {
        AddressDB addressdb = new AddressDB();
        Validator validator = new Validator();
        int addressId = 0;
        ResultSet customerData = this.getItemByID(customerID, customerIDName, customerDBName);
        while (customerData.next()) {
            addressId = customerData.getInt("addressId");
        }
        addressdb.handleAddress(addressVal, address2Val, phoneVal, zipVal, cityVal, "update", addressId);
        HashMap<String, String> custMap = new HashMap<>();
   
        custMap.put("customerName", nameVal);
        custMap.put("active", "1");
        custMap.put("lastUpdateBy", BaseClass.getLoggedInUser());
        custMap.put("customerId", Integer.toString(customerID));
        custMap.put("addressId", Integer.toString(addressId));
        
        ArrayList<String> requiredList = new ArrayList<>(requiredFields);
        if (validator.containsNoEmpties(custMap, requiredList)) {
            String queryString = "UPDATE customer "
                    + "SET customerName = :customerName, "
                    + "active = :active, "
                    + "lastUpdateBy = :lastUpdateBy, "
                    + "addressId = :addressId "
                    + "WHERE customerId = :customerId";
            this.run(queryString, custMap);
        }
    }
    
    public void deleteCustomer(int customerID) throws SQLException {
        AddressDB addressdb = new AddressDB();
        ResultSet customerData = this.getItemByID(customerID, customerIDName, customerDBName);
        int addressId = 0;
        while (customerData.next()) {
            addressId = customerData.getInt(addressIDName);
        }
        int addressCount = addressdb.getAddressCount(addressId);
        if (addressCount == 1) {
            //Only one customer has this address.
            this.deleteByID(addressId, addressIDName, addressDBName);
        }
        this.deleteByID(customerID, customerIDName, customerDBName);
    }
    
    protected void getCustomerByID(int customerID) {
        //Needs to return
        //Customer customer = new Customer();
    }
    
}
