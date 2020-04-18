package model;

import beans.DoctorBean;
import com.google.gson.JsonObject;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import util.DBConnection;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

            JsonObject msg = new JsonObject();
            msg.addProperty("id", keyGen());
            msg.addProperty("username", doc.getDoctorUsername());
            msg.addProperty("password", doc.getDoctorPassword());
            msg.addProperty("role", "doctor");

            HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
            Client client = ClientBuilder.newBuilder().register(feature).build();
            WebTarget webTarget = client.target("http://localhost:8080/UserService/UserService").path("users");
            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
            Response response = invocationBuilder.post(Entity.entity(msg.toString(), MediaType.APPLICATION_JSON));


            con.close();
            output = "Successfully Inserted a Doctor to the System";
            System.out.println(output);

        } catch (Exception e) {

            output = "Error while inserting a doctor.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public List<DoctorBean> viewDoctor() {

        return	viewDoctor(0);

    }

    public DoctorBean viewDoctorById(int id) {

        List<DoctorBean> list =viewDoctor(id);

        if(!list.isEmpty()) {

            return	list.get(0);

        }

        return null;

    }

    public List<DoctorBean> viewDoctor(int id) {
        List <DoctorBean> doctorList = new ArrayList<>();

        try
        {
            Connection con = DBConnection.connect();
            if (con == null) {

                System.out.println("Error While reading from database");
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
            output = "Successfully Updated the Doctor";

        }
        catch (Exception e) {

            output = "Error while updating the Doctor.";
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

            // Binding values
            preparedStmt.setInt(1, Integer.parseInt(doctorID));

            // execute the statement
            preparedStmt.execute();

            con.close();
            output = "Deleted successfully";

        }
        catch (Exception e) {

            output = "An error occurred while deleting the Doctor.";
            System.err.println(e.getMessage());

        }

        return output;
    }

    public int keyGen() {

        List<DoctorBean> list ;
        list = viewDoctor();
        int num = list.size()+10;

        return num;

    }

}
