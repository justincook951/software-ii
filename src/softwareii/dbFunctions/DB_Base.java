package softwareii.dbFunctions;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import softwareii.model.Query;

/**
 *
 * @author Jay
 */
public class DB_Base {

    protected static Connection connection = null;
    protected static Statement statement = null;
    protected static PreparedStatement pstatement = null;
    protected static String driver = "com.mysql.cj.jdbc.Driver";
    protected static String db = "U04fd2";
    protected static String url = "jdbc:mysql://52.206.157.109/" + db;
    protected static String user = "U04fd2";
    protected static String pass = "53688226882";
    protected static String loggedInUser = "Overlord";
    protected static List<String> columns;
    protected static String customerDBName = "customer";
    protected static String customerIDName = "customerId";
    protected static String addressDBName = "address";
    protected static String addressIDName = "addressId";

    
    
                //========================================================================================
                //==========================EXECUTE AND QUERY SPECIFIC FUNCTIONS==========================
                //========================================================================================
    
    public ResultSet execute(String queryString, HashMap<String, String> params) throws SQLException {
        Query query = new Query(queryString, params);
        ResultSet results = this.execute(query);
        return results;
    }
    
    protected ResultSet execute(Query query) throws SQLException {
        ResultSet results = null;
        this.bindStatement(query);
        results = db_executeQuery();
        return results;
    }
    
    public ResultSet getItemByID(int ID, String identifier, String table) throws SQLException {
        HashMap<String, String> params = new HashMap();
        params.put(identifier, Integer.toString(ID));
        String queryString = "SELECT * FROM " + table + " WHERE " + identifier + " = :" + identifier;
        Query query = new Query(queryString, params);
        return this.execute(query);
    }
    
                //========================================================================================
                //==========================RUN AND UPDATE SPECIFIC FUNCTIONS=============================
                //========================================================================================
    
    /**
     * Accepts a pre-formatted query string and the parameters that need to be bound.
     * @param queryString
     * @param params 
     */
    public void run(String queryString, HashMap<String, String> params) throws SQLException {
        Query query = new Query(queryString, params);
        this.bindStatement(query);
        db_executeUpdate();
    }
    
    /**
     * Accepts a pre-formatted query string and the parameters that need to be bound.
     * Returns the ID of the value that was inserted into the table.
     * @param queryString
     * @param params 
     */
    public int insertAndGetID(String queryString, HashMap<String, String> params) throws SQLException {
        int result = 0;
        Query query = new Query(queryString, params);
        System.out.println("Insert params: " + params);
        System.out.println("And now sorted: " + query.getSortedParams());
        this.bindStatement(query);
        db_executeUpdate();
        ResultSet rset = pstatement.getGeneratedKeys();
        if (rset.next()) {
            result = rset.getInt(1);
        }
        return result;
    }
    
    protected void deleteByID(int ID, String identifier, String table) throws SQLException {
        HashMap<String, String> params = new HashMap();
        params.put(identifier, Integer.toString(ID));
        String queryString = "DELETE FROM " + table + " WHERE " + identifier + " = :" + identifier;
        Query query = new Query(queryString, params);
        this.bindStatement(query);
        db_executeUpdate();
    }
    
                //========================================================================================
                //============================INNER WORKINGS AND MISC=====================================
                //========================================================================================
    
    private static boolean createConnection() {
        boolean couldGenerate = true;
        if (connection == null || statement == null) {
            couldGenerate = false;
            try {
                connection = DriverManager.getConnection(url,user,pass);
                statement = connection.createStatement();
                couldGenerate = true;
            } catch (Exception e) {
                System.out.println("There was an error generating a MySQL connection");
            }
        }
        return couldGenerate;
    }

    private static ResultSet db_executeQuery() throws SQLException {
        ResultSet results = null;
        if (DB_Base.createConnection()) {
            results = pstatement.executeQuery();
        }
        return results;
    }

    private static void db_executeUpdate() throws SQLException {
        if (DB_Base.createConnection()) {
            pstatement.executeUpdate();
        }
    }
    
    protected void bindStatement(Query query) throws SQLException {
        //WHERE value = :val; {"val" => (Obj)val};
        if (createConnection()) {
            pstatement = connection.prepareStatement(query.getParamedString(), Statement.RETURN_GENERATED_KEYS);
            Map<String, String> params = query.getSortedParams();
            int paramCounter = 1;
            Iterator paramIterator = params.entrySet().iterator();
            while (paramIterator.hasNext()) {
                Map.Entry pair = (Map.Entry)paramIterator.next();
                pstatement.setObject(paramCounter, pair.getValue());
                paramCounter++;
            }
        }        
    }
    
    protected String generateWhere(HashMap<String, String> inbMap) {
        String returnString = "";
        String key;
        for (Map.Entry<String, String> mapVal : inbMap.entrySet()) {
            key = mapVal.getKey(); 
            returnString += key + " = :" + key + " AND ";
        }
        returnString = returnString.substring(0, returnString.length() - 5);
        return returnString;
    }
    

    
}