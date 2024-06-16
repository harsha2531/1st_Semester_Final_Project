package lk.ijse.gdse69.cs_fashion.Model.Tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerTM {
    private String id;
    private String name;
    private String address;
    private String tel;
    private LocalDate dob;
    private String email;

}
