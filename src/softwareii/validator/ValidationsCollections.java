/*
 */
package softwareii.validator;

import java.time.LocalTime;
import java.util.function.Predicate;

/**
 *
 * @author Jay
 */
public class ValidationsCollections {
    
    //Use a bunch of lambda functions to fill out Predicate tests
    public Predicate<String> notNullStr = (i) -> i != null;
    public Predicate<Integer> notNullInt = (i) -> i != null;
    public Predicate<String> notEmpty = (i) -> !i.equals("");
    public Predicate<String> notZeroStr = (i) -> !i.equals("0");
    public Predicate<Integer> notZeroInt = (i) -> i != 0;
    //Lol.
    public Predicate<LocalTime> inOperatingHours = (i) -> LocalTime.of(8, 0).isBefore(i) && LocalTime.of(18, 0).isAfter(i);
    
}
