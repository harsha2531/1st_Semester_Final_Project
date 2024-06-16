package lk.ijse.gdse69.cs_fashion.Model.Tm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeTM {
    private String id;
    private String name;
    private String position;
    private Integer tel;
    private String email;
}
