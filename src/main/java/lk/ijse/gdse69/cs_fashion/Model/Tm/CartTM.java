package lk.ijse.gdse69.cs_fashion.Model.Tm;

import com.jfoenix.controls.JFXButton;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class CartTM {
    private String itemCode;
    private String itemName;
    private Double unitPrice;
    private Integer qty;
    private Double totalAmount;
    private Double paidAmount;
    private JFXButton btnRemove;



}
