package lk.ijse.gdse71.final_project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse71.final_project.CrudUtil.CrudUtil;
import lk.ijse.gdse71.final_project.dto.CourseDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseModel {
    public ObservableList<String> getAllCoursseIds() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Course_id from course;");

        ObservableList<String> ids = FXCollections.observableArrayList();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            ids.add(id);
        }
        return ids;
    }

    public String getCourseName(String id) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Course_name from course where Course_id = ?",id);

        while (resultSet.next()){
                return resultSet.getString(1);
        }

        return null;
    }

    public ArrayList<CourseDto> getAllCourses() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from course");
        ArrayList<CourseDto> courseDtos = new ArrayList<>();

        while (resultSet.next()){
            CourseDto courseDto = new CourseDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3)
            );
            courseDtos.add(courseDto);
        }
        return courseDtos;
    }

    public boolean savaCourse(CourseDto courseDto) throws SQLException {
        return CrudUtil.execute("insert into course values(?,?,?);",
                courseDto.getCourseId(),
                courseDto.getCourseName(),
                courseDto.getDuration()
        );
    }

    public String getNextCourseId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Course_id from course order by Course_id desc limit 1;");

        while (resultSet.next()){
            String id = resultSet.getString(1);
            String subString = id.substring(1);
            int nextId = Integer.parseInt(subString)+1;
            return String.format("C%03d",nextId);
        }
        return "C001";
    }

    public boolean deleteCourse(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM course WHERE Course_id = ? ;",id);
    }

    public boolean updatecourse(CourseDto courseDto) throws SQLException {
        return CrudUtil.execute("UPDATE course SET Course_name = ?, Duration = ? WHERE Course_id = ?;",
                courseDto.getCourseName(),
                courseDto.getDuration(),
                courseDto.getCourseId()
                );
    }
}
