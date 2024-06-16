package lk.ijse.gdse69.cs_fashion.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse69.cs_fashion.Database.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationFormController {
    public AnchorPane rootNode;
    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUserName;
    @FXML

    private TextField txtPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtDate;
    private DbConnection DbConnection;


    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        String userId = txtUserId.getText();
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();
        String date = txtDate.getText();


        try {
            boolean isSaved = saveUser(userId, username, password,email,date);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "User saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean saveUser(String userId, String username, String password, String email,String date) throws SQLException {
        String sql = "INSERT INTO User VALUES(?, ?, ?,?,?)";



        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userId);
        pstm.setObject(2, email);
        pstm.setObject(3, username);
        pstm.setObject(4, password);
        pstm.setObject(5, date);

        return pstm.executeUpdate() > 0;
    }
}