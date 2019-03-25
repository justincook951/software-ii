/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareii.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jay
 */
public class ReportResult_ApptTypes extends ReportResult {
    
    //Required fields for instantiation; matches query result fields
    protected String Some;
    protected String Test;
    protected String Columns;
    
    public ReportResult_ApptTypes(ArrayList<String> columnNames, HashMap<String, String> columnResults) {
        super(columnNames, columnResults);
    }

    public String getSome() {
        return Some;
    }

    public void setSome(String Some) {
        this.Some = Some;
    }

    public String getTest() {
        return Test;
    }

    public void setTest(String Test) {
        this.Test = Test;
    }

    public String getColumns() {
        return Columns;
    }

    public void setColumns(String Columns) {
        this.Columns = Columns;
    }
    
}
