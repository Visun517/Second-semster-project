package lk.ijse.gdse71.final_project.model;

import lk.ijse.gdse71.final_project.CrudUtil.CrudUtil;
import lk.ijse.gdse71.final_project.dto.StudentDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentModel {

    public String getNextStudentId() throws SQLException {
       ResultSet resultSet = CrudUtil.execute("select Student_id from student order by Student_id desc limit 1;");

       while (resultSet.next()){
           String id = resultSet.getString(1);
           String subString = id.substring(1);
           int nextId = Integer.parseInt(subString)+1;
           return String.format("S%03d",nextId);
       }
       return "S001";
    }

    public ArrayList<StudentDto> getAllStudent() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from student");

        ArrayList<StudentDto> studentDtos = new ArrayList<>();

        while (resultSet.next()){
            StudentDto studentDto = new StudentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            studentDtos.add(studentDto);
        }
        return studentDtos;
    }
}
