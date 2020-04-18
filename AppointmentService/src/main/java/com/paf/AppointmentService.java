package com.paf;

import beans.AppointmentBean;
import model.Appointment;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/Appointment")
public class AppointmentService {

    Appointment appointmentObj = new Appointment();
    AppointmentBean appointmentbean ;

    @RolesAllowed({"admin","user"})
    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    public String readAppointment()
    {

        String output = appointmentObj.readAppointment();
        return output;

    }

    @RolesAllowed({"admin","user"})
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertAppointment(String appointmentData)
    {

        appointmentbean =  new AppointmentBean();
        appointmentbean.convertStringToJSONInsert(appointmentData);
        String output = appointmentObj.insertAppointment(appointmentbean);
        return output;

    }

    @RolesAllowed({ "admin","user" })
    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateAppointment(String appointmentData)
    {

        appointmentbean =  new AppointmentBean();
        appointmentbean.convertStringToJSONUpdate(appointmentData);
        String output = appointmentObj.updateAppointment(appointmentbean);
        return output;

    }

    @RolesAllowed({ "admin","patient" })
    @DELETE
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteItem(String appointmentData)
    {

        appointmentbean =  new AppointmentBean();
        appointmentbean.convertStringToJSONDelete(appointmentData);
        String output = appointmentObj.deleteAppointment(appointmentbean);
        return output;

    }

}
