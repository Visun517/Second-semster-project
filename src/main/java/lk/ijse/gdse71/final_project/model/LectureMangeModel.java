package lk.ijse.gdse71.final_project.model;

import lk.ijse.gdse71.final_project.CrudUtil.CrudUtil;
import lk.ijse.gdse71.final_project.dto.LectureManageDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LectureMangeModel {
    public String getNextLectureId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select LectureManagement_id from lecturemanagement order by LectureManagement_id desc limit 1;");

        while (resultSet.next()){
            String id = resultSet.getString(1);
            String subString = id.substring(2);
            int nextId = Integer.parseInt(subString)+1;
            return String.format("LM%03d",nextId);
        }
        return "LM001";
    }

    public boolean addLectureMange(LectureManageDto lectureManageDto) throws SQLException {
        return CrudUtil.execute("insert into lecturemanagement values(?,?,?,?);",
                lectureManageDto.getLectureManageId(),
                lectureManageDto.getLectureId(),
                lectureManageDto.getClassroomId(),
                lectureManageDto.getScheduleId()
        );

    }
}
