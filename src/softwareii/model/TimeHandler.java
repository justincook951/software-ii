/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareii.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import softwareii.initializer.Initializer;

/**
 *
 * @author Jay
 */
public class TimeHandler {
    
    public static ZonedDateTime buildUTCZonedDateTime(LocalDateTime utcDateTime) {
        ZoneId utc = ZoneId.of("UTC");
        return ZonedDateTime.of(utcDateTime, utc);
    }
    
    public static ZonedDateTime buildLocalZonedDateTime(LocalDateTime localDateTime) {
        ZoneId localZone = Initializer.currentZone;
        return ZonedDateTime.of(localDateTime, localZone);
    }
    
    public static LocalDateTime buildLocalDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateTimeString, formatter);
    }
    
    public static ZonedDateTime convertLocalZDTtoUTC(ZonedDateTime LocalZDT) {
        //Initializer.currentZone;
        ZoneId utc = ZoneId.of("UTC");
        ZonedDateTime utcTime = LocalZDT.withZoneSameInstant(utc);
        return utcTime;
    }
    
    public static ZonedDateTime convertUTCZDTtoLocal(ZonedDateTime UTCZDT) {
        //Initializer.currentZone;
        ZonedDateTime localTime = UTCZDT.withZoneSameInstant(Initializer.currentZone);
        return localTime;
    }
    
    public static String buildInsertFromZDT(ZonedDateTime UTCZonedDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return UTCZonedDateTime.format(formatter);
    }
    
    public static ZonedDateTime ZDTFromQueryString(String inputString, String formatString) {
        ZonedDateTime inboundZDT = ZonedDateTime.parse(inputString, DateTimeFormatter.ofPattern(formatString).withZone(ZoneId.of("UTC")));
        return inboundZDT;
    }
    
}
