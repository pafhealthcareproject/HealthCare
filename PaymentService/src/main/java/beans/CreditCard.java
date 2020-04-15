package beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

public class CreditCard extends Payment implements Serializable {

    private  String cardNumber;
    private String expires;
    private int cvv;

    public CreditCard() {}

    public CreditCard(double paymentAmount, String paymentDate, String paymentTime, int appointmentID, String cardNumber, String expires, int cvv) {

        super(paymentAmount, paymentDate, paymentTime, appointmentID);

        this.cardNumber = cardNumber;
        this.expires = expires;
        this.cvv = cvv;

    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

}
