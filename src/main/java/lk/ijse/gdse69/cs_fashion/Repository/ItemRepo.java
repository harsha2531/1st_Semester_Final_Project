package lk.ijse.gdse69.cs_fashion.Repository;

import lk.ijse.gdse69.cs_fashion.Database.DbConnection;
import lk.ijse.gdse69.cs_fashion.Model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


import lk.ijse.gdse69.cs_fashion.Model.Item;
import lk.ijse.gdse69.cs_fashion.Model.OrderItem;


import java.util.ArrayList;
import java.util.List;

public class ItemRepo {
    public static Object itemCode;

    public static boolean save(Item item) throws SQLException {
        String sql = "INSERT INTO Item VALUES(?, ?, ?, ?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, item.getItemCode());
        pstm.setObject(2, item.getName());
        pstm.setObject(3, item.getCategory());
        pstm.setObject(4, item.getBrand());
        pstm.setObject(5, item.getPrice());
        pstm.setObject(6, item.getQty());

        return pstm.executeUpdate() > 0;
    }

    public static Item searchById(String id) throws SQLException {
        String sql = "SELECT * FROM Item WHERE item_code = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String item_code = resultSet.getString(1);
            String name = resultSet.getString(2);
            String category = resultSet.getString(3);
            String brand = resultSet.getString(4);
            Double price = resultSet.getDouble(5);
            Integer stock_quantity = resultSet.getInt(6);


            Item item = new Item(item_code, name, category, brand, price, stock_quantity);

            return item;
        }

        return null;
    }

    public static boolean update(Item item) throws SQLException {
        String sql = "UPDATE Item SET name = ?, category = ?, brand = ?, price = ?, stock_quantity = ? WHERE item_code = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, item.getName());
        pstm.setObject(2, item.getCategory());
        pstm.setObject(3, item.getBrand());
        pstm.setObject(4, item.getPrice());
        pstm.setObject(5, item.getQty());
        pstm.setObject(6, item.getItemCode());


        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {
        String sql = "DELETE FROM Item WHERE item_code = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Item> getAll() throws SQLException {
        String sql = "SELECT * FROM Item";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Item> itemList = new ArrayList<>();

        while (resultSet.next()) {
            String item_code = resultSet.getString(1);
            String name = resultSet.getString(2);
            String category = resultSet.getString(3);
            String brand = resultSet.getString(4);
            Double price = resultSet.getDouble(5);
            Integer stock_quantity = resultSet.getInt(6);


            Item item = new Item(item_code, name, category, brand, price, stock_quantity);
            itemList.add(item);
        }
        return itemList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT item_code FROM Item";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String item_code = resultSet.getString(1);
            idList.add(item_code);
        }
        return idList;
    }

    public static List<String> getCodes() throws SQLException {
        String sql = "SELECT item_code FROM Item";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> codeList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String item_code = resultSet.getString(1);
            codeList.add(item_code);
        }
        return codeList;
    }

    public static boolean updateQty(List<OrderItem> odList) throws SQLException {
        boolean isUpdate = false;
        for(OrderItem ob:odList ){
            isUpdate = updateSet(ob.getItemCode(),ob.getQty());
        }
        return isUpdate;
    }

    private static boolean updateSet(String itemCode, Integer qty) throws SQLException {
        String sql = "UPDATE Item SET stock_quantity = stock_quantity - ? WHERE item_code = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setInt(1, qty);
        pstm.setObject(2, itemCode);


        return pstm.executeUpdate() > 0;

    }
}