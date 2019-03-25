/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareii.dbFunctions;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
    
    @Test
    public void testGetOverlappingAppointmentCount() {
        AppointmentDB instance = new AppointmentDB();
        //Overlapping time, non-edit
        String startStr = "2019-02-04 02:10:07";
        String endStr = "2019-02-04 3:15:07";
        int appointmentCount = instance.getOverlappingAppointmentCount(startStr, endStr, 0);
        assert(appointmentCount > 0);
        
        //Overlapping time, edit, but another appointment shares time
        startStr = "2019-02-04 02:10:07";
        endStr = "2019-02-04 3:15:07";
        appointmentCount = instance.getOverlappingAppointmentCount(startStr, endStr, 1);
        assert(appointmentCount > 0);
        
        //Overlapping time, edit, but the time is for the appointment being edited
        startStr = "2019-02-26 17:15:00";
        endStr = "2019-02-26 17:30:00";
        appointmentCount = instance.getOverlappingAppointmentCount(startStr, endStr, 13);
        assertEquals(appointmentCount, 0);
        
        //Overlapping time, edit, but the time is NOT for the appointment being edited
        startStr = "2019-02-26 17:15:00";
        endStr = "2019-02-26 17:30:00";
        appointmentCount = instance.getOverlappingAppointmentCount(startStr, endStr, 14);
        assert(appointmentCount > 0);
        
        //No overlapping time
        startStr = "2019-03-02 18:16:00";
        endStr = "2019-03-02 18:20:00";
        appointmentCount = instance.getOverlappingAppointmentCount(startStr, endStr, 0);
        assertEquals(appointmentCount, 0);

    }

    /*
    @Test
    public void testGetAppointments_0args() {
        System.out.println("getAppointments");
        AppointmentDB instance = new AppointmentDB();
        ArrayList<Appointment> expResult = null;
        ArrayList<Appointment> result = instance.getAppointments();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testGetAppointments_String() {
        System.out.println("getAppointments");
        String interval = "";
        AppointmentDB instance = new AppointmentDB();
        ArrayList<Appointment> expResult = null;
        ArrayList<Appointment> result = instance.getAppointments(interval);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testPerformAppointmentsQuery() {
        System.out.println("performAppointmentsQuery");
        String queryString = "";
        AppointmentDB instance = new AppointmentDB();
        ArrayList<Appointment> expResult = null;
        ArrayList<Appointment> result = instance.performAppointmentsQuery(queryString);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetUpcomingAppointmentCount() {
        System.out.println("getUpcomingAppointmentCount");
        AppointmentDB instance = new AppointmentDB();
        int expResult = 0;
        int result = instance.getUpcomingAppointmentCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testNewAppointment() throws Exception {
        System.out.println("newAppointment");
        int customerId = 0;
        String description = "";
        String start = "";
        String end = "";
        AppointmentDB instance = new AppointmentDB();
        instance.newAppointment(customerId, description, start, end);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testUpdateAppointment() throws Exception {
        System.out.println("updateAppointment");
        int appointmentId = 0;
        int customerId = 0;
        String description = "";
        String start = "";
        String end = "";
        AppointmentDB instance = new AppointmentDB();
        instance.updateAppointment(appointmentId, customerId, description, start, end);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testDeleteAppointment() throws Exception {
        System.out.println("deleteAppointment");
        int apptId = 0;
        AppointmentDB instance = new AppointmentDB();
        instance.deleteAppointment(apptId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */
}
