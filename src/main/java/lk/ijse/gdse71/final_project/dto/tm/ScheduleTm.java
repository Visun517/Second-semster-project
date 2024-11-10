package lk.ijse.gdse71.final_project.dto.tm;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScheduleTm {
    private String scheduleId;
    private String CourseId;
    private String classroomId;
    private String startTime;
    private String endTime;
    private String weekDay;
    private Date date;
}
