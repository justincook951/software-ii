/*
 */
package softwareii.model;

import java.util.Date;

/**
 *
 * @author Jay
 */
public class Appointment extends BaseClass {
    
    protected int customerID;
    protected String appointmentType;
    protected Date apptDate;
    protected long startTime;
    protected long endTime;
    protected int appointmentId;
    
    public Appointment(int customerID, String appointmentType, int appointmentId, long start, long end) {
        this.customerID = customerID;
        this.appointmentType = appointmentType;
        this.apptDate = new Date(start);
        this.startTime = start;
        this.endTime = end;
        this.appointmentType = appointmentType;
        this.appointmentId = appointmentId;
        System.out.println("I've finished building this appointment. "
            + "I have a customerID of " + this.customerID
            + " and an appointmentType of " + this.appointmentType
            + " and an apptDate of " + this.apptDate
            + " and an appointmentID of " + this.appointmentId);
    }
    
    public int getCustomerID() {
        return this.customerID;
    }
    
    public Date getAppointmentDate() {
        //TEST CODE PLS IGNORE
        return this.apptDate;
    }
    
    public int getAppointmentId() {
        System.out.println("Hi, I'm a method inside of Appointment and I believe my appointment ID is " + this.appointmentId);
        return this.appointmentId;
    }
    
    public String getStartTime() {
        //TEST CODE PLS IGNORE
        //Needs to return a time only
        return unixToLocalTimeString(this.startTime);
    }
    
    public String getEndTime() {
        //TEST CODE PLS IGNORE
        //Needs to return a time only
        return unixToLocalTimeString(this.endTime);
    }
    
    public String getAppointmentType() {
        return this.appointmentType;
    }
    
}
