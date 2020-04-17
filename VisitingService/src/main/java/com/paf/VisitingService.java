package com.paf;

import beans.VisitBean;
import model.Visit;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URISyntaxException;
import java.util.List;

@Path("/visits")
public class VisitingService {

    Visit visitObj = new Visit();

    @RolesAllowed("admin")
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readVisit(@QueryParam("doctorID") int doctorID, @QueryParam("time") String time, @QueryParam("hospitalID") int hospitalID) {

        List<VisitBean> list;
        Response response;

        if (doctorID > 0) {

            list = visitObj.getVisitsByDoctor(doctorID);
            response = Response.ok(visitObj.getVisitsByDoctor(doctorID)).build();

        } else if (hospitalID > 0) {

            list = visitObj.getVisitsByHospital(hospitalID);
            response = Response.ok(visitObj.getVisitsByHospital(hospitalID)).build();

        } else if (time != null) {

            list = visitObj.getHospitalsByTime(time);
            response = Response.ok(visitObj.getHospitalsByTime(time)).build();

        } else {

            list = visitObj.readVisit();
            response = Response.ok(visitObj.readVisit()).build();

        }

        if (!list.isEmpty()) {

            return response;

        }

        return Response.noContent().build();

    }

    @RolesAllowed({ "doctor", "admin", "user"})
    @GET
    @Path("/{visitID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readVisitById(@PathParam("visitID") int id) {

        VisitBean visit = visitObj.readVisitById(id);

        if (visit != null) {

            return Response.ok().entity(visitObj.readVisitById(id)).build();

        }

        return Response.noContent().build();

    }

    @RolesAllowed({ "admin", "doctor"})
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertVisit(VisitBean visit, @Context UriInfo uri) throws URISyntaxException {

        Response response = visitObj.insertVisit(visit, uri);
        return response;

    }

    @RolesAllowed({"admin", "doctor"})
    @PUT
    @Path("/{visitID}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateVisit(@PathParam("visitID") int visitID, VisitBean visit, @Context UriInfo uri) throws URISyntaxException {

        visit.setId(visitID);
        return visitObj.updateVisit(visit, uri);

    }

    @RolesAllowed({"admin", "doctor"})
    @DELETE
    @Path("/{visitID}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteVisit(@PathParam("visitID") int visitID) {

        return visitObj.deleteVisit(visitID);

    }

}
