package lk.ijse.gdse69.cs_fashion.Repository;

import lk.ijse.gdse69.cs_fashion.Database.DbConnection;
import lk.ijse.gdse69.cs_fashion.Model.Order;
import lk.ijse.gdse69.cs_fashion.Database.DbConnection;
import lk.ijse.gdse69.cs_fashion.Model.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRepo {
    public static String getCurrentId() throws SQLException {
        String sql = "SELECT order_id FROM Orders ORDER BY order_id DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String orderId = resultSet.getString(1);
            return orderId;
        }
        return null;
    }

    public static boolean save(Order order) throws SQLException {
        System.out.println(order);
        String sql = "INSERT INTO Orders VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, order.getOrderId());
        pstm.setDate(2, order.getOrderDate());
        pstm.setString(3, order.getCustomerId());
        pstm.setString(4, order.getPaymentId());

        return pstm.executeUpdate() > 0;
    }


}
