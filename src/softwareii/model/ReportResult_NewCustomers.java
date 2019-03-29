/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareii.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jay
 */
public class ReportResult_NewCustomers extends ReportResult {
    
    //Required fields for instantiation; matches query result fields
    protected String customerId;
    protected String customerName;
    
    public ReportResult_NewCustomers(ArrayList<String> columnNames, HashMap<String, String> columnResults) {
        super(columnNames, columnResults);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    
}
