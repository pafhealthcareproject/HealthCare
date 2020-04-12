package com.paf;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import beans.HospitalBean;
import beans.VisitBean;
import com.google.gson.*;
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.*;

import model.Visit;

import java.util.List;

@Path("/Visits")
public class VisitingService {

    Visit visitObj = new Visit();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<VisitBean> readVisit() {

        return visitObj.readVisit();

    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertVisit(String visitData) {

        VisitBean visit = new VisitBean(visitData);
        String output =	visitObj.insertVisit(visit);

        return output;

    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateVisit(String visitData) {

        VisitBean visit = new VisitBean (visitData);
        String output =	visitObj.updateVisit(visit);

        return output;

    }

    @DELETE
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteVisit(String visitData) {

        JsonObject visitObject = new JsonParser().parse(visitData).getAsJsonObject();
        String visitID = visitObject.get("visitID").getAsString();

        String output = visitObj.deleteVisit(visitID);

        return output;

    }

}
