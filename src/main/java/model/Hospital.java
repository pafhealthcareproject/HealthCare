package model;

import com.google.gson.JsonObject;

import java.sql.*;

public class Hospital {

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

    public String insertHospital(String hospitalName, String hospitalAddress, String hospitalUsername, String hospitalPassword, String hospitalPhone) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while inserting the hospital details.";

            }

            // Creating prepared statements
            String hospitalQuery = "insert into hospital" + "(hospitalID, hospitalName, hospitalAddress, hospitalUsername, hospitalPassword)" + " values (?, ?, ?, ?, ?)";
            String hospitalPhoneQuery = "insert into hospitalphone" + "(hospitalID, hospitalPhone)" + " values (?, ?)";

            PreparedStatement preparedStmtForHospital = con.prepareStatement(hospitalQuery);
            PreparedStatement preparedStmtForHospitalPhone = con.prepareStatement(hospitalPhoneQuery);

            // Binding values to hospital Table
            preparedStmtForHospital.setInt(1, 0);
            preparedStmtForHospital.setString(2, hospitalName);
            preparedStmtForHospital.setString(3, hospitalAddress);
            preparedStmtForHospital.setString(4, hospitalUsername);
            preparedStmtForHospital.setString(5, hospitalPassword);

            // Binding values to hospitalPhone Table
            preparedStmtForHospitalPhone.setInt(1, 0);
            preparedStmtForHospitalPhone.setString(2, hospitalPhone);

            // Executing the statements
            preparedStmtForHospital.execute();
            preparedStmtForHospitalPhone.execute();

            con.close();

            output = "Hospital details inserted successfully.";

        } catch (Exception e) {

            output = "An error occurred while inserting the hospital details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String readHospital() {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while reading the hospital details.";

            }

            String query = "select h.hospitalID, h.hospitalName, h.hospitalAddress, h.hospitalUsername, h.hospitalPassword, p.hospitalPhone\n" + "from hospital h, hospitalphone p\n" + "where h.hospitalID=p.hospitalID;";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                String hospitalID = Integer.toString(rs.getInt("hospitalID"));
                String hospitalName = rs.getString("hospitalName");
                String hospitalAddress = rs.getString("hospitalAddress");
                String hospitalUsername = rs.getString("hospitalUsername");
                String hospitalPassword = rs.getString("hospitalPassword");
                String hospitalPhone = rs.getString("hospitalPhone");

                JsonObject hospitalDetails = new JsonObject();

                hospitalDetails.addProperty("hospitalID", hospitalID);
                hospitalDetails.addProperty("hospitalName", hospitalName);
                hospitalDetails.addProperty("hospitalAddress", hospitalAddress);
                hospitalDetails.addProperty("hospitalUsername", hospitalUsername);
                hospitalDetails.addProperty("hospitalPassword", hospitalPassword);

                output = hospitalDetails.toString();

            }

            con.close();

        } catch (Exception e) {

            output = "An error occurred while reading the hospital details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String updateHospital(String hospitalID, String hospitalName, String hospitalAddress, String hospitalUsername, String hospitalPassword, String hospitalPhone) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while updating the hospital details.";

            }

            // Creating prepared statements
            String hospitalQuery = "UPDATE hospital SET" + " hospitalName=?," + "hospitalAddress=?," + "hospitalUsername=?," + "hospitalPassword=?" + "WHERE hospitalID=?";
            String hospitalPhoneQuery = "UPDATE hospitalphone SET" + " hospitalPhone=?" + "WHERE hospitalID=?";

            PreparedStatement hospitalDetails = con.prepareStatement(hospitalQuery);
            PreparedStatement hospitalPhoneDetails = con.prepareStatement(hospitalPhoneQuery);

            // Binding values to hospitalQuery
            hospitalDetails.setString(1, hospitalName);
            hospitalDetails.setString(2, hospitalAddress);
            hospitalDetails.setString(3, hospitalUsername);
            hospitalDetails.setString(4, hospitalPassword);
            hospitalDetails.setInt(5, Integer.parseInt(hospitalID));

            // Binding values to hospitalPhoneQuery
            hospitalPhoneDetails.setString(1, hospitalPhone);
            hospitalPhoneDetails.setInt(2, Integer.parseInt(hospitalID));

            // Executing the statements
            hospitalDetails.execute();
            hospitalPhoneDetails.execute();

            con.close();

            output = "Hospital details updated successfully.";

        } catch (Exception e) {

            output = "An error occurred while updating the hospital details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String deleteHospital(String hospitalID) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while deleting the hospital details.";

            }

            // Creating the prepared statements
            String deleteHospital = "delete from hospital where hospitalID=?";
            String deleteHospitalPhone = "delete from hospitalphone where hospitalID=?";

            PreparedStatement preparedStmtForHospital = con.prepareStatement(deleteHospital);
            PreparedStatement preparedStmtForHospitalPhone = con.prepareStatement(deleteHospitalPhone);

            // Binding the values
            preparedStmtForHospital.setInt(1, Integer.parseInt(hospitalID));
            preparedStmtForHospitalPhone.setInt(1, Integer.parseInt(hospitalID));

            // Executing the statements
            preparedStmtForHospital.execute();
            preparedStmtForHospitalPhone.execute();

            con.close();

            output = "Hospital details deleted successfully.";

        } catch (Exception e) {

            output = "An error occurred while deleting the hospital details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

}
