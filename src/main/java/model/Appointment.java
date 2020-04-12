package model;

import com.google.gson.JsonObject;

import java.sql.*;

public class Appointment {

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

    public String insertAppointment(String userID, String doctorID, String appointmentDate, String appointmentTime) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while inserting the appointment details.";

            }

            // Creating prepared statements
            String appointmentQuery = "insert into appointment" + "(appointmentID, userID, doctorID, appointmentDate, appointmentTime)" + " values (?, ?, ?, ?, ?)";


            PreparedStatement preparedStmtForAppointment = con.prepareStatement(appointmentQuery);


            // Binding values to Appointment Table
            preparedStmtForAppointment.setInt(1, 0);
            preparedStmtForAppointment.setString(2, userID);
            preparedStmtForAppointment.setString(3, doctorID);
            preparedStmtForAppointment.setString(4, appointmentDate);
            preparedStmtForAppointment.setString(5, appointmentTime);



            // Executing the statements
            preparedStmtForAppointment.execute();


            con.close();

            output = "Appointment details inserted successfully.";

        } catch (Exception e) {

            output = "An error occurred while inserting the appointment details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String readAppointment() {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while reading the appointment details.";

            }

            String query = "select appointmentID, userID, doctorID, appointmentDate, appointmentTime\n" + "from appointment a\n" ;

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                String appointmentID = Integer.toString(rs.getInt("appointmentID"));
                String userID = rs.getString("userID");
                String doctorID = rs.getString("doctorID");
                String appointmentDate = rs.getString("appointmentDate");
                String appointmentTime = rs.getString("appointmentTime");


                JsonObject appointmentDetails = new JsonObject();

                appointmentDetails.addProperty("appointmentID", appointmentID);
                appointmentDetails.addProperty("userID", userID);
                appointmentDetails.addProperty("doctorID", doctorID);
                appointmentDetails.addProperty("appointmentDate", appointmentDate);
                appointmentDetails.addProperty("appointmentTime", appointmentTime);


                output = appointmentDetails.toString();

            }

            con.close();

        } catch (Exception e) {

            output = "An error occurred while reading the appointment details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String updateAppointment(String appointmentID, String userID, String doctorID, String appointmentDate, String appointmentTime) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while updating the appointment details.";

            }

            // Creating prepared statements
            String appointmentQuery = "UPDATE appointment SET" + " userID=?," + "doctorID=?," + "appointmentDate=?," + "appointmentTime=?" + "WHERE appointmentID=?";

            PreparedStatement appointmentDetails = con.prepareStatement(appointmentQuery);

            // Binding values to appointmentQuery
            appointmentDetails.setString(1, userID);
            appointmentDetails.setString(2, doctorID);
            appointmentDetails.setString(3, appointmentDate);
            appointmentDetails.setString(4, appointmentTime);
            appointmentDetails.setInt(5, Integer.parseInt(appointmentID));



            // Executing the statements
            appointmentDetails.execute();


            con.close();

            output = "Appointment details updated successfully.";

        } catch (Exception e) {

            output = "An error occurred while updating the appointment details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String deleteAppointment(String appointmentID) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while deleting the appointment details.";

            }

            // Creating the prepared statements
            String deleteAppointment = "delete from appointment where appointmentID=?";


            PreparedStatement preparedStmtForAppointment = con.prepareStatement(deleteAppointment);


            // Binding the values
            preparedStmtForAppointment.setInt(1, Integer.parseInt(appointmentID));


            // Executing the statements
            preparedStmtForAppointment.execute();


            con.close();

            output = "Appointment details deleted successfully.";

        } catch (Exception e) {

            output = "An error occurred while deleting the Appointment details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

}
