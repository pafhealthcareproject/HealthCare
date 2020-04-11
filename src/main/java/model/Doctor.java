package model;

import com.google.gson.JsonObject;

import java.sql.*;

public class Doctor {

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

    public String insertDoctor(String doctorName,String specialization,String doctorUsername,String doctorPassword,String adminID) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while inserting the doctor details.";

            }

            // Creating prepared statements
            String doctorQuery = "insert into doctor" + "(doctorID, doctorName, specialization, doctorUsername, doctorPassword, adminID)" + " values (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStmtForDoctor = con.prepareStatement(doctorQuery);

            // Binding values to doctor Table
            preparedStmtForDoctor.setInt(1, 0);
            preparedStmtForDoctor.setString(2, doctorName);
            preparedStmtForDoctor.setString(3, specialization);
            preparedStmtForDoctor.setString(4, doctorUsername);
            preparedStmtForDoctor.setString(5, doctorPassword);
            preparedStmtForDoctor.setString(6, adminID);


            // Executing the statements
            preparedStmtForDoctor.execute();


            con.close();

            output = "Doctor details inserted successfully.";

        } catch (Exception e) {

            output = "An error occurred while inserting the Doctor details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String readDoctor() {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while reading the doctor details.";

            }

            String query = "select d.doctorName, d.specialization, d.doctorUsername, d.doctorPassword\n" + "from doctor d\n" ;

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                String doctorID = Integer.toString(rs.getInt("doctorID"));
                String doctorName = rs.getString("doctorName");
                String specialization = rs.getString("specialization");
                String doctorUsername = rs.getString("doctorUsername");
                String doctorPassword = rs.getString("doctorPassword");
                String adminID = rs.getString("adminID");

                JsonObject doctorDetails = new JsonObject();

                doctorDetails.addProperty("doctorID", doctorID);
                doctorDetails.addProperty("doctorName", doctorName);
                doctorDetails.addProperty("specialization", specialization);
                doctorDetails.addProperty("doctorUsername", doctorUsername);
                doctorDetails.addProperty("doctorPassword", doctorPassword);
                doctorDetails.addProperty("adminID", adminID);

                output = doctorDetails.toString();

            }

            con.close();

        } catch (Exception e) {

            output = "An error occurred while reading the doctor details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String updateDoctor(String doctorID,String doctorName,String specialization,String doctorUsername,String doctorPassword,String adminID) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while updating the doctor details.";

            }

            // Creating prepared statements
            String doctorQuery = "UPDATE doctor SET" + " doctorName=?," + "specialization=?," + "doctorUsername=?," + "doctorPassword=?," + "adminID=?" + "WHERE doctorID=?";

            PreparedStatement doctorDetails = con.prepareStatement(doctorQuery);

            // Binding values to doctorQuery
            doctorDetails.setString(1, doctorName);
            doctorDetails.setString(2, specialization);
            doctorDetails.setString(3, doctorUsername);
            doctorDetails.setString(4, doctorPassword);
            doctorDetails.setString(5, adminID);
            doctorDetails.setInt(6, Integer.parseInt(doctorID));

            // Executing the statements
            doctorDetails.execute();

            con.close();

            output = "Doctor details updated successfully.";

        } catch (Exception e) {

            output = "An error occurred while updating the doctor details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String deleteDoctor(String doctorID) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while deleting the doctor details.";

            }

            // Creating the prepared statements
            String deleteDoctor = "delete from doctor where doctorID=?";

            PreparedStatement preparedStmtForDoctor = con.prepareStatement(deleteDoctor);

            // Binding the values
            preparedStmtForDoctor.setInt(1, Integer.parseInt(doctorID));

            // Executing the statements
            preparedStmtForDoctor.execute();

            con.close();

            output = "Doctor details deleted successfully.";

        } catch (Exception e) {

            output = "An error occurred while deleting the doctor details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

}
