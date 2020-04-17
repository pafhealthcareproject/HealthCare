package com.paf;

import beans.DoctorBean;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Doctor;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/Doctors")
@PermitAll
public class DoctorService {

    Doctor objDoctor = new Doctor();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DoctorBean> viewDoctor() {

        return objDoctor.viewDoctors();

    }

    @GET
    @Path("/{d_ID}")
    // @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DoctorBean viewDoctorById(@PathParam("d_ID") int id) {

        return objDoctor.viewDoctorById(id);

    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertDoctor(String PatientData) {

        DoctorBean doctor = new DoctorBean(PatientData);

        String output = objDoctor.insertDoctor(doctor);
        return output;

    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateDoctor(String Doctor) {

        DoctorBean doc = new DoctorBean(Doctor);

        String output = objDoctor.updateDoctor(doc);
        return output;

    }

    @DELETE
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String removeDoctor(String doctor) {

        JsonObject DoctorObject = new JsonParser().parse(doctor).getAsJsonObject();

        String doctorID = DoctorObject.get("d_ID").getAsString();
        String output = objDoctor.removeDoctor(doctorID);

        return output;

    }

}
