package lk.ijse.gdse71.final_project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse71.final_project.CrudUtil.CrudUtil;
import lk.ijse.gdse71.final_project.dto.LectureDto;
import lk.ijse.gdse71.final_project.dto.tm.LectureTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LectureModel {
    public ArrayList<LectureDto> getAllLectures() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from lecture");
        ArrayList<LectureDto> lectureDtos = new ArrayList<>();

        while (resultSet.next()){
            LectureDto lectureDto = new LectureDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
            lectureDtos.add(lectureDto);
        }
        return lectureDtos;
    }

    public String getNextLectureId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Lecture_id from lecture order by Lecture_id desc limit 1;");

        while (resultSet.next()){
            String id = resultSet.getString(1);
            String subString = id.substring(1);
            int nextId = Integer.parseInt(subString)+1;
            return String.format("L%03d",nextId);
        }
        return "L001";
    }

    public boolean lectureSave(LectureDto lectureDto) throws SQLException {
        return CrudUtil.execute("insert into lecture values(?,?,?,?)",
                lectureDto.getLectureId(),
                lectureDto.getSubjectId(),
                lectureDto.getLecTitle(),
                lectureDto.getName()
        );
    }

    public boolean lectureUpdate(LectureDto lectureDto) throws SQLException {
        return CrudUtil.execute("UPDATE lecture SET Subject_id = ?, Lecture_title = ?,name = ? WHERE Lecture_id = ?;",
                lectureDto.getSubjectId(),
                lectureDto.getLecTitle(),
                lectureDto.getName(),
                lectureDto.getLectureId()
        );
    }

    public boolean lectureDelete(String id) throws SQLException {
        return CrudUtil.execute("delete from lecture where Lecture_id =?;",id);
    }

    public ObservableList<String> getAllLecturesIds() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Lecture_id from Lecture;");
        ObservableList<String> ids = FXCollections.observableArrayList();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            ids.add(id);
        }
        return ids;
    }
    public static String getLectureName(String id) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select name from lecture where lecture_id = ?;",id);

        while (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

}
