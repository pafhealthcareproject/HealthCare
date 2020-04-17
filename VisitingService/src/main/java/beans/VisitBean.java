package beans;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VisitBean {

    int id;
    private String hospitalID;
    private String doctorID;
    private String visitTime;

    public VisitBean() {}

    public VisitBean(String visit) {

        JsonObject visitObject = new JsonParser().parse(visit).getAsJsonObject();

        if (visitObject.get("visitID") !=null) {
            this.id = visitObject.get("visitID").getAsInt();
        }
        this.hospitalID = visitObject.get("hospitalID").getAsString();
        this.doctorID = visitObject.get("doctorID").getAsString();
        this.visitTime = visitObject.get("visitTime").getAsString();

    }

    public VisitBean(int id, String hospitalID, String doctorID, String visitTime) {

        this.id = id;
        this.hospitalID = hospitalID;
        this.doctorID = doctorID;
        this.visitTime = visitTime;

    }

    public VisitBean(String hospitalID, String doctorID, String visitTime) {

        this.hospitalID = hospitalID;
        this.doctorID = doctorID;
        this.visitTime = visitTime;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

}
