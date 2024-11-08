package lk.ijse.gdse71.final_project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse71.final_project.CrudUtil.CrudUtil;
import lk.ijse.gdse71.final_project.dto.SubjectDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SemesterModel {
    public ObservableList<String> getAllSemesterIds() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Semester_id from semester;");
        ObservableList<String> ids = FXCollections.observableArrayList();

        while (resultSet.next()){
          String id = resultSet.getString(1);
          ids.add(id);
        }
        return ids;
    }

    public boolean saveSubject(SubjectDto subjectDto) throws SQLException {
        return CrudUtil.execute("insert into subject values(?,?,?,?);",
                subjectDto.getSubjectId(),
                subjectDto.getSubjectName(),
                subjectDto.getSubDesc(),
                subjectDto.getSemesterId()
        );
    }

    public boolean updateSubject(SubjectDto subjectDto) throws SQLException {
        return CrudUtil.execute("UPDATE subject SET Subject_name = ?, Sub_desc = ?,Semester_id = ? WHERE Subject_id = ?;",
                subjectDto.getSubjectName(),
                subjectDto.getSubDesc(),
                subjectDto.getSemesterId(),
                subjectDto.getSubjectId()
        );
    }

    public boolean deleteSubject(String id) throws SQLException {
        return CrudUtil.execute("delete from subject where Subject_id = ? ;",id);
    }
}
