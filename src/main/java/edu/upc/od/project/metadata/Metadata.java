package edu.upc.od.project.metadata;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alvaro on 13/05/14.
 */
public class Metadata {
    private ObjectMapper mapper;
    public static final String METADATA_REPO = "C:\\Users\\Adriana\\Documents\\GitHub\\bookrecommender\\";
    public Metadata(){
        this.mapper = new ObjectMapper();

    }
    public HashMap<String, ArrayList< QueryMD>> getQueriesMD() throws IOException {
        return mapper.readValue(new File(METADATA_REPO + "queries_metadata.json"), new TypeReference<HashMap<String, ArrayList< QueryMD>>>(){});
    }
    public HashMap<String, SourceMD> getSourcesMD() throws IOException {
        return mapper.readValue(new File(METADATA_REPO + "sources_metadata.json"), new TypeReference<HashMap<String, SourceMD>>(){});
    }
    public IntegrationMD getIntegrationMD() throws IOException {
        return mapper.readValue(new File(METADATA_REPO + "integration_metadata.json"), IntegrationMD.class);
    }
}
