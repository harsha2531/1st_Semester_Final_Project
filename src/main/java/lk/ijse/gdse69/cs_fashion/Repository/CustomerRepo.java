package lk.ijse.gdse69.cs_fashion.Repository;

import lk.ijse.gdse69.cs_fashion.Database.DbConnection;
import lk.ijse.gdse69.cs_fashion.Model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;


import javafx.scene.control.Alert;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {
    public static boolean save(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customer VALUES(?, ?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, customer.getId());
        pstm.setObject(2, customer.getName());
        pstm.setObject(3, customer.getEmail());
        pstm.setObject(4, customer.getTel());
        pstm.setObject(5, customer.getAddress());
        pstm.setObject(6, customer.getDob());

        return pstm.executeUpdate() > 0;
    }

    public static Customer searchById(String id) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE customer_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String customer_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String email = resultSet.getString(3);
            String contact_no = resultSet.getString(4);
            String address = resultSet.getString(5);
            LocalDate dob = resultSet.getDate(6).toLocalDate();


            Customer customer = new Customer(customer_id, name, email,contact_no,address,dob);

            return customer;
        }

        return null;
    }

    public static boolean update(Customer customer) throws SQLException {
        String sql = "UPDATE Customer SET name = ?, address = ?, contact_no = ?, email = ?, dob = ? WHERE customer_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, customer.getName());
        pstm.setObject(2, customer.getAddress());
        pstm.setObject(3, customer.getTel());
        pstm.setObject(4, customer.getEmail());
        pstm.setObject(5, customer.getDob());
        pstm.setObject(6, customer.getId());



        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM Customer WHERE customer_id = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM Customer";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Customer> cusList = new ArrayList<>();

        while (resultSet.next()) {
            String customer_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String email = resultSet.getString(3);
            String contact_no = resultSet.getString(4);
            String address = resultSet.getString(5);
            LocalDate dob = resultSet.getDate(6).toLocalDate();



            Customer customer = new Customer(customer_id, name, email, contact_no,address,dob);
            cusList.add(customer);
        }
        return cusList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT customer_id FROM Customer";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;
    }
}
