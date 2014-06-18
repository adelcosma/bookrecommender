package edu.upc.od.project;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by alvaro on 14/05/14.
 */
public class QueryMongoDB extends QueryEngine {
    protected ArrayList<HashMap<String, String>> doQuery(String queryStr, String endpoint){
        try {
            String[] conn = endpoint.split("//");
            MongoClient mongoClient = new MongoClient();
            DB db = mongoClient.getDB(conn[0]);
            DBCollection coll = db.getCollection(conn[1]);
            DBObject query = (DBObject)JSON.parse(queryStr);

            DBCursor cursor = coll.find(query);
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toMap().toString());
                
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(QueryMongoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
   /* test
    * public static void main(String args[]) {
        QueryMongoDB qmdb = new QueryMongoDB();
        qmdb.doQuery("{\"authors\" : [ { \"key\" : \"/a/OL18485A\"}]}", "od//book_collection");
    }
    * */
}
