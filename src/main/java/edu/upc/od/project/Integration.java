package edu.upc.od.project;

import edu.upc.od.project.metadata.IntegrationMD;
import edu.upc.od.project.metadata.Metadata;
import edu.upc.od.project.metadata.QueryMD;
import edu.upc.od.project.metadata.SourceMD;
import org.codehaus.jackson.map.ObjectMapper;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alvaro on 13/05/14.
 */
public class Integration {
    private ObjectMapper mapper;
    private ScriptEngine engine;
    private ScriptEngineManager engFactory;
    private Metadata md;
    Map<String, SourceMD> sourcesMD;
    Map<String, ArrayList<QueryMD>> queriesMD;
    IntegrationMD integrationMD;

    public Integration() throws IOException {
        this.md = new Metadata();
        this.mapper = new ObjectMapper();
        this.engFactory = new ScriptEngineManager();
        this.sourcesMD = md.getSourcesMD();
        this.queriesMD = md.getQueriesMD();
        this.integrationMD = md.getIntegrationMD();
    }

    public ArrayList<Map<String, String>> performQuery(String queryId, ArrayList<String> by) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, ScriptException {

        ArrayList<QueryMD> query = queriesMD.get(queryId);

        // index_value -> source -> book
        Map<String, Map<String, Map<String, String>>> indexedPartialResults = new HashMap<String, Map<String, Map<String,String>>>();

        for(QueryMD sourceQuery: query){
            SourceMD source = sourcesMD.get(sourceQuery.getSource());
            QueryEngine qe = (QueryEngine) Class.forName(source.getIface()).newInstance();
            for(Map<String, String> partialResult: qe.performQuery(sourceQuery, source, by)){
                String indexKey = integrationMD.getIndex();
                if(partialResult == null) {
                    break;
                }
                String indexValue = partialResult.get(indexKey);
                if(indexValue == null){
                    continue;
                }

                if(indexedPartialResults.get(indexValue) == null){
                    indexedPartialResults.put(indexValue, new HashMap<String, Map<String, String>>());
                }

                indexedPartialResults.get(indexValue).put(sourceQuery.getSource(), partialResult);
            }
        }
        // [source -> book, ...]
        return integrate(indexedPartialResults);
    }

    public ArrayList<Map<String, String>> integrate(Map<String, Map<String,Map<String, String>>> partialResults) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException, ScriptException {
        ArrayList<Map<String, String>> integratedBooks = new ArrayList<Map<String, String>>();

        for(Map.Entry<String, Map<String, Map<String, String>>> pResultEntry: partialResults.entrySet()){
            Map<String, Map<String, String>> results = pResultEntry.getValue();
            for(Map.Entry<String, SourceMD> sourceEntry: sourcesMD.entrySet()){
                if(results.get(sourceEntry.getKey()) == null){
                    // Get book byISBN from the source
                    QueryEngine qe = (QueryEngine) Class.forName(sourceEntry.getValue().getIface()).newInstance();
                    for(QueryMD query: md.getQueriesMD().get(integrationMD.getByIndexQuery())){
                        if(query.getSource() == sourceEntry.getKey()){
                            ArrayList<String> by = new ArrayList<String>();
                            by.add(pResultEntry.getKey());
                            ArrayList<Map<String, String>> byISBNResults = qe.performQuery(query, sourceEntry.getValue(), by);
                            if(byISBNResults != null){
                                results.put(sourceEntry.getKey(), byISBNResults.get(0));
                            }
                        }
                    }

                }
            }
            String resultsStr = mapper.writeValueAsString(results);
            Map<String, String> integratedBook = new HashMap<String, String>();

            for(Map.Entry<String, String> merge : integrationMD.getMapping().entrySet()){
                engine = engFactory.getEngineByName("JavaScript");
                String js = "var $query="+resultsStr+";"+"var $mapping="+merge.getValue()+";$result=$mapping()";
                engine.eval(js);
                integratedBook.put(merge.getKey(), (String) engine.get("$result"));
            }

            integratedBooks.add(integratedBook);
        }

        return integratedBooks;
    }
}
