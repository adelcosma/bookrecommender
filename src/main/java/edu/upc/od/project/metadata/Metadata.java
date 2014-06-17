package edu.upc.od.project.metadata;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alvaro on 13/05/14.
 */
public class Metadata {
    private static final String SOURCES_MD_JSON= "{\n" +
                                                 "       \"dbpedia\": {\n" +
                                                 "             \"endpoint\": \"http://dbpedia.org/sparql\",\n" +
                                                 "             \"iface\": \"edu.upc.od.project.QuerySparql\"\n" +
                                                 "       },\n" +
                                                 "       \"mongodb\": {\n" +
                                                 "             \"endpoint\": \"\",\n" +
                                                 "             \"iface\": \"edu.upc.od.project.QueryMongoDB\"\n" +
                                                 "       }\n" +
                                                 "}";

    private static final String QUERIES_MD_JSON=    "{\n" +
                                                    "    \"byISBN\": [\n" +
                                                    "      {\n" +
                                                    "          \"source\": \"dbpedia\",\n" +
                                                    "          \"byProcessing\": \"function(){return by}\",\n" +
                                                    "          \"output\": [ \"s_isbn\", \"s_title\", \"s_synopsis\", \"s_language\", \"s_genre\", \"s_country\", \"s_author\" ],\n" +
                                                    "          \"mapping\":{\n" +
                                                    "            \"isbn\": \"function(){ return ISBN.parse(s_isbn.split(',')[0].split(' ')[1]).asIsbn10();}\",\n" +
                                                    "            \"title\": \"function(){return s_title}\",\n" +
                                                    "            \"synopsis\": \"function(){return s_synopsis}\",\n" +
                                                    "            \"language\": \"function(){return s_language}\",\n" +
                                                    "            \"genre\": \"function(){return s_genre}\",\n" +
                                                    "            \"country\": \"function(){return s_country}\",\n" +
                                                    "            \"author\": \"function(){return s_author}\"\n" +
                                                    "          },\n" +
                                                    "\n" +
                                                    "          \"query\": \"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\\nPREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\\nPREFIX dbpprop: <http://dbpedia.org/property/>\\nPREFIX ontology: <http://dbpedia.org/ontology/>\\nselect ?s_isbn ?s_title ?s_synopsis ?s_language ?s_genre ?s_country ?s_author\\nwhere {\\n?book rdf:type ontology:Book.\\n?book dbpprop:isbn ?s_isbn. FILTER regex(?s_isbn, \\\"{{BY0}}\\\"). FILTER( langMatches( lang( ?s_isbn ), \\\"en\\\" ) ).\\n?book dbpprop:genre ?_genre. OPTIONAL{?_genre rdfs:label ?s_genre. FILTER( langMatches( lang( ?s_genre ), \\\"en\\\" ) )}.\\nOPTIONAL{?book dbpprop:name ?s_title. FILTER( langMatches( lang( ?s_title ), \\\"en\\\" ) )}.\\nOPTIONAL{?book ontology:abstract ?s_synopsis. FILTER( langMatches( lang( ?s_synopsis ), \\\"en\\\" ) )}.\\n?book dbpprop:language ?_language. OPTIONAL{?_language  dbpprop:iso ?s_language. FILTER( langMatches( lang( ?s_language ), \\\"en\\\" ) )}.\\n?book dbpprop:country ?_country. OPTIONAL{?_country dbpprop:countryCode ?s_country. FILTER( langMatches( lang( ?s_country ), \\\"en\\\" ) )}.\\n?book dbpprop:author ?_author. OPTIONAL{?_author dbpprop:name ?s_author. FILTER( langMatches( lang( ?s_author ), \\\"en\\\" ) )}.\\n}\"," +
                                                    "\n" +
                                                    "          \"outputProcessing\": \"function(){for(var i=0;i<$output.length;i++){if($output[i].language.length == 2){return [$output[i]]}} return [$output[0]]}\"" +
                                                    "      }\n" +
                                                    "    ]\n" +
                                                    "}";
    private static final String INTEGRATION_MD_JSON=    "{\n" +
                                                        "    \"index\": \"isbn\",\n" +
                                                        "    \"byIndexQuery\": \"byISBN\",\n" +
                                                        "    \"mapping\": {\n" +
                                                        "        \"title\": \"function(){return $query.dbpedia.title}\",\n" +
                                                        "        \"isbn\": \"function(){return $query.dbpedia.isbn}\",\n" +
                                                        "        \"synopsis\": \"function(){return $query.dbpedia.synopsis}\",\n" +
                                                        "        \"language\": \"function(){return $query.dbpedia.language}\",\n" +
                                                        "        \"genre\": \"function(){return $query.dbpedia.genre}\",\n" +
                                                        "        \"country\": \"function(){return $query.dbpedia.country}\",\n" +
                                                        "        \"author\": \"function(){return $query.dbpedia.author}\"\n" +
                                                        "    }\n" +
                                                        "}";
    private ObjectMapper mapper;
    public Metadata(){
        this.mapper = new ObjectMapper();

    }
    public HashMap<String, ArrayList< QueryMD>> getQueriesMD() throws IOException {
        return mapper.readValue(QUERIES_MD_JSON, new TypeReference<HashMap<String, ArrayList< QueryMD>>>(){});
    }
    public HashMap<String, SourceMD> getSourcesMD() throws IOException {
        return mapper.readValue(SOURCES_MD_JSON, new TypeReference<HashMap<String, SourceMD>>(){});
    }
    public IntegrationMD getIntegrationMD() throws IOException {
        return mapper.readValue(INTEGRATION_MD_JSON, IntegrationMD.class);
    }
}
