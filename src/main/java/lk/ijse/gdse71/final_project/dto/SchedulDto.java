package lk.ijse.gdse71.final_project.dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SchedulDto {
    private String schedulId;
    private String courseId;
    private String classRoomId;
    private String startTime;
    private String endTime;
    private String weekday;
    private Date date;

    private LectureManageDto lectureManageDto;

    public SchedulDto(String schedulId, String courseId, String classRoomId, String startTime, String endTime, String weekday,Date date,LectureManageDto lectureManageDto) {
        this.schedulId = schedulId;
        this.courseId = courseId;
        this.classRoomId = classRoomId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekday = weekday;
        this.date = date;
        this.lectureManageDto = lectureManageDto;

    }
    public SchedulDto(String schedulId, String courseId, String classRoomId, String startTime, String endTime, String weekday,Date date) {
        this.schedulId = schedulId;
        this.courseId = courseId;
        this.classRoomId = classRoomId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekday = weekday;
        this.date = date;

    }

}
