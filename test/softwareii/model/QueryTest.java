/*
 */
package softwareii.model;

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
public class QueryTest {
    
    protected Query query;
    
    public QueryTest() {
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
    public void testGetParamedStringInsert() {
        System.out.println("Doing insert");
        String queryString = "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdateBy) VALUES (:customerName, :addressId, :active, now(), :createdBy, :lastUpdateBy)";
        HashMap params = new HashMap<>();
        params.put("customerName", "autoTest");
        params.put("addressId", "19");
        params.put("active", "1");
        params.put("createdBy", "Overlord");
        params.put("lastUpdateBy", "Overlord");
        this.query = new Query(queryString, params);
        String expResult = "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdateBy) VALUES (?, ?, ?, now(), ?, ?)";
        String result = query.getParamedString();
        assertEquals(expResult, result);
        
        queryString = "INSERT INTO customer (customerName, addressId, active, createdBy, lastUpdateBy, createDate) VALUES (:customerName, :addressId, :active, :createdBy, :lastUpdateBy, now())";
        this.query = new Query(queryString, params);
        expResult = "INSERT INTO customer (customerName, addressId, active, createdBy, lastUpdateBy, createDate) VALUES (?, ?, ?, ?, ?, now())";
        result = query.getParamedString();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetParamedStringDelete() {
        System.out.println("getParamedString");
        String queryString = "DELETE FROM table WHERE column1 = :column1 AND column2 = :column2";
        String expResult = "DELETE FROM table WHERE column1 = ? AND column2 = ?";
        HashMap params = new HashMap<>();
        params.put("column1", "testVal1");
        params.put("column2", "testVal2");
        this.query = new Query(queryString, params);
        String result = query.getParamedString();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetParamedStringUpdate() {
        String queryString = "UPDATE table SET val1 = :val1, val2 = :val2 WHERE column1 = :column1 AND column2 = :column2";
        String expResult = "UPDATE table SET val1 = ?, val2 = ? WHERE column1 = ? AND column2 = ?";
        HashMap params = new HashMap<>();
        params.put("column1", "testVal1");
        params.put("column2", "testVal2");
        params.put("val1", "val1");
        params.put("val2", "val2");
        this.query = new Query(queryString, params);
        String result = query.getParamedString();
        assertEquals(expResult, result);
    }
    
        @Test
    public void testGetParamedStringUpdate2() {
        String queryString = "UPDATE customer SET customerName = :customerName, lastUpdateBy = :lastUpdateBy WHERE customerId = :customerId AND createDate = now()";
        String expResult = "UPDATE customer SET customerName = ?, lastUpdateBy = ? WHERE customerId = ? AND createDate = now()";
        HashMap<String, String> params = new HashMap<>();
        params.put("customerName", "autoUpdateTest");
        params.put("customerId", Integer.toString(19));
        params.put("lastUpdateBy", "Underlord");
        this.query = new Query(queryString, params);
        String result = query.getParamedString();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetParamedStringSelect() {
        System.out.println("getParamedString");
        String queryString = "SELECT * FROM table WHERE value1 = :value1 AND value2 = :value2 AND createDate = now()";
        HashMap params = new HashMap<>();
        params.put("value1", "testVal1");
        params.put("value2", "testVal2");
        String expResult = "SELECT * FROM table WHERE value1 = ? AND value2 = ? AND createDate = now()";
        this.query = new Query(queryString, params);
        String result = query.getParamedString();
        assertEquals(expResult, result);
    }

     
    @Test
    public void testGetSortedParams() {      
        String queryString = "INSERT INTO customer (customerName, addressId, active, createDate, lastUpdateBy, createdBy) VALUES (:customerName, :addressId, :active, now(), :lastUpdateBy, :createdBy)";
        HashMap<String, String> params = new HashMap<>();
        params.put("customerName", "autoTest");
        params.put("addressId", "19");
        params.put("active", "1");
        params.put("lastUpdateBy", "Overlord");
        params.put("createdBy", "Overlord");
        this.query = new Query(queryString, params);
        HashMap<String, String> expResult = new HashMap<>();
        expResult.put("customername", "autoTest");
        expResult.put("addressid", "19");
        expResult.put("active", "1");
        expResult.put("createdby", "Overlord");
        expResult.put("lastupdateby", "Overlord");
        HashMap result = query.getSortedParams();
        assertEquals(expResult, result);
    }
    
}
