/*
 */
package softwareii.model;

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
public class CustomerTest {
    
    private Customer instance;
    
    public CustomerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {


    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new Customer(0, "Hi", "yee", "yee2", 0, 0, 0, "123123");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getCustomerID method, of class Customer.
     */
    @Test
    public void testGetCustomerID() {
        System.out.println("getCustomerID");
        int expResult = 0;
        int result = instance.getCustomerID();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCustomerName method, of class Customer.
     */
    @Test
    public void testGetCustomerName() {
        System.out.println("getCustomerName");
        Customer instance = null;
        String expResult = "";
        String result = instance.getCustomerName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCustomerName method, of class Customer.
     */
    @Test
    public void testSetCustomerName() {
        System.out.println("setCustomerName");
        String customerName = "";
        Customer instance = null;
        instance.setCustomerName(customerName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAddress1 method, of class Customer.
     */
    @Test
    public void testGetAddress1() {
        System.out.println("getAddress1");
        Customer instance = null;
        String expResult = "";
        String result = instance.getAddress1();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAddress1 method, of class Customer.
     */
    @Test
    public void testSetAddress1() {
        System.out.println("setAddress1");
        String address = "";
        Customer instance = null;
        instance.setAddress1(address);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAddress2 method, of class Customer.
     */
    @Test
    public void testGetAddress2() {
        System.out.println("getAddress2");
        Customer instance = null;
        String expResult = "";
        String result = instance.getAddress2();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAddress2 method, of class Customer.
     */
    @Test
    public void testSetAddress2() {
        System.out.println("setAddress2");
        String address = "";
        Customer instance = null;
        instance.setAddress2(address);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCityId method, of class Customer.
     */
    @Test
    public void testGetCityId() {
        System.out.println("getCityId");
        Customer instance = null;
        int expResult = 0;
        int result = instance.getCityId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCityName method, of class Customer.
     */
    @Test
    public void testGetCityName() {
        System.out.println("getCityName");
        Customer instance = null;
        String expResult = "";
        String result = instance.getCityName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCity method, of class Customer.
     */
    @Test
    public void testSetCity() {
        System.out.println("setCity");
        int cityId = 0;
        Customer instance = null;
        instance.setCity(cityId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCityName method, of class Customer.
     */
    @Test
    public void testSetCityName() {
        System.out.println("setCityName");
        String cityName = "";
        Customer instance = null;
        instance.setCityName(cityName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCountryId method, of class Customer.
     */
    @Test
    public void testGetCountryId() {
        System.out.println("getCountryId");
        Customer instance = null;
        int expResult = 0;
        int result = instance.getCountryId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCountryName method, of class Customer.
     */
    @Test
    public void testGetCountryName() {
        System.out.println("getCountryName");
        Customer instance = null;
        String expResult = "";
        String result = instance.getCountryName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCountry method, of class Customer.
     */
    @Test
    public void testSetCountry() {
        System.out.println("setCountry");
        int countryId = 0;
        Customer instance = null;
        instance.setCountry(countryId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCountryName method, of class Customer.
     */
    @Test
    public void testSetCountryName() {
        System.out.println("setCountryName");
        String countryName = "";
        Customer instance = null;
        instance.setCountryName(countryName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getZip method, of class Customer.
     */
    @Test
    public void testGetZip() {
        System.out.println("getZip");
        Customer instance = null;
        int expResult = 0;
        int result = instance.getZip();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setZip method, of class Customer.
     */
    @Test
    public void testSetZip() {
        System.out.println("setZip");
        int zip = 0;
        Customer instance = null;
        instance.setZip(zip);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPhone method, of class Customer.
     */
    @Test
    public void testGetPhone() {
        System.out.println("getPhone");
        Customer instance = null;
        String expResult = "";
        String result = instance.getPhone();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPhone method, of class Customer.
     */
    @Test
    public void testSetPhone() {
        System.out.println("setPhone");
        String phone = "";
        Customer instance = null;
        instance.setPhone(phone);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
