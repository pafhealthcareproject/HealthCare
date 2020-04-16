package model;

import beans.AppointmentBean;
import okhttp3.*;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Appointment {

    public String output;
    private Connection con;
    private String query;
    private PreparedStatement preparedStmt;
    private int finalAppointmentID;

    public String readAppointment() {

        output = "";
        AppointmentBean appointmentBeanRead =  new AppointmentBean();

        try {

            con = DBConnection.connect();

            if (con == null) {

                return "Error while connecting to the database while reading.";

            }

            // Prepare the html table
            output = "<table border=\"1\"><tr>"+
                    "<th>AppointmentID</th>"+
                    "<th>User ID</th>"+
                    "<th>Doctor ID</th>"+
                    "<th>Appointment Date</th>"+
                    "<th>Appointment Time</th>"+
                    "<th>Token No.</th>"+
                    "</tr>";

            query = "SELECT * FROM appointment";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                appointmentBeanRead.setAppointmentID(rs.getInt("AppointmentID"));
                appointmentBeanRead.setUserID(rs.getInt("userID"));
                appointmentBeanRead.setDoctorID(rs.getInt("doctorID"));
                appointmentBeanRead.setAppointmentDate(rs.getString("appointmentDate"));
                appointmentBeanRead.setAppointmentTime(rs.getString("appointmentTime"));
                appointmentBeanRead.setTokenNo(rs.getInt("tokenNo"));

                // Add into the HTML table
                output += "<tr><td>" + appointmentBeanRead.getAppointmentID() + "</td>";
                output += "<td>" + appointmentBeanRead.getUserID() + "</td>";
                output += "<td>" + appointmentBeanRead.getDoctorID()+ "</td>";
                output += "<td>" + appointmentBeanRead.getAppointmentDate() + "</td>";
                output += "<td>" + appointmentBeanRead.getAppointmentTime() + "</td>";
                output += "<td>" + appointmentBeanRead.getTokenNo() + "</td></tr>";

            }

            con.close();

            output += "</table>";

        } catch (Exception e) {

            output = "Error while reading the Appointments.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String insertAppointment(AppointmentBean appointment){

        output = "";

        try {

            con = DBConnection.connect();

            if (con == null) {

                return "Error while connecting to the database while inserting.";

            }

            // create a prepared statement
            query = "INSERT INTO appointment(userID, doctorID, appointmentDate, appointmentTime, tokenNo) "+ " VALUES (?, ?, CURDATE(), CURTIME(), ?);";

            preparedStmt = con.prepareStatement(query);

            // binding values
            preparedStmt.setInt(1, appointment.getUserID());
            preparedStmt.setInt(2, appointment.getDoctorID());
            preparedStmt.setInt(5, appointment.getTokenNo());

            // execute the statement
            preparedStmt.execute();

            //get the AppointmentID of the current inserted row & assign it to FinalAppointmentID variable
            GetAppointmentIdOfRecentInsert(con);

            //insert into related Payment Tables
            GetInsertServiceFromPayment(appointment);

            con.close();
            output = "Inserted successfully to Appointment";


        } catch (Exception e) {

            output = "Error Occurred while inserting the Appointment.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public void GetAppointmentIdOfRecentInsert(Connection con) {

        try {

            query = "SELECT appointmentID FROM appointment ORDER BY appointmentID DESC LIMIT 1";

            preparedStmt = con.prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery(query);

            // iterate through the rows in the result set
            while (rs.next()) {

                finalAppointmentID = rs.getInt("appointmentID");

            }

        }catch (Exception e) {

            System.out.println("Error when fetching the final row of Appointment table");

        }

    }

    //Calling payment API and insert appointment details to payment table
    public String  GetInsertServiceFromPayment(AppointmentBean appointment) {

        try {

            MediaType JSONType = MediaType.get("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create( "{ 'appointmentID':'"+finalAppointmentID+"','PaymentType':'"+appointment.getPaymentType()+"','amount':'"+appointment.getAmount()+"'}", JSONType);

            Request request = new Request.Builder().url("http://localhost:8080/HealthCare/api/payment/insertPaymentFromAppointment").post(body).build();

            try (Response response = client.newCall(request).execute()) {

                return response.body().string();

            }

        }catch (Exception e) {

            System.out.println("Error in GetInsertServiceFromPayment " + e);

        }

        return "Inserted to payment Successfully";

    }

    public String updateAppointment(AppointmentBean appointment) {

        output = "";

        try {
            con = DBConnection.connect();

            if (con == null) {

                return "Error while connecting to the database for Updating.";

            }

            // create a prepared statement
            query = " UPDATE appointment SET "+
                    " userID = ?, "+
                    " doctorID = ?, "+
                    " appointmentDate = ?, "+
                    " appointmentTime = ?, "+
                    " tokenNo = ? "+
                    " WHERE appointmentID = ?";

            preparedStmt = con.prepareStatement(query);

            // binding values
            preparedStmt.setInt(1, appointment.getUserID());
            preparedStmt.setInt(2, appointment.getDoctorID());
            preparedStmt.setString(3, appointment.getAppointmentDate());
            preparedStmt.setString(4, appointment.getAppointmentTime());
            preparedStmt.setInt(5, appointment.getTokenNo());
            preparedStmt.setInt(6, appointment.getAppointmentID());

            // execute the statement
            preparedStmt.executeUpdate();

            con.close();

            output = "Appointment Updated successfully";
            GetUpdateServiceFromPayment(appointment);

        } catch (Exception e) {

            output = "Error while updating the Appointment.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    //Calling payment API and update appointment details to payment table
    public String  GetUpdateServiceFromPayment(AppointmentBean appointment) {

        try {

            MediaType JSONType = MediaType.get("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();

            RequestBody body = RequestBody.create( "{ 'appointmentID':'"+appointment.getAppointmentID()+"','PaymentType':'"+appointment.getPaymentType()+"','amount':'"+appointment.getAmount()+"'}", JSONType);
            Request request = new Request.Builder().url("http://localhost:8080/HealthCare/api/payment/updatePaymentFromAppointment").put(body).build();

            try (Response response = client.newCall(request).execute()) {

                return response.body().string();

            }

        }catch (Exception e) {

            System.out.println("Error in GetUpdateServiceFromPayment" + e);

        }

        return "Update to payment successful";

    }


    public String deleteAppointment(AppointmentBean appointment) {

        output = "";

        try {

            //Calling delete method from payment and delete the payment method
            GetDeleteServiceFromPayment(appointment);

            con = DBConnection.connect();

            if (con == null) {

                return "Error while connecting to the database when deleting.";

            }

            // create a prepared statement
            query = " DELETE FROM appointment where appointmentID = ?";
            preparedStmt = con.prepareStatement(query);

            // binding values
            preparedStmt.setInt(1, appointment.getAppointmentID());

            // execute the statement
            preparedStmt.executeUpdate();
            con.close();
            output = "Successfully Deleted";

        } catch (Exception e) {

            output = "Error occurred while deleting the Appointment.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    //Calling payment API and delete appointment details to payment table
    public String  GetDeleteServiceFromPayment(AppointmentBean appointment) {

        try {

            MediaType JSONType = MediaType.get("application/json; charset=utf-8");
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create( "{ 'appointmentID':'"+appointment.getAppointmentID()+"'}",JSONType);
            Request request = new Request.Builder().url("http://localhost:8080/HealthCare/api/payment/deletePaymentFromAppointment").delete(body).build();

            try (Response response = client.newCall(request).execute()) {

                return response.body().string();

            }

        }catch (Exception e) {

            System.out.println("Error in GetDeleteServiceFromPayment " + e);

        }

        return "Delete Successfully from Payment";

    }

}
