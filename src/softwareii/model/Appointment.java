package softwareii.model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
public class Appointment extends BaseClass {
    
    protected int customerID;
    protected String appointmentType;
    protected String apptDate;
    protected String startTime;
    protected String endTime;
    protected int appointmentId;
    
    public Appointment(int customerID, String appointmentType, int appointmentId, String start, String end) {
        this.customerID = customerID;
        this.appointmentType = appointmentType;
        ZonedDateTime inboundStartZDT = TimeHandler.ZDTFromQueryString(start, "yyyy-MM-dd HH:mm:ss"); //UTC Time
        ZonedDateTime convertedStartZDT = TimeHandler.convertUTCZDTtoLocal(inboundStartZDT); //Local Time
        this.apptDate = convertedStartZDT.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.startTime = convertedStartZDT.format(DateTimeFormatter.ofPattern("hh:mm a"));
        
        ZonedDateTime inboundEndZDT = TimeHandler.ZDTFromQueryString(end, "yyyy-MM-dd HH:mm:ss"); //UTC Time
        ZonedDateTime convertedEndZDT = TimeHandler.convertUTCZDTtoLocal(inboundEndZDT); //Local Time
        this.endTime = convertedEndZDT.format(DateTimeFormatter.ofPattern("hh:mm a"));
        this.appointmentId = appointmentId;
    }
    
    public int getCustomerID() {
        return this.customerID;
    }
    
    public String getApptDate() {
        return this.apptDate;
    }
    
    public int getAppointmentId() {
        return this.appointmentId;
    }
    
    public String getStartTime() {
        return this.startTime;
    }
    
    public String getEndTime() {
        return this.endTime;
    }
    
    public String getAppointmentType() {
        return this.appointmentType;
    }
    
}
