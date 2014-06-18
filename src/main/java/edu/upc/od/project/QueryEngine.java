package edu.upc.od.project;

import edu.upc.od.project.metadata.QueryMD;
import edu.upc.od.project.metadata.SourceMD;
import org.apache.commons.lang.StringEscapeUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alvaro on 14/05/14.
 */
public abstract class QueryEngine {
    private ObjectMapper mapper;
    private ScriptEngine engine;
    private ScriptEngineManager engFactory;

    public QueryEngine() {
        this.mapper = new ObjectMapper();
        engFactory = new ScriptEngineManager();
    }


    protected abstract ArrayList<Map<String, String>> doQuery(String queryStr, String endpoint);

    public ArrayList<Map<String, String>> performQuery(QueryMD query, SourceMD sourceMD, ArrayList<String> by) throws IOException {

        ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
        ArrayList<String> byProc = new ArrayList<String>();
        try {
            byProc = processBy(query.getByProcessing(), by);
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        String queryStr = generateQuery(query, byProc);
        result = doQuery(queryStr, sourceMD.getEndpoint());
        try {
            result = resultsProcess(result, query.getOutput(), query.getMapping(), query.getOutputProcessing());
        } catch (ScriptException e) {
            e.printStackTrace();
        }

        return result;
    }
    ArrayList<String> processBy(String byProcessStr, ArrayList<String> by) throws IOException, ScriptException {
        String byStr = mapper.writeValueAsString(by);
        engine = engFactory.getEngineByName("JavaScript");
        engine.eval(new java.io.FileReader("isbn-groups.js"));
        engine.eval(new java.io.FileReader("isbn.js"));
        engine.eval("var by="+byStr+";"+"var $byProcessing="+byProcessStr+";$result=$byProcessing()");
        return (ArrayList<String>) mapper.readValue(mapper.writeValueAsString(engine.get("$result")), new TypeReference<ArrayList<String>>() {
        });
    }

    private ArrayList<Map<String, String>> resultsProcess(ArrayList<Map<String, String>> books, ArrayList<String> outputs, Map<String, String> mapping, String outputProcess) throws ScriptException, IOException {
        ArrayList<Map<String, String>> results = new ArrayList<Map<String, String>>();

        for(Map<String, String> book: books){
            Map<String,String> result = new HashMap<String, String>();
            String vars = "";
            for(String output: outputs){
                vars += "var "+output+"=\""+ StringEscapeUtils.escapeJavaScript(book.get(output))+"\";";
            }
            for(Map.Entry<String, String> merge : mapping.entrySet()){
                engine = engFactory.getEngineByName("JavaScript");
                engine.eval(new java.io.FileReader("isbn-groups.js"));
                engine.eval(new java.io.FileReader("isbn.js"));
                String js = vars+"var $mapping="+ merge.getValue()+";$result=$mapping()";
                engine.eval(js);
                result.put(merge.getKey(), (String) engine.get("$result"));
            }
            results.add(result);
        }

        String resultsStr = mapper.writeValueAsString(results);
        engine = engFactory.getEngineByName("JavaScript");
        String js = "var $output="+resultsStr+";var $outputProcessing="+outputProcess+";$result=$outputProcessing()";
        engine.eval(js);
        results = mapper.readValue(mapper.writeValueAsString(engine.get("$result")), new TypeReference<ArrayList<Map<String, String>>>() {});

        return results;
    }

    private String generateQuery(QueryMD query, ArrayList<String> by){
        String queryStr = query.getQuery();

        for(int i = 0; i<by.size(); i++){
            queryStr = queryStr.replace("{{BY"+ i +"}}", by.get(i));
        }

        return queryStr;
    }
}
