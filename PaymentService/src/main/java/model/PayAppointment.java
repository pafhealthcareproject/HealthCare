package model;

import beans.PayAppointmentBean;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PayAppointment {

    public String InsertPayment(PayAppointmentBean payAppBean) {

        String output = "";

        try {

            Connection con  =  DBConnection.connect();

            if (con == null) {

                return "Error while connecting to the database for payment inserting.";

            }

            //create the insert query
            String query = "INSERT INTO payment (appointmentID, paymentType, paymentAmount) "+ "VALUES (?, ?, ?);";

            // create a prepared statement
            PreparedStatement preparedStmt = con.prepareStatement(query);

            // binding values
            preparedStmt.setInt(1, payAppBean.getAppointmentID());
            preparedStmt.setString(2, payAppBean.getPayMethod());
            preparedStmt.setDouble(3, payAppBean.getAmount());

            // execute the statement
            preparedStmt.execute();

            con.close();

            output = "Inserted successfully";

        } catch (Exception e) {

            output = "Error while inserting the payment details.";
            System.err.println(e.getMessage());

        }

        return output;

    }


    public String UpdatePayment(PayAppointmentBean payAppBean) {

        String output = "";

        try {

            Connection con  =  DBConnection.connect();

            if (con == null) {

                return "Error while connecting to the database for payment inserting.";

            }

            //create the insert query
            String query = "UPDATE payment SET paymentAmount = ? , paymentType = ? WHERE appointmentID = ?;";

            // create a prepared statement
            PreparedStatement preparedStmt = con.prepareStatement(query);

            // binding values
            preparedStmt.setDouble(1, payAppBean.getAmount());
            preparedStmt.setString(2, payAppBean.getPayMethod());
            preparedStmt.setInt(3, payAppBean.getAppointmentID());

            // execute the statement
            preparedStmt.executeUpdate();

            con.close();

            output = "Updated successfully";

        } catch (Exception e) {

            output = "Error while Updating the payment details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String DeletePayment(PayAppointmentBean payAppBean) {

        String output = "";

        try {
            Connection con  =  DBConnection.connect();

            if (con == null) {
                return "Error while connecting to the database for payment inserting.";
            }

            //create the insert query
            String query = "DELETE FROM payment WHERE  appointmentID = ?;";

            // create a prepared statement
            PreparedStatement preparedStmt = con.prepareStatement(query);

            // binding values
            preparedStmt.setInt(1, payAppBean.getAppointmentID());

            // execute the statement
            preparedStmt.execute();

            con.close();

            output = "Deleted successfully";

        } catch (Exception e) {

            output = "Error while deleting the payment details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

}
