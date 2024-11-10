package lk.ijse.gdse71.final_project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse71.final_project.CrudUtil.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClassroomModel {
    public static ObservableList<String> getAllClassroomIds() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select classroom_id from classroom;");
        ObservableList<String> ids = FXCollections.observableArrayList();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            ids.add(id);
        }
        return ids;
    }
}
