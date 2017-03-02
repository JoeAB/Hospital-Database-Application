import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.HashMap;

class DatabaseConnection {
  private MongoClient mongoClient;
  private MongoDatabase database;

  DatabaseConnection() {
    try {
      mongoClient = new MongoClient("localhost", 27017);
      database = mongoClient.getDatabase("hospital_db");

    }catch (Exception e) {
      System.out.println("Critical error encountered.");
    }
  }
    void addPatient(Patient patient){
      MongoCollection patients = database.getCollection("patient");
      org.bson.Document document= new org.bson.Document();
      patients.insertOne(document.parse(patient.toJson()));
  }

  ArrayList<Patient> getPatients(HashMap search) {
    MongoCollection patients = database.getCollection("patient");
    BasicDBObject fields = new BasicDBObject();
    for (Object key : search.keySet()) {
      if (search.get(key) != "") {
        fields.put(key.toString(), search.get(key));
      }
    }
   MongoCursor<Patient> cursor = patients.find(fields).iterator();
    while(cursor.hasNext()){
      System.out.println(cursor.next());
    }
    return new ArrayList<>();
  }

  void averageAge(){
    DB database = mongoClient.getDB("hospital_db");
    DBCollection patients =  database.getCollection("patient");

    DBObject match = new BasicDBObject("$match", new BasicDBObject());

    DBObject projectQuery = new BasicDBObject("age",  1);
    projectQuery.put("_id",0);
    DBObject project = new BasicDBObject("$project",projectQuery);

    DBObject groupFields = new BasicDBObject( "_id", "");
    groupFields.put("average", new BasicDBObject( "$avg", "$age"));
    DBObject group = new BasicDBObject("$group", groupFields);

    AggregationOutput output = patients.aggregate(match,project, group);
    for (DBObject document: output.results()) {
        System.out.println(document.get("average"));
    }
  }

  void mostCommon(){
    DB database = mongoClient.getDB("hospital_db");
    DBCollection patients =  database.getCollection("patient");
    //match to all documents in the collection
    DBObject match = new BasicDBObject("$match", new BasicDBObject());
    //retrieve the last name attribute only from every document in the collection
    DBObject projectQuery = new BasicDBObject("lastName",  1);
    projectQuery.put("_id",0);
    DBObject project = new BasicDBObject("$project",projectQuery);
    //last name is now used as _id, group by the lastName, and count the total times it occurs
    DBObject groupFields = new BasicDBObject( "_id", "$lastName");
    groupFields.put("count", new BasicDBObject( "$sum", 1));
    DBObject group = new BasicDBObject("$group", groupFields);
    //{ $sort: { score: { $meta: "textScore" }, posts: -1 } }
    //sort by the count from greatest to least
    DBObject sortFields = new BasicDBObject("$sort", new BasicDBObject("count", -1));
    //limit return results to 1 record
    DBObject limit = new BasicDBObject("$limit", 1);
    //create pipeline and return results
    AggregationOutput output = patients.aggregate(match,project, group, sortFields, limit);
    for (DBObject document: output.results()) {
      System.out.println(document.get("_id")+": "+document.get("count"));
    }
  }
}
