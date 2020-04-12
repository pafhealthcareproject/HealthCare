package beans;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Appointment;

@XmlRootElement
public class AppointmentBean {

    int id;
    private String userID;
    private String doctorID;
    private String appointmentDate;
    private String appointmentTime;


    public AppointmentBean() {}

    public AppointmentBean(String app) {

        JsonObject appointmentObject = new JsonParser().parse(app).getAsJsonObject();

        if (appointmentObject.get("appointmentID") !=null) {
            this.id = appointmentObject.get("appointmentID").getAsInt();
        }
        this.userID = appointmentObject.get("userID").getAsString();
        this.doctorID = appointmentObject.get("doctorID").getAsString();
        this.appointmentDate = appointmentObject.get("appointmentDate").getAsString();
        this.appointmentTime = appointmentObject.get("appointmentTime").getAsString();


    }

    public AppointmentBean(int id, String userID, String doctorID, String appointmentDate, String appointmentTime) {

        this.id = id;
        this.userID = userID;
        this.doctorID = doctorID;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;

    }

    public AppointmentBean(String userID, String doctorID, String appointmentDate, String appointmentTime){

        this.userID = userID;
        this.doctorID = doctorID;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID= userID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
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


}
