package softwareii.dbFunctions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import softwareii.model.Appointment;
import softwareii.model.BaseClass;


public class AppointmentDB extends DB_Base {
    
    public ArrayList<Appointment> getAppointments() {
        String queryString = "SELECT appointmentId, customerId, description, start, end FROM appointment";
        return performAppointmentsQuery(queryString);
    }
    
    public ArrayList<Appointment> getAppointments(String interval) {
        String queryString = "SELECT appointmentId, customerId, description, start, end FROM appointment";
        switch (interval) {
            case "week":
                queryString += " WHERE YEARWEEK(`start`, 1) = YEARWEEK(CURDATE(), 1)";
                break;
            case "month":
                queryString += " WHERE MONTH(`start`) = MONTH(CURDATE())";
                break;
            default:
                break;
        }
        return performAppointmentsQuery(queryString);
    }
    
    protected ArrayList<Appointment> performAppointmentsQuery(String queryString) {
        HashMap<String, String> params = new HashMap<>();
        ArrayList<Appointment> appointments = new ArrayList();
        try {
           ResultSet results = this.execute(queryString, params); 
           int customerId;
           int apptId;
           String apptReason;
           String start;
           String end;
           while (results.next()) {
               customerId = results.getInt("customerId");
               apptReason = results.getString("description");
               apptId = results.getInt("appointmentId");
               start = results.getString("start");
               end = results.getString("end");
               Appointment appointment = new Appointment(customerId, apptReason, apptId, start, end);
               appointments.add(appointment);
           }
        }
        catch (SQLException e) {
            
        }
        return appointments;
    }
    
    public int getUpcomingAppointmentCount() {
        String queryString = "SELECT count(*) AS count FROM appointment WHERE start BETWEEN NOW() AND  NOW() + INTERVAL 15 MINUTE";
        HashMap<String, String> params = new HashMap<>();
        int count = 0;
        try {
           ResultSet results = this.execute(queryString, params); 
           while (results.next()) {
               count = results.getInt("count");
           }
        }
        catch (SQLException e) {
            
        }
        return count;
    }
    
    public int getOverlappingAppointmentCount(String start, String end, int appointmentId) {
        String queryString = "SELECT count(*) AS count FROM appointment WHERE "
                + "( :start1 BETWEEN start AND end OR :end1 BETWEEN start AND end "
                + "OR start BETWEEN :start2 AND :end2 ) ";
        HashMap<String, String> params = new HashMap<>();
        if (appointmentId != 0) {
            queryString += " AND appointmentId != :appointmentId";
            params.put("appointmentId", Integer.toString(appointmentId));
        }
        params.put("start1", start);
        params.put("end1", end);
        params.put("start2", start);
        params.put("end2", end);      
        int count = 0;
        try {
           ResultSet results = this.execute(queryString, params); 
           while (results.next()) {
               count = results.getInt("count");
           }
        }
        catch (SQLException e) {
            // :(
        }
        return count;
    }
    
    public void newAppointment(int customerId, String description, String start, String end) throws SQLException {
        String query = "INSERT INTO appointment "
                + "(`customerId`, `description`, `start`, `end`, `createDate`, `createdBy`, `lastUpdate`, `lastUpdateBy`) "
                + "VALUES "
                + "(:customerId, :description, :start, :end, now(), :createdBy, now(), :lastUpdateBy)";
        HashMap<String, String> params = new HashMap<>();
        params.put("customerId", Integer.toString(customerId));
        params.put("description", description);
        params.put("start", start);
        params.put("end", end);
        params.put("createdBy", BaseClass.getLoggedInUser());
        params.put("lastUpdateBy", BaseClass.getLoggedInUser());
        this.run(query, params);
    }
    
    public void updateAppointment(int appointmentId, int customerId, String description, String start, String end) throws SQLException {
        String query = "UPDATE appointment "
                + "SET customerId = :customerId, "
                + "description = :description, "
                + "start = :start, "
                + "end = :end, "
                + "lastUpdateBy = :lastUpdateBy "
                + "WHERE appointmentId = :appointmentId";
        HashMap<String, String> params = new HashMap<>();
        params.put("appointmentId", Integer.toString(appointmentId));
        params.put("customerId", Integer.toString(customerId));
        params.put("description", description);
        params.put("start", start);
        params.put("end", end);
        params.put("lastUpdateBy", BaseClass.getLoggedInUser());
        this.run(query, params);
    }
    
    public void deleteAppointment(int apptId) throws SQLException {
        String query = "DELETE FROM appointment "
                + "WHERE appointmentId = :appointmentId";
        HashMap<String, String> params = new HashMap<>();
        params.put("appointmentId", Integer.toString(apptId));
        this.run(query, params);
    }
    
}
