package lk.ijse.gdse71.final_project.dto.tm;

import lombok.*;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegistrationTm {
    private String registrationId;
    private String studentId;
    private String courseId;
    private Date registrationDate;
    private double fullPayment;

}
