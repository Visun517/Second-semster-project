package lk.ijse.gdse71.final_project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.gdse71.final_project.dto.AttendenceDto;
import lk.ijse.gdse71.final_project.dto.tm.AttendenceTm;
import lk.ijse.gdse71.final_project.model.AttendenceModel;
import lk.ijse.gdse71.final_project.model.ShedulModel;
import lk.ijse.gdse71.final_project.model.StudentModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AttendenceFromViewController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cbmStudentId;

    @FXML
    private ComboBox<String> cmbRemark;

    @FXML
    private ComboBox<String> cmbSchedulId;

    @FXML
    private TableColumn<AttendenceTm, String> colAttendenceId;

    @FXML
    private TableColumn<AttendenceTm, Date> colClassDate;

    @FXML
    private TableColumn<AttendenceTm, String> colRemark;

    @FXML
    private TableColumn<AttendenceTm, String> colSchedulId;

    @FXML
    private TableColumn<AttendenceTm, String> colStudentId;

    @FXML
    private Label lblAttendenceId;

    @FXML
    private Label lblAttendenceIdShow;

    @FXML
    private Label lblClassDate;

    @FXML
    private Label lblDateshow;

    @FXML
    private Label lblRemark;

    @FXML
    private Label lblSchedulId;

    @FXML
    private Label lblStudentId;

    @FXML
    private TableView<AttendenceTm> tblattendence;

    private AttendenceModel attendenceModel = new AttendenceModel();
    private ShedulModel shedulModel = new ShedulModel();
    private StudentModel studentModel = new StudentModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colAttendenceId.setCellValueFactory(new PropertyValueFactory<>("attendenceId"));
        colSchedulId.setCellValueFactory(new PropertyValueFactory<>("schedulId"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colClassDate.setCellValueFactory(new PropertyValueFactory<>("classDate"));
        colRemark.setCellValueFactory(new PropertyValueFactory<>("remark"));

        getAllAttendence();
        getNextAttendenceId();
        getSchedulIds();
        getAllStudentIds();
        reamrk();
    }
    public void getAllAttendence(){
        try {
            ArrayList<AttendenceDto> attendenceDtos = attendenceModel.getAllAttendence();
            ObservableList<AttendenceTm> attendenceTms = FXCollections.observableArrayList();

            for (AttendenceDto attendenceDto : attendenceDtos ){
                AttendenceTm attendenceTm = new AttendenceTm(
                        attendenceDto.getAttendenceId(),
                        attendenceDto.getSchedulId(),
                        attendenceDto.getStudentId(),
                        attendenceDto.getClassDate(),
                        attendenceDto.getRemark()
                );
                attendenceTms.add(attendenceTm);
            }
            tblattendence.setItems(attendenceTms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void getNextAttendenceId(){
        try {
            String id = attendenceModel.getNextAttendenceId();
            lblAttendenceIdShow.setText(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void getSchedulIds(){
        try {
            ObservableList<String> studentIds =  shedulModel.getSchedulIds();
            cmbSchedulId.setItems(studentIds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void getAllStudentIds(){
        try {
            ObservableList<String> allStudentId = studentModel.getAllStudentId();
            cbmStudentId.setItems(allStudentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void reamrk(){
        String[] remark = {"Present","Absent","Late"};
        ObservableList<String> remarks = FXCollections.observableArrayList();
        remarks.addAll(remark);
        cmbRemark.setItems(remarks);

        //Ithuru button tika implement karnna
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void cbmStudentIdOnAction(ActionEvent event) {

    }

    @FXML
    void cmbRemarkOnAction(ActionEvent event) {

    }

    @FXML
    void cmbSchedulIdOnAction(ActionEvent event) {

    }


}
