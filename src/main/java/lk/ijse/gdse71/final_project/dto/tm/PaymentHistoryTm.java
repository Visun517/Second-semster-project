package lk.ijse.gdse71.final_project.dto.tm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentHistoryTm {
    private String studentId;
    private String paymentId;
    private String name;
    private Date date;
    private double amount;
}
