package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import beans.UserBean;
import javax.ws.rs.core.Response.Status;
import util.DBConnection;

public class User {

    public Response insertUsers(UserBean usr) {

        Response response;
        String output = "{\"Status\":\"Success\"}";

        try {

            Connection con = DBConnection.connect();

            if (con == null) {

                output = "{\"Status\":\"Connection Failed\"}";
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(output).build();

            }

            // Creating prepared statements
            String userQuery = "insert into user" + "(userID, firstName, lastName, age, gender, email, address, username, password, role)" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            preparedStmtForUser.setString(10,  usr.getRole());

            // Binding values to userPhone Table
            preparedStmtForUserPhone.setInt(1, 0);
            preparedStmtForUserPhone.setString(2, usr.getUserphone());

            // Executing the statements
            preparedStmtForUser.execute();
            preparedStmtForUserPhone.execute();

            output = "{\"Status\":\"Success\"}";
            response = Response.status(Status.CREATED).entity(output).build();

            con.close();

        } catch (Exception e) {

            output = "{\"Status\":"+e.getMessage()+"}";
            response=Response.status(Status.INTERNAL_SERVER_ERROR).entity(output).build();
            System.err.println(e.getMessage());

        }

        return response;

    }

    public List<UserBean> readUsers() {

        return	readUsers(0);

    }

    public UserBean readUserById(int id) {

        List<UserBean> list = readUsers(id);

        if(!list.isEmpty()) {

            return	list.get(0);

        }

        return null;

    }
    public List<UserBean> readUsers(int id) {

        List<UserBean> userList = new ArrayList<>();

        try {

            Connection con = DBConnection.connect();

            if (con == null) {

                System.out.println("An error occurred while reading the user details.");
                return userList;

            }

            String query;

            if (id==0) {

                query = "select * from user";

            }else {

                query = "select * from user where userID="+id;

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

            System.out.println("An error occurred while reading the user details.");
            System.err.println(e.getMessage());

        }

        return userList;

    }

    public Response updateUser(UserBean usr) {

        String output = "";
        Response response;

        try {

            Connection con = DBConnection.connect();

            if (con == null) {

                output = "{\"status\":\"Connection Failed\"}";
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(output).build();

            }

            // Creating prepared statements
            String userQuery = "UPDATE user SET" + " firstName=?," + "lastName=?," + "age=?," + "gender=?," + "email=?," + "address=?," + "username=?," + "password=?," + "role=?" + "WHERE userID=?";
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
            userDetails.setString(10, usr.getRole());

            // Binding values to userPhoneQuery
            userPhoneDetails.setString(1, usr.getUserphone());
            userPhoneDetails.setInt(2, usr.getId());

            // Executing the statements
            userDetails.execute();
            userPhoneDetails.execute();

            con.close();
            output = "{\"Status\":\"Success\"}";
            response = Response.status(Status.ACCEPTED).entity(output).build();

        } catch (Exception e) {

            output = "{\"Status\":"+e.getMessage()+"}";
            response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(output).build();
            System.err.println(e.getMessage());

        }

        return response;

    }

    public Response deleteUser(int ID) {

        String output = "";
        Response response;

        try {

            Connection con = DBConnection.connect();

            if (con == null) {

                output = "{\"Status\":\"Connection Failed\"}";
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(output).build();

            }

            // Creating the prepared statements
            String deleteUser = "delete from user where userID=?";
            String deleteUserPhone = "delete from userphone where userID=?";

            PreparedStatement preparedStmtForUser = con.prepareStatement(deleteUser);
            PreparedStatement preparedStmtForUserPhone = con.prepareStatement(deleteUserPhone);

            // Binding the values
            preparedStmtForUser.setInt(1, ID);
            preparedStmtForUserPhone.setInt(1, ID);

            // Executing the statements
            preparedStmtForUser.execute();
            preparedStmtForUserPhone.execute();

            con.close();
            output = "{\"Status\":\"Success\"}";
            response = Response.status(Status.ACCEPTED).entity(output).build();

        } catch (Exception e) {

            output = "{\"Status\":"+e.getMessage()+"}";
            response=Response.status(Status.INTERNAL_SERVER_ERROR).entity(output).build();
            System.err.println(e.getMessage());

        }

        return response;

    }

}
