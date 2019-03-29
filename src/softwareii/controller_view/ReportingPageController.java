package softwareii.controller_view;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import softwareii.dbFunctions.ReportsDB;
import softwareii.initializer.Initializer;
import softwareii.model.ReportResult;
import softwareii.model.ReportResult_ApptTypes;
import softwareii.model.ReportResult_NewCustomers;
import softwareii.model.ReportResult_Schedule;

public class ReportingPageController extends BaseController implements Initializable {
    
    @FXML protected ChoiceBox reportCB;
    @FXML protected ChoiceBox monthCB;
    @FXML protected ChoiceBox consultantCB;
    protected ReportsDB reportsDB;
    @FXML protected TableView resultsTV;
    protected ObservableList<ReportResult> reportWrapper;
    protected ArrayList<ReportResult> dataList;
    @FXML protected Label resultsLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        resultsLabel.setText("");
        this.reportsDB = Initializer.reportsdb;
        reportCB.setItems(FXCollections.observableArrayList("Appointment Types By Month", "Schedule by Consultant", "New Customers By Month"));
        monthCB.setItems(FXCollections.observableArrayList(
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
            )
        );
        ArrayList<String> custNames = reportsDB.getConsultants();
        consultantCB.setItems(FXCollections.observableArrayList(
                reportsDB.getConsultants()
        ));
        dataList = new ArrayList();
    }
    
    @FXML
    protected void getReport(ActionEvent e) {
        //Reset data on each search
        resultsTV.getColumns().clear();
        dataList.clear();
        resultsLabel.setText("");
        int reportType = reportCB.getSelectionModel().getSelectedIndex();
        switch(reportType) {
            case 0:
                setColumns("apptTypes");
                break;
            case 1:
                setColumns("schedule");
                break;
            case 2:
                setColumns("newCustomers");
                break;
            default:
                resultsLabel.setText(props.getProperty("noReport"));
                break;
        }
        
    }
    
    protected void setColumns(String reportType) {
        ArrayList<String> columnNames = new ArrayList();
        int month = monthCB.getSelectionModel().getSelectedIndex() + 1;
        String consultant = "";
        try {
            consultant = consultantCB.getValue().toString();
        }
        catch (NullPointerException e) {
            //Handle later down in function
        }
        ArrayList<HashMap> queryResults = new ArrayList();
        ArrayList<ReportResult> reportResults = new ArrayList();
        switch (reportType) {
            case "apptTypes":
                if (month != 0) {
                    columnNames.add("number");
                    columnNames.add("description");
                    queryResults = reportsDB.getAppointmentsByType(month);
                    for (HashMap singularResult : queryResults) {
                        ReportResult singleReportEntry = new ReportResult_ApptTypes(columnNames, singularResult);
                        reportResults.add(singleReportEntry);
                    }
                    if (reportResults.size() > 0) {
                        setValues(reportResults);
                    }
                    else {
                        resultsLabel.setText(props.getProperty("noResults"));
                    }
                }
                else {
                    resultsLabel.setText(props.getProperty("noMonth"));
                }
                
                break;
            case "schedule":
                if (!consultant.equals("")) {
                    columnNames.add("start");
                    columnNames.add("end");
                    columnNames.add("description");
                    queryResults = reportsDB.getAppointmentsByConsultant(consultant);
                    for (HashMap singularResult : queryResults) {
                        ReportResult singleReportEntry = new ReportResult_Schedule(columnNames, singularResult);
                        reportResults.add(singleReportEntry);
                    }
                    if (reportResults.size() > 0) {
                        setValues(reportResults);
                    }
                    else {
                        resultsLabel.setText(props.getProperty("noResults"));
                    }
                }
                else {
                    resultsLabel.setText(props.getProperty("noConsultant"));
                }
                break;
            case "newCustomers":
                if (month != 0) {
                    columnNames.add("customerId");
                    columnNames.add("customerName");
                    queryResults = reportsDB.getNewCustomersThisMonth(month);
                    for (HashMap singularResult : queryResults) {
                        ReportResult singleReportEntry = new ReportResult_NewCustomers(columnNames, singularResult);
                        reportResults.add(singleReportEntry);
                    }
                    if (reportResults.size() > 0) {
                        setValues(reportResults);
                    }
                    else {
                        resultsLabel.setText(props.getProperty("noResults"));
                    }
                }
                else {
                    resultsLabel.setText(props.getProperty("noMonth"));
                }
                break;
            default:
                break;
        }
        
    }
    
    protected void setValues(ArrayList<ReportResult> inbResult) {
        //Get columns for tableview
        ArrayList<String> testColumns = inbResult.get(0).getColumnNames();
        //Set columns for tableview
        for (int i = 0; i < testColumns.size(); i++) {
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    testColumns.get(i)
            );
            column.setCellValueFactory(new PropertyValueFactory<>(testColumns.get(i)));
            resultsTV.getColumns().add(column);
        }
        //Loop through all results and add them to the dataList for rendering in the tableview
        inbResult.forEach((reportResult) -> {
            dataList.add(reportResult);
        });
        //Put the results into the tableview
        reportWrapper = FXCollections.observableArrayList(
            dataList
        );
        resultsTV.getItems().setAll(reportWrapper);
    }
    
}
