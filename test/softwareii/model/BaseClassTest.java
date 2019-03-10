/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareii.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
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
public class BaseClassTest {
    
    public BaseClassTest() {
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
    public void testUnixToLocalTimeString() {
        //Get a Long value that represents a Unix UTC timestamp and convert it to a Local Time String
        long unixTime = 1549249987000L;
        BaseClass instance = new BaseClass();
        String expResult = "2019-02-03 20:13:07";
        String result = instance.unixToLocalTimeString(unixTime);
        assertEquals(expResult, result);
    }

    /**
     * Test of localTimeToUnix method, of class BaseClass.
     */
    @Test
    public void testLocalTimeToUnix() {
        String localTimeStr = "2019-02-03 20:13:07";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(localTimeStr, formatter);
        //dateTime.
        //We've handed a timestamp in local time (for me, MST, -7) and now we anticipate the subtraction of 7 hours
        ZoneId zoneId = ZoneId.ofOffset("UTC", ZoneOffset.ofHours(-7));
        long epoch = dateTime.atZone(zoneId).toEpochSecond()*1000L;
        System.out.println("I convered the localTimeStr to unix time after applying to a -7 offset and got " + epoch);
        BaseClass instance = new BaseClass();
        Long expResult = 1549249987000L;
        Long result = BaseClass.localTimeToUTCUnix(dateTime);
        System.out.println("The result of the function call loncalTimetoUnix is " + result);
        assertEquals(expResult, result);
    }
    
    public void testUnixToDate(long unixTime) {
        System.out.println("unixToDate");
        unixTime = System.currentTimeMillis();
        System.out.println(unixTime);
    }
    
}
