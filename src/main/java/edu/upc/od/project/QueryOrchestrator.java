package edu.upc.od.project;


import javax.script.ScriptException;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class QueryOrchestrator
{
    public static void main( String[] args ) throws IOException, ScriptException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Recommendation r = new Recommendation();
        System.out.print("Enter user: ");
        Scanner sc = new Scanner(System.in);
        String user = sc.next();
        do {
            print(r.getRecommendations(user));
            System.out.print("Enter 'more' or type any character to exit: ");
            user = sc.next();
        } while (user.equals("more"));
    }
    
    private static void print(ArrayList<Map<String, String>> s) {
        for (Map<String,String> m : s) {
            for (Entry<String,String> e : m.entrySet()) {
                System.out.print("{"+e.getKey()+": "+e.getValue()+"}");
            }
            System.out.println();
        }
    }
}
