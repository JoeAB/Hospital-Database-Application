import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;

class Patient extends BasicDBObject {

    Patient(String firstName, String lastName) {
        put("firstName", firstName);
        put("lastName", lastName);
    }

     Patient(String firstName, String lastName, int age) {
         put("firstName", firstName);
         put("lastName", lastName);
         put("age", age);
    }
    Patient(String firstName, String lastName, int age, String condition, String doctorID) {
        put("firstName", firstName);
        put("lastName", lastName);
        put("age", age);
        //put("sex", sex);
        put("condition", condition);
        put("doctorID", new ObjectId(doctorID));
    }
}
