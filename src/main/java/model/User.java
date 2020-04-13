package model;

import beans.UserBean;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public String insertUser(UserBean usr){

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

            con.close();

            output = "User details inserted successfully.";

        } catch (Exception e) {

            output = "An error occurred while inserting the user details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public List<UserBean> readUser()  {

        List<UserBean> usrList = new ArrayList<>();

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                System.out.println("Database connection error occurred while reading the user details.");
                return usrList;
            }

            String query = "select u.userID, u.firstName, u.lastName, u.age, u.gender, u.email, u.address,u.username,u.password,p.userPhone from user u,userphone p where u.userID=p.userID";

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
                        rs.getString("userPhone")

                );

                usrList.add(usr);

            }

            con.close();

        } catch (Exception e) {

            System.out.println("An error occurred while reading the user details.");
            System.err.println(e.getMessage());

        }

        return usrList;

    }

    public String updateUser(UserBean usr) {

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

            output = "User details updated successfully.";

        } catch (Exception e) {

            output = "An error occurred while updating the user details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String deleteUser(String ID) {

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
            preparedStmtForUser.setInt(1, Integer.parseInt(ID));
            preparedStmtForUserPhone.setInt(1, Integer.parseInt(ID));

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
