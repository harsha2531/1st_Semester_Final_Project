package lk.ijse.gdse69.cs_fashion.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse69.cs_fashion.Database.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DashboardController {
    public AnchorPane rootNode;
    public AnchorPane subRoot;
    public Label lblDate11;
    public Label lblOrderCount;
    public Label lblDate1;
    public Label lblCusCount;
    public Label lblDate111;
    public Label lblItemCount;
    public Label dateLabel;
    public Label timeLabel;

    @FXML
    private AnchorPane pane;

    @FXML
    private AnchorPane root;
    @FXML
    private LineChart<?, ?> chart;
    private ActionEvent actionEvent;
    private Stage stage;

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
        startDateTimeService();

    }



    private void startDateTimeService() {
        Thread dateTimeThread = new Thread(() -> {
            while (true) {
                LocalDate localDate = LocalDate.now();
                LocalTime localTime = LocalTime.now();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

                Platform.runLater(() -> {
                    dateLabel.setText(dateFormatter.format(localDate));
                    timeLabel.setText(timeFormatter.format(localTime));

                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        dateTimeThread.setDaemon(true);
        dateTimeThread.start();
    }
//=============================================
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


//----------------------------------------------------
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


//========================================================================
    public void navigateTo(String url) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource(url));
        this.rootNode.getChildren().removeAll();
        this.rootNode.getChildren().setAll(rootNode);
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/customer_form.fxml"));
        Parent subRoo = loader.load();
        subRoot.getChildren().clear();
        subRoot.getChildren().add(subRoo);


    }

    public void btnOrderOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/View/OrderForm.fxml"));
        subRoot.getChildren().clear();
        subRoot.getChildren().add(rootNode);


    }

    public void btnItemOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Item_Form.fxml"));
        Parent subRoo = loader.load();
        subRoot.getChildren().clear();
        subRoot.getChildren().add(subRoo);
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) {
    }





    public void btnDashboardOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/View/dashBoadForm.fxml"));

     //   Scene scene = new Scene(rootNode);

      //  stage.setScene(scene);
     //   stage.setTitle("login page");
     //   stage.show();

        subRoot.getChildren().clear();
        subRoot.getChildren().add(rootNode);

    }

    public void btnLogOutAction(ActionEvent actionEvent) throws IOException {

        Node node = FXMLLoader.load(this.getClass().getResource("/View/LogInPage.fxml"));
//        Scene scene = new Scene(rootNode);
        subRoot.getChildren().clear();
        subRoot.getChildren().setAll(node);
//        Stage stage = (Stage) lblOrderCount.getScene().getWindow();
//        stage.setScene(scene);
//        stage.centerOnScreen();
//        stage.setTitle("Dashboard Form");



      /*  Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login Page");
        stage.show();*/

        AnchorPane root = FXMLLoader.load(this.getClass().getResource("/View/LogInPage.fxml"));
        Scene scene = new Scene(root);
        Stage stage1 = (Stage) rootNode.getScene().getWindow();
        stage1.setScene(scene);
        stage1.centerOnScreen();
        stage1.show();



    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Employee.fxml"));
        Parent subRoo = loader.load();
        subRoot.getChildren().clear();
        subRoot.getChildren().add(subRoo);



    }


}
