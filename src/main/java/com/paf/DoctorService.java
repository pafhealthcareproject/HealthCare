package com.paf;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import beans.DoctorBean;
import com.google.gson.*;
import model.Doctor;
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.*;

import model.Doctor;

import java.util.List;


@Path("/Doctors")
public class DoctorService {

    Doctor doctorObj = new Doctor();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DoctorBean> readDoctor() {

        return doctorObj.readDoctor();

    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertDoctor(String doctorData) {

        DoctorBean doc = new DoctorBean(doctorData);
        String output =	doctorObj.insertDoctor(doc);

        return output;

    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateDoctor(String doctorData) {

        DoctorBean doc = new DoctorBean(doctorData);
        String output =	doctorObj.updateDoctor(doc);

        return output;

    }

    @DELETE
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteDoctor(String doctorData) {

        JsonObject doctorObject = new JsonParser().parse(doctorData).getAsJsonObject();
        String doctorID = doctorObject.get("doctorID").getAsString();

        String output = doctorObj.deleteDoctor(doctorID);

        return output;

    }

}
