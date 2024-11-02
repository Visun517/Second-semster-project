package lk.ijse.gdse71.final_project.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentDto {
    private String student_id;
    private String name;
    private String email;
    private String address;
    private String gender;
}
