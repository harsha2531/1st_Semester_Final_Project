package lk.ijse.gdse69.cs_fashion.Repository;

import lk.ijse.gdse69.cs_fashion.Database.DbConnection;
import lk.ijse.gdse69.cs_fashion.Model.PlaceOrder;
import lk.ijse.gdse69.cs_fashion.Model.Tm.Payment;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderRepo {

    public static boolean placeOrder(PlaceOrder po, Payment payment) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false);


        try {
            boolean isPaymentSaved = PaymentRepo.save(payment.getPaymentId(),payment.getAmount(),payment.getPaymentDate());
            System.out.println("1 "+isPaymentSaved);


            boolean isOrderSaved = OrderRepo.save(po.getOrder());
            System.out.println("1 "+isOrderSaved);
            if (isOrderSaved) {
                boolean isQtyUpdated = ItemRepo.updateQty(po.getOdList());
                System.out.println("2 "+isQtyUpdated);
                if (isQtyUpdated) {
                    boolean isOrderDetailSaved = OrderItemRepo.save(po.getOdList());
                    System.out.println("3 "+isOrderDetailSaved);
                    if (isOrderDetailSaved) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);

//            connection.rollback();
//            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
