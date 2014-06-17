package edu.upc.od.project.metadata;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alvaro on 13/05/14.
 */
public class QueryMD {
    private String source;
    private String byProcessing;
    private ArrayList<String> output;
    private HashMap<String, String> mapping;
    private String query;
    private String outputProcessing;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getByProcessing() {
        return byProcessing;
    }

    public void setByProcessing(String byProcessing) {
        this.byProcessing = byProcessing;
    }

    public ArrayList<String> getOutput() {
        return output;
    }

    public void setOutput(ArrayList<String> output) {
        this.output = output;
    }

    public HashMap<String, String> getMapping() {
        return mapping;
    }

    public void setMapping(HashMap<String, String> mapping) {
        this.mapping = mapping;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getOutputProcessing() {
        return outputProcessing;
    }

    public void setOutputProcessing(String outputProcessing) {
        this.outputProcessing = outputProcessing;
    }
}
