package lk.ijse.gdse69.cs_fashion.Repository;

import lk.ijse.gdse69.cs_fashion.Database.DbConnection;
import lk.ijse.gdse69.cs_fashion.Model.Order;
import lk.ijse.gdse69.cs_fashion.Model.Payment;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentRepo {

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT payment_id FROM Payment ORDER BY payment_id DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String paymentId = resultSet.getString(1);
            return paymentId;
        }
        return null;
    }

    /*public static boolean save(Payment payment) throws SQLException {
        String sql = "INSERT INTO Payment VALUES(?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, payment.getPaymentId());
        pstm.setDouble(2, payment.getAmount());
        pstm.setDate(3, payment.getPaymentDate());

        return pstm.executeUpdate() > 0;
    }*/


    public static boolean save(String paymentId, Double amount, Date paymentDate) throws SQLException {
        String sql = "INSERT INTO Payment VALUES(?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, paymentId);
        pstm.setDouble(2, amount);
        pstm.setDate(3, paymentDate);

        return pstm.executeUpdate() > 0;
    }
}
