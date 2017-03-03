import com.mongodb.BasicDBObject;

public class Doctor extends BasicDBObject {
  public Doctor(String firstName, String lastName, String specialty){
    put("firstName", firstName);
    put("lastName", lastName);
    put("specialty", specialty);
  }
}
