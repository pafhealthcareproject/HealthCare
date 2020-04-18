package model;

import beans.UserBean;
import com.google.gson.JsonObject;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import util.DBConnection;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User {

    public String insertUser(UserBean usr) {

        String output = "";

        try {

            Connection con = DBConnection.connect();

            if (con == null) {

                return "Error while connecting to the database for inserting.";

            }

            // Creating prepared statements
            String userQuery = "insert into user" + "(userID, firstName, lastName, age, gender, email, address, username, password)" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String userPhoneQuery = "insert into userphone" + "(userID, userPhone)" + " values (?, ?)";

            PreparedStatement preparedStmtForUser = con.prepareStatement(userQuery);
            PreparedStatement preparedStmtForUserPhone = con.prepareStatement(userPhoneQuery);

            // Binding values to user Table
            preparedStmtForUser.setInt(1, 0);
            preparedStmtForUser.setString(2, usr.getFirstName());
            preparedStmtForUser.setString(3,  usr.getLastName());
            preparedStmtForUser.setInt(4, Integer.parseInt(usr.getAge()));
            preparedStmtForUser.setString(5,  usr.getGender());
            preparedStmtForUser.setString(6,  usr.getEmail());
            preparedStmtForUser.setString(7,  usr.getAddress());
            preparedStmtForUser.setString(8,  usr.getUsername());
            preparedStmtForUser.setString(9,  usr.getPassword());

            // Binding values to userPhone Table
            preparedStmtForUserPhone.setInt(1, 0);
            preparedStmtForUserPhone.setString(2, usr.getUserphone());

            // Executing the statements
            preparedStmtForUser.execute();
            preparedStmtForUserPhone.execute();

            JsonObject msg = new JsonObject();
            msg.addProperty("id", keyGen());
            msg.addProperty("username", usr.getUsername());
            msg.addProperty("password", usr.getPassword());
            msg.addProperty("role", "user");

            HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
            Client client = ClientBuilder.newBuilder().register(feature).build();
            WebTarget webTarget = client.target("http://localhost:8080/AuthService/AuthService").path("users");
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.post(Entity.entity(msg.toString(), MediaType.APPLICATION_JSON));

            con.close();
            output = "Inserted successfully";

        } catch (Exception e) {

            output = "Error while inserting the user.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public List<UserBean> readUser() {

        return readUser(0);

    }

    public UserBean readUserById(int id) {

        List<UserBean> list = readUser(id);

        if (!list.isEmpty()) {

            return list.get(0);

        }

        return null;

    }

    public List<UserBean> readUser(int id) {

        List<UserBean> userList = new ArrayList<>();

        try {

            Connection con = DBConnection.connect();

            if (con == null) {

                System.out.println("Error while connecting to the database for reading.");
                return userList;

            }

            String query;

            if (id == 0) {

                query = "select * from user";

            } else {

                query = "select * from user where userID=" + id;

            }

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                UserBean usr = new UserBean(

                        rs.getInt("userID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("age"),
                        rs.getString("gender"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("userPhone"),
                        rs.getString("role")

                );

                userList.add(usr);
            }
            con.close();

        } catch (Exception e) {
            System.out.println("Error while reading the users.");
            System.err.println(e.getMessage());
        }
        return userList;
    }

    public String updateUser(UserBean usr) {

        String output = "";

        try {

            Connection con = DBConnection.connect();

            if (con == null) {

                return "Error while connecting to the database for updating.";

            }

            // Creating prepared statements
            String userQuery = "UPDATE user SET" + " firstName=?," + "lastName=?," + "age=?," + "gender=?," + "email=?," + "address=?," + "username=?," + "password=?" + "WHERE userID=?";
            String userPhoneQuery = "UPDATE userphone SET" + " userPhone=?" + "WHERE userID=?";

            PreparedStatement userDetails = con.prepareStatement(userQuery);
            PreparedStatement userPhoneDetails = con.prepareStatement(userPhoneQuery);

            // Binding values to userQuery
            userDetails.setString(1, usr.getFirstName());
            userDetails.setString(2, usr.getLastName());
            userDetails.setString(3, usr.getAge());
            userDetails.setString(4, usr.getGender());
            userDetails.setString(5, usr.getEmail());
            userDetails.setString(6, usr.getAddress());
            userDetails.setString(7, usr.getUsername());
            userDetails.setString(8, usr.getPassword());
            userDetails.setInt(9, usr.getId());

            // Binding values to userPhoneQuery
            userPhoneDetails.setString(1, usr.getUserphone());
            userPhoneDetails.setInt(2, usr.getId());

            // Executing the statements
            userDetails.execute();
            userPhoneDetails.execute();

            con.close();
            output = "Updated successfully";

        } catch (Exception e) {

            output = "Error while updating the user.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String deleteUser(String userID) {

        String output = "";

        try {

            Connection con = DBConnection.connect();

            if (con == null) {

                return "Error while connecting to the database when deleting.";

            }

            // create a prepared statement
            String query = "delete from user where userID=?";
            PreparedStatement preparedStmt = con.prepareStatement(query);

            // binding values
            preparedStmt.setInt(1, Integer.parseInt(userID));

            // execute the statement
            preparedStmt.execute();

            con.close();
            output = "Deleted successfully";

        } catch (Exception e) {

            output = "Error while deleting the user.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public int keyGen() {

        List<UserBean> list;
        list = readUser();
        int num = list.size() + 1000;
        return num;

    }

}
