package edu.upc.od.project.metadata;

import java.util.HashMap;

/**
 * Created by alvaro on 13/05/14.
 */
public class IntegrationMD {
    private String index;
    private String byIndexQuery;
    private HashMap<String, String> mapping;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getByIndexQuery() {
        return byIndexQuery;
    }

    public void setByIndexQuery(String byIndexQuery) {
        this.byIndexQuery = byIndexQuery;
    }

    public HashMap<String, String> getMapping() {
        return mapping;
    }

    public void setMapping(HashMap<String, String> mapping) {
        this.mapping = mapping;
    }
}
