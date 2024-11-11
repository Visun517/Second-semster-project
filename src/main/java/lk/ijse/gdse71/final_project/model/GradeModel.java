package lk.ijse.gdse71.final_project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse71.final_project.CrudUtil.CrudUtil;
import lk.ijse.gdse71.final_project.dto.ExamDto;
import lk.ijse.gdse71.final_project.dto.GradeDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GradeModel {
    public ArrayList<GradeDto> getAllGrads() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from grade;");
        ArrayList<GradeDto> gradeDtos = new ArrayList<>();

        while (resultSet.next()){
            GradeDto gradeDto = new GradeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            gradeDtos.add(gradeDto);
        }
        return gradeDtos;
    }

    public String getNextGradeId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Grade_id from grade order by Grade_id desc limit 1;");

        while (resultSet.next()){
            String id = resultSet.getString(1);
            String subString = id.substring(1);
            int nextId = Integer.parseInt(subString)+1;
            return String.format("G%03d",nextId);
        }
        return "G001";
    }

    public boolean saveGrade(GradeDto gradeDto) throws SQLException {
        return CrudUtil.execute("insert into grade values(?,?,?,?,?);",
                gradeDto.getGradeId(),
                gradeDto.getExamId(),
                gradeDto.getGrade(),
                gradeDto.getDesc(),
                gradeDto.getStudentId()
        );
    }

    public boolean updaetGrade(GradeDto gradeDto) throws SQLException {
        return CrudUtil.execute("UPDATE grade SET Exam_id = ?, Grade = ?, Description = ?,student_id = ? where Grade_id = ?;",
                gradeDto.getExamId(),
                gradeDto.getGrade(),
                gradeDto.getDesc(),
                gradeDto.getStudentId(),
                gradeDto.getGradeId()
        );
    }

    public boolean deleteGrade(String id) throws SQLException {
        return CrudUtil.execute("delete from grade where Grade_id = ?;",id);
    }
}
