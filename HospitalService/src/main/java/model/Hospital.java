package model;

import beans.HospitalBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Hospital {

    private Connection connect() {

        Connection con = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            // DB Name, Username and Password
            con = DriverManager.getConnection("jdbc:mysql://localhost:3308/healthcare", "root", "");

        } catch (Exception e) {

            e.printStackTrace();

        }

        return con;

    }

    public String insertHospital(HospitalBean hosp){

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while inserting the hospital details.";

            }

            // Creating prepared statements
            String hospitalQuery = "insert into hospital" + "(hospitalID, hospitalName, hospitalAddress, hospitalUsername, hospitalPassword, appointmentCharge, adminID)" + " values (?, ?, ?, ?, ?, ?, ?)";
            String hospitalPhoneQuery = "insert into hospitalphone" + "(hospitalID, hospitalPhone)" + " values (?, ?)";

            PreparedStatement preparedStmtForHospital = con.prepareStatement(hospitalQuery);
            PreparedStatement preparedStmtForHospitalPhone = con.prepareStatement(hospitalPhoneQuery);

            // Binding values to hospital Table
            preparedStmtForHospital.setInt(1, 0);
            preparedStmtForHospital.setString(2, hosp.getHospitalName());
            preparedStmtForHospital.setString(3, hosp.getHospitalAddress());
            preparedStmtForHospital.setString(4, hosp.getHospitalUsername());
            preparedStmtForHospital.setString(5, hosp.getHospitalPassword());
            preparedStmtForHospital.setString(6, hosp.getAppointmentCharge());
            preparedStmtForHospital.setInt(7, Integer.parseInt(hosp.getAdminID()));

            // Binding values to hospitalPhone Table
            preparedStmtForHospitalPhone.setInt(1, 0);
            preparedStmtForHospitalPhone.setString(2, hosp.getHospitalPhone());

            // Executing the statements
            preparedStmtForHospital.execute();
            preparedStmtForHospitalPhone.execute();

            con.close();

            output = "Hospital details inserted successfully.";

        } catch (Exception e) {

            output = "An error occurred while inserting the hospital details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public List<HospitalBean> readHospital()  {

        return	readHospital(0);

    }

    public HospitalBean readHospitalById(int id)
    {
        List<HospitalBean> list = readHospital(id);

        if(!list.isEmpty()) {

            return	list.get(0);

        }

        return null;
    }

    public List<HospitalBean> readHospital(int hospitalID) {

        List<HospitalBean> hospList = new ArrayList<>();

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                System.out.println("Database connection error occurred while reading the hospital details.");
                return hospList;

            }

            String query = "select h.hospitalID, h.hospitalName, h.hospitalAddress, h.hospitalUsername, h.hospitalPassword, h.appointmentCharge, h.adminID, p.hospitalPhone\n" + "from hospital h, hospitalphone p\n" + "where h.hospitalID=p.hospitalID;";

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                HospitalBean hosp = new HospitalBean(

                        rs.getInt("hospitalID"),
                        rs.getString("hospitalName"),
                        rs.getString("hospitalAddress"),
                        rs.getString("hospitalUsername"),
                        rs.getString("hospitalPassword"),
                        rs.getString("appointmentCharge"),
                        rs.getString("adminID"),
                        rs.getString("hospitalPhone")

                        );

                hospList.add(hosp);

            }

            con.close();

        } catch (Exception e) {

            System.out.println("An error occurred while reading the hospital details.");
            System.err.println(e.getMessage());

        }

        return hospList;

    }

    public String updateHospital(HospitalBean hosp) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while updating the hospital details.";

            }

            // Creating prepared statements
            String hospitalQuery = "UPDATE hospital SET" + " hospitalName=?," + "hospitalAddress=?," + "hospitalUsername=?," + "hospitalPassword=?," + "appointmentCharge=?" + "WHERE hospitalID=?";
            String hospitalPhoneQuery = "UPDATE hospitalphone SET" + " hospitalPhone=?" + "WHERE hospitalID=?";

            PreparedStatement hospitalDetails = con.prepareStatement(hospitalQuery);
            PreparedStatement hospitalPhoneDetails = con.prepareStatement(hospitalPhoneQuery);

            // Binding values to hospitalQuery
            hospitalDetails.setString(1, hosp.getHospitalName());
            hospitalDetails.setString(2, hosp.getHospitalAddress());
            hospitalDetails.setString(3, hosp.getHospitalUsername());
            hospitalDetails.setString(4, hosp.getHospitalPassword());
            hospitalDetails.setString(5, hosp.getAppointmentCharge());
            hospitalDetails.setInt(6, hosp.getId());

            // Binding values to hospitalPhoneQuery
            hospitalPhoneDetails.setString(1, hosp.getHospitalPhone());
            hospitalPhoneDetails.setInt(2, hosp.getId());

            // Executing the statements
            hospitalDetails.execute();
            hospitalPhoneDetails.execute();

            con.close();

            output = "Hospital details updated successfully.";

        } catch (Exception e) {

            output = "An error occurred while updating the hospital details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

    public String deleteHospital(String ID) {

        String output = "";

        try {

            Connection con = connect();

            if (con == null) {

                return "Database connection error occurred while deleting the hospital details.";

            }

            // Creating the prepared statements
            String deleteHospital = "delete from hospital where hospitalID=?";
            String deleteHospitalPhone = "delete from hospitalphone where hospitalID=?";

            PreparedStatement preparedStmtForHospital = con.prepareStatement(deleteHospital);
            PreparedStatement preparedStmtForHospitalPhone = con.prepareStatement(deleteHospitalPhone);

            // Binding the values
            preparedStmtForHospital.setInt(1, Integer.parseInt(ID));
            preparedStmtForHospitalPhone.setInt(1, Integer.parseInt(ID));

            // Executing the statements
            preparedStmtForHospital.execute();
            preparedStmtForHospitalPhone.execute();

            con.close();

            output = "Hospital details deleted successfully.";

        } catch (Exception e) {

            output = "An error occurred while deleting the hospital details.";
            System.err.println(e.getMessage());

        }

        return output;

    }

}
