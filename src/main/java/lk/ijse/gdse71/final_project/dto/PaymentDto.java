package lk.ijse.gdse71.final_project.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentDto {
    private String paymentId;
    private String studentId;
    private String status;
    private String payType;
    private String referenceNum;
    private double amount;
}