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
    public String readVisit() {

        return visitObj.readVisit();

    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertVisit(String visitData) {

        JsonObject visitObject = new JsonParser().parse(visitData).getAsJsonObject();

        String hospitalID = visitObject.get("hospitalID").getAsString();
        String doctorID = visitObject.get("doctorID").getAsString();
        String visitTime = visitObject.get("visitTime").getAsString();


        String output = visitObj.insertVisit(hospitalID, doctorID, visitTime);

        return output;

    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateVisit(String visitData) {

        JsonObject visitObject = new JsonParser().parse(visitData).getAsJsonObject();

        String hospitalID = visitObject.get("hospitalID").getAsString();
        String doctorID = visitObject.get("doctorID").getAsString();
        String visitTime = visitObject.get("visitTime").getAsString();


        String output = visitObj.updateVisit(hospitalID, doctorID, visitTime);

        return output;

    }

    @DELETE
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteVisit(String visitData) {

        JsonObject visitObject = new JsonParser().parse(visitData).getAsJsonObject();

        String doctorID = visitObject.get("doctorID").getAsString();

        String output = visitObj.deleteVisit(doctorID);

        return output;

    }

}
