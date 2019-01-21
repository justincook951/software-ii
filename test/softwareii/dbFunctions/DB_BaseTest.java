/*
 */
package softwareii.dbFunctions;

import java.sql.ResultSet;
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
public class DB_BaseTest {
    
    protected int generatedCustomer;
    
    public DB_BaseTest() {
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
    public void testExecute_String_Map() throws Exception {
        System.out.println("execute");
        String queryString = "SELECT * FROM address WHERE addressId = :addressId";
        HashMap<String, String> params = new HashMap<>();
        params.put("addressId", "1");
        DB_Base instance = new DB_Base();
        String expectedRes = "123 Legit Street";
        ResultSet result = instance.execute(queryString, params);
        String addressRes = "";
        while (result.next()) {
            addressRes = result.getString("address");
        }
        assertEquals(addressRes, expectedRes);
    }
    
    
    @Test
    public void testGetItemByID() throws Exception {
        System.out.println("getItemByID");
        int ID = 1;
        String identifier = "addressId";
        String table = "address";
        DB_Base instance = new DB_Base();
        String expectedRes = "123 Legit Street";
        ResultSet result = instance.getItemByID(ID, identifier, table);
        String addressRes = "";
        while (result.next()) {
            addressRes = result.getString("address");
        }
        assertEquals(addressRes, expectedRes);
    }
    
    @Test
    public void testInsertAndGetID() throws Exception {
        System.out.println("insertAndGetID");
        String queryString = "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdateBy) VALUES (:customerName, :addressId, :active, now(), :createdBy, :lastUpdateBy)";
        HashMap<String, String> params = new HashMap<>();
        params.put("customerName", "autoTest");
        params.put("addressId", "19");
        params.put("active", "1");
        params.put("createdBy", "Overlord");
        params.put("lastUpdateBy", "Overlord");
        DB_Base instance = new DB_Base();
        int result = instance.insertAndGetID(queryString, params);
        assertTrue(result > 0);
        this.generatedCustomer = result;
    }
    
    
   
    @Test
    public void testRun() throws Exception {
        System.out.println("run");
        String queryString = "UPDATE customer SET customerName = :customerName, lastUpdateBy = :lastUpdateBy WHERE customerId = :customerId";
        HashMap<String, String> params = new HashMap<>();
        params.put("customerName", "autoUpdateTest");
        params.put("customerId", Integer.toString(19));
        params.put("lastUpdateBy", "Underlord");
        DB_Base instance = new DB_Base();
        instance.run(queryString, params);
        ResultSet result = instance.getItemByID(19, "customerId", "customer");
        String customerName = "";
        String lastUpdateBy = "";
        while(result.next()) {
            customerName = result.getString("customerName");
            lastUpdateBy = result.getString("lastUpdateBy");
        }
        assertEquals(customerName, "autoUpdateTest");
        assertEquals(lastUpdateBy, "Underlord");
        
        params = new HashMap<>();
        params.put("customerName", "autoNewUpdateTest");
        params.put("customerId", Integer.toString(19));
        params.put("lastUpdateBy", "Overlord");
        instance.run(queryString, params);
        result = instance.getItemByID(19, "customerId", "customer");
        while(result.next()) {
            customerName = result.getString("customerName");
            lastUpdateBy = result.getString("lastUpdateBy");
        }
        assertEquals(customerName, "autoNewUpdateTest");
        assertEquals(lastUpdateBy, "Overlord");
    }
    
    
    @Test
    public void testDeleteByID() throws Exception {
        System.out.println("deleteByID");
        int ID = 500;
        String queryString = "INSERT INTO customer (customerId, customerName, addressId, active, createDate, createdBy, lastUpdateBy) VALUES (:customerId, :customerName, :addressId, :active, now(), :createdBy, :lastUpdateBy)";
        HashMap<String, String> params = new HashMap<>();
        params.put("customerId", Integer.toString(ID));
        params.put("customerName", "autoTest");
        params.put("addressId", "19");
        params.put("active", "1");
        params.put("createdBy", "Overlord");
        params.put("lastUpdateBy", "Overlord");
        DB_Base instance = new DB_Base();
        instance.run(queryString, params);
        String identifier = "customerId";
        String table = "customer";
        instance.deleteByID(ID, identifier, table);
        ResultSet result = instance.getItemByID(ID, identifier, table);
        String customerName = "";
        String lastUpdateBy = "";
        while(result.next()) {
            customerName = result.getString("customerName");
            lastUpdateBy = result.getString("lastUpdateBy");
        }
        assertEquals(customerName, "");
        assertEquals(lastUpdateBy, "");
    }
    
    /*
    @Test
    public void testGenerateWhere() {
        System.out.println("generateWhere");
        HashMap<String, String> inbMap = null;
        DB_Base instance = new DB_Base();
        String expResult = "";
        String result = instance.generateWhere(inbMap);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */
}
