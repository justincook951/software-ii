/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareii.dbFunctions;

import java.sql.Timestamp;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import softwareii.model.Appointment;

/**
 *
 * @author Jay
 */
public class AppointmentDBTest {
    
    public AppointmentDBTest() {
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

    /**
     * Test of getAppointments method, of class AppointmentDB.
     */
    @Test
    public void testGetAppointments() {
        AppointmentDB instance = new AppointmentDB();
        //Appointment expResult = new Appointment();
        long startUnix = 1549246387L * 1000;
       
        long endUnix = 1549249987L * 1000;
        Appointment expapp = new Appointment(1, "Blood Drawing", 1, startUnix, endUnix);
        ArrayList<Appointment> result = instance.getAppointments();
        Appointment actapp = result.get(0);
        assert(expapp.getStartTime() == actapp.getStartTime());
        assert(expapp.getEndTime()== actapp.getEndTime());
    }
    
    public void testAddAppointment() {
        AppointmentDB instance = new AppointmentDB();
        //Add a new appointment, using a pre-determined timestamp that is a Long integer value
    }

    /*
    @Test
    public void testGetDBTimestamp() {
        System.out.println("getDBTimestamp");
        int appointmentId = 0;
        AppointmentDB instance = new AppointmentDB();
        String expResult = "";
        String result = instance.getDBTimestamp(appointmentId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
    
}
