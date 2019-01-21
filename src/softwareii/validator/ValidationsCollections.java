/*
 */
package softwareii.validator;

import java.util.function.Predicate;

/**
 *
 * @author Jay
 */
public class ValidationsCollections {
    
    //Use a bunch of lambda functions to fill out Predicate tests
    public static Predicate<String> notNullStr = (i) -> i != null;
    public static Predicate<Integer> notNullInt = (i) -> i != null;
    public static Predicate<String> notEmpty = (i) -> !i.equals("");
    public static Predicate<String> notZeroStr = (i) -> !i.equals("0");
    public static Predicate<Integer> notZeroInt = (i) -> i != 0;
    
}
