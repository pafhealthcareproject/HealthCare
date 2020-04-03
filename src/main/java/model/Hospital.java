package model;

import java.sql.*;
import com.google.gson.*;

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

            // Preparing the HTML table to be displayed
            output = "<link href=\"https://fonts.googleapis.com/css?family=Roboto&display=swap\" rel=\"stylesheet\"> <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css\" integrity=\"sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh\" crossorigin=\"anonymous\"> <style>body { font-family: 'Roboto', sans-serif !important; background-color:#F5F5F5; }</style>" + "<br><h3 align=center>HealthCare System</h3><br><div class=\"container\">\n" + "<div class=\"row\">\n" + "<div class=\"col-sm-14\"><div class=\"card\">\n" +
                    "  <div class=\"card-body\"><h4>Hospital Management</h4><hr><table class=\"table table-bordered\" align=\"center\"><thead class=\"thead-dark\"><tr align=center><th>Hospital ID</th><th>Hospital Name</th><th>Hospital Address</th></th><th>Hospital Phone</th><th>Hospital Username</th></th><th>Hospital Password</th><th>Update</th><th>Remove</th></tr></thread>";

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

                // Adding into the HTML table
                output += "<tr align=center><td class=\"align-middle\">" + hospitalID + "</td>";
                output += "<td class=\"align-middle\">" + hospitalName + "</td>";
                output += "<td class=\"align-middle\">" + hospitalAddress + "</td>";
                output += "<td class=\"align-middle\">" + hospitalPhone + "</td>";
                output += "<td class=\"align-middle\">" + hospitalUsername + "</td>";
                output += "<td class=\"align-middle\">" + hospitalPassword + "</td>";

                // Buttons
                output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>" + "<td><form method=\"post\" action=\"Hospitals.jsp\">" + "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\" class=\"btn btn-danger\">" + "<input name=\"itemID\" type=\"hidden\" value=\"" + hospitalID + "\">" + "</form></td></tr></div></div></div></div></div>";

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
