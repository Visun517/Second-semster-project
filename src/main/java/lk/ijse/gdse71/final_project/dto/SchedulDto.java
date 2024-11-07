package lk.ijse.gdse71.final_project.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SchedulDto {
    private String schedulId;
    private String courseId;
    private String classRoomId;
    private Time startTime;
    private Time endTime;
    private String weekday;
    private Date date;
}
