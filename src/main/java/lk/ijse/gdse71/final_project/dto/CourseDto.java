package lk.ijse.gdse71.final_project.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseDto {
    private String courseId;
    private String courseName;
    private int duration;
    private double payment;

}
