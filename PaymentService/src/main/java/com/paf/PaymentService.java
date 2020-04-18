package com.paf;

import beans.BankPayment;
import beans.CreditCard;
import beans.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentService {

    private Connection sqlConnection;

    public PaymentService(Connection sqlConnection) {
        this.sqlConnection = sqlConnection;
    }

    //select payment method
    public boolean makePayment(Payment payment) throws SQLException {

        if (payment.getClass().getCanonicalName() == CreditCard.class.getCanonicalName()) {
            return makeCreditCardPayment((CreditCard) payment);

        } else if (payment.getClass().getCanonicalName() == BankPayment.class.getCanonicalName()) {

            return makeBankPayment((BankPayment) payment);

        } else {

            return false;

        }

    }

    private void assignParameters(PreparedStatement statement, Payment payment) throws SQLException {

        statement.setInt(1, payment.getAppointmentID());
        statement.setDouble(2, payment.getPaymentAmount());
        statement.setDate(3, new Date(new java.util.Date().getTime()));

    }

    private boolean makeCreditCardPayment(CreditCard payment) throws SQLException {

        String sql = "INSERT INTO payment(" +
                "appointmentID, " +
                "paymentAmount, " +
                "paymentDate, " +
                "paymentTime, " +
                "paymentType, " +
                "cardNumber, " +
                "expires, " +
                "cvv) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = this.sqlConnection.prepareStatement(sql);

        this.assignParameters(statement, payment);

        statement.setString(6, payment.getCardNumber());
        statement.setString(7, payment.getExpires());
        statement.setInt(8, payment.getCvv());
        statement.setString(5, PaymentMethod.CreditCard.name());

        return statement.execute();

    }

    private boolean makeBankPayment(BankPayment payment) throws SQLException {

        String sql = "INSERT INTO payment(" +
                "appointmentID, " +
                "paymentAmount, " +
                "paymentDate, " +
                "paymentTime, " +
                "paymentType, " +
                "onlinePaymentID" +
                ") VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = this.sqlConnection.prepareStatement(sql);

        this.assignParameters(statement, payment);

        statement.setString(6, payment.getOnlinePaymentID());
        statement.setString(5, PaymentMethod.BankPayment.name());

        return statement.execute();

    }

    public List<Payment> getAllPayments() throws SQLException {
        String sql = "SELECT 'paymentID', " +
                "'appointmentID', " +
                "'paymentAmount', " +
                "'paymentDate', " +
                "'paymentTime', " +
                "'paymentType', " +
                "'cardNumber', " +
                "'expires', " +
                "'cvv', " +
                "'onlinePaymentID' " +
                "FROM payment";
        ResultSet resultSet = this.sqlConnection.prepareStatement(sql).executeQuery();
        List<Payment> payments = new ArrayList<>();

        while (resultSet.next()) {

            String type = resultSet.getString(14);

            if(type.equals(PaymentMethod.CreditCard.name())) {

                payments.add( new CreditCard(

                        resultSet.getDouble(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(2),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getInt(9)

                ));

            } else if(type.equals(PaymentMethod.BankPayment.name())) {

                payments.add( new BankPayment(

                        resultSet.getDouble(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(2),
                        resultSet.getString(10)

                ));

            }

        }

        return payments;

    }

}
