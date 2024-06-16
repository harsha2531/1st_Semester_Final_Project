package lk.ijse.gdse69.cs_fashion.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
    private String id;
    private String name;
    private String address;
    private String tel;
    private LocalDate dob;
    private String email;



    public Customer(String id, String name, String email, String tel,String address, LocalDate dob) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.dob= dob;
        this.email = email;

    }

}
