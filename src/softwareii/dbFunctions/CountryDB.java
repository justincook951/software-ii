/*
 */
package softwareii.dbFunctions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jay
 */
public class CountryDB extends DB_Base {
    
    public ArrayList<String> getCountries() {
        String selectString = "SELECT countryId, country "
                + "FROM country";
        ArrayList<String> resultList = new ArrayList();
        try {
            ResultSet results = this.execute(selectString, new HashMap<>());
            while(results.next()) {
                String countryName = results.getString("country");
                resultList.add(countryName);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Some error");
        }
        return resultList;
    }
        
    public String getCountryName(int countryID) {
        String returnVal = "";
        try {
            ResultSet results = this.getItemByID(countryID, "countryId", "country");
            while (results.next()) {
                returnVal = results.getString("country");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnVal;
    }
    
    public int getCountryId(String countryName) {
        int returnVal = 0;
        String selectString = "SELECT countryId FROM country WHERE country = :country";
        HashMap<String, String> params = new HashMap<>();
        params.put("country", countryName);
        try {
            ResultSet results = this.execute(selectString, params);
            while (results.next()) {
                returnVal = results.getInt("countryId");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnVal;
    }
    
    public ArrayList<String> getCountryCities(String countryName) {
        String selectString = "SELECT city FROM city "
                + "JOIN country USING (countryId) "
                + "WHERE country = :country";
        HashMap<String, String> params = new HashMap<>();
        params.put("country", countryName);
        
        ArrayList<String> resultList = new ArrayList();
        try {
            ResultSet results = this.execute(selectString, params);
            while (results.next()) {
                String cityName = results.getString("city");
                resultList.add(cityName);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultList;
    }
    
}
