package softwareii.dbFunctions;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

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
    
    public ArrayList<String> getAppointmentsByConsultant(String appointmentOwner) {
        ArrayList<String> returnAL = new ArrayList();
        String query = "SELECT * FROM appointment WHERE createdBy = :createdBy";
        HashMap<String, String> params = new HashMap();
        params.put("createdBy", appointmentOwner);
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
    
    public ArrayList<String> getAppointmentsByType(int month) {
        ArrayList<String> returnAL = new ArrayList();
        String query = "SELECT count(*) AS number, description FROM appointment WHERE month(start) = :month GROUP BY description";
        HashMap<String, String> params = new HashMap();
        params.put("month", Integer.toString(month));
        try {
            ResultSet results = this.execute(query, params);
            while (results.next()) {
                returnAL.add(results.getString("number"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return returnAL;
    }
    
    public ArrayList<String> getNewCustomersThisMonth() {
        ArrayList<String> returnAL = new ArrayList();
        String query = "SELECT * FROM appointment WHERE createdBy = :createdBy";
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
    
}
