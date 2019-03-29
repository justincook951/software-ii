/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareii.dbFunctions;

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
public class ReportsDBTest {
    
    public ReportsDBTest() {
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
     * Test of getAppointmentsByType method, of class ReportsDB.
     */
    @Test
    public void testGetAppointmentsByType() {
        System.out.println("getAppointmentsByType");
        int month = 3;
        HashMap<String, String> hs1 = new HashMap();
        hs1.put("number", "1");
        hs1.put("description", "Blood Drawing3");
        HashMap<String, String> hs2 = new HashMap();
        hs2.put("number", "2");
        hs2.put("description", "Blood DrawingFuture2");
        HashMap<String, String> hs3 = new HashMap();
        hs3.put("number", "1");
        hs3.put("description", "Raaaar");
        ReportsDB instance = new ReportsDB();
        ArrayList<HashMap> expResult = new ArrayList();
        expResult.add(hs1);
        expResult.add(hs2);
        expResult.add(hs3);
        ArrayList<HashMap> result = instance.getAppointmentsByType(month);
        assertEquals(expResult, result);
    }
    
}
