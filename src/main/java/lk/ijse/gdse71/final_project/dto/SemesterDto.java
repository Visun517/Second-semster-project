package lk.ijse.gdse71.final_project.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SemesterDto {
    private String semesterId;
    private String semesterName;
    private Date starDate;
    private Date endDate;
}
