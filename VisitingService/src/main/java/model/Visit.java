package model;

import beans.VisitBean;
import util.DBConnection;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Visit {

    public Response insertVisit(VisitBean visit, UriInfo uri) {

        Response response;
        String output = "{\"Status\":\"Success\"}";
        int key = kenGen();

        try {

            Connection con = DBConnection.connect();
            if (con == null) {

                output = "{\"status\":\"Connection failed\"}";
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(output).build();

            }

            // Creating prepared statements
            String visitQuery = "insert into hospitalvisit" + "(visitID, hospitalID, doctorID, visitTime)" + " values (?, ?, ?, ?)";

            PreparedStatement preparedStmtForVisit = con.prepareStatement(visitQuery);

            // Binding values to hospitalVisit Table
            preparedStmtForVisit.setInt(1, 0);
            preparedStmtForVisit.setInt(2, Integer.parseInt(visit.getHospitalID()));
            preparedStmtForVisit.setInt(3, Integer.parseInt(visit.getDoctorID()));
            preparedStmtForVisit.setString(4, visit.getVisitTime());

            // Executing the statements
            preparedStmtForVisit.execute();

            output = "{\"Status\":\"Success\"}";
            response = Response.created(uri.getAbsolutePathBuilder().path(""+key).build()).entity(output).build();
            con.close();

        } catch (Exception e) {

            output = "{\"Status\":"+e.getMessage()+"}";
            response=Response.status(Status.INTERNAL_SERVER_ERROR).entity(output).build();
            System.err.println(e.getMessage());

        }

        return response;

    }

    public List<VisitBean> readVisit() {

        return readVisit(0);

    }

    public VisitBean readVisitById(int id) {

        List<VisitBean> list = readVisit(id);

        if(!list.isEmpty()) {

            return list.get(0);

        }

        return null;

    }

    public List<VisitBean> readVisit(int id ) {

        List<VisitBean> visitList = new ArrayList<>();

        try {

            Connection con = DBConnection.connect();

            if (con == null) {

                System.out.println("Error While reading from database");


            }

            String query;

            if ( id==0) {

                query = "select * from hospitalvisit";

            }else {

                query = "select * from hospitalvisit where visitID="+id;

            }

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

            System.out.println("An error occurred while reading");
            System.err.println(e.getMessage());

        }

        return visitList;

    }

    public Response updateVisit(VisitBean visit ,UriInfo uri) {

        String output = "";
        Response response;

        try {

            Connection con = DBConnection.connect();

            if (con == null) {

                output = "{\"status\":\"Connection Failed\"}";
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(output).build();

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
            output = "{\"Status\":\"Success\"}";
            response = Response.accepted(uri.getAbsolutePathBuilder().path(""+visit.getId()).build()).entity(output).build();

        } catch (Exception e) {

            output = "{\"Status\":"+e.getMessage()+"}";
            response=Response.status(Status.INTERNAL_SERVER_ERROR).entity(output).build();
            System.err.println(e.getMessage());

        }

        return response;

    }

    public Response deleteVisit(int ID) {

        String output = "";
        Response response;

        try {

            Connection con = DBConnection.connect();

            if (con == null) {
                output = "{\"Status\":\"Connection Failed\"}";
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(output).build();
            }

            // Creating the prepared statements
            String deleteVisit = "delete from hospitalvisit where visitID=?";

            PreparedStatement preparedStmtForVisit = con.prepareStatement(deleteVisit);

            // Binding the values
            preparedStmtForVisit.setInt(1, ID);

            // Executing the statements
            preparedStmtForVisit.execute();

            con.close();
            output = "{\"Status\":\"Success\"}";
            response = Response.status(Status.ACCEPTED).entity(output).build();

        } catch (Exception e) {

            output = "{\"Status\":"+e.getMessage()+"}";
            response=Response.status(Status.INTERNAL_SERVER_ERROR).entity(output).build();
            System.err.println(e.getMessage());

        }

        return response;

    }

    public List<VisitBean> getVisitsByDoctor(int doctorID){
        List<VisitBean> list = new ArrayList<>();

        for(VisitBean visit : readVisit()){

            if(Integer.parseInt(visit.getDoctorID()) == doctorID) {

                list.add(visit);

            }

        }

        return list;
    }

    public List<VisitBean> getVisitsByHospital(int hospitalId){

        List<VisitBean> list = new ArrayList<>();

        for(VisitBean visit : readVisit()){

            if(Integer.parseInt(visit.getHospitalID()) == hospitalId) {

                list.add(visit);

            }

        }

        return list;

    }

    public List<VisitBean> getHospitalsByTime(String visitTime){

        List<VisitBean> list = new ArrayList<>();

        for(VisitBean visit : readVisit()){

            if(visitTime.equals(visit.getVisitTime())) {

                list.add(visit);

            }

        }

        return list;
    }

    public int kenGen(){

        int id =0 ;

        for(VisitBean visit : readVisit()){

            if(id < visit.getId()) {

                id = visit.getId();

            }

        }

        return id+1;

    }

}
