package beans;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AppointmentBean {

    private int AppointmentID ;
    private int userID;
    private int doctorID;
    private String appointmentDate;
    private String appointmentTime;
    private int tokenNo;
    private double amount;
    private String PaymentType;
    private JsonObject AppointmentObject;

    public int getAppointmentID() {
        return AppointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        AppointmentID = appointmentID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public int getTokenNo() {
        return tokenNo;
    }

    public void setTokenNo(int tokenNo) {
        this.tokenNo = tokenNo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        PaymentType = paymentType;
    }

    public JsonObject getAppointmentObject() {
        return AppointmentObject;
    }

    public void setAppointmentObject(JsonObject appointmentObject) {
        AppointmentObject = appointmentObject;
    }

    public void convertStringToJSONInsert(String appointmentData) {

        AppointmentObject  = new JsonParser().parse(appointmentData).getAsJsonObject();

        setUserID(AppointmentObject.get("userID").getAsInt());
        setDoctorID(AppointmentObject.get("doctorID").getAsInt());
        setAppointmentDate(AppointmentObject.get("appointmentDate").getAsString());
        setAppointmentTime(AppointmentObject.get("appointmentTime").getAsString());
        setTokenNo(AppointmentObject.get("tokenNo").getAsInt());
        setAmount(AppointmentObject.get("amount").getAsDouble());
        setPaymentType(AppointmentObject.get("paymentType").getAsString());

    }

    public void convertStringToJSONUpdate(String appointmentData) {

        AppointmentObject  = new JsonParser().parse(appointmentData).getAsJsonObject();

        setAppointmentID(AppointmentObject.get("appointmentID").getAsInt());
        setUserID(AppointmentObject.get("userID").getAsInt());
        setDoctorID(AppointmentObject.get("doctorID").getAsInt());
        setAppointmentDate(AppointmentObject.get("appointmentDate").getAsString());
        setAppointmentTime(AppointmentObject.get("appointmentTime").getAsString());
        setTokenNo(AppointmentObject.get("tokenNo").getAsInt());
        setAmount(AppointmentObject.get("amount").getAsDouble());
        setPaymentType(AppointmentObject.get("paymentType").getAsString());

    }

    public void convertStringToJSONDelete(String appointmentData) {

        AppointmentObject  = new JsonParser().parse(appointmentData).getAsJsonObject();
        setAppointmentID(AppointmentObject.get("appointmentID").getAsInt());

    }

}
