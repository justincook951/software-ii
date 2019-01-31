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
import softwareii.model.BaseClass;
import softwareii.validator.Validator;

/**
 *
 * @author Jay
 */
public class AddressDB extends DB_Base {
    
    
    
    protected static List<String> columns = Arrays.asList(new String[]{
        "address", 
        "address2",
        "cityId",
        "postalCode",
        "phone",
        "createDate",
        "createdBy",
        "lastUpdateBy"
    });
    
    protected static List<String> requiredFields = Arrays.asList(new String[] {
        "customerName",
        "addressId",
        "createdBy",
        "lastUpdateBy"
    });
    
    public int findAddressId(HashMap addressData) {
        //addressId starts at 1. 0 is no result.
        int addressVal = 0;
        String queryString = "SELECT addressId "
                + "FROM address "
                + "WHERE " + this.generateWhere(addressData);
        
        try {
            ResultSet results = this.execute(queryString, addressData);
            while (results.next()) {
                addressVal = results.getInt("addressId");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error on findaddress");
        }
        return addressVal;
    }
    
    public int createNewAddress(HashMap addressData) throws Exception {
        int result = 0;
        //Ugh.
        ArrayList<String> requiredList = new ArrayList<>(requiredFields);
        Validator validator = new Validator();
        
        if (validator.containsNoEmpties(addressData, requiredList)) {
            String queryString = "INSERT INTO address "
                    + "(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdateBy) "
                    + "VALUES "
                    + "(:address, :address2, :cityId, :postalCode, :phone, now(), :createdBy, :lastUpdateBy)";
            result = this.insertAndGetID(queryString, addressData);
        }
        else {
            throw new Exception("Invalid input received. Please ensure all of your input is valid.");
        } 
        return result;
    }
    
    public int updateOldAddress(HashMap addressData) throws Exception {
        int result = 0;
        ArrayList<String> columnsList = new ArrayList<>(columns);
        columnsList.remove("createDate");
        columnsList.remove("createdBy");
        columnsList.add("key");
        String queryString = "UPDATE address "
                + "SET address = :address, "
                + "address2 = :address2, "
                + "phone = :phone, "
                + "postalCode = :postalCode, "
                + "cityId = :cityId, "
                + "lastUpdateBy = :lastUpdateBy "
                + "WHERE addressId = :addressId";
        this.run(queryString, addressData);
        return result;
    }
    
    public int getAddressCount(int addressId) {
        String queryString = "SELECT count(*) AS count FROM customer WHERE addressId = :addressId";
        HashMap<String, String> params = new HashMap<>();
        params.put("addressId", Integer.toString(addressId));
        int countResult = 1;
        try {
            ResultSet results = this.execute(queryString, params);
            while (results.next()) {
                countResult = results.getInt("count");
            }
        }
        catch (SQLException e) {
            //No action needed. Just return 1. (shouldn't happen)
        }
        return countResult;
    }
    
    public int handleAddress(String addressVal, String address2Val, String phoneVal, int zipVal, int cityVal, String purpose, int addressId) throws Exception {
        HashMap<String, String> addressMap = new HashMap<>();
        
        addressMap.put("address", addressVal);
        addressMap.put("address2", address2Val);
        addressMap.put("phone", phoneVal);
        addressMap.put("postalCode", Integer.toString(zipVal));
        addressMap.put("cityId", Integer.toString(cityVal));
        addressMap.put("lastUpdateBy", BaseClass.getLoggedInUser());
                    
        int existingAddress = findAddressId(addressMap);
        
        //Address didn't exist as it was queried
        if (existingAddress == 0 && purpose.equals("create")) {
            //To avoid accidentally querying by these values
            addressMap.put("createdBy", BaseClass.getLoggedInUser());
            existingAddress = createNewAddress(addressMap);
        }
        else if (existingAddress == 0 && purpose.equals("update")) {
            addressMap.put("addressId", Integer.toString(addressId));
            existingAddress = updateOldAddress(addressMap);
        }
        return existingAddress;
    }
    
}