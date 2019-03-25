/*
 */
package softwareii.validator;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jay
 */
public class ValidatorTest {
    
    public ValidatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        Validator validateTest = new Validator();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testNotNull() {
        
    }

    @Test
    public void testIsInOperatingHours() {
        System.out.println("isInOperatingHours");
        LocalTime validStartTime1 = LocalTime.of(11, 15);
        LocalTime validStartTime2 = LocalTime.of(14, 0);
        LocalTime invalidStartTime1 = LocalTime.of(19, 30);
        LocalTime invalidStartTime2 = LocalTime.of(5, 59);
        Validator instance = new Validator();
        boolean result = instance.isInOperatingHours(validStartTime1);
        assertEquals(true, result);
        result = instance.isInOperatingHours(validStartTime2);
        assertEquals(true, result);
        result = instance.isInOperatingHours(invalidStartTime1);
        assertEquals(false, result);
        result = instance.isInOperatingHours(invalidStartTime2);
        assertEquals(false, result);
    }
    
}
