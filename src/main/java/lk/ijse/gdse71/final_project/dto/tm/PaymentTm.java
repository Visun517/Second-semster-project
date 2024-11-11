package lk.ijse.gdse71.final_project.dto.tm;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentTm {
    private String paymentId;
    private String studentId;
    private String status;
    private String payType;
    private String referenceNum;
    private double amount;
}
