package lk.ijse.gdse71.final_project.model;

import lk.ijse.gdse71.final_project.CrudUtil.CrudUtil;
import lk.ijse.gdse71.final_project.dto.SubjectDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubjectModel {
    public ArrayList<SubjectDto> getAllSubjects() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from subject");
        ArrayList<SubjectDto> subjectDtos = new ArrayList<>();

        while (resultSet.next()){
            SubjectDto subjectDto = new SubjectDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            subjectDtos.add(subjectDto);
        }
        return subjectDtos;
    }

    public String getNextSubjectId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Subject_id from subject order by Subject_id desc limit 1;");

        while (resultSet.next()){
            String id = resultSet.getString(1);
            String subString = id.substring(3);
            int nextId = Integer.parseInt(subString)+1;
            return String.format("SUB%03d",nextId);
        }
        return "SUB001";
    }

    public String getSemsterName(String id) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Semester_name from semester where Semester_id = ?;",id);

        while (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }
}
