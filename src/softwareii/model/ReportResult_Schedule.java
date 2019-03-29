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
public class ReportResult_Schedule extends ReportResult {
    
    //Required fields for instantiation; matches query result fields
    protected String start;
    protected String end;
    protected String description;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public ReportResult_Schedule(ArrayList<String> columnNames, HashMap<String, String> columnResults) {
        super(columnNames, columnResults);
    }

    
}
