package lk.ijse.gdse69.cs_fashion.Controllers;

import com.sun.javafx.charts.Legend;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import lk.ijse.gdse69.cs_fashion.Model.Customer;
import lk.ijse.gdse69.cs_fashion.Model.Tm.CustomerTM;
import lk.ijse.gdse69.cs_fashion.Repository.CustomerRepo;
import lk.ijse.gdse69.cs_fashion.Util.Regex;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CustomerFormController {

    public TextField txtCustomerAddress;
    @FXML
    private TableColumn<?, ?> coladdress;

    @FXML
    private TableColumn<?, ?> coldob;

    @FXML
    private TableColumn<?, ?> colemail;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableColumn<?, ?> coltel;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<?> tbl;

    @FXML
    private TextField TxtCustomerAddress;

    @FXML
    private DatePicker txtCustomerDob;

    @FXML
    private TextField txtCustomerEmail;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtCustomerTel;
    @FXML
    private TableView<CustomerTM> tblCustomer;
    private AnchorPane root;
    private Scene scene;


    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
    }

    private void setCellValueFactory() {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        coltel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("address"));
        coldob.setCellValueFactory(new PropertyValueFactory<>("dob"));


    }

    private void loadAllCustomers() {
        ObservableList<CustomerTM> obList = FXCollections.observableArrayList();

        try {
            List<Customer> customerList = CustomerRepo.getAll();
            for (Customer customer : customerList) {
                CustomerTM tm = new CustomerTM(
                        customer.getId(),
                        customer.getName(),
                        customer.getEmail(),
                        customer.getTel(),
                        customer.getDob(),
                        customer.getAddress()
                );

                obList.add(tm);
            }

            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtCustomerId.getText();
        String name = txtCustomerName.getText();
        String email = txtCustomerEmail.getText();
        String tel = txtCustomerTel.getText();
        String address = txtCustomerAddress.getText();
        LocalDate dob = Date.valueOf((txtCustomerDob.getValue())).toLocalDate();

       // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
      //  txtCustomerDob.setConverter(new LocalDateStringConverter(formatter, null));


        Customer customer = new Customer(id, name, email, tel,address,dob);

        try {
            boolean isSaved = CustomerRepo.save(customer);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Successfully Saved!").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // Check if any required field is empty
        if(isValied()) {
            if (id.isEmpty() || name.isEmpty() || email.isEmpty() || tel.isEmpty() || address.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "Empty fields! Please fill all required fields.").show();
                return;
            }
            // Save the client
            try {
                boolean isSaved = CustomerRepo.save(customer);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer saved successfully!").show();
                    // loadAllClient();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "An error occurred: " + e.getMessage()).show();
            }
        }

    }

    private boolean isValied() {

        if (!Regex.setTextColor(lk.ijse.gdse69.cs_fashion.Util.TextField.ID,txtCustomerId)) return false;
        return true;

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        //System.out.println("click");
        String id = txtCustomerId.getText();
        String name = txtCustomerName.getText();
        String email = txtCustomerEmail.getText();
        String tel = txtCustomerTel.getText();
        String address = txtCustomerAddress.getText();
        LocalDate dob = Date.valueOf((txtCustomerDob.getValue())).toLocalDate();


        Customer customer = new Customer(id, name, email, tel,address,dob);

        try {
            boolean isUpdated = CustomerRepo.update(customer);
            if(isUpdated) {
                System.out.println("ok");
                new Alert(Alert.AlertType.CONFIRMATION, "Customer updated!").show();
                initialize();
            }
        } catch (SQLException e) {
                System.out.println("no");
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtCustomerId.getText();

        try {
            boolean isDeleted = CustomerRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Successfully Deleted!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtCustomerId.clear();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerEmail.clear();
        txtCustomerTel.clear();
        txtCustomerDob.getEditor().clear();

    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/dashboardPage.fxml"));
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard Form");
    }

    public void txtCustomerIDOnKeyReleaseAction(KeyEvent keyEvent) {
        Regex.setTextColor(lk.ijse.gdse69.cs_fashion.Util.TextField.ID,txtCustomerId);


    }

    public void mouseClickOnActon(MouseEvent mouseEvent) {

        Integer index = tblCustomer.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtCustomerId.setText(colid.getCellData(index).toString());
        txtCustomerName.setText(colname.getCellData(index).toString());
        txtCustomerEmail.setText(colemail.getCellData(index).toString());
        txtCustomerTel.setText(coltel.getCellData(index).toString());
        txtCustomerAddress.setText(coladdress.getCellData(index).toString());
        txtCustomerDob.setValue(LocalDate.parse(coldob.getCellData(index).toString()));

    }


    public void btnSendMailOnAction(ActionEvent actionEvent) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/View/emailForm.fxml"));

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Email Form");

        stage.show();

    }
} /*Scene scene = new Scene(rootNode);

Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");*/