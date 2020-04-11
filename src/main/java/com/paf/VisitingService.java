package com.paf;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import com.google.gson.*;

import model.Visit;

@Path("/Visits")
public class VisitingService {

    Visit visitObj = new Visit();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public String readHospital() {

        return visitObj.readVisit();

    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertHospital(String visitData) {

        JsonObject visitObject = new JsonParser().parse(visitData).getAsJsonObject();

        String hospitalName = visitObject.get("hospitalName").getAsString();
        String hospitalAddress = visitObject.get("hospitalAddress").getAsString();
        String hospitalUsername = visitObject.get("hospitalUsername").getAsString();
        String hospitalPassword = visitObject.get("hospitalPassword").getAsString();
        String appointmentCharge = visitObject.get("appointmentCharge").getAsString();
        String adminID = visitObject.get("adminID").getAsString();
        String hospitalPhone = visitObject.get("hospitalPhone").getAsString();

        String output = visitObj.insertVisit(hospitalName, hospitalAddress, hospitalUsername, hospitalPassword, appointmentCharge, adminID, hospitalPhone);

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

        String output = visitObj.updateVisit(hospitalID, hospitalName, hospitalAddress, hospitalUsername, hospitalPassword,appointmentCharge, hospitalPhone);

        return output;

    }

    @DELETE
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteHospital(String hospitalData) {

        JsonObject hospitalObject = new JsonParser().parse(hospitalData).getAsJsonObject();

        String hospitalID = hospitalObject.get("hospitalID").getAsString();

        String output = visitObj.deleteVisit(hospitalID);

        return output;

    }

}
