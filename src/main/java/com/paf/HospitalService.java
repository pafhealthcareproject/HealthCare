package com.paf;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import com.google.gson.*;
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.*;

import model.Hospital;

@Path("/Hospitals")
public class HospitalService {

    Hospital hospitalObj = new Hospital();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public String readHospital() {

        return hospitalObj.readHospital();

    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertHospital(String hospitalData) {

        JsonObject hospitalObject = new JsonParser().parse(hospitalData).getAsJsonObject();

        String hospitalName = hospitalObject.get("hospitalName").getAsString();
        String hospitalAddress = hospitalObject.get("hospitalAddress").getAsString();
        String hospitalUsername = hospitalObject.get("hospitalUsername").getAsString();
        String hospitalPassword = hospitalObject.get("hospitalPassword").getAsString();
        String appointmentCharge = hospitalObject.get("appointmentCharge").getAsString();
        String adminID = hospitalObject.get("adminID").getAsString();
        String hospitalPhone = hospitalObject.get("hospitalPhone").getAsString();

        String output = hospitalObj.insertHospital(hospitalName, hospitalAddress, hospitalUsername, hospitalPassword, appointmentCharge, adminID, hospitalPhone);

        return output;

    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateHospital(String hospitalData) {

        JsonObject hospitalObject = new JsonParser().parse(hospitalData).getAsJsonObject();

        String hospitalID = hospitalObject.get("hospitalID").getAsString();
        String hospitalName = hospitalObject.get("hospitalName").getAsString();
        String hospitalAddress = hospitalObject.get("hospitalAddress").getAsString();
        String hospitalUsername = hospitalObject.get("hospitalUsername").getAsString();
        String hospitalPassword = hospitalObject.get("hospitalPassword").getAsString();
        String appointmentCharge = hospitalObject.get("appointmentCharge").getAsString();
        String hospitalPhone = hospitalObject.get("hospitalPhone").getAsString();

        String output = hospitalObj.updateHospital(hospitalID, hospitalName, hospitalAddress, hospitalUsername, hospitalPassword,appointmentCharge, hospitalPhone);

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
