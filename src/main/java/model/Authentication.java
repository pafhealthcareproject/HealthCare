package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DBConnection;

public class Authentication {

    public static boolean auth(String username, String password) {

        try {

            Connection con = DBConnection.connect();

            if (con == null) {

            }

            // create a prepared statements
            String queryUser = " SELECT * FROM user where username = '" + username + "' and password = '" + password + "' ";
            String queryHospital = " SELECT * FROM hospital where hospitalUsername = '" + username + "' and hospitalPassword = '" + password + "' ";
            String queryDoctor = " SELECT * FROM doctor where doctorUsername = '" + username + "' and doctorPassword = '" + password + "' ";

            PreparedStatement preparedStmtUser = con.prepareStatement(queryUser);
            PreparedStatement preparedStmtHospital = con.prepareStatement(queryHospital);
            PreparedStatement preparedStmtDoctor = con.prepareStatement(queryDoctor);

            preparedStmtUser.execute();
            preparedStmtHospital.execute();
            preparedStmtDoctor.execute();

            //executing the statements
            ResultSet rsUser = preparedStmtUser.executeQuery(queryUser);
            ResultSet rsHospital = preparedStmtHospital.executeQuery(queryHospital);
            ResultSet rsDoctor = preparedStmtDoctor.executeQuery(queryDoctor);

            if(rsUser.next()) {

                con.close();
                return true;

            }

            if(rsHospital.next()) {

                con.close();
                return true;

            }

            if(rsDoctor.next()) {

                con.close();
                return true;

            }

            con.close();

        } catch (Exception e) {

            System.err.println(e.getMessage());

        }

        return false;

    }

}
