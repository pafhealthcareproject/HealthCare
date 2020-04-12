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

    public String insertVisit(String hospitalID, String doctorID, String visitTime) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while inserting the visit details.";

            }

            // Creating prepared statements
            String visitQuery = "insert into hospitalvisit" + "(hospitalID, doctorID, visitTime)" + " values (?, ?, ?)";

            PreparedStatement preparedStmtForVisit = con.prepareStatement(visitQuery);

            // Binding values to hospital Table

            preparedStmtForVisit.setInt(1, Integer.parseInt(hospitalID));
            preparedStmtForVisit.setInt(2, Integer.parseInt( doctorID));
            preparedStmtForVisit.setString(3, visitTime);


            // Executing the statements
            preparedStmtForVisit.execute();

            con.close();

            output = "Visit details inserted successfully.";

        } catch (Exception e) {

            output = "An error occurred while inserting the visit details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String readVisit() {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while reading the visit details.";

            }

            String query = "select v.hospitalID, v.doctorID, v.visitTime\n" + "from hospitalvisit v\n";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                String hospitalID = Integer.toString(rs.getInt("hospitalID"));
                String doctorID = Integer.toString(rs.getInt("doctorID"));
                String visitTime = rs.getString("visitTime");


                JsonObject visitDetails = new JsonObject();

                visitDetails.addProperty("hospitalID", hospitalID);
                visitDetails.addProperty("doctorID", doctorID);
                visitDetails.addProperty("visitTime", visitTime);

                output = visitDetails.toString();

            }

            con.close();

        } catch (Exception e) {

            output = "An error occurred while reading the visit details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String updateVisit(String hospitalID, String doctorID, String visitTime) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while updating the visit details.";

            }

            // Creating prepared statements
            String visitQuery = "UPDATE hospitalvisit SET" + " hospitalID=?," + "visitTime=?"  + "WHERE doctorID=?";

            PreparedStatement visitDetails = con.prepareStatement(visitQuery);

            // Binding values to hospitalQuery
            visitDetails.setInt(1, Integer.parseInt(hospitalID));
            visitDetails.setString(2, visitTime);
            visitDetails.setInt(3, Integer.parseInt(doctorID));

            // Executing the statements
            visitDetails.execute();

            con.close();

            output = "Visit details updated successfully.";

        } catch (Exception e) {

            output = "An error occurred while updating the visit details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String deleteVisit(String doctorID) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while deleting the visit details.";

            }

            // Creating the prepared statements
            String deleteVisit = "delete from hospitalvisit where doctorID=?";

            PreparedStatement preparedStmtForVisit = con.prepareStatement(deleteVisit);

            // Binding the values
            preparedStmtForVisit.setInt(1, Integer.parseInt(doctorID));

            // Executing the statements
            preparedStmtForVisit.execute();

            con.close();

            output = "Visit details deleted successfully.";

        } catch (Exception e) {

            output = "An error occurred while deleting the visit details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

}
