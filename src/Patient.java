import com.mongodb.BasicDBObject;

class Patient extends BasicDBObject {
    private int id;
    private String firstName;
    private String lastName;


     Patient(String firstName, String lastName, String age) {
         put("firstName", firstName);
         put("lastName", lastName);
         put("age", age);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
