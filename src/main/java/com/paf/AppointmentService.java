package com.paf;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import beans.AppointmentBean;
import com.google.gson.*;
import model.Appointment;
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.*;

import model.Appointment;

import java.util.List;

@Path("/Appointments")
public class AppointmentService {

    Appointment appointmentObj = new Appointment();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AppointmentBean> readAppointment() {

        return appointmentObj.readAppointment();

    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertAppointment(String appointmentData) {

        AppointmentBean app = new AppointmentBean(appointmentData);

        String output = appointmentObj.insertAppointment(app);

        return output;

    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateAppointment(String appointmentData) {

        AppointmentBean app = new AppointmentBean(appointmentData);
        String output = appointmentObj.updateAppointment(app);

        return output;

    }

    @DELETE
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteAppointment(String appointmentData) {

        JsonObject appointmentObject = new JsonParser().parse(appointmentData).getAsJsonObject();

        String appointmentID = appointmentObject.get("appointmentID").getAsString();

        String output = appointmentObj.deleteAppointment(appointmentID);

        return output;

    }

}
