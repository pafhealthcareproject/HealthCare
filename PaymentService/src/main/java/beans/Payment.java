package beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({CreditCard.class})
public class Payment implements Serializable {

    private double paymentAmount;
    private String paymentDate;
    private String paymentTime;
    private int appointmentID;

    public Payment(double paymentAmount, String paymentDate, String paymentTime, int appointmentID) {

        super();
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
        this.paymentTime = paymentTime;
        this.appointmentID = appointmentID;

    }

    public Payment() {
        super();
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

}
