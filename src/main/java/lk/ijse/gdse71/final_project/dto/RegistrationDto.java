package lk.ijse.gdse71.final_project.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegistrationDto {
    private String registrationId;
    private String studentId;
    private String courseId;
    private Date registrationDate;

}
