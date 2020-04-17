package com.paf;

import beans.HospitalBean;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Hospital;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/hospitals")

public class HospitalService {

    Hospital hospitalObj = new Hospital();

    @RolesAllowed({ "admin", "doctor","user" })
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<HospitalBean> readHospital() {

        return hospitalObj.readHospital();

    }

    @RolesAllowed({ "admin", "doctor","user" })
    @GET
    @Path("/{hospitalID}")
    @Produces(MediaType.APPLICATION_JSON)
    public HospitalBean readHospitalById(@PathParam("hospitalID") int id) {

        return hospitalObj.readHospitalById(id);

    }

    @RolesAllowed("admin")
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertHospital(String hospitalData) {

        HospitalBean hos = new HospitalBean(hospitalData);

        String output = hospitalObj.insertHospital(hos);
        return output;

    }

    @RolesAllowed("admin")
    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateHospital(String hospitalData) {

        HospitalBean hos = new HospitalBean(hospitalData);

        String output = hospitalObj.updateHospital(hos);
        return output;

    }

    @RolesAllowed("admin")
    @DELETE
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteHospital(String hospitalData) {

        JsonObject hospitalObject = new JsonParser().parse(hospitalData).getAsJsonObject();

        String h_ID = hospitalObject.get("hospitalID").getAsString();

        String output = hospitalObj.deleteHospital(h_ID);
        return output;

    }

}
