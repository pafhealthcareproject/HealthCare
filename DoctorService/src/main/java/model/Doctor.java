package model;

import beans.DoctorBean;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Doctor {

    public String insertDoctor(DoctorBean doc) {

        String output = "";

        try {

            Connection con = DBConnection.connect();

            if (con == null) {

                return "Error while connecting to the database for inserting.";

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
            output = "Inserted successfully";
            System.out.println(output);

        } catch (Exception e) {

            output = "Error while inserting the patient.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public List<DoctorBean> viewDoctors() {

        return viewDoctors(0);

    }

    public DoctorBean viewDoctorById(int id) {

        List<DoctorBean> list = viewDoctors(id);

        if(!list.isEmpty()) {

            return list.get(0);

        }

        return null;

    }

    public List<DoctorBean> viewDoctors(int id) {

        List <DoctorBean> doctorList = new ArrayList<>();

        try {

            Connection con = DBConnection.connect();

            if (con == null) {

                System.out.println("Error while reading from database");
                return doctorList;

            }

            String query;

            if(id==0) {

                query = "select * from doctor";

            }
            else {

                query = "select * from doctor where doctorID="+id;

            }

            Statement stmt = con.createStatement();
            ResultSet results = stmt.executeQuery(query);

            while (results.next()) {

                DoctorBean doc = new DoctorBean(


                        results.getString("doctorName"),
                        results.getString("specialization"),
                        results.getString("doctorUsername"),
                        results.getString("doctorPassword"),
                        results.getString("adminID")


                );

                doctorList.add(doc);

            }

            con.close();

        }
        catch (Exception e) {

            System.out.println("Error While Reading");
            System.err.println(e.getMessage());

        }

        return doctorList;

    }

    public String updateDoctor(DoctorBean doc) {

        String output = "";

        try {

            Connection con = DBConnection.connect();

            if (con == null) {

                return "Error while connecting to the database for updating.";

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
            output = "Updated successfully";

        } catch (Exception e) {

            output = "Error occurred while updating the Doctor.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String removeDoctor(String doctorID) {

        String output = "";

        try {

            Connection con = DBConnection.connect();

            if (con == null) {

                return "Error while connecting to the database for deleting.";

            }

            // Prepared Statement
            String query = "delete from doctor where doctorID=?";

            PreparedStatement preparedStmt = con.prepareStatement(query);


            // Binding value
            preparedStmt.setInt(1, Integer.parseInt(doctorID));


            // execute the statement
            preparedStmt.execute();

            con.close();
            output = "Deleted successfully";

        }
        catch (Exception e) {

            output = "Error while deleting the Doctor.";
            System.err.println(e.getMessage());

        }

        return output;

    }

}
