package lk.ijse.gdse69.cs_fashion.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse69.cs_fashion.Model.Item;
import lk.ijse.gdse69.cs_fashion.Model.Tm.ItemTM;
import lk.ijse.gdse69.cs_fashion.Repository.ItemRepo;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ItemFormController {

    @FXML
    private TextField txtICategory;

    @FXML
    private TableColumn<?, ?> colBrand;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colStockQty;

    @FXML
    private TableColumn<?, ?> coleCategory;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<ItemTM> tblCustomer;

    @FXML
    private TextField txtBrand;

    @FXML
    private TextField txtItemCode;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtStockQuantity;
    @FXML
    private TableView<ItemTM> tblItem;
    private AnchorPane root;


    public void initialize() {
        setCellValueFactory();
        loadAllItems();
        loadAllItemsName();
    }



    private void loadAllItemsName() {
        try {
            List<String> itemCodeList = ItemRepo.getIds();
            System.out.println(itemCodeList);
            TextFields.bindAutoCompletion(txtItemCode,itemCodeList);
        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION,"ERROR").show();
        }
    }


    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        coleCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStockQty.setCellValueFactory(new PropertyValueFactory<>("qty"));


    }

    private void loadAllItems() {
        ObservableList<ItemTM> obList = FXCollections.observableArrayList();

        try {
            List<Item> customerList = ItemRepo.getAll();
            for (Item customer : customerList) {
                ItemTM tm = new ItemTM(
                        customer.getItemCode(),
                        customer.getName(),
                        customer.getCategory(),
                        customer.getBrand(),
                        customer.getPrice(),
                        customer.getQty()
                );

                obList.add(tm);
            }

            tblItem.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String item_code = txtItemCode.getText();
        String name = txtName.getText();
        String category = coleCategory.getText();
        String brand = txtBrand.getText();
        Double price = Double.valueOf(txtPrice.getText());
        Integer stock_quantity = Integer.valueOf(txtStockQuantity.getText());


        Item item = new Item(item_code, name, category, brand,price,stock_quantity);

        try {
            boolean isSaved = ItemRepo.save(item);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Successfully Saved!").show();
                clearFields();
                initialize();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String item_code = txtItemCode.getText();
        String name = txtName.getText();
        String category = coleCategory.getText();
        String brand = txtBrand.getText();
        Double price = Double.valueOf(txtPrice.getText());
        Integer stock_quantity = Integer.valueOf(txtStockQuantity.getText());


        Item item = new Item(item_code, name, category, brand,price,stock_quantity);

        try {
            boolean isUpdated = ItemRepo.update(item);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item updated!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String item_code = txtItemCode.getText();

        try {
            boolean isDeleted = ItemRepo.delete(item_code);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Successfully Deleted!").show();
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
        txtItemCode.clear();
        txtName.clear();
        txtICategory.clear();
        txtBrand.clear();
        txtPrice.clear();
        txtStockQuantity.clear();

    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/dashboardPage.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    public void categorySearchOnAction(ActionEvent actionEvent) {
    }
    @FXML
    void itemCodeSearchOnAction(ActionEvent actionEvent) {
        String itemCode = txtItemCode.getText();
        try {
            Item item = ItemRepo.searchById(itemCode);
            if(item != null){
                txtItemCode.setText(item.getItemCode());
                txtName.setText(item.getName());
                txtICategory.setText(item.getCategory());
                txtBrand.setText(item.getBrand());
                txtPrice.setText(String.valueOf(item.getPrice()));
                txtStockQuantity.setText(String.valueOf(item.getQty()));
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION,"ERROR").show();
        }
    }


    public void mouseClickOnActon(MouseEvent mouseEvent) {
        Integer index = tblItem.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }
        txtItemCode.setText(colItemCode.getCellData(index).toString());
        txtName.setText(colname.getCellData(index).toString());
        txtICategory.setText(coleCategory.getCellData(index).toString());
        txtBrand.setText(colBrand.getCellData(index).toString());
        txtPrice.setText(colPrice.getCellData(index).toString());
        txtStockQuantity.setText(colStockQty.getCellData(index).toString());

    }

}