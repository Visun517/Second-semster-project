package lk.ijse.gdse71.final_project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    private Label lblStudentNameShow;

    @FXML
    private Label lblStudentName;

    @FXML
    private Button btnReset;

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

        refresh();
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

    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id =tblattendence.getSelectionModel().getSelectedItem().getAttendenceId() ;

        try {
            boolean isDelete = attendenceModel.attendenceDelete(id);
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Attendence is Delete.........!").showAndWait();
                refresh();
            }else{
                new Alert(Alert.AlertType.ERROR,"Attendence is not Delete.........!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String attendenceId = lblAttendenceIdShow.getText();
        String schedulId = cmbSchedulId.getValue();
        String studentId = cbmStudentId.getValue();
        String remark = cmbRemark.getValue();


        if (cmbSchedulId.getSelectionModel().getSelectedItem().isEmpty()) {
            showAlert("Selection Required", "Please select a value from the ComboBox.");
            cmbSchedulId.requestFocus(); // Focus on the ComboBox
            return;
        } else {
            System.out.println("Selected Value: " + cmbSchedulId.getValue());
        }

        if (cbmStudentId.getSelectionModel().getSelectedItem().isEmpty()) {
            showAlert("Selection Required", "Please select a value from the ComboBox.");
            cbmStudentId.requestFocus(); // Focus on the ComboBox
            return;
        } else {
            System.out.println("Selected Value: " + cbmStudentId.getValue());
        }

        if (cmbRemark.getSelectionModel().getSelectedItem().isEmpty()) {
            showAlert("Selection Required", "Please select a value from the ComboBox.");
            cmbRemark.requestFocus(); // Focus on the ComboBox
            return;
        } else {
            System.out.println("Selected Value: " + cmbRemark.getValue());
        }

        Date date = Date.valueOf(lblDateshow.getText());
        AttendenceDto attendenceDto = new AttendenceDto(attendenceId,schedulId,studentId,date,remark);
        try {
            boolean isSaved = attendenceModel.attendenceSave(attendenceDto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Attendence save successfully......!").showAndWait();
                refresh();
            }else{
                new Alert(Alert.AlertType.ERROR,"Attendence save not successfully......!").showAndWait();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String attendenceId = lblAttendenceIdShow.getText();
        String schedulId = cmbSchedulId.getValue();
        String studentId = cbmStudentId.getValue();
        String remark = cmbRemark.getValue();

        if (cmbSchedulId.getSelectionModel().getSelectedItem().isEmpty()) {
            showAlert("Selection Required", "Please select a value from the ComboBox.");
            cmbSchedulId.requestFocus();
            return;
        } else {
            System.out.println("Selected Value: " + cmbSchedulId.getValue());
        }

        if (cbmStudentId.getSelectionModel().getSelectedItem().isEmpty()) {
            showAlert("Selection Required", "Please select a value from the ComboBox.");
            cbmStudentId.requestFocus(); // Focus on the ComboBox
            return;
        } else {
            System.out.println("Selected Value: " + cbmStudentId.getValue());
        }

        if (cmbRemark.getSelectionModel().getSelectedItem().isEmpty()) {
            showAlert("Selection Required", "Please select a value from the ComboBox.");
            cmbRemark.requestFocus(); // Focus on the ComboBox
            return;
        } else {
            System.out.println("Selected Value: " + cmbRemark.getValue());
        }

        Date date = Date.valueOf(lblDateshow.getText());
        AttendenceDto attendenceDto = new AttendenceDto(attendenceId,schedulId,studentId,date,remark);
        try {
            boolean isUpdate = attendenceModel.attendenceUpdate(attendenceDto);
            if (isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Attendence update successfully......!").showAndWait();
                refresh();
            }else{
                new Alert(Alert.AlertType.ERROR,"Attendence update not successfully......!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cbmStudentIdOnAction(ActionEvent event) {
        String id = cbmStudentId.getSelectionModel().getSelectedItem();
        try {
            String name = studentModel.getStudentName(id);
            lblStudentNameShow.setText(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbRemarkOnAction(ActionEvent event) {

    }

    @FXML
    void cmbSchedulIdOnAction(ActionEvent event) {
        String id = cmbSchedulId.getSelectionModel().getSelectedItem();
        try {
            Date date = shedulModel.getDate(id);
            lblDateshow.setText(String.valueOf(date));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void tblattendenceOnCliked(MouseEvent event) {
        AttendenceTm attendenceTm = tblattendence.getSelectionModel().getSelectedItem();
        if (attendenceTm == null){
            showAlert("Wrong row","You cliked wrong row....!");
            return;
        }
        lblAttendenceIdShow.setText(attendenceTm.getAttendenceId());
        cmbSchedulId.setValue(attendenceTm.getSchedulId());
        lblDateshow.setText(String.valueOf(attendenceTm.getClassDate()));
        cbmStudentId.setValue(attendenceTm.getStudentId());
        String id = attendenceTm.getStudentId();
        try {
            String name = studentModel.getStudentName(id);
            lblStudentNameShow.setText(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        cmbRemark.setValue(attendenceTm.getRemark());

        btnSave.setDisable(true);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }
    public void refresh(){
        getAllAttendence();
        getNextAttendenceId();
        getSchedulIds();
        getAllStudentIds();
        reamrk();

        cmbSchedulId.setValue("");
        lblDateshow.setText("");
        cbmStudentId.setValue("");
        lblStudentNameShow.setText("");
        cmbRemark.setValue("");

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }
    @FXML
    void btnResetOnAction(ActionEvent event) {
        refresh();
    }
    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
