package edu.upc.od.project;

import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import org.codehaus.jackson.map.ObjectMapper;
import sun.awt.image.ImageWatched;

import javax.script.ScriptException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class QueryOrchestrator
{
    public static void main( String[] args ) throws IOException, ScriptException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Integration in = new Integration();
        ArrayList<String> by = new ArrayList<String>();
        by.add("0-553-10803-4");
        System.out.println(in.performQuery("byISBN", by).toString());
    }
}
