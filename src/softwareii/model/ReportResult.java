package softwareii.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class ReportResult extends BaseClass {
    protected ArrayList<String> columnNames;
    
    public ReportResult(ArrayList<String> columnNames, HashMap<String, String> columnResults) {
        this.columnNames = columnNames;
        //After properly extending this class, this can be used to dynamically set fields :D
        columnResults.entrySet().forEach((entry) -> {
            try {
                Field field = this.getClass().getDeclaredField(entry.getKey());
                field.set(this, entry.getValue());  
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ArrayList<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(ArrayList<String> columnNames) {
        this.columnNames = columnNames;
    }    
    
}
