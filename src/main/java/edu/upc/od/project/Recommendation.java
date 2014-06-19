package edu.upc.od.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptException;

/**
 * Created by alvaro on 13/05/14.
 */
public class Recommendation {
    public ArrayList<Map<String, String>> getRecommendations(String user){
        try {
            Integration in = new Integration();
            return top10(in);
        } catch (IOException ex) {
            Logger.getLogger(Recommendation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Recommendation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Recommendation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Recommendation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ScriptException ex) {
            Logger.getLogger(Recommendation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<Map<String, String>>();
    }

    private ArrayList<Map<String, String>> top10(Integration in) throws IOException, ScriptException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ArrayList<String> by = new ArrayList<String>();
        ArrayList<Map<String, String>> res = in.performQuery("randomQuery", by);
        ArrayList<Map<String, String>> top = new ArrayList<Map<String, String>>(10);
        for (int i = 0; i < 10; i++) {
            double index = Math.random()*(res.size()-1);
            top.add(res.get((int)index));
        }
        return top;
    }

}
