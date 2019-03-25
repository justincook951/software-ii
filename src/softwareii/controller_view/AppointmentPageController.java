package softwareii.controller_view;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import softwareii.dbFunctions.AppointmentDB;
import softwareii.dbFunctions.CustomerDB;
import softwareii.initializer.Initializer;
import softwareii.model.Appointment;
import softwareii.model.Customer;
import softwareii.model.TimeHandler;
import softwareii.validator.Validator;

public class AppointmentPageController extends BaseController implements Initializable {
    
    @FXML private ChoiceBox customerDropdown;
    private CustomerDB customerDB;
    private AppointmentDB appointmentDB;
    private ObservableList<Appointment> appointmentWrapper;
    @FXML private ChoiceBox startAMPM;
    @FXML private ChoiceBox endAMPM;
    @FXML private TableView<Appointment> appointmentTV;
    @FXML private TableColumn<Customer, Integer> customerIDCol;
    @FXML private TableColumn<Customer, Integer> apptDateCol;
    @FXML private TableColumn<Customer, Integer> apptStart;
    @FXML private TableColumn<Customer, Integer> apptEnd;
    @FXML private TableColumn<Customer, Integer> apptType;
    @FXML private Label warningLabel;
    @FXML private Label editingLabel;
    private boolean isEditing;
    @FXML private TextField appointmentIdField;
    @FXML private TextField startHr;
    @FXML private TextField endHr;
    @FXML private TextField startMin;
    @FXML private TextField endMin;
    @FXML private DatePicker pickedApptDate;
    @FXML private TextField appointmentReason;
    @FXML private ToggleGroup intervalSelect;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        warningLabel.setText("");
        editingLabel.setText("");
        isEditing = false;
        this.customerDB = Initializer.customerdb;
        this.appointmentDB = Initializer.appointmentdb;
        try {
            this.populateCustomers();
            this.populateAppointments();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        startAMPM.setItems(FXCollections.observableArrayList("AM","PM"));
        endAMPM.setItems(FXCollections.observableArrayList("AM","PM"));
    }
    
    protected void populateCustomers() {
        ArrayList<Customer> customers = customerDB.getCustomers();
        ArrayList<String> custNames = new ArrayList<>();
        for (Customer customer : customers) {
            custNames.add(customer.getCustomerName() + "(" + customer.getCustomerID() + ")");
        }
        customerDropdown.setItems(FXCollections.observableArrayList(
            custNames
        ));
    }
    
    @FXML
    protected void updateAppointmentsList() {
        RadioButton selectedRadioButton = (RadioButton) intervalSelect.getSelectedToggle();
        String toogleGroupValue = selectedRadioButton.getText();
        String interval = "all";
        switch(toogleGroupValue) {
            case "Monthly View":
                interval = "month";
                break;
            case "Weekly View":
                interval = "week";
                break;
            default:
                break;
        }
        populateAppointments(interval);
    }
    
    protected void populateAppointments() {
        appointmentWrapper = FXCollections.observableArrayList(
            appointmentDB.getAppointments()
        );
        setAppointmentFactories();
    }
    
    protected void populateAppointments(String appointmentInterval) {
        appointmentWrapper = FXCollections.observableArrayList(
            appointmentDB.getAppointments(appointmentInterval)
        );
        setAppointmentFactories();
    }
    
    protected void setAppointmentFactories() {
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptDateCol.setCellValueFactory(new PropertyValueFactory<>("apptDate"));
        apptStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        apptEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        apptType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appointmentTV.getItems().setAll(appointmentWrapper);
    }
    
    @FXML protected void deleteAppt(ActionEvent e) {
        warningLabel.setText("");
        Appointment selectedAppt = appointmentTV.getSelectionModel().getSelectedItem();
        int selectedID = 0;
        try {
           selectedID = selectedAppt.getAppointmentId(); 
           System.out.println("Selected: " + selectedID);
           try {
                appointmentDB.deleteAppointment(selectedID);
                //Refresh the view
                populateAppointments();
            }
            catch (SQLException exception) {
                warningLabel.setText("SQL Error on deleteCustomer.");
            }
        }
        catch (NullPointerException exception) {
            warningLabel.setText("No appointment selected.");
        }
    }
    
    @FXML protected void editAppt(ActionEvent e) {
        warningLabel.setText("");
        Appointment selectedAppt = appointmentTV.getSelectionModel().getSelectedItem();
        if (selectedAppt != null) {
            editingLabel.setText("Editing Appointment: ");
            isEditing = true;
            String apptId = Integer.toString(selectedAppt.getAppointmentId());
            appointmentIdField.setText(apptId);
        }
        else {
            warningLabel.setText("Select an appointment to edit.");
        }
    }
    
    @FXML protected void createAppt(ActionEvent e) {
        //*Sigh* not my best work.
        warningLabel.setText("");
        int appointmentId = 0;
        if (isEditing) {
            appointmentId = Integer.parseInt(appointmentIdField.getText());
        }
        if (validated(appointmentId)) {
            String customerSelection = customerDropdown.getValue().toString();
            int customerId = -1;
            if (!customerSelection.equals("")) {
                String customerIntVal = customerSelection.replaceAll("[^\\d.]", "");
                customerId = Integer.parseInt(customerIntVal);
                System.out.println(customerId);
            }
            String apptReason = appointmentReason.getText();
            //Build SQL Strings based on UTC ZonedDateTime
            String startSQL = this.buildTimeSQL("start");
            String endSQL = this.buildTimeSQL("end");
            try {
                if (isEditing) {
                    appointmentDB.updateAppointment(appointmentId, customerId, apptReason, startSQL, endSQL);
                }
                else {
                    appointmentDB.newAppointment(customerId, apptReason, startSQL, endSQL);
                }
                
                //Refresh the view
                populateAppointments();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private String formatToProperTime(String localString) {
        String formattedString =
                LocalTime.parse(
                    localString ,
                    DateTimeFormatter.ofPattern(
                        "hh:mm a" ,
                        Locale.US
                    )
                )
                .format( DateTimeFormatter.ofPattern("HH:mm") )
            ;
        return formattedString;
    }
    
    private boolean validated(int appointmentId) {
        boolean valid = true;
        Validator validator = new Validator();
        try {
            if (customerDropdown.getValue() == null) {
                throw new Exception("Please select a customer.");
            }
            if (pickedApptDate.getValue() == null) {
                throw new Exception("Please select a date for the appointment.");
            }
            String startHrInput = scrubInputs(startHr.getText());
            String endHrInput = scrubInputs(endHr.getText());
            String startMinInput = scrubInputs(startMin.getText());
            String endMinInput = scrubInputs(endMin.getText());
            if (startHrInput.equals("") 
                    || endHrInput.equals("") 
                    || startMinInput.equals("")
                    || endMinInput.equals("")) {
                throw new Exception("Ensure your time values are correct.");
            }
            if (startAMPM.getValue() == null || endAMPM.getValue() == null) {
                throw new Exception("Don't forget to set AM/PM!");
            }
            if (appointmentReason.getText().equals("")) {
                throw new Exception("Please provide an appointment reason / description.");
            }
            
            LocalTime startTime = LocalTime.parse(this.getTimeValues("start"));
            LocalTime endTime = LocalTime.parse(getTimeValues("end"));
            if (!validator.isInOperatingHours(startTime) || !validator.isInOperatingHours(endTime)) {
                throw new Exception("Please put your appointment in the operating hours for your store.");
            }
            String startSQL = this.buildTimeSQL("start");
            String endSQL = this.buildTimeSQL("end");
            int overlappingCount = appointmentDB.getOverlappingAppointmentCount(startSQL, endSQL, appointmentId);
            if (overlappingCount > 0) {
                throw new Exception("You attempted to submit an overlapping appointment. Don't do that.");
            }
        }
        catch (Exception ex) {
            valid = false;
            try {
                warningLabel.setText(ex.getMessage());
            }
            catch (Exception labelEx) {
                warningLabel.setText("Verify all of your input is correct.");
            }          
            ex.printStackTrace();
        }
        return valid;
    }
    
    protected String scrubInputs(String input) {
        return input.replaceAll("[^\\d.]", "");
    }
    
    protected String getTimeValues(String startOrEnd) {
        String hrStr = "";
        String minStr = "";
        String AMPMVal = "";
        switch (startOrEnd) {
            case "start":
                hrStr = String.format("%02d", Integer.parseInt(startHr.getText()));
                minStr = String.format("%02d", Integer.parseInt(startMin.getText()));
                AMPMVal = startAMPM.getValue().toString();
                break;
            case "end":
                hrStr = String.format("%02d", Integer.parseInt(endHr.getText()));
                minStr = String.format("%02d", Integer.parseInt(endMin.getText()));
                AMPMVal = endAMPM.getValue().toString();
                break;
            default:
                break;
        }
        String localStartStr = hrStr + ":" + minStr + " " + AMPMVal; // -_- when you just need something that works
        return this.formatToProperTime(localStartStr);
    }
    
    protected String buildTimeSQL(String startOrEnd) {
        String returnSQL = "";
        if (startOrEnd.equals("start") || startOrEnd.equals("end")) {
            //Get times and format them
            String formattedTimeStr = this.getTimeValues(startOrEnd);
            String apptDateVal = pickedApptDate.getValue().toString();
            String finalTimeStr = apptDateVal + " " + formattedTimeStr;
            //Build LocalDateTime to convert to ZonedDateTime
            LocalDateTime timeLDT = TimeHandler.buildLocalDateTime(finalTimeStr);
            //Build a Local Time Zone based ZonedDateTime
            ZonedDateTime localTimeZDT = TimeHandler.buildLocalZonedDateTime(timeLDT);
            //Convert Local Time Zoned based ZonedDateTime to UTC
            ZonedDateTime UTCZoneTime = TimeHandler.convertLocalZDTtoUTC(localTimeZDT);
            //Build SQL Strings based on UTC ZonedDateTime
            returnSQL = TimeHandler.buildInsertFromZDT(UTCZoneTime);
        }
        return returnSQL;
    }
}
