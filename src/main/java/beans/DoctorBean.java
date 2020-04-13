package beans;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Doctor;

@XmlRootElement
public class DoctorBean {

    int id;
    private String doctorName;
    private String specialization;
    private String doctorUsername;
    private String doctorPassword;
    private String adminID;


    public DoctorBean() {}

    public DoctorBean(String doc) {

        JsonObject doctorObject = new JsonParser().parse(doc).getAsJsonObject();

        if (doctorObject.get("doctorID") !=null) {
            this.id = doctorObject.get("doctorID").getAsInt();
        }
        this.doctorName = doctorObject.get("doctorName").getAsString();
        this.specialization = doctorObject.get("specialization").getAsString();
        this.doctorUsername = doctorObject.get("doctorUsername").getAsString();
        this.doctorPassword = doctorObject.get("doctorPassword").getAsString();
        this.adminID = doctorObject.get("adminID").getAsString();


    }

    public DoctorBean(int id, String doctorName, String specialization, String doctorUsername, String doctorPassword, String adminID) {

        this.id = id;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.doctorUsername = doctorUsername;
        this.doctorPassword = doctorPassword;
        this.adminID = adminID;


    }

    public DoctorBean(String doctorName, String specialization, String doctorUsername, String doctorPassword, String adminID){

        this.doctorName = doctorName;
        this.specialization = specialization;
        this.doctorUsername = doctorUsername;
        this.doctorPassword = doctorPassword;
        this.adminID = adminID;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }


    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }


    public String getDoctorUsername() {
        return doctorUsername;
    }

    public void setDoctorUsername(String doctorUsername) {
        this.doctorUsername = doctorUsername;
    }


    public String getDoctorPassword() {
        return doctorPassword;
    }

    public void setDoctorPassword(String doctorPassword) {
        this.doctorPassword = doctorPassword;
    }


    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }



}
