package com.paf;

import beans.DoctorBean;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Doctor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/Doctors")
@PermitAll
public class DoctorService {

    Doctor objDoctor = new Doctor();

    @RolesAllowed({ "doctor", "admin","user"})
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DoctorBean> viewDoctor() {
        return objDoctor.viewDoctor();
    }

    @RolesAllowed({ "doctor", "admin","user" })
    @GET
    @Path("/{doctorID}")
    // @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DoctorBean viewDoctorById(@PathParam("doctorID") int id) {
        return objDoctor.viewDoctorById(id);
    }

    @RolesAllowed("admin")
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertDoctor(DoctorBean doc) {

        String output = objDoctor.insertDoctor(doc);
        return output;

    }

    @RolesAllowed({ "doctor", "admin"})
    @PUT
    @Path("/{doctorID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateDoctor(DoctorBean doctor, @PathParam("doctorID") int id) {

        doctor.setId(id);
        String output = objDoctor.updateDoctor(doctor);
        return output;

    }

    @RolesAllowed({ "doctor", "admin"})
    @DELETE
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String removeDoctor(String doctor) {

        JsonObject DoctorObject = new JsonParser().parse(doctor).getAsJsonObject();

        String doctorID = DoctorObject.get("doctorID").getAsString();
        String output = objDoctor.removeDoctor(doctorID);

        return output;

    }

}
