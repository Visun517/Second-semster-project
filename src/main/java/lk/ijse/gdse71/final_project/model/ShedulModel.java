package lk.ijse.gdse71.final_project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse71.final_project.CrudUtil.CrudUtil;
import lk.ijse.gdse71.final_project.db.DBConnection;
import lk.ijse.gdse71.final_project.dto.SchedulDto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShedulModel {

    private LectureMangeModel lectureMangeModel = new LectureMangeModel();

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

    public ArrayList<SchedulDto> getAllSchedul() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from schedule;");
        ArrayList<SchedulDto> scheduleDtos = new ArrayList<>();

        while (resultSet.next()){
            SchedulDto scheduleDto = new SchedulDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getDate(7)

            );
            scheduleDtos.add(scheduleDto);
        }
        return scheduleDtos;
    }

    public String getNextScheduleId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Schedule_id from schedule order by Schedule_id desc limit 1;");

        while (resultSet.next()){
            String id = resultSet.getString(1);
            String subString = id.substring(2);
            int nextId = Integer.parseInt(subString)+1;
            return String.format("SC%03d",nextId);

        }
        return "SC001";
    }

    public boolean addSchedule(SchedulDto scheduleDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            boolean isSaved = CrudUtil.execute("insert into schedule values(?,?,?,?,?,?,?);",
                    scheduleDto.getSchedulId(),
                    scheduleDto.getCourseId(),
                    scheduleDto.getClassRoomId(),
                    scheduleDto.getStartTime(),
                    scheduleDto.getEndTime(),
                    scheduleDto.getWeekday(),
                    scheduleDto.getDate()
            );
            if (isSaved){
                boolean isLectureManeSaved = lectureMangeModel.addLectureMange(scheduleDto.getLectureManageDto());
                if (isLectureManeSaved){
                    connection.commit();
                    return true;
                }

            }
            connection.rollback();
            return false;

        }catch (Exception e){
            e.printStackTrace();
            connection.rollback();
            return false;

        }finally {

            connection.setAutoCommit(true);
        }
    }

    public boolean updateSchedule(SchedulDto scheduleDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            boolean isUpdate = CrudUtil.execute("UPDATE schedule SET Course_id = ?, Classroom_id = ?, Start_time = ?,End_time = ?,Week_day = ?,Date = ? WHERE Schedule_id = ?;",
                        scheduleDto.getCourseId(),
                        scheduleDto.getClassRoomId(),
                        scheduleDto.getStartTime(),
                        scheduleDto.getEndTime(),
                        scheduleDto.getWeekday(),
                        scheduleDto.getDate(),
                        scheduleDto.getSchedulId()
                    );
            if (isUpdate){
                boolean isLectureManeSaved = lectureMangeModel.updateLectureMange(scheduleDto.getLectureManageDto());
                if (isLectureManeSaved){
                    connection.commit();
                    return true;
                }

            }
            connection.rollback();
            return false;

        }catch (Exception e){
            e.printStackTrace();
            connection.rollback();
            return false;

        }finally {

            connection.setAutoCommit(true);
        }
    }

    public boolean deleteSchedule(String scheduleId) throws SQLException {
        return CrudUtil.execute("DELETE FROM schedule WHERE Schedule_id = ?;", scheduleId);
    }
}
