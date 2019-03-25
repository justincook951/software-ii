/*
 */
package softwareii.validator;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Validator {
    
    protected ValidationsCollections vc;
    
    public Validator() {
        vc = new ValidationsCollections();
    }
    
    public boolean containsNoEmpties(HashMap<String, String> paramMap, ArrayList<String> requiredFields) {
        //Only require checking if the column is in the requiredFields list
        List<String> checkableFields = paramMap.entrySet()
                .stream()
                .filter(e -> requiredFields.contains(e.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        return(checkableFields.stream().allMatch(vc.notNullStr.and(vc.notEmpty).and(vc.notZeroStr))) ;
    }
    
    public boolean isNotNull(String t) {
        return vc.notNullStr.test(t);
    }
    
    public boolean isNotNull(int t) {
        return vc.notNullInt.test(t);
    }
    
    public boolean isInOperatingHours(LocalTime t) {
        return vc.inOperatingHours.test(t);
    }
    
}
