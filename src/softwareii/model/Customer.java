/*
 */
package softwareii.model;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import softwareii.dbFunctions.DB_Base;

/**
 *
 * @author Jay
 */
public class Customer extends BaseClass {
    
    protected List<String> customerDBColumns;
    
    protected int customerID;
    protected String customerName;
    protected String address1;
    protected String address2;
    protected int cityId;
    protected String cityName;
    protected int countryId;
    protected String countryName;
    protected int zip;
    protected String phone;
    protected DB_Base db;
    
    public Customer(int customerID, String customerName, String address1, String address2, int cityId, int countryId, int zip, String phone) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address1 = address1;
        this.address2 = address2;
        this.cityId = cityId;
        this.countryId = countryId;
        this.zip = zip;
        this.phone = phone;
        this.customerDBColumns = Arrays.asList("customerId", "customerName", "addressId", "active", "createDate", "createdBy", "lastUpdate", "lastUpdateBy");
        this.db = new DB_Base();
    }

    public int getCustomerID() { return customerID; }
    //customerID should be immutable for our purposes
    //public void setCustomerID(int customerID) { this.customerID = customerID; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public String getAddress1() { return address1; }
    public void setAddress1(String address) { this.address1 = address; }
    
    public String getAddress2() { return address2; }
    public void setAddress2(String address) { this.address2 = address; }
    
    public int getCityId() { return cityId; }
    public String getCityName() { 
        try {
            ResultSet results = db.getItemByID(this.cityId, "cityId", "city");
            while (results.next()) {
                cityName = results.getString("city");
            } 
        }
        catch (Exception e) {
            cityName = "";
        }
        return cityName; 
    }
    public void setCity(int cityId) { this.cityId = cityId; }
    public void setCityName(String cityName) { this.cityName = cityName; }
    
    public int getCountryId() { return countryId; }
    public String getCountryName() {  
        try {
            ResultSet results = db.getItemByID(this.countryId, "countryId", "country");
            while (results.next()) {
                countryName = results.getString("country");
            } 
        }
        catch (Exception e) {
            countryName = "";
        }
        return countryName; 
    }
    public void setCountry(int countryId) { this.countryId = countryId; }
    public void setCountryName(String countryName) { this.countryName = countryName; }
    
    
    public int getZip() { return zip; }
    public void setZip(int zip) { this.zip = zip; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
}
