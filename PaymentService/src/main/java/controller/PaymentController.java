package controller;

import beans.*;
import com.paf.PaymentService;
import model.PayAppointment;
import util.DBConnection;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Path("payment")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController() throws ClassNotFoundException, SQLException {

        this.paymentService = new PaymentService(DBConnection.connect());

    }

    @RolesAllowed("admin")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("bank")
    public Response makePayment(BankPayment payment) {

        try {

            this.paymentService.makePayment(payment);
            return Response.OK("Success");

        } catch (Exception e) {

            return Response.Error(e);

        }

    }

    @RolesAllowed("admin")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("card")
        public Response makePayment(CreditCard payment) {
        try {

            this.paymentService.makePayment(payment);
            return Response.OK("Success");

        } catch (Exception e) {

            return Response.Error(e);

        }

    }

    @RolesAllowed("admin")
    @GET
    @Path("view")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Payment> view() throws SQLException {

        return this.paymentService.getAllPayments();

    }

    @RolesAllowed("admin")
    @POST
    @Path("insertPaymentFromAppointment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertPayAppointment(String appPayDetails) {

        PayAppointmentBean payAppBean = new PayAppointmentBean(appPayDetails);
        payAppBean.ConvertStringToJSON();
        PayAppointment payAppoint = new PayAppointment();
        return payAppoint.InsertPayment(payAppBean);

    }

    @RolesAllowed("admin")
    @PUT
    @Path("updatePaymentFromAppointment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String updatePayAppointment(String appPayDetails) {

        PayAppointmentBean payAppBean = new PayAppointmentBean(appPayDetails);
        payAppBean.ConvertStringToJSON();

        PayAppointment payAppoint = new PayAppointment();
        return payAppoint.UpdatePayment(payAppBean);

    }

    @RolesAllowed("admin")
    @DELETE
    @Path("deletePaymentFromAppointment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String deletePayAppointment(String appPayDetails) {

        PayAppointmentBean payAppBean = new PayAppointmentBean(appPayDetails);
        payAppBean.ConvertStringToJSONDelete();

        PayAppointment payAppoint = new PayAppointment();
        return payAppoint.DeletePayment(payAppBean);

    }

}
