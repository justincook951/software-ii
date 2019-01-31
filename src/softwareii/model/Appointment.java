/*
 */
package softwareii.model;

/**
 *
 * @author Jay
 */
public class Appointment {
    
    protected int customerID;
    protected String appointmentType;
    
    public Appointment(int customerID, String appointmentType) {
        this.customerID = customerID;
        this.appointmentType = appointmentType;
    }
    
}
