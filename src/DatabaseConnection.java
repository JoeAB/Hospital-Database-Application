import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;

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

  void add(BasicDBObject person, String type){
    MongoCollection collection = database.getCollection(type);
    org.bson.Document document= new org.bson.Document();
    collection.insertOne(document.parse(person.toJson()));
  }

  //code to create a more generalized search method
  ArrayList<BasicDBObject> searchDB(HashMap search, String type) {
    MongoCollection collection = database.getCollection(type, BasicDBObject.class);
    BasicDBObject fields = new BasicDBObject();
    for (Object key : search.keySet()) {
        fields.put(key.toString(), search.get(key));
    }
    MongoCursor<BasicDBObject> cursor = collection.find(fields).iterator();
    ArrayList<BasicDBObject> results = new ArrayList<BasicDBObject>();
    while(cursor.hasNext()){
      results.add(cursor.next());
    }
    return results;
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

  //try to change method to allow string input parameter to find most common occurrence of particular fields
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

  void getPatientDoctor(String patientID){
    DB database = mongoClient.getDB("hospital_db");
    DBCollection patients =  database.getCollection("patient");
    BasicDBObject match = new BasicDBObject();
    BasicDBObject matchQuery = new BasicDBObject();
    matchQuery.put("_id", new ObjectId(patientID));
    match.put("$match", matchQuery);

    //DBObject match = new BasicDBObject("$match", new BasicDBObject().put("_id",new ObjectId(patientID)));
    DBObject lookupFields = new BasicDBObject("from","doctor");
    lookupFields.put("localField","doctorID");
    lookupFields.put("foreignField", "_id");
    lookupFields.put("as","doctorResult");
    DBObject lookUp = new BasicDBObject("$lookup", lookupFields);

    AggregationOutput output = patients.aggregate(match, lookUp);
    for (DBObject document: output.results()) {
      BasicDBList result = (BasicDBList) document.get("doctorResult");
      System.out.println(((BasicDBObject)result.get(0)).get("lastName"));
    }
  }

  BulkWriteResult getPatientStats(ArrayList<BasicDBObject> queries){
    DB database = mongoClient.getDB("hospital_db");
    DBCollection patients =  database.getCollection("patient");
    BulkWriteOperation stat = patients.initializeOrderedBulkOperation();
    for (BasicDBObject query:queries) {
      stat.find(query);
    }
    return stat.execute();
  }
}