package softwareii.controller_view;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import softwareii.dbFunctions.AppointmentDB;
import softwareii.dbFunctions.CustomerDB;
import softwareii.initializer.Initializer;
import softwareii.model.Appointment;
import softwareii.model.BaseClass;
import softwareii.model.Customer;

public class AppointmentPageController extends BaseController implements Initializable {
    
    @FXML private ChoiceBox customerDropdown;
    private CustomerDB customerDB;
    private AppointmentDB appointmentDB;
    private ObservableList<Appointment> appointmentWrapper;
    @FXML private ChoiceBox startAMPM;
    @FXML private ChoiceBox endAMPM;
    @FXML private TableView<Appointment> appointmentTV;
    @FXML private TableColumn<Customer, Integer> customerIDCol;
    @FXML private TableColumn<Customer, Integer> apptDate;
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
    
    protected void populateAppointments() {
        appointmentWrapper = FXCollections.observableArrayList(
            appointmentDB.getAppointments()
        );
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        apptDate.setCellValueFactory(new PropertyValueFactory<>("apptDate"));
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
            warningLabel.setText("No customer selected.");
        }
    }
    
    @FXML protected void editAppt(ActionEvent e) {
        warningLabel.setText("");
        editingLabel.setText("Editing Appointment: ");
        Appointment selectedAppt = appointmentTV.getSelectionModel().getSelectedItem();
        if (selectedAppt != null) {
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
        if (validated()) {
            String customerSelection = customerDropdown.getValue().toString();
            int customerId = -1;
            if (!customerSelection.equals("")) {
                String customerIntVal = customerSelection.replaceAll("[^\\d.]", "");
                customerId = Integer.parseInt(customerIntVal);
                System.out.println(customerId);
            }
            String apptReason = appointmentReason.getText();
            
            //Getting start and end times
            String startHrStr = Integer.toString(Integer.parseInt(startHr.getText()));
            String startMinStr = String.format("%02d", Integer.parseInt(startMin.getText()));
            String startAMPMVal = endAMPM.getValue().toString();
            String localStartStr = startHrStr + ":" + startMinStr + " " + startAMPMVal; // -_- when you just need something that works
            String formattedStartStr = this.formatToProperTime(localStartStr);
            
            String endHrStr = Integer.toString(Integer.parseInt(endHr.getText()));
            String endMinStr = String.format("%02d", Integer.parseInt(endMin.getText()));
            String endAMPMVal = endAMPM.getValue().toString();
            String localEndStr = endHrStr + ":" + endMinStr + " " + endAMPMVal;
            String formattedEndStr = this.formatToProperTime(localEndStr);
            
            String apptDateVal = pickedApptDate.getValue().toString();
            
            String finalStartStr = apptDateVal + " " + formattedStartStr;
            String finalEndStr = apptDateVal + " " + formattedEndStr;
            
            try {
                LocalDateTime finalStartTime = BaseClass.localStringToLocalDateTime(finalStartStr);
                LocalDateTime finalEndTime = BaseClass.localStringToLocalDateTime(finalEndStr);
                
                Long unixStart = BaseClass.localTimeToUTCUnix(finalStartTime);
                Long unixEnd = BaseClass.localTimeToUTCUnix(finalEndTime);
                if (isEditing) {
                    int appointmentId = Integer.parseInt(appointmentIdField.getText());
                    appointmentDB.updateAppointment(appointmentId, customerId, apptReason, unixStart, unixEnd);
                }
                else {
                    appointmentDB.newAppointment(customerId, apptReason, unixStart, unixEnd);
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
    
    private boolean validated() {
        boolean valid = true;
        try {
            if (customerDropdown.getValue() == null) {
                throw new Exception("Please select a customer.");
            }
            if (pickedApptDate.getValue() == null) {
                throw new Exception("Please select a date for the appointment.");
            }
            if (startHr.getText().replaceAll("[^\\d.]", "").equals("") 
                    || endHr.getText().replaceAll("[^\\d.]", "").equals("") 
                    || startMin.getText().replaceAll("[^\\d.]", "").equals("")
                    || endMin.getText().replaceAll("[^\\d.]", "").equals("")) {
                throw new Exception("Ensure your time values are correct.");
            }
            if (startAMPM.getValue() == null || endAMPM.getValue() == null) {
                throw new Exception("Don't forget to set AM/PM!");
            }
            if (appointmentReason.getText().equals("")) {
                throw new Exception("Please provide an appointment reason / description.");
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
}
