package lk.ijse.gdse71.final_project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse71.final_project.CrudUtil.CrudUtil;
import lk.ijse.gdse71.final_project.dto.tm.StudentTm;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShedulModel {
    public ObservableList<String> getSchedulIds() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from schedule");
        ObservableList<String> ids = FXCollections.observableArrayList();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            ids.add(id);
        }
        return ids;
    }

    public Date getDate(String id) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Date from schedule where Schedule_id =?;",id);

        while (resultSet.next()){
            return  resultSet.getDate(1);
        }
        return null;
    }
}
