package lk.ijse.gdse69.cs_fashion.Repository;

import lk.ijse.gdse69.cs_fashion.Database.DbConnection;
import lk.ijse.gdse69.cs_fashion.Model.OrderItem;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderItemRepo {

    public static boolean save(List<OrderItem> odList) throws SQLException {
        for (OrderItem od : odList) {
            boolean isSaved = save(od);
            if(!isSaved) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(OrderItem od) throws SQLException {
        String sql = "INSERT INTO Order_Item VALUES(?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setString(1, od.getOrderId());
        pstm.setString(2, od.getItemCode());
        pstm.setDouble(3, od.getUnitPrice());
        pstm.setInt(4, od.getQty());

        return pstm.executeUpdate() > 0;    //false ->  |
    }
}
