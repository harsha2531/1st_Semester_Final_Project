package lk.ijse.gdse69.cs_fashion.Model.Tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemTM {
    private String itemCode;
    private String name;
    private String category;
    private String brand;
    private Double price;
    private Integer qty;

}
