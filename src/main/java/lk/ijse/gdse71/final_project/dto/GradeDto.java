package lk.ijse.gdse71.final_project.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GradeDto {
    private String gradeId;
    private String examId;
    private String grade;
    private String desc;
    private String studentId;
}
