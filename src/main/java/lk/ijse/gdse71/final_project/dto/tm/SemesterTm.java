package lk.ijse.gdse71.final_project.dto.tm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SemesterTm {
    private String semesterId;
    private String semesterName;
    private Date starDate;
    private Date endDate;
}
