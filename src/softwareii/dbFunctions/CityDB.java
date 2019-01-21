/*
 */
package softwareii.dbFunctions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jay
 */
public class CityDB extends DB_Base {
    
    public int getCityId(String cityName) {
        int returnVal = 0;
        String selectString = "SELECT cityId FROM city WHERE city = :city";
        HashMap<String, String> params = new HashMap<>();
        params.put("city", cityName);
        
        try {
            ResultSet results = this.execute(selectString, params);
            while (results.next()) {
                returnVal = results.getInt("cityId");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnVal;
    }
    
    public String getCityName(int cityID) {
        String returnVal = "";
        String selectString = "SELECT cityId FROM city WHERE cityId = :cityId";
        HashMap<String, String> params = new HashMap<>();
        params.put("cityId", Integer.toString(cityID));
        try {
            ResultSet results = this.execute(selectString, params);
            while (results.next()) {
                returnVal = results.getString("city");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return returnVal;
    }
    
}
