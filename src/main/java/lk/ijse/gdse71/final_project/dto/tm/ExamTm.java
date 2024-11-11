package lk.ijse.gdse71.final_project.dto.tm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExamTm {
    private String examId;
    private String subjectId;
    private String desc;
    private Date date;
}
