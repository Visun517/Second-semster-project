package lk.ijse.gdse71.final_project.dto.tm;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttendenceTm {
    private String attendenceId;
    private String schedulId;
    private String studentId;
    private Date classDate;
    private String remark;

}
