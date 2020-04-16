package com.paf;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import beans.HospitalBean;
import com.google.gson.*;
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.*;

import model.Hospital;

import java.util.List;

@Path("/Hospitals")
public class HospitalService {

    Hospital hospitalObj = new Hospital();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<HospitalBean> readHospital() {
        return hospitalObj.readHospital();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertHospital(String hospitalData) {

        HospitalBean hosp = new HospitalBean(hospitalData);
        String output =	hospitalObj.insertHospital(hosp);

        return output;

    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateHospital(String hospitalData) {

        HospitalBean hosp = new HospitalBean(hospitalData);
        String output =	hospitalObj.updateHospital(hosp);

        return output;

    }

    @DELETE
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteHospital(String hospitalData) {

        JsonObject hospitalObject = new JsonParser().parse(hospitalData).getAsJsonObject();
        String hospitalID = hospitalObject.get("hospitalID").getAsString();

        String output = hospitalObj.deleteHospital(hospitalID);

        return output;

    }

}
