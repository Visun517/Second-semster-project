package lk.ijse.gdse71.final_project.dto.tm;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseTm {
    private String courseId;
    private String courseName;
    private int duration;
    private double payment;
}
