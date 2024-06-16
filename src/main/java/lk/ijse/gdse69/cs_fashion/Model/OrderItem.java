package lk.ijse.gdse69.cs_fashion.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItem {
    private String orderId;
    private String itemCode;
    private Double unitPrice;
    private Integer qty;
}
