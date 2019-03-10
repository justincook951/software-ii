package softwareii.model;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import softwareii.initializer.Initializer;

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
    
    public static LocalDateTime localStringToLocalDateTime(String inpString) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(inpString, formatter);
        return dateTime;
    }
    
    public String unixToLocalTimeString(long unixTime) {
        //Takes a unix long and converts it to a presentable string in the user's local time.
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime datetime = LocalDateTime.ofInstant(Instant.ofEpochMilli(unixTime), 
                                Initializer.zoneOffset); 
        String formattedDate = datetime.format(format);
        return formattedDate;
    }
    
    public static Long localTimeToUTCUnix(LocalDateTime date) {
        //Takes a String that's in a presentable format in the user's local time and converts it to a unix long
        long unixTime = date.toEpochSecond(Initializer.zoneOffset.UTC) * 1000L;
        return unixTime;
    }
    
    public static String unixTimeToTimestamp(long unixTime) {
        //Takes a unix long and converts it to a presentable string in UTC time.
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime datetime = LocalDateTime.ofInstant(Instant.ofEpochMilli(unixTime), 
                                Initializer.zoneOffset.UTC); 
        String formattedDate = datetime.format(format);
        return formattedDate;
    }

            
}
