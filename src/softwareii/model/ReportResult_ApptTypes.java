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
public class ReportResult_ApptTypes extends ReportResult {
    
    //Required fields for instantiation; matches query result fields
    protected String number;
    protected String description;
    
    public ReportResult_ApptTypes(ArrayList<String> columnNames, HashMap<String, String> columnResults) {
        super(columnNames, columnResults);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
}
