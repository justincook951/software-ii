package softwareii.dbFunctions;

import java.sql.ResultSet;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import softwareii.model.TimeHandler;

public class ReportsDB extends DB_Base {
    
    public ArrayList<String> getConsultants() {
        ArrayList<String> returnAL = new ArrayList();
        String query = "SELECT userName FROM user";
        HashMap<String, String> params = new HashMap();
        try {
            ResultSet results = this.execute(query, params);
            while (results.next()) {
                returnAL.add(results.getString("userName"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return returnAL;
    }
    
    public ArrayList<HashMap> getAppointmentsByConsultant(String appointmentOwner) {
        ArrayList<HashMap> returnAL = new ArrayList();
        String query = "SELECT start, end, description FROM appointment WHERE createdBy = :createdBy";
        HashMap<String, String> params = new HashMap();
        params.put("createdBy", appointmentOwner);
        try {
            ResultSet results = this.execute(query, params);
            while (results.next()) {
                HashMap<String, String> resultsHM = new HashMap();
                String sqlstart = results.getString("start");
                ZonedDateTime inboundStartZDT = TimeHandler.ZDTFromQueryString(sqlstart, "yyyy-MM-dd HH:mm:ss"); //UTC Time
                ZonedDateTime convertedStartZDT = TimeHandler.convertUTCZDTtoLocal(inboundStartZDT); //Local Time
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm a");
                String start = convertedStartZDT.format(formatter);
                resultsHM.put("start", start);
                String sqlend = results.getString("end");
                ZonedDateTime inboundEndZDT = TimeHandler.ZDTFromQueryString(sqlend, "yyyy-MM-dd HH:mm:ss"); //UTC Time
                ZonedDateTime convertedEndZDT = TimeHandler.convertUTCZDTtoLocal(inboundEndZDT); //Local Time
                String end = convertedEndZDT.format(formatter);
                resultsHM.put("end", end);
                String description = results.getString("description");
                resultsHM.put("description", description);
                returnAL.add(resultsHM);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return returnAL;
    }
    
    public ArrayList<HashMap> getAppointmentsByType(int month) {
        ArrayList<HashMap> returnAL = new ArrayList();
        String query = "SELECT count(*) AS number, description FROM appointment WHERE month(start) = :month GROUP BY description";
        HashMap<String, String> params = new HashMap();
        params.put("month", Integer.toString(month));
        try {
            ResultSet results = this.execute(query, params);
            while (results.next()) {
                HashMap<String, String> resultsHM = new HashMap();
                String number = results.getString("number");
                resultsHM.put("number", number);
                String description = results.getString("description");
                resultsHM.put("description", description);
                returnAL.add(resultsHM);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return returnAL;
    }
    
    public ArrayList<HashMap> getNewCustomersThisMonth(int month) {
        ArrayList<HashMap> returnAL = new ArrayList();
        String query = "SELECT customerId, customerName FROM customer WHERE month(createDate) = :month AND createDate > NOW() - INTERVAL 1 YEAR";
        HashMap<String, String> params = new HashMap();
        params.put("month", Integer.toString(month));
        try {
            ResultSet results = this.execute(query, params);
            while (results.next()) {
                HashMap<String, String> resultsHM = new HashMap();
                String customerId = results.getString("customerId");
                resultsHM.put("customerId", customerId);
                String customerName = results.getString("customerName");
                resultsHM.put("customerName", customerName);
                returnAL.add(resultsHM);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return returnAL;
    }
    
}
