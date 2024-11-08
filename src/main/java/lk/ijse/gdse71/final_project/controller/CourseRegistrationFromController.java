package lk.ijse.gdse71.final_project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.final_project.dto.RegistrationDto;
import lk.ijse.gdse71.final_project.dto.tm.RegistrationTm;
import lk.ijse.gdse71.final_project.dto.tm.StudentTm;
import lk.ijse.gdse71.final_project.model.CourseModel;
import lk.ijse.gdse71.final_project.model.RegistrationModel;
import lk.ijse.gdse71.final_project.model.StudentModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CourseRegistrationFromController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRegistration;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbCourseId;

    @FXML
    private ComboBox<String> cmbStudentId;

    @FXML
    private TableColumn<RegistrationTm, String> colCourseId;

    @FXML
    private TableColumn<RegistrationTm, Date> colRegistrationDate;

    @FXML
    private TableColumn<RegistrationTm, String> colRegistrationId;

    @FXML
    private TableColumn<RegistrationTm, String> colStudentId;

    @FXML
    private Label lblCourseId;

    @FXML
    private Label lblCourseName;

    @FXML
    private Label lblCourseNameShow;

    @FXML
    private Label lblDateShow;

    @FXML
    private Label lblRegiestrationId;

    @FXML
    private Label lblRegistrationIdShow;

    @FXML
    private Label lblStudentId;

    @FXML
    private Label lblStudentName;

    @FXML
    private Label lblStudentNameShow;

    @FXML
    private TableView<RegistrationTm> tblRegistration;

    private RegistrationModel registrationModel = new RegistrationModel();
    private StudentModel studentModel = new StudentModel();
    private CourseModel courseModel = new CourseModel();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colRegistrationId.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colRegistrationDate.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        refresh();
    }

    public void getAllRegistrattions(){
        try {
            ArrayList<RegistrationDto> registrationDtos = registrationModel.getAllRegistrations();
            ObservableList<RegistrationTm> registrationTms = FXCollections.observableArrayList();

            for (RegistrationDto registrationDto : registrationDtos ){
                RegistrationTm registrationTm = new RegistrationTm(
                        registrationDto.getRegistrationId(),
                        registrationDto.getStudentId(),
                        registrationDto.getCourseId(),
                        registrationDto.getRegistrationDate()
                );
                registrationTms.add(registrationTm);
            }
            tblRegistration.setItems(registrationTms);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAllStudentIds() {
        try {
            ObservableList<String> allStudentId = studentModel.getAllStudentId();
            cmbStudentId.setItems(allStudentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAllCourseIds(){
        try {
            ObservableList<String> courseIds  = courseModel.getAllCoursseIds();
            cmbCourseId.setItems(courseIds);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void getNextRegistrationId(){
        try {
            String id = registrationModel.getNextRegistrationId();
            lblRegistrationIdShow.setText(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = tblRegistration.getSelectionModel().getSelectedItem().getRegistrationId();

        try {
            boolean isDelete = registrationModel.delete(id);
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Successfully Delete.......!").showAndWait();
            }else {
                new Alert(Alert.AlertType.ERROR,"Successfully Delete.......!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnRegistrationOnAction(ActionEvent event) {

        String studentId = cmbStudentId.getSelectionModel().getSelectedItem();
        String courseId = cmbCourseId.getSelectionModel().getSelectedItem();
        String registrationId = lblRegistrationIdShow.getText();
        Date date = Date.valueOf(lblDateShow.getText());

        RegistrationDto registrationDto = new RegistrationDto(registrationId,studentId,courseId,date);

        try {
            boolean isSaved = registrationModel.registerStudent(registrationDto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Registration successfully...!").showAndWait();
            }else {
                new Alert(Alert.AlertType.ERROR,"Registration not successfully...!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String studentId = cmbStudentId.getSelectionModel().getSelectedItem();
        String courseId = cmbCourseId.getSelectionModel().getSelectedItem();
        String registrationId = lblRegistrationIdShow.getText();
        Date date = Date.valueOf(lblDateShow.getText());

        RegistrationDto registrationDto = new RegistrationDto(registrationId,studentId,courseId,date);

        try {
            boolean isUpdate = registrationModel.updateStudent(registrationDto);
            if (isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Update successfully...!").showAndWait();
            }else {
                new Alert(Alert.AlertType.ERROR,"Update not successfully...!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void cmbCourseId(ActionEvent event) {
        String id = cmbCourseId.getSelectionModel().getSelectedItem();
        try {
            String name = courseModel.getCourseName(id);
            lblCourseNameShow.setText(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbStudentId(ActionEvent event) {
        String id = cmbStudentId.getSelectionModel().getSelectedItem();
        try {
            String studentName = studentModel.getStudentName(id);
            lblStudentNameShow.setText(studentName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void tblRegistrationOnCliked(MouseEvent event) {
        RegistrationTm registrationTm = tblRegistration.getSelectionModel().getSelectedItem();
        cmbStudentId.setValue(registrationTm.getStudentId());
        cmbCourseId.setValue(registrationTm.getCourseId());
        lblRegistrationIdShow.setText(registrationTm.getRegistrationId());
        lblDateShow.setText(String.valueOf(registrationTm.getRegistrationDate()));
    }

    public void refresh(){
        cmbStudentId.setValue(null);
        cmbCourseId.setValue(null);
        lblRegistrationIdShow.setText("");
        lblDateShow.setText("");

        getAllRegistrattions();
        getAllStudentIds();
        getAllCourseIds();
        getNextRegistrationId();
        lblDateShow.setText(LocalDate.now().toString());
    }
}
