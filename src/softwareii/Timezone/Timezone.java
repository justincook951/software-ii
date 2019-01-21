/*
 */
package softwareii.Timezone;

import java.time.*;  
import java.util.Date;
import java.sql.Timestamp;

public class Timezone {
    
    public static Timestamp getSQLTime() {
        OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
        Timestamp ts = Timestamp.valueOf(LocalDateTime.ofInstant(utc.toInstant(), ZoneOffset.UTC));
        return ts;
    }
    
    //public static Date SQLtoLocal(Timestamp sqlTime) {
        
    //}
    
}
