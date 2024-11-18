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
    @FXML
    private Button btnReset;

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
                showAlert(Alert.AlertType.INFORMATION,"Delete...!","successfully Delete..!");
                refresh();
            }else{
                showAlert(Alert.AlertType.ERROR,"Delete...!","unsuccessfully Delete..!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String scheduleId = lblScheduleIdShow.getText();
        String courseId = cmbCourseId.getValue();
        //System.out.println(courseId);
        String classroomId = cmbClassroomId.getValue();
        String sTime = txtStartTime.getText();
        String eTime = txtEndTime.getText();
        String weekday = cmbWeekDay.getValue();


        if (cmbCourseId.getValue().isEmpty()&&cmbClassroomId.getValue().isEmpty()&&txtStartTime.getText().isEmpty()&&txtEndTime.getText().isEmpty()&&cmbWeekDay.getValue().isEmpty()&&datePicker.getEditor().getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Text feild are empty...!","Fill all text field...!");
            return;
        }
        Date date = Date.valueOf(datePicker.getValue());
        validateDatePicker();

        txtStartTime.setStyle(txtStartTime.getStyle() + ";-fx-border-color: #7367F0;");
        txtEndTime.setStyle(txtEndTime.getStyle() + ";-fx-border-color: #7367F0;");

        String startTimePattern = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";
        String endTimePattern = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";

        boolean isValidStart = sTime.matches(startTimePattern);
        boolean isValidEnd = eTime.matches(endTimePattern);

        if (!isValidStart) {
            txtStartTime.setStyle(txtStartTime.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input start time is invalid...!");
            showAlert(Alert.AlertType.ERROR,"Invalid time", "Please enter a valid time..");
        }
        if (!isValidEnd) {
            txtEndTime.setStyle(txtEndTime.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input end time is invalid....!");
            showAlert(Alert.AlertType.ERROR,"Invalid time", "Please enter a valid time..");
            return;
        }
        if (isValidStart&&isValidEnd){
            String lectureId = cmbLectureId.getValue();
            LectureManageDto lectureManageDto = new LectureManageDto(id, lectureId, classroomId, scheduleId);
            SchedulDto scheduleDto = new SchedulDto(scheduleId, courseId, classroomId, sTime, eTime, weekday, date, lectureManageDto);

            try {
                boolean isAdd = scheduleModel.addSchedule(scheduleDto);
                if (isAdd) {
                    showAlert(Alert.AlertType.INFORMATION,"Add...!","successfully Add..!");
                    refresh();
                } else {
                    showAlert(Alert.AlertType.ERROR,"Add...!","unsuccessfully Add..!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }
    public void validateDatePicker() {
        if (datePicker.getValue() == null || datePicker.getEditor().getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR,"Invalid Date", "Please select a valid date.");
            datePicker.requestFocus();
        } else {
            System.out.println("Date selected: " + datePicker.getValue());
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
        if (cmbCourseId.getValue().isEmpty()&&cmbClassroomId.getValue().isEmpty()&&txtStartTime.getText().isEmpty()&&txtEndTime.getText().isEmpty()&&cmbWeekDay.getValue().isEmpty()&&datePicker.getEditor().getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Text feild are empty...!","Fill all text field...!");
            return;
        }
        Date date1 = Date.valueOf(datePicker.getValue());
        validateDatePicker();

        txtStartTime.setStyle(txtStartTime.getStyle() + ";-fx-border-color: #7367F0;");
        txtEndTime.setStyle(txtEndTime.getStyle() + ";-fx-border-color: #7367F0;");

        String startTimePattern = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";
        String endTimePattern = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$";

        boolean isValidStart = sTime.matches(startTimePattern);
        boolean isValidEnd = eTime.matches(endTimePattern);

        if (!isValidStart) {
            txtStartTime.setStyle(txtStartTime.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input start time is invalid...!");
            showAlert(Alert.AlertType.ERROR,"Invalid time", "Please enter a valid time..");
        }
        if (!isValidEnd) {
            txtEndTime.setStyle(txtEndTime.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input end time is invalid....!");
            showAlert(Alert.AlertType.ERROR,"Invalid time", "Please enter a valid time..");
            return;
        }
        if (isValidStart&&isValidEnd){
            String lectureId = cmbLectureId.getValue();
            LectureManageDto lectureManageDto = new LectureManageDto(lectureManageId, lectureId, classroomId, scheduleId);
            SchedulDto scheduleDto = new SchedulDto(scheduleId, courseId, classroomId, sTime, eTime, weekday, date, lectureManageDto);

            try {
                boolean isUpdate = scheduleModel.updateSchedule(scheduleDto);
                if (isUpdate) {
                    showAlert(Alert.AlertType.INFORMATION,"Update...!","successfully Update..!");
                    refresh();
                } else {
                    showAlert(Alert.AlertType.ERROR,"Update...!","unsuccessfully Update..!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void tbleScheduleOnCliked(MouseEvent event) {
        ScheduleTm scheduleTm = tbleSchedule.getSelectionModel().getSelectedItem();
        System.out.println(scheduleTm);
        if (scheduleTm == null){
            showAlert(Alert.AlertType.ERROR,"Wrong row","You cliked wrong row....!");
            return;
        }
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
        btnAdd.setDisable(true);
        btnUpdate.setDisable(false);
        DeleteSchedule.setDisable(false);

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
    @FXML
    void btnResetOnAction(ActionEvent event) {
        refresh();
    }

    private void refresh() {
        getNextLectureId();
        getAllSchedule();
        getLectureIds();
        courseIds();
        setWeekDays();
        getAllClassroomIds();
        getNextScheduleId();

        cmbCourseId.setValue("");
        cmbClassroomId.setValue("");
        txtStartTime.setText("");
        txtEndTime.setText("");
        cmbWeekDay.setValue("");
        cmbLectureId.setValue("");
        datePicker.setValue(null);

        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        DeleteSchedule.setDisable(true);
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
