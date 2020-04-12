package model;

import beans.VisitBean;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public String insertVisit(VisitBean visit){

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while inserting the visit details.";

            }

            // Creating prepared statements
            String visitQuery = "insert into hospitalvisit" + "(visitID, hospitalID, doctorID, visitTime)" + " values (?, ?, ?, ?)";

            PreparedStatement preparedStmtForVisit = con.prepareStatement(visitQuery);

            // Binding values to hospitalvisit Table
            preparedStmtForVisit.setInt(1, 0);
            preparedStmtForVisit.setInt(2, Integer.parseInt(visit.getHospitalID()));
            preparedStmtForVisit.setInt(3, Integer.parseInt(visit.getDoctorID()));
            preparedStmtForVisit.setString(4, visit.getVisitTime());

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

    public List<VisitBean> readVisit()  {

        List<VisitBean> visitList = new ArrayList<>();

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                System.out.println("Database connection error occurred while reading the visit details.");
                return visitList;
            }

            String query = "select v.visitID, v.hospitalID, v.doctorID, v.visitTime\n" + "from hospitalvisit v\n";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);


            while (rs.next()) {

                VisitBean visit = new VisitBean(

                        rs.getInt("visitID"),
                        rs.getString("hospitalID"),
                        rs.getString("doctorID"),
                        rs.getString("visitTime")

                );

                visitList.add(visit);

            }

            con.close();

        } catch (Exception e) {

            System.out.println("An error occurred while reading the visit details.");
            System.err.println(e.getMessage());

        }

        return visitList;

    }

    public String updateVisit(VisitBean visit) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while updating the visit details.";

            }

            // Creating prepared statements
            String visitQuery = "UPDATE hospitalvisit SET" + " hospitalID=?," + "visitTime=?"  + "WHERE doctorID=?";

            PreparedStatement visitDetails = con.prepareStatement(visitQuery);

            // Binding values to visitQuery

            visitDetails.setString(1, visit.getHospitalID());
            visitDetails.setString(2, visit.getVisitTime());
            visitDetails.setString(3, visit.getDoctorID());

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

    public String deleteVisit(String ID) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while deleting the visit details.";

            }

            // Creating the prepared statements
            String deleteVisit = "delete from hospitalvisit where visitID=?";

            PreparedStatement preparedStmtForVisit = con.prepareStatement(deleteVisit);

            // Binding the values
            preparedStmtForVisit.setInt(1, Integer.parseInt(ID));

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
