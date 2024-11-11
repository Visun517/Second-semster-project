package lk.ijse.gdse71.final_project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse71.final_project.CrudUtil.CrudUtil;
import lk.ijse.gdse71.final_project.dto.ExamDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExamModel {

    public ArrayList<ExamDto> getAllExams() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from exam;");
        ArrayList<ExamDto> examDtos = new ArrayList<>();

        while (resultSet.next()){
            ExamDto examDto = new ExamDto(
                    resultSet.getNString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4)
            );
            examDtos.add(examDto);
        }
        return examDtos;
    }

    public String getNextExamId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Exam_id from exam order by Exam_id desc limit 1;");

        while (resultSet.next()){
            String id = resultSet.getString(1);
            String subString = id.substring(1);
            int nextId = Integer.parseInt(subString)+1;
            return String.format("E%03d",nextId);
        }
        return "E001";
    }

    public boolean saveExam(ExamDto examDto) throws SQLException {
        System.out.println(examDto.getDesc());
        return CrudUtil.execute("insert into exam values(?,?,?,?)",
                            examDto.getExamId(),
                            examDto.getSubjectId(),
                            examDto.getDesc(),
                            examDto.getDate()
                );
    }

    public ObservableList<String> getAllExamIds() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Exam_id from exam;");
        ObservableList<String> examIds = FXCollections.observableArrayList();

        while (resultSet.next()){
            String ids = resultSet.getString(1);
            examIds.add(ids);
        }
        return  examIds;
    }

    public boolean updateExam(ExamDto examDto) throws SQLException {
        return CrudUtil.execute("UPDATE exam SET Subject_id = ?, Description = ?, Date = ? WHERE Exam_id = ?;",
                examDto.getSubjectId(),
                examDto.getDesc(),
                examDto.getDate(),
                examDto.getExamId()
        );
    }

    public boolean deleteExam(String id) throws SQLException {
        return CrudUtil.execute("delete from exam where Exam_id = ?",id);
    }
}
