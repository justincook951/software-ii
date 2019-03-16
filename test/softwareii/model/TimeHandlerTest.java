/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareii.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import softwareii.initializer.Initializer;

/**
 *
 * @author Jay
 */
public class TimeHandlerTest {
    
    public TimeHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testBuildLocalDateTime() {
        System.out.println("buildLocalDate");
        String dateTimeString = "2019-02-04 18:00";
        LocalDateTime expResult = LocalDateTime.of(2019, 2, 4, 18, 0, 0, 0);
        LocalDateTime result = TimeHandler.buildLocalDateTime(dateTimeString);
        assertEquals(expResult, result);
    }

    @Test
    public void testConvertLocalZDTtoUTC() {
        System.out.println("convertLocalZDTtoUTC");
        ZoneId utc = ZoneId.of("UTC");
        ZonedDateTime expResult = ZonedDateTime.of(2019, 2, 4, 10, 0, 0, 0, utc);
        
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime eastZDT = ZonedDateTime.of(2019, 2, 4, 5, 0, 0, 0, zone);
        ZonedDateTime result = TimeHandler.convertLocalZDTtoUTC(eastZDT);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testConvertUTCZDTtoLocal() {
        System.out.println("convertUTCZDTtoLocal");
        ZoneId utc = ZoneId.of("UTC");
        ZoneId zone = ZoneId.systemDefault();
        
        ZonedDateTime expResult = ZonedDateTime.of(2019, 2, 4, 5, 0, 0, 0, zone);
        ZonedDateTime utcZDT = ZonedDateTime.of(2019, 2, 4, 10, 0, 0, 0, utc);
        ZonedDateTime result = TimeHandler.convertUTCZDTtoLocal(utcZDT);
        assertEquals(expResult, result);
    }

    @Test
    public void testBuildInsertFromZDT() {
        System.out.println("buildInsertFromZDT");
        ZoneId utc = ZoneId.of("UTC");
        ZonedDateTime utcZDT = ZonedDateTime.of(2019, 2, 4, 10, 0, 0, 0, utc);
        String expResult = "2019-02-04 10:00:00";
        String result = TimeHandler.buildInsertFromZDT(utcZDT);
        assertEquals(expResult, result);
    }

    @Test
    public void testZDTFromQueryString() {
        System.out.println("ZDTFromQueryString");
        String inputString = "2019-02-04 02:13:07";
        String formatString = "yyyy-MM-dd HH:mm:ss";
        ZoneId utc = ZoneId.of("UTC");
        ZonedDateTime expResult = ZonedDateTime.of(2019, 2, 4, 2, 13, 7, 0, utc);
        ZonedDateTime result = TimeHandler.ZDTFromQueryString(inputString, formatString);
        assertEquals(expResult, result);
    }

    /**
     * Test of buildUTCZonedDateTime method, of class TimeHandler.
     */
    @Test
    public void testBuildUTCZonedDateTime() {
        System.out.println("buildUTCZonedDateTime");
        LocalDateTime utcDateTime = LocalDateTime.of(2019, 2, 4, 18, 0, 0, 0);
        ZoneId utc = ZoneId.of("UTC");
        ZonedDateTime expResult = ZonedDateTime.of(2019, 2, 4, 18, 0, 0, 0, utc);
        ZonedDateTime result = TimeHandler.buildUTCZonedDateTime(utcDateTime);
        assertEquals(expResult, result);
    }

    /**
     * Test of buildLocalZonedDateTime method, of class TimeHandler.
     */
    @Test
    public void testBuildLocalZonedDateTime() {
        System.out.println("buildLocalZonedDateTime");
        LocalDateTime localDateTime = LocalDateTime.of(2019, 2, 4, 18, 0, 0, 0);
        ZoneId zone = Initializer.currentZone;
        ZonedDateTime expResult = ZonedDateTime.of(2019, 2, 4, 18, 0, 0, 0, zone);
        ZonedDateTime result = TimeHandler.buildLocalZonedDateTime(localDateTime);
        assertEquals(expResult, result);
    }
    
}
