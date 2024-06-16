package lk.ijse.gdse69.cs_fashion.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse69.cs_fashion.Model.Customer;
import lk.ijse.gdse69.cs_fashion.Model.Employee;
import lk.ijse.gdse69.cs_fashion.Model.Tm.CustomerTM;
import lk.ijse.gdse69.cs_fashion.Model.Tm.EmployeeTM;
import lk.ijse.gdse69.cs_fashion.Repository.CustomerRepo;
import lk.ijse.gdse69.cs_fashion.Repository.EmployeeRepo;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EmployeeFormController {

    public TextField txtEmployeeEmail;
    public TableColumn colEmail;
    public TableColumn colContactNo;
    public TableColumn colposition;
    public TableColumn colname;
    public TableColumn colid;
    public TableView tblEmployee;
    public TextField txtEmployeeContactNo;
    public TextField txtEmployeePosition;
    public TextField txtEmployeeId;
    public AnchorPane rootNode;
    public TextField txtemployeeName;

    private lk.ijse.gdse69.cs_fashion.Model.Employee Employee;

    public void initialize() {
        setCellValueFactory();
        loadAllEmployeees();
    }

    private void setCellValueFactory() {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("position"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colposition.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    private void loadAllEmployeees() {
        ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();

        try {
            List<Employee> employeeList = EmployeeRepo.getAll();
            for (Employee employee : employeeList) {
                EmployeeTM tm = new EmployeeTM(
                        employee.getId(),
                        employee.getName(),
                        employee.getPosition(),
                        employee.getTel(),
                        employee.getEmail()
                );

                obList.add(tm);
            }

            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();
        String name = txtemployeeName.getText();
        String position = txtEmployeePosition.getText();
        Integer tel = Integer.valueOf(txtEmployeeContactNo.getText());
        String email = txtEmployeeEmail.getText();

        Employee employee = new Employee(id, name, position,tel,email);

        try {
            boolean isSaved = EmployeeRepo.save(employee);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Successfully Saved!").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();
        String name = txtemployeeName.getText();
        String position = txtEmployeePosition.getText();
        Integer tel = Integer.valueOf(txtEmployeeContactNo.getText());
        String email = txtEmployeeEmail.getText();

        Employee employee = new Employee(id, name, position,tel,email);

        try {
            boolean isUpdated = EmployeeRepo.update(Employee);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Updated!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();

        try {
            boolean isDeleted = EmployeeRepo.delete(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Successfully Deleted!").show();
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
        txtEmployeeId.clear();
        txtemployeeName.clear();
        txtEmployeePosition.clear();
        txtEmployeeContactNo.clear();
        txtEmployeeEmail.clear();

    }
    public void mouseClickOnActon(MouseEvent mouseEvent) {
        Integer index = tblEmployee.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtEmployeeId.setText(colid.getCellData(index).toString());
        txtemployeeName.setText(colname.getCellData(index).toString());
        txtEmployeePosition.setText(colposition.getCellData(index).toString());
        txtEmployeeContactNo.setText(colContactNo.getCellData(index).toString());
        txtEmployeeEmail.setText(colEmail.getCellData(index).toString());

    }


} /*Scene scene = new Scene(rootNode);

Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");*/


