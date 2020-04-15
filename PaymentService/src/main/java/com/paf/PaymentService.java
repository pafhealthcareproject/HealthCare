package com.paf;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import beans.Payment;
import beans.CreditCard;
import beans.Response;
import beans.BankPayment;
import model.PaymentModel;

import util.DBConnection;


@Path("Payments")
public class PaymentService {

    private PaymentModel paymentModel;

    public PaymentService() throws ClassNotFoundException, SQLException {

        this.paymentModel = new PaymentModel(DBConnection.connect());

    }

    @GET
    @Path("viewPayments")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Payment> view() throws SQLException {

        return this.paymentModel.getAllPayments();

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("online")
    public Response makePayment(BankPayment payment) {

        try {

            this.paymentModel.makePayment(payment);
            return Response.OK("Payment Successfully Completed");

        } catch (Exception e) {

            return Response.Error(e);

        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("card")
    public Response makePayment(CreditCard payment) {

        try {

            this.paymentModel.makePayment(payment);
            return Response.OK("Payment Successfully Completed");

        } catch (Exception e) {

            return Response.Error(e);

        }

    }

}
