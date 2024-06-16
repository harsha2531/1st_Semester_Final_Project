package lk.ijse.gdse69.cs_fashion.Model.Tm;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Payment {
    private String paymentId;
    private Double amount;
    private Date paymentDate;

}
