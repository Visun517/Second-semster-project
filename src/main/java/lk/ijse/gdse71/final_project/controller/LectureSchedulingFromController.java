package lk.ijse.gdse71.final_project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.final_project.dto.LectureManageDto;
import lk.ijse.gdse71.final_project.dto.SchedulDto;
import lk.ijse.gdse71.final_project.dto.tm.ScheduleTm;
import lk.ijse.gdse71.final_project.model.*;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LectureSchedulingFromController implements Initializable {

    @FXML
    private Button DeleteSchedule;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbClassroomId;

    @FXML
    private ComboBox<String> cmbCourseId;

    @FXML
    private ComboBox<String> cmbLectureId;

    @FXML
    private TableColumn<ScheduleTm, String> colClassroomId;

    @FXML
    private TableColumn<ScheduleTm, String> colCourseId;

    @FXML
    private TableColumn<ScheduleTm, Date> colDate;

    @FXML
    private TableColumn<ScheduleTm, Time> colEndTime;

    @FXML
    private TableColumn<ScheduleTm, String> colScheduleId;

    @FXML
    private TableColumn<ScheduleTm, Time> colStartTime;

    @FXML
    private TableColumn<ScheduleTm, String> colWeekDay;

    @FXML
    private Label lblClassroomId;

    @FXML
    private Label lblCourseId;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblEndTime;

    @FXML
    private Label lblLectureName;

    @FXML
    private Label lblScheduleIdShow;

    @FXML
    private Label lblScheduleIdShowe;

    @FXML
    private Label lblSelectLecture;

    @FXML
    private Label lblStratTime;

    @FXML
    private TableView<ScheduleTm> tbleSchedule;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtEndTime;

    @FXML
    private TextField txtStartTime;
    @FXML
    private ComboBox<String> cmbWeekDay;
    @FXML
    private Label lblWeekday;
    @FXML
    private DatePicker datePicker;


    private ShedulModel scheduleModel = new ShedulModel();
    private LectureModel lectureModel = new LectureModel();
    private CourseModel courseModel = new CourseModel();
    private ClassroomModel classroomModel = new ClassroomModel();
    private LectureMangeModel lectureMangeModel = new LectureMangeModel();
    private String id;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colScheduleId.setCellValueFactory(new PropertyValueFactory<>("scheduleId"));
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("CourseId"));
        colClassroomId.setCellValueFactory(new PropertyValueFactory<>("classroomId"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colWeekDay.setCellValueFactory(new PropertyValueFactory<>("weekDay"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        refresh();

    }

    private void getAllSchedule() {
        try {
            ArrayList<SchedulDto> scheduleDtos = scheduleModel.getAllSchedul();
            ObservableList<ScheduleTm> scheduleTms = FXCollections.observableArrayList();

            for (SchedulDto scheduleDto : scheduleDtos) {
                ScheduleTm scheduleTm = new ScheduleTm(
                        scheduleDto.getSchedulId(),
                        scheduleDto.getCourseId(),
                        scheduleDto.getClassRoomId(),
                        scheduleDto.getStartTime(),
                        scheduleDto.getEndTime(),
                        scheduleDto.getWeekday(),
                        scheduleDto.getDate()
                );
                scheduleTms.add(scheduleTm);
            }
            tbleSchedule.setItems(scheduleTms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getLectureIds() {
        try {
            ObservableList<String> lectureIds = lectureModel.getAllLecturesIds();
            cmbLectureId.setItems(lectureIds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void courseIds() {
        try {
            ObservableList<String> allCoursseIds = courseModel.getAllCoursseIds();
            cmbCourseId.setItems(allCoursseIds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setWeekDays() {
        String days[] = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        ObservableList<String> days1 = FXCollections.observableArrayList();
        days1.addAll(days);
        cmbWeekDay.setItems(days1);
    }

    private void getAllClassroomIds() {
        try {
            ObservableList<String> ids = ClassroomModel.getAllClassroomIds();
            cmbClassroomId.setItems(ids);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getNextScheduleId() {
        try {
            String id = scheduleModel.getNextScheduleId();
            lblScheduleIdShow.setText(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void DeleteScheduleOnAction(ActionEvent event) {
        String scheduleId = lblScheduleIdShow.getText();
        System.out.println(scheduleId);

        try {
            boolean isDelete = scheduleModel.deleteSchedule(scheduleId);
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION, "Schedule is Delete...!").showAndWait();
            }else{
                new Alert(Alert.AlertType.ERROR, "Schedule is not delete...!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        getNextLectureId();
        String scheduleId = lblScheduleIdShow.getText();
        String courseId = cmbCourseId.getValue();
        //System.out.println(courseId);
        String classroomId = cmbClassroomId.getValue();
        String sTime = txtStartTime.getText();
        String eTime = txtEndTime.getText();
        String weekday = cmbWeekDay.getValue();
        Date date = Date.valueOf(datePicker.getValue());

        String lectureId = cmbLectureId.getValue();
        LectureManageDto lectureManageDto = new LectureManageDto(id, lectureId, classroomId, scheduleId);
        SchedulDto scheduleDto = new SchedulDto(scheduleId, courseId, classroomId, sTime, eTime, weekday, date, lectureManageDto);
        //System.out.println(scheduleDto);
        //System.out.println(scheduleDto.getSchedulId().toString());


        try {
            boolean isAdd = scheduleModel.addSchedule(scheduleDto);
            if (isAdd) {
                new Alert(Alert.AlertType.INFORMATION, "Schedule is add...!").showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, "Schedule is not add...!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void getNextLectureId() {
        try {
            id = lectureMangeModel.getNextLectureId();
            System.out.println(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String scheduleId = lblScheduleIdShow.getText();
        String courseId = cmbCourseId.getValue();
        String classroomId = cmbClassroomId.getValue();
        String sTime = txtStartTime.getText();
        String eTime = txtEndTime.getText();
        String weekday = cmbWeekDay.getValue();
        Date date = Date.valueOf(datePicker.getValue());

        String lectureManageId = null;
        try {
            lectureManageId = lectureMangeModel.getLectureMangeId(scheduleId);
            System.out.println(lectureManageId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String lectureId = cmbLectureId.getValue();
        LectureManageDto lectureManageDto = new LectureManageDto(lectureManageId, lectureId, classroomId, scheduleId);
        SchedulDto scheduleDto = new SchedulDto(scheduleId, courseId, classroomId, sTime, eTime, weekday, date, lectureManageDto);
//        System.out.println(lectureManageId);
//        System.out.println(scheduleDto.getSchedulId().toString());


        try {
            boolean isUpdate = scheduleModel.updateSchedule(scheduleDto);
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Schedule is update...!").showAndWait();
            } else {
                new Alert(Alert.AlertType.ERROR, "Schedule is not update...!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void tbleScheduleOnCliked(MouseEvent event) {
        ScheduleTm scheduleTm = tbleSchedule.getSelectionModel().getSelectedItem();
        System.out.println(scheduleTm);
        lblScheduleIdShow.setText(scheduleTm.getScheduleId());
        cmbCourseId.setValue(scheduleTm.getCourseId());
        cmbClassroomId.setValue(scheduleTm.getClassroomId());
        txtStartTime.setText(scheduleTm.getStartTime());
        txtEndTime.setText(scheduleTm.getEndTime());
        cmbWeekDay.setValue(scheduleTm.getWeekDay());
        datePicker.setValue(scheduleTm.getDate().toLocalDate());

        try {
            String id = lectureMangeModel.getLectureId(scheduleTm.getScheduleId());
            cmbLectureId.setValue(id);
            String name = LectureModel.getLectureName(id);
            lblLectureName.setText(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void cmbLectureIdOnAction(ActionEvent event) {
        String id = cmbLectureId.getValue();
        try {
            String name = LectureModel.getLectureName(id);
            lblLectureName.setText(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbWeekDayOnAction(ActionEvent actionEvent) {

    }

    private void refresh() {
        getAllSchedule();
        getLectureIds();
        courseIds();
        setWeekDays();
        getAllClassroomIds();
        getNextScheduleId();

    }

}
