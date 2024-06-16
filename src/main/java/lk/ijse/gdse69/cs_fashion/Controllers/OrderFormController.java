package lk.ijse.gdse69.cs_fashion.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.gdse69.cs_fashion.Model.*;
import lk.ijse.gdse69.cs_fashion.Model.Tm.CartTM;
import lk.ijse.gdse69.cs_fashion.Model.Tm.Payment;
import lk.ijse.gdse69.cs_fashion.Repository.*;

import java.io.IOException;
import java.security.cert.PolicyNode;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lk.ijse.gdse69.cs_fashion.Repository.ItemRepo.itemCode;

public class OrderFormController {

    public AnchorPane rootNode;
    public TableView tblPlaceOrder;
    public TableColumn colName;
    public TableColumn colPaidAmount;
    public Label lblName;
    public JFXComboBox cmbPaymentMethod;
    public Label lblOrderDate11;
    public JFXButton btnPlaceOrder;
    public TextField txtPaymentAmount;
    public Label lblPaymentId;
    public Label lblPaymentDate;
    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<CartTM> tblOrderCart;

    @FXML
    private TextField txtQty;

    private ObservableList<CartTM> obList = FXCollections.observableArrayList();
    private AnchorPane subRoot;
    private String itemCode;
    private double totalAmount;
    private double paidAmount;
    private Date orderDate;
    private String userId;
    private String customerId;
    private String paymentId;
    private ActionEvent event;
    private Date paymentDate;

    public void initialize() {
        setDate();
        getCurrentOrderId();
        getCustomerIds();
        getItemCodes();
        setCellValueFactory();
        getCurrentPaymentId();
    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
        colName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colPaidAmount.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));

    }


    private void getItemCodes() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<String> codeList = ItemRepo.getCodes();

            for (String code : codeList) {
                obList.add(code);
            }
            cmbItemCode.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = CustomerRepo.getIds();

            for(String id : idList) {
                obList.add(id);
            }

            cmbCustomerId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCurrentOrderId() {
        try {
            String currentId = OrderRepo.getCurrentId();

            String nextOrderId = generateNextOrderId(currentId);
            lblOrderId.setText(nextOrderId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateNextOrderId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("O");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "O" + ++idNum;
        }
        return "O1";
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblOrderDate.setText(String.valueOf(now));
        lblPaymentDate.setText(String.valueOf(now));
    }
    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String code = cmbItemCode.getValue();
        String name = lblName.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = qty * unitPrice;
        double paidAmount = Double.parseDouble(txtPaymentAmount.getText());
        JFXButton btnRemove = new JFXButton("Remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblPlaceOrder.getSelectionModel().getSelectedIndex();
                System.out.println(selectedIndex);
                obList.remove(selectedIndex);

                tblPlaceOrder.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            if(code.equals(colItemCode.getCellData(i))) {
                CartTM tm = obList.get(i);
                qty += tm.getQty();
                total = qty * unitPrice;

                tm.setQty(qty);
                tm.setTotalAmount(total);

                tblPlaceOrder.refresh();
                calculateNetTotal();
                return;
            }
        }

        CartTM tm = new CartTM(code, name, unitPrice, qty, total,paidAmount, btnRemove);
        obList.add(tm);
        tblPlaceOrder.setItems(obList);
        calculateNetTotal();
        txtQty.setText("");
    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            Double total = (Double) colTotal.getCellData(i);
            if (total != null) {
                netTotal += total;
            }
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }


    @FXML
    void btnNewCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode1 = FXMLLoader.load(getClass().getResource("/View/customer_form.fxml"));
        rootNode.getChildren().clear();
        rootNode.getChildren().add(rootNode1);

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException {
        String orderId = lblOrderId.getText();
        String cusId = cmbCustomerId.getValue();
        Date date = Date.valueOf(LocalDate.now());
        String paymentId = lblPaymentId.getText();
        Double amount = Double.valueOf(txtPaymentAmount.getText());
        Date payDate = Date.valueOf(lblPaymentDate.getText());


        var order = new Order(orderId, date, userId,cusId,paymentId);

        List<OrderItem> odList = new ArrayList<>();

        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            CartTM tm = obList.get(i);

            OrderItem od = new OrderItem(
                    orderId,
                    tm.getItemCode(),
                    tm.getUnitPrice(),
                    tm.getQty()
            );

            odList.add(od);
        }
        double total = Double.parseDouble(lblNetTotal.getText());
        Payment payment = new Payment(paymentId,total,date);
        PlaceOrder po = new PlaceOrder(order, odList,payment);
        try {
            boolean isPlaced = PlaceOrderRepo.placeOrder(po,payment);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "Order Placed!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Order Placed Unsuccessfully!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

     }


    @FXML
    void cmbCustomerOnAction(ActionEvent event) {
        String id = cmbCustomerId.getValue();
        try {
            Customer customer = CustomerRepo.searchById(id);

            lblCustomerName.setText(customer.getName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) {
        String code = cmbItemCode.getValue();

        try {
            Item item = ItemRepo.searchById(code);
            if(item != null) {
                lblName.setText(item.getName());
                lblUnitPrice.setText(String.valueOf(item.getPrice()));
                lblQtyOnHand.setText(String.valueOf(item.getQty()));
            }

            txtQty.requestFocus();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }
    private void getCurrentPaymentId() {
        try {
            String currentId = PaymentRepo.getCurrentId();

            String nextPaymentId = generateNextPaymentId(currentId);
            lblPaymentId.setText(nextPaymentId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private String generateNextPaymentId(String currentId) {
        if(currentId != null) {
            String[] split = currentId.split("O");  //" ", "2"
            int idNum = Integer.parseInt(split[1]);
            return "O" + ++idNum;
        }
        return "O1";
    }


    public void txtPaymentAmountOnAction(ActionEvent actionEvent) {

        btnAddToCartOnAction(event);
    }
}