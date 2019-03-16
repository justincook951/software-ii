package softwareii.model;

import java.util.HashMap;
import java.util.Map;

public class BaseClass {
    
    protected static String loggedInUser = "Overlord";
    
    public static String getLoggedInUser() {
        return loggedInUser;
    }
    
    public static void setLoggedInUser(String user) {
        loggedInUser = user;
    }
    
    public static HashMap<String, String> convertToLower(HashMap<String, String> inbMap) { 
       HashMap<String, String> allLower = new HashMap<>();
       for (Map.Entry<String, String> entry : inbMap.entrySet()) {
           String valKey = entry.getKey().toLowerCase();
           String value = entry.getValue();
           allLower.put(valKey, value);
       }
       return allLower;
    }            
}
