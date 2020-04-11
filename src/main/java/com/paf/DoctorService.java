package com.paf;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import com.google.gson.*;
import model.Doctor;
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.*;

import model.Doctor;

@Path("/Doctors")
public class DoctorService {

    Doctor doctorobj = new Doctor();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public String readDoctor() {

        return doctorobj.readDoctor();

    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertDoctor(String doctorData) {

        JsonObject doctorObject = new JsonParser().parse(doctorData).getAsJsonObject();

        String doctorName = doctorObject.get("doctorName").getAsString();
        String specialization = doctorObject.get("specialization").getAsString();
        String doctorUsername = doctorObject.get("doctorUsername").getAsString();
        String doctorPassword = doctorObject.get("doctorPassword").getAsString();
        String adminID = doctorObject.get("adminID").getAsString();


        String output = doctorobj.insertDoctor(doctorName, specialization, doctorUsername,doctorPassword, adminID);

        return output;

    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateDoctor(String doctorData) {

        JsonObject doctorObject = new JsonParser().parse(doctorData).getAsJsonObject();

        String doctorID = doctorObject.get("doctorID").getAsString();
        String doctorName = doctorObject.get("doctorName").getAsString();
        String specialization = doctorObject.get("specialization").getAsString();
        String doctorUsername = doctorObject.get("doctorUsername").getAsString();
        String doctorPassword = doctorObject.get("doctorPassword").getAsString();
        String adminID = doctorObject.get("adminID").getAsString();


        String output = doctorobj.updateDoctor(doctorID, doctorName, specialization, doctorUsername, doctorPassword, adminID);

        return output;

    }

    @DELETE
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteDoctor(String doctorData) {

        JsonObject doctorObject = new JsonParser().parse(doctorData).getAsJsonObject();

        String doctorID = doctorObject.get("doctorID").getAsString();

        String output = doctorobj.deleteDoctor(doctorID);

        return output;

    }

}
