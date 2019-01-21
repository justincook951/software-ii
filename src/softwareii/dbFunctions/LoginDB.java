package softwareii.dbFunctions;

import java.sql.ResultSet;
import java.util.HashMap;

/**
 *
 * @author Jay
 */
public class LoginDB extends DB_Base {
    
    public boolean isValidUser(String username, String password) {
        boolean isValid = false;
        String loginQuery = "SELECT COUNT(*) AS user FROM user WHERE userName = :userName AND password = :password";
        HashMap<String, String> params = new HashMap<>();
        params.put("userName", username);
        params.put("password", password);
        try {
            ResultSet results = this.execute(loginQuery, params);
            while (results.next()) {
                int count = results.getInt("user");
                if (count == 1) {
                    isValid = true;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }
    
}
