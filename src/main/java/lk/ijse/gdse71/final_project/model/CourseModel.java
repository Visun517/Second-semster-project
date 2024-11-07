package lk.ijse.gdse71.final_project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse71.final_project.CrudUtil.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM registration WHERE Registration_id = ? ;",id);
    }
}
