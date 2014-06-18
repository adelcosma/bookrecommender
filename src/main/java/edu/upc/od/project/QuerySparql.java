package edu.upc.od.project;

import com.hp.hpl.jena.query.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

/**
 * Created by alvaro on 14/05/14.
 */
public class QuerySparql extends QueryEngine{


    protected ArrayList<Map<String, String>> doQuery(String queryStr, String endpoint){
        Query query = QueryFactory.create(queryStr);
        QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query);

        ArrayList<Map<String, String>> books = new ArrayList<Map<String, String>>();

        ResultSet results = qexec.execSelect();


        while( results.hasNext() ){
            Map<String, String> book = new HashMap<String, String>();

            QuerySolution qs = results.next();
            Iterator<String> varNames = qs.varNames();
            while(varNames.hasNext()){
                String varName = varNames.next();
                book.put(varName, qs.getLiteral(varName).getString());
            }
            books.add(book);
        }
        qexec.close();
        return books;
    }
}
