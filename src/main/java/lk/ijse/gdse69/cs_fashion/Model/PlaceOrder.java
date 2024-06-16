package lk.ijse.gdse69.cs_fashion.Model;


import lk.ijse.gdse69.cs_fashion.Model.Tm.Payment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PlaceOrder {
    private Order order;
    private List<OrderItem> odList;
    private Payment payment;

}
