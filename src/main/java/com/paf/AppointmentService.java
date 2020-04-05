package com.paf;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import com.google.gson.*;
import model.Appointment;
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.*;

import model.Appointment;

@Path("/Appointments")
public class AppointmentService {

    Appointment appointmentObj = new Appointment();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public String readAppointment() {

        return appointmentObj.readAppointment();

    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertAppointment(String appointmentData) {

        JsonObject appointmentObject = new JsonParser().parse(appointmentData).getAsJsonObject();

        String userID = appointmentObject.get("userID").getAsString();
        String doctorID = appointmentObject.get("doctorID").getAsString();
        String appointmentDate = appointmentObject.get("appointmentDate").getAsString();
        String appointmentTime = appointmentObject.get("appointmentTime").getAsString();


        String output = appointmentObj.insertAppointment(userID, doctorID, appointmentDate, appointmentTime);

        return output;

    }

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateAppointment(String appointmentData) {

        JsonObject appointmentObject = new JsonParser().parse(appointmentData).getAsJsonObject();

        String appointmentID = appointmentObject.get("appointmentID").getAsString();
        String userID = appointmentObject.get("userID").getAsString();
        String doctorID = appointmentObject.get("doctorID").getAsString();
        String appointmentDate = appointmentObject.get("appointmentDate").getAsString();
        String appointmentTime = appointmentObject.get("appointmentTime").getAsString();


        String output = appointmentObj.updateAppointment(appointmentID, userID, doctorID, appointmentDate, appointmentTime);

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
