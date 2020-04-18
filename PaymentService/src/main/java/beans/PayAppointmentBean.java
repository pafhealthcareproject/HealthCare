package beans;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PayAppointmentBean {

    private int AppointmentID ;
    private String appPayDetails;
    private String PayMethod;
    private double Amount;

    public PayAppointmentBean(String appPayDetails) {
        this.appPayDetails =  appPayDetails;
    }

    public void ConvertStringToJSON() {
        JsonObject  AppointmentObject = new JsonParser().parse(appPayDetails).getAsJsonObject();

        setAppointmentID(AppointmentObject.get("appointmentID").getAsInt());
        setPayMethod(AppointmentObject.get("paymentType").getAsString());
        setAmount(AppointmentObject.get("amount").getAsDouble());
    }

    public void ConvertStringToJSONDelete() {
        JsonObject  AppointmentObject = new JsonParser().parse(appPayDetails).getAsJsonObject();

        setAppointmentID(AppointmentObject.get("appointmentID").getAsInt());
    }

    public int getAppointmentID() {
        return AppointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        AppointmentID = appointmentID;
    }

    public String getPayMethod() {
        return PayMethod;
    }

    public void setPayMethod(String payMethod) {
        PayMethod = payMethod;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

}
