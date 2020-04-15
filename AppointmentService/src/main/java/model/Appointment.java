package model;

import beans.AppointmentBean;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
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

    public String insertAppointment(AppointmentBean app) {

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
            preparedStmtForAppointment.setString(2, app.getUserID());
            preparedStmtForAppointment.setString(3, app.getDoctorID());
            preparedStmtForAppointment.setString(4, app.getAppointmentDate());
            preparedStmtForAppointment.setString(5, app.getAppointmentTime());

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

    public List<AppointmentBean> readAppointment() {

        List<AppointmentBean> appList = new ArrayList<>();

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                System.out.println("Database connection error occurred while reading the appointment details.");
                return appList;
            }

            String query = "select a.appointmentID, a.userID, a.doctorID, a.appointmentDate, a.appointmentTime\n" + "from appointment a\n" ;

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                AppointmentBean app = new AppointmentBean(

                  rs.getInt("appointmentID"),
                  rs.getString("userID"),
                  rs.getString("doctorID"),
                  rs.getString("appointmentDate"),
                  rs.getString("appointmentTime"));

                appList.add(app);

            }

            con.close();

        } catch (Exception e) {

            System.out.println("An error occurred while reading the appointment details.");
            System.err.println(e.getMessage());

        }

        return appList;

    }

    public String updateAppointment(AppointmentBean app) {

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
            appointmentDetails.setString(1, app.getUserID());
            appointmentDetails.setString(2, app.getDoctorID());
            appointmentDetails.setString(3, app.getAppointmentDate());
            appointmentDetails.setString(4, app.getAppointmentTime());
            appointmentDetails.setInt(5, app.getId());

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

    public String deleteAppointment(String ID) {

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
            preparedStmtForAppointment.setInt(1, Integer.parseInt(ID));

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
