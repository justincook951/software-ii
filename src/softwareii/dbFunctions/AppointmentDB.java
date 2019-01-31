/*
 */
package softwareii.dbFunctions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import softwareii.model.Appointment;

/**
 *
 * @author Jay
 */
public class AppointmentDB extends DB_Base {
    
    public ArrayList<Appointment> getAppointments() {
        String queryString = "SELECT * FROM appointment";
        HashMap<String, String> params = new HashMap<>();
        ArrayList<Appointment> appointments = new ArrayList();
        try {
           ResultSet results = this.execute(queryString, params); 
        }
        catch (SQLException e) {
            
        }
        return appointments;
    }
    
}
