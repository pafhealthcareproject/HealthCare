package model;

import beans.DoctorBean;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public String insertDoctor(DoctorBean doc) {

        List<DoctorBean> docList = new ArrayList<>();

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
            preparedStmtForDoctor.setString(2, doc.getDoctorName());
            preparedStmtForDoctor.setString(3, doc.getSpecialization());
            preparedStmtForDoctor.setString(4, doc.getDoctorUsername());
            preparedStmtForDoctor.setString(5, doc.getDoctorPassword());
            preparedStmtForDoctor.setInt(6, Integer.parseInt(doc.getAdminID()));

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

    public List<DoctorBean> readDoctor() {

        List<DoctorBean> docList = new ArrayList<>();

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                System.out.println("Database connection error occurred while reading the doctor details.");
                return docList;
            }

            String query = "select d.doctorID, d.doctorName, d.specialization, d.doctorUsername, d.doctorPassword, d.adminID from doctor d" ;

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                DoctorBean doc = new DoctorBean(


                       rs.getString("doctorName"),
                       rs.getString("specialization"),
                       rs.getString("doctorUsername"),
                       rs.getString("doctorPassword"),
                       rs.getString("adminID")

                       );

                     docList.add(doc);

                }

            con.close();

        } catch (Exception e) {

                System.out.println("An error occurred while reading the doctor details.");
                System.err.println(e.getMessage());

        }

        return docList;

    }

    public String updateDoctor(DoctorBean doc) {

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
            doctorDetails.setString(1, doc.getDoctorName());
            doctorDetails.setString(2, doc.getSpecialization());
            doctorDetails.setString(3, doc.getDoctorUsername());
            doctorDetails.setString(4, doc.getDoctorPassword());
            doctorDetails.setString(5, doc.getAdminID());
            doctorDetails.setInt(6, doc.getId());

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

    public String deleteDoctor(String ID) {

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
            preparedStmtForDoctor.setInt(1, Integer.parseInt(ID));

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
