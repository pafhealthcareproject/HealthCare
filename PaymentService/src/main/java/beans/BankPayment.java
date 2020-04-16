package beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

public class BankPayment extends Payment implements Serializable{

    public String onlinePaymentID;

    public BankPayment(double paymentAmount, String paymentDate, String paymentTime, int appointmentId, String onlinePaymentID) {

        super(paymentAmount, paymentDate, paymentTime, appointmentId);
        this.onlinePaymentID = onlinePaymentID;

    }

    public BankPayment() {
        super();
    }

    public String getOnlinePaymentID() {
        return onlinePaymentID;
    }

    public void setOnlinePaymentID(String onlinePaymentID) {
        this.onlinePaymentID = onlinePaymentID;
    }

}
