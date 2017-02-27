import com.mongodb.*;

import java.util.ArrayList;
import java.util.HashMap;

class DatabaseConnection {
  private MongoClient mongoClient;
  private DB database;

  DatabaseConnection() {
    try {
      mongoClient = new MongoClient("localhost", 27017);
      database = mongoClient.getDB("hospital_db");

    }catch (Exception e) {
      System.out.println("Critical error encountered.");
    }
  }
    void addPatient(Patient patient){
      DBCollection patients = database.getCollection("patient");
      patients.insert(patient);
  }

  ArrayList<Patient> getPatients(HashMap search) {
    DBCollection patients = database.getCollection("patient");
    BasicDBObject fields = new BasicDBObject();
    for (Object key : search.keySet()){
      if (search.get(key) != "") {
        fields.put(key.toString(), search.get(key));
      }
    }
    DBCursor cursor = patients.find(fields);
    while (cursor.hasNext()){
      System.out.println(cursor.next());
    }
    return new ArrayList<>();
  }



}
