package softwareii.dbFunctions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import softwareii.model.Appointment;
import softwareii.model.BaseClass;

/*
Time flow (UI to DB) -> Combine user input into a timezone string. Parse the timezone string ON IMPORT into UTC. Convert this timezone string into Unix time.
Other operations can now commence. Then, Input Unix time into DB.
Time flow (DB to UI) -> Return Unix time. Perform operations, if necessary, and logic. Then, convert time to local timezone. Then, convert to a displayable string.

General idea: Do everything as much as possible with UTC-based Long Unix values. Only when a time needs to be displayed should it be converted to local time.

TODO:
1. Test UTC Unix Long values inserting directly into DB. Is that gonna break shit?
2. Write unit tests for insert and confirm that values can then be pulled as expected.
3. Write delete functionality by ID.
4. (probably not tonight) build connection between JavaFX front-end and controller to shuffle the information to the insert functionality.
    4a. Build a class or base function set to convert from local to UTC and then to Long stamp? Probably.
*/

public class AppointmentDB extends DB_Base {
    
    public ArrayList<Appointment> getAppointments() {
        String queryString = "SELECT appointmentId, customerId, description, start, end FROM appointment";
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
