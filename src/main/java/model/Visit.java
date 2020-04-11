package model;

import com.google.gson.JsonObject;

import java.sql.*;

public class Visit {

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

    public String insertVisit(String hospitalName, String hospitalAddress, String hospitalUsername, String hospitalPassword,String adminID,String appointmentCharge, String hospitalPhone) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while inserting the hospital details.";

            }

            // Creating prepared statements
            String hospitalQuery = "insert into hospital" + "(hospitalID, hospitalName, hospitalAddress, hospitalUsername, hospitalPassword, appointmentCharge, adminID)" + " values (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStmtForHospital = con.prepareStatement(hospitalQuery);

            // Binding values to hospital Table
            preparedStmtForHospital.setInt(1, 0);
            preparedStmtForHospital.setString(2, hospitalName);
            preparedStmtForHospital.setString(3, hospitalAddress);
            preparedStmtForHospital.setString(4, hospitalUsername);
            preparedStmtForHospital.setString(5, hospitalPassword);
            preparedStmtForHospital.setString(6, appointmentCharge);
            preparedStmtForHospital.setInt(7, Integer.parseInt(adminID));

            // Executing the statements
            preparedStmtForHospital.execute();

            con.close();

            output = "Hospital details inserted successfully.";

        } catch (Exception e) {

            output = "An error occurred while inserting the hospital details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String readVisit() {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while reading the hospital details.";

            }

            String query = "select h.hospitalID, h.hospitalName, h.hospitalAddress, h.hospitalUsername, h.hospitalPassword, h.appointmentCharge, h.adminID, p.hospitalPhone\n" + "from hospital h, hospitalphone p\n" + "where h.hospitalID=p.hospitalID;";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                String hospitalID = Integer.toString(rs.getInt("hospitalID"));
                String hospitalName = rs.getString("hospitalName");
                String hospitalAddress = rs.getString("hospitalAddress");
                String hospitalUsername = rs.getString("hospitalUsername");
                String hospitalPassword = rs.getString("hospitalPassword");
                String appointmentCharge = rs.getString("appointmentCharge");
                String adminID = Integer.toString(rs.getInt("adminID"));
                String hospitalPhone = rs.getString("hospitalPhone");

                JsonObject hospitalDetails = new JsonObject();

                hospitalDetails.addProperty("hospitalID", hospitalID);
                hospitalDetails.addProperty("hospitalName", hospitalName);
                hospitalDetails.addProperty("hospitalAddress", hospitalAddress);
                hospitalDetails.addProperty("hospitalPhone", hospitalPhone);
                hospitalDetails.addProperty("hospitalUsername", hospitalUsername);
                hospitalDetails.addProperty("hospitalPassword", hospitalPassword);
                hospitalDetails.addProperty("appointmentCharge", appointmentCharge);
                hospitalDetails.addProperty("adminID", adminID);

                output = hospitalDetails.toString();

            }

            con.close();

        } catch (Exception e) {

            output = "An error occurred while reading the hospital details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String updateVisit(String hospitalID, String hospitalName, String hospitalAddress, String hospitalUsername, String hospitalPassword, String appointmentCharge, String hospitalPhone) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while updating the hospital details.";

            }

            // Creating prepared statements
            String hospitalQuery = "UPDATE hospital SET" + " hospitalName=?," + "hospitalAddress=?," + "hospitalUsername=?," + "hospitalPassword=?," + "appointmentCharge=?" + "WHERE hospitalID=?";

            PreparedStatement hospitalDetails = con.prepareStatement(hospitalQuery);

            // Binding values to hospitalQuery
            hospitalDetails.setString(1, hospitalName);
            hospitalDetails.setString(2, hospitalAddress);
            hospitalDetails.setString(3, hospitalUsername);
            hospitalDetails.setString(4, hospitalPassword);
            hospitalDetails.setString(5, appointmentCharge);
            hospitalDetails.setInt(6, Integer.parseInt(hospitalID));

            // Executing the statements
            hospitalDetails.execute();

            con.close();

            output = "Hospital details updated successfully.";

        } catch (Exception e) {

            output = "An error occurred while updating the hospital details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String deleteVisit(String hospitalID) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while deleting the hospital details.";

            }

            // Creating the prepared statements
            String deleteHospital = "delete from hospital where hospitalID=?";

            PreparedStatement preparedStmtForHospital = con.prepareStatement(deleteHospital);

            // Binding the values
            preparedStmtForHospital.setInt(1, Integer.parseInt(hospitalID));

            // Executing the statements
            preparedStmtForHospital.execute();

            con.close();

            output = "Hospital details deleted successfully.";

        } catch (Exception e) {

            output = "An error occurred while deleting the hospital details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

}
