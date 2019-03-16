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
    
    public void testAddAppointment() {
        AppointmentDB instance = new AppointmentDB();
        //Add a new appointment, using a pre-determined timestamp that is a Long integer value
    }
    
}
