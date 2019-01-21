/*
 */
package softwareii.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Validator {
    
    public boolean containsNoEmpties(HashMap<String, String> paramMap, ArrayList<String> requiredFields) {
        ValidationsCollections vc = new ValidationsCollections();
        //Only require checking if the column is in the requiredFields list
        List<String> checkableFields = paramMap.entrySet()
                .stream()
                .filter(e -> requiredFields.contains(e.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        return(checkableFields.stream().allMatch(vc.notNullStr.and(vc.notEmpty).and(vc.notZeroStr))) ;
    }
    
    public boolean isNotNull(String t) {
        ValidationsCollections vc = new ValidationsCollections();
        return vc.notNullStr.test(t);
    }
    
    public boolean isNotNull(int t) {
        ValidationsCollections vc = new ValidationsCollections();
        return vc.notNullInt.test(t);
    }
    
}
