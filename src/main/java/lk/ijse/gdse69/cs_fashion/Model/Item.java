package lk.ijse.gdse69.cs_fashion.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class Item {
    private String itemCode;
    private String name;
    private String category;
    private String brand;
    private Double price;
    private Integer qty;




    public Item(String itemCode, String name, String category, String brand,Double price, Integer qty) {
        this.itemCode = itemCode;
        this.name = name;
        this.category = category;
        this.brand = brand;
        this.price= price;
        this.qty = qty;

    }

}
