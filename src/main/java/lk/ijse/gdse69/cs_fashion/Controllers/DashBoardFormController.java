package lk.ijse.gdse69.cs_fashion.Controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.gdse69.cs_fashion.Database.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashBoardFormController {
    public Label lblDate1;
    public Label lblCusCount;
    public Label lblDate111;
    public Label lblDate11;
    public Label lblTime11;
    public Label lblOrderCount;
    public Label lblItemCount;
    private int customerCount;
    private int itemCount;
    private int ordersCount;

    public void initialize() {

        try {
            customerCount = getCustomerCount();
            itemCount = getItemCount();
            ordersCount = getOrdersCount();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

        }
        setCustomerCount(customerCount);
        setItemCount(itemCount);
        setOrdersCount(ordersCount);


    }

    private void setCustomerCount(int customerCount) {
        lblCusCount.setText(String.valueOf(customerCount));
    }

    private int getCustomerCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS customer_count FROM Customer";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("customer_count");
        }
        return 0;
    }
    private void setItemCount(int itemCount) {
        lblItemCount.setText(String.valueOf(itemCount));
    }

    private int getItemCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS item_count FROM Item";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("item_count");
        }
        return 0;
    }
    //---------------------------------------------------------------
    private void setOrdersCount(int ordersCount) {
        lblOrderCount.setText(String.valueOf(ordersCount));
    }

    private int getOrdersCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS orders_count FROM Orders";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getInt("orders_count");
        }
        return 0;
    }

}
