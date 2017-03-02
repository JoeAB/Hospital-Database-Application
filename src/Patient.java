import com.mongodb.BasicDBObject;

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
    Patient(String firstName, String sex, String lastName, int age, String condition) {
        put("firstName", firstName);
        put("lastName", lastName);
        put("age", age);
        put("sex", sex);
        put("condition", condition);
    }
}
