package lk.ijse.gdse71.final_project.model;

import lk.ijse.gdse71.final_project.CrudUtil.CrudUtil;
import lk.ijse.gdse71.final_project.dto.AttendenceDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendenceModel {
    public ArrayList<AttendenceDto> getAllAttendence() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from attendance");
        ArrayList<AttendenceDto> attendenceDtos = new ArrayList<>();

        while (resultSet.next()){
            AttendenceDto attendenceDto = new AttendenceDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4),
                    resultSet.getString(5)

                    );
            attendenceDtos.add(attendenceDto);
        }
        return attendenceDtos;
    }

    public String getNextAttendenceId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Attendance_id from attendance order by Attendance_id desc limit 1;");

        while (resultSet.next()){
            String id = resultSet.getString(1);
            String subString = id.substring(1);
            int nextId = Integer.parseInt(subString)+1;
            return String.format("A%03d",nextId);
        }
        return "A001";
    }

    public boolean attendenceSave(AttendenceDto attendenceDto) throws SQLException {
        return CrudUtil.execute(" insert into attendance values(?,?,?,?,?);",
                attendenceDto.getAttendenceId(),
                attendenceDto.getSchedulId(),
                attendenceDto.getStudentId(),
                attendenceDto.getClassDate(),
                attendenceDto.getRemark()
        );
    }

    public boolean attendenceUpdate(AttendenceDto attendenceDto) throws SQLException {
        return CrudUtil.execute("UPDATE attendance SET Schedule_id = ?, Student_id = ?,Class_date = ?,Remark = ? WHERE Attendance_id = ?;",
                attendenceDto.getSchedulId(),
                attendenceDto.getStudentId(),
                attendenceDto.getClassDate(),
                attendenceDto.getRemark(),
                attendenceDto.getAttendenceId()
        );
    }

    public boolean attendenceDelete(String id) throws SQLException {
       return CrudUtil.execute("DELETE FROM attendance WHERE Attendance_id = ?;",id);
    }
}
