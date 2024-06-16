package lk.ijse.gdse69.cs_fashion.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    private String orderId;
    private Date orderDate;
    private String userId;
    private String customerId;
    private String paymentId;
}
