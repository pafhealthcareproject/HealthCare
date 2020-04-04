package model;

import com.google.gson.JsonObject;

import java.sql.*;

public class User {

    private Connection connect() {

        Connection con = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            // DB Name, Username and Password
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/healthcare", "root", "");

        } catch (Exception e) {

            e.printStackTrace();

        }

        return con;

    }

    public String insertUser(String firstName, String lastName, String age, String gender, String email, String address, String username, String password, String userPhone) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while inserting the user details.";

            }

            // Creating prepared statements
            String userQuery = "insert into user" + "(userID, firstName, lastName, age, gender, email, address, username, password)" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String userPhoneQuery = "insert into userphone" + "(userID, userPhone)" + " values (?, ?)";

            PreparedStatement preparedStmtForUser = con.prepareStatement(userQuery);
            PreparedStatement preparedStmtForUserPhone = con.prepareStatement(userPhoneQuery);

            // Binding values to user Table
            preparedStmtForUser.setInt(1, 0);
            preparedStmtForUser.setString(2, firstName);
            preparedStmtForUser.setString(3, lastName);
            preparedStmtForUser.setInt(4, Integer.parseInt(age));
            preparedStmtForUser.setString(5, gender);
            preparedStmtForUser.setString(6, email);
            preparedStmtForUser.setString(7, address);
            preparedStmtForUser.setString(8, username);
            preparedStmtForUser.setString(9, password);

            // Binding values to userPhone Table
            preparedStmtForUserPhone.setInt(1, 0);
            preparedStmtForUserPhone.setString(2, userPhone);

            // Executing the statements
            preparedStmtForUser.execute();
            preparedStmtForUserPhone.execute();

            con.close();

            output = "User details inserted successfully.";

        } catch (Exception e) {

            output = "An error occurred while inserting the user details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String readUser() {

        String output = "";

        try {
            Connection con = connect();
            if (con == null) {

                return "Database connection error occurred while reading the user details.";

            }

            String userQuery = "select * from user";
            String userPhoneQuery = "select * from userphone";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(userQuery);

            Statement stmtPhone = con.createStatement();
            ResultSet rsPhone = stmtPhone.executeQuery(userPhoneQuery);

            while (rs.next()) {

                String userID = Integer.toString(rs.getInt("userID"));
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String age = Integer.toString(rs.getInt("age"));
                String gender = rs.getString("gender");
                String email = rs.getString("email");
                String address = rs.getString("address");
                String username = rs.getString("username");
                String password = rs.getString("password");

                JsonObject userDetails = new JsonObject();

                userDetails.addProperty("userID", userID);
                userDetails.addProperty("firstName", firstName);
                userDetails.addProperty("lastName", lastName);
                userDetails.addProperty("age", age);
                userDetails.addProperty("gender", gender);
                userDetails.addProperty("email", email);
                userDetails.addProperty("address", address);
                userDetails.addProperty("username", username);
                userDetails.addProperty("password", password);

                output = userDetails.toString();

            }

            while (rsPhone.next()) {

                String userID = Integer.toString(rs.getInt("userID"));
                String userPhone = rs.getString("userPhone");

                JsonObject userPhoneDetails = new JsonObject();

                userPhoneDetails.addProperty("userID", userID);
                userPhoneDetails.addProperty("userPhone", userPhone);

                output = userPhoneDetails.toString();

            }

            con.close();

        } catch (Exception e) {

            output = "An error occurred while reading the user details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String updateUser(String userID, String firstName, String lastName, String age, String gender, String email, String address, String username, String password, String userPhone) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while updating the user details.";

            }

            // Creating prepared statements
            String userQuery = "UPDATE user SET" + " firstName=?," + "lastName=?," + "age=?," + "gender=?," + "email=?," + "address=?," + "username=?," + "password=?" + "WHERE userID=?";
            String userPhoneQuery = "UPDATE userphone SET" + " userPhone=?" + "WHERE userID=?";

            PreparedStatement userDetails = con.prepareStatement(userQuery);
            PreparedStatement userPhoneDetails = con.prepareStatement(userPhoneQuery);

            // Binding values to userQuery
            userDetails.setString(1, firstName);
            userDetails.setString(2, lastName);
            userDetails.setInt(3, Integer.parseInt(age));
            userDetails.setString(4, gender);
            userDetails.setString(5, email);
            userDetails.setString(6, address);
            userDetails.setString(7, username);
            userDetails.setString(8, password);
            userDetails.setInt(9, Integer.parseInt(userID));

            // Binding values to userPhoneQuery
            userPhoneDetails.setString(1, userPhone);
            userPhoneDetails.setInt(2, Integer.parseInt(userID));

            // Executing the statements
            userDetails.execute();
            userPhoneDetails.execute();

            con.close();

            output = "User details updated successfully.";

        } catch (Exception e) {

            output = "An error occurred while updating the user details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String deleteUser(String userID) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while deleting the user details.";

            }

            // Creating the prepared statements
            String deleteUser = "delete from user where userID=?";
            String deleteUserPhone = "delete from userphone where userID=?";

            PreparedStatement preparedStmtForUser = con.prepareStatement(deleteUser);
            PreparedStatement preparedStmtForUserPhone = con.prepareStatement(deleteUserPhone);

            // Binding the values
            preparedStmtForUser.setInt(1, Integer.parseInt(userID));
            preparedStmtForUserPhone.setInt(1, Integer.parseInt(userID));

            // Executing the statements
            preparedStmtForUser.execute();
            preparedStmtForUserPhone.execute();

            con.close();

            output = "User details deleted successfully.";

        } catch (Exception e) {

            output = "An error occurred while deleting the user details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

}
