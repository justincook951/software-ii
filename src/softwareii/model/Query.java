package softwareii.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Query extends BaseClass {
    protected String queryString;
    protected HashMap parameters;
    protected LinkedHashMap sortedParams;
    protected String queryType;
    
    public Query (String queryString, HashMap<String, String> parameters) {
        //"SELECT * FROM TABLE WHERE VALUE = :VAL AND VALUE2 = :VAL2
        this.queryString = queryString;
        //["VAL" = (Obj)VAL, "VAL2" = (Obj)VAL2]
        this.parameters = convertToLower(parameters);
        this.sortedParams = new LinkedHashMap<>();
        //INSERT, UPDATE, DELETE, SELECT... All have 6 letters?? How fortunate!
        this.queryType = queryString.substring(0, 6);
    }
    
    public String getParamedString() {
        //this.queryString = standardizeQuery(this.queryString); scrapped in favor of, er, writing the queries right the first time
        parameterizeQuery(this.queryString);
        return this.queryString;
    }
    
    public HashMap getSortedParams() {
        if (this.sortedParams.isEmpty()) {
            this.parameterizeQuery(this.queryString);
        }
        System.out.println("sortedparams from inside method: " + sortedParams);
        return sortedParams;
    }
    
    protected void parameterizeQuery(String queryString) {
        if (this.queryType.equals("SELECT")) {
            this.parameterizeSelect(queryString);
        }
        else if (this.queryType.equals("DELETE")) {
            this.parameterizeDelete(queryString);
        }
        else if (this.queryType.equals("INSERT")) {
            this.parameterizeInsert(queryString);
        }
        
        else if (this.queryType.equals("UPDATE")) {
            this.parameterizeUpdate(queryString);
        }
        else {
            //Throw custom exception
        }
    }
    
    protected void parameterizeSelect(String queryString) {
        //"SELECT * FROM TABLE WHERE VALUE = :VAL AND VALUE2 = :VAL2 AND SOMETIMEVALUE = now()
        //PDO, anyone? :) Only true PHP fans will remember
        String[] strList = queryString.split(" ");
        ArrayList<String> strArrayList = new ArrayList<String>(Arrays.asList(strList));
        /*
        SELECT * FROM TABLE WHERE VALUE = 
        VAL AND VALUE 2 = 
        VAL2
        */
        String tempQueryStr = "";
        for(String queryStrPart : strArrayList) {
            if (queryStrPart.contains(":")) {
                //Is a binding parameter
                String paramSegTest = queryStrPart.toLowerCase().replace(":", "");;
                //Add the key:value pair to sortedParams in the order that they appear in the query.
                this.parameters.forEach(
                    (key, value) -> {
                        //System.out.println("I've split on " + paramSegTest + " and I am attempting to find a match.");
                        if (key.equals(paramSegTest)) {
                           this.sortedParams.put(key, value); 
                           //System.out.println("I found a match for " + key);
                        }
                    }
                );
                //Replace the value that needs to be parameterized with the Java placeholder
                queryStrPart = "?";
            }
            //Re-attach manipulated strings to the final query string
            tempQueryStr += queryStrPart + " "; 
        }
        tempQueryStr = tempQueryStr.substring(0, tempQueryStr.length() - 1);
        System.out.println(tempQueryStr);
        this.queryString = tempQueryStr;
    }
    
    protected void parameterizeDelete(String queryString) {
        //Well, ain't this handy?
        this.parameterizeSelect(queryString);
    }
    
    protected void parameterizeInsert(String queryString) {
        //"SELECT * FROM TABLE WHERE VALUE = :VAL AND VALUE2 = :VAL2 AND SOMETIMEVALUE = now()
        //PDO, anyone? :) Only true PHP fans will remember
        String[] strList = queryString.split(" ");
        ArrayList<String> strArrayList = new ArrayList<String>(Arrays.asList(strList));
        /*
        SELECT * FROM TABLE WHERE VALUE = 
        VAL AND VALUE 2 = 
        VAL2
        */
        String tempQueryStr = "";
        boolean addedLeadingParen = false;
        for(String queryStrPart : strArrayList) {
            if (queryStrPart.contains(":")) {
                //Is a binding parameter
                String paramSegTest = queryStrPart.toLowerCase().replaceAll("[:]|[,]|[(]|[)]","");
                //Add the key:value pair to sortedParams in the order that they appear in the query.
                this.parameters.forEach(
                    (key, value) -> {
                        System.out.println("I've split on " + paramSegTest + " and I am attempting to find a match.");
                        if (key.equals(paramSegTest)) {
                           this.sortedParams.put(key, value); 
                        }
                    }
                );
                queryStrPart = "";
                if (!addedLeadingParen) {
                    //VALUES ?, etc
                    queryStrPart += "(";
                    addedLeadingParen = true;
                }
                //Replace the value that needs to be parameterized with the Java placeholder
                queryStrPart += "?,";
            }
            //Re-attach manipulated strings to the final query string
            tempQueryStr += queryStrPart + " "; 
        }
        tempQueryStr = tempQueryStr.substring(0, tempQueryStr.length() - 2) + ")";
        System.out.println(tempQueryStr);
        this.queryString = tempQueryStr;
    }
    
    protected void parameterizeUpdate(String queryString) {
        //"SELECT * FROM TABLE WHERE VALUE = :VAL AND VALUE2 = :VAL2 AND SOMETIMEVALUE = now()
        //PDO, anyone? :) Only true PHP fans will remember
        String[] strList = queryString.split(" ");
        ArrayList<String> strArrayList = new ArrayList<String>(Arrays.asList(strList));
        /*
        SELECT * FROM TABLE WHERE VALUE = 
        VAL AND VALUE 2 = 
        VAL2
        */
        String tempQueryStr = "";
        boolean inWhereClause = false;
        for(String queryStrPart : strArrayList) {
            if (queryStrPart.contains(":")) {
                //Is a binding parameter
                String paramSegTest = queryStrPart.toLowerCase().replaceAll("[:]|[,]|[(]|[)]","");
                //Add the key:value pair to sortedParams in the order that they appear in the query.
                this.parameters.forEach(
                    (key, value) -> {
                        System.out.println("I've split on " + paramSegTest + " and I am attempting to find a match.");
                        if (key.equals(paramSegTest)) {
                           this.sortedParams.put(key, value); 
                           System.out.println("I found a match for " + key);
                        }
                    }
                );
                //Replace the value that needs to be parameterized with the Java placeholder
                queryStrPart = "?";
                if (!inWhereClause) {
                    //WHERE column1=?, column2=?
                    queryStrPart += ",";
                }
            }
            // = ?, WHERE
            else if (queryStrPart.toLowerCase().contains("where")) {
                tempQueryStr = tempQueryStr.substring(0, tempQueryStr.length() - 2 ) + " ";
                inWhereClause = true;
            }
            //Re-attach manipulated strings to the final query string
            tempQueryStr += queryStrPart + " "; 
        }
        tempQueryStr = tempQueryStr.substring(0, tempQueryStr.length() - 1);
        System.out.println(tempQueryStr);
        this.queryString = tempQueryStr;
    }
    
    /*protected String standardizeQuery(String queryString) {
        queryString = queryString.replaceAll("[=]", " = ");
        String[] strList = queryString.split(" ");
        ArrayList<String> strArrayList = new ArrayList<>(Arrays.asList(strList));
        String newQueryStr = "";
        newQueryStr = strArrayList.stream().map((queryStrPart) -> {
            if (queryStrPart.contains("(")) {
                //ensure that there is a space before the "("
                if (!queryStrPart.equals("now()")) {
                    queryStrPart = queryStrPart.replace("(", " (");
                }
            }
            return queryStrPart;
        }).map((queryStrPart) -> {
            if (queryStrPart.contains(")")) {
                //ensure that there is a space after the ")"
                if (!queryStrPart.equals("now()")) {
                    queryStrPart = queryStrPart.replace(")", ") ");
                }
            }
            return queryStrPart;
        }).map((queryStrPart) -> queryStrPart + " ").reduce(newQueryStr, String::concat);
        newQueryStr = newQueryStr.substring(0, newQueryStr.length() - 1);
        newQueryStr = newQueryStr.trim().replaceAll(" +", " ");
        return newQueryStr;
    }*/
    
}
