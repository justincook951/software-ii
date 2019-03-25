package softwareii.controller_view;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import softwareii.dbFunctions.ReportsDB;
import softwareii.initializer.Initializer;
import softwareii.model.ReportResult;
import softwareii.model.ReportResult_ApptTypes;

public class ReportingPageController extends BaseController implements Initializable {
    
    @FXML protected ChoiceBox reportCB;
    @FXML protected ChoiceBox monthCB;
    @FXML protected ChoiceBox consultantCB;
    protected ReportsDB reportsDB;
    @FXML protected TableView resultsTV;
    protected ObservableList<ReportResult> reportWrapper;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.reportsDB = Initializer.reportsdb;
        reportCB.setItems(FXCollections.observableArrayList("Appointment Types By Month", "Schedule by Consultant", "New Customers This Month"));
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
    }
    
    @FXML
    protected void getReport(ActionEvent e) {
        int reportType = reportCB.getSelectionModel().getSelectedIndex();
        switch(reportType) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                break;
        }
        setColumns("Test");
    }
    
    protected void setColumns(String reportType) {
        ArrayList<String> columnNames = new ArrayList();
        //TEST CODE PLS IGNORE
        columnNames.add("Some");
        columnNames.add("Test");
        columnNames.add("Columns");
        HashMap<String, String> columnValues = new HashMap();
        columnValues.put("Some", "Testval1");
        columnValues.put("Test", "Testval2");
        columnValues.put("Columns", "Testval3");
        ReportResult_ApptTypes testReport = new ReportResult_ApptTypes(columnNames, columnValues);
        switch (reportType) {
            
        }
        ArrayList<String> testColumns = testReport.getColumnNames();
        for (int i = 0; i < testColumns.size(); i++) {
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    testColumns.get(i)
            );
            column.setCellValueFactory(new PropertyValueFactory<>(testColumns.get(i)));
            resultsTV.getColumns().add(column);
        }
        ArrayList<ReportResult> testList = new ArrayList();
        testList.add(testReport);
        reportWrapper = FXCollections.observableArrayList(
            testList
        );
        resultsTV.getItems().setAll(reportWrapper);
    }
    
}
