package beans;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.User;

@XmlRootElement
public class UserBean {
    int id;
    private String firstName;
    private String lastName;
    private String age;
    private String gender;
    private String email;
    private String address;
    private String username;
    private String password;

    public UserBean() {}

    public UserBean(String usr) {

        JsonObject userObject = new JsonParser().parse(usr).getAsJsonObject();

        if (userObject.get("userID") !=null) {
            this.id = userObject.get("userID").getAsInt();
        }
        this.firstName = userObject.get("firstName").getAsString();
        this.lastName = userObject.get("lastName").getAsString();
        this.age = userObject.get("age").getAsString();
        this.gender = userObject.get("gender").getAsString();
        this.email = userObject.get("email").getAsString();
        this.address = userObject.get("address").getAsString();
        this.username = userObject.get("username").getAsString();
        this. password = userObject.get("password").getAsString();

    }

    public UserBean(int id, String firstName, String lastName, String age, String gender, String email, String address, String username, String password) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.username = username;
        this.password = password;

    }

    public UserBean(String firstName, String lastName, String age, String gender, String email, String address, String username, String password){

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.username = username;
        this.password = password;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
