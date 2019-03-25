package softwareii.initializer;

import java.time.ZoneId;
import softwareii.dbFunctions.AddressDB;
import softwareii.dbFunctions.AppointmentDB;
import softwareii.dbFunctions.CityDB;
import softwareii.dbFunctions.CountryDB;
import softwareii.dbFunctions.CustomerDB;
import softwareii.dbFunctions.LoginDB;
import softwareii.dbFunctions.ReportsDB;
import softwareii.validator.Validator;

public class Initializer {
    //DB Classes
    public static AddressDB addressdb = new AddressDB();
    public static AppointmentDB appointmentdb = new AppointmentDB();
    public static CityDB citydb = new CityDB();
    public static CountryDB countrydb = new CountryDB();
    public static CustomerDB customerdb = new CustomerDB();
    public static LoginDB logindb = new LoginDB();
    public static ReportsDB reportsdb = new ReportsDB();
    
    //Validator
    public static Validator validator = new Validator();
    
    //Zone offset for timestamp functions
    public static ZoneId currentZone = ZoneId.systemDefault();
    
}
