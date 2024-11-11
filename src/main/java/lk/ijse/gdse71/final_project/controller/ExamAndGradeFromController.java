package lk.ijse.gdse71.final_project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.final_project.dto.ExamDto;
import lk.ijse.gdse71.final_project.dto.GradeDto;
import lk.ijse.gdse71.final_project.dto.tm.ExamTm;
import lk.ijse.gdse71.final_project.dto.tm.GradeTm;
import lk.ijse.gdse71.final_project.model.ExamModel;
import lk.ijse.gdse71.final_project.model.GradeModel;
import lk.ijse.gdse71.final_project.model.StudentModel;
import lk.ijse.gdse71.final_project.model.SubjectModel;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ExamAndGradeFromController implements Initializable {

    public Label lblExamIdshow;
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnExamSave;

    @FXML
    private Button btnGradeDelete;

    @FXML
    private Button btnGradeSave;

    @FXML
    private Button btnGradeUpdate;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbExamId;

    @FXML
    private ComboBox<String> cmbGrade;

    @FXML
    private ComboBox<String> cmbStudentId;

    @FXML
    private ComboBox<String> cmbSubjectId;

    @FXML
    private TableColumn<ExamTm, Date> colDate;

    @FXML
    private TableColumn<ExamTm, String> colDescription;

    @FXML
    private TableColumn<ExamTm, String> colExamId;

    @FXML
    private TableColumn<GradeTm, String> colGradExamId;

    @FXML
    private TableColumn<GradeTm, String> colGradId;

    @FXML
    private TableColumn<GradeTm, String> colGrade;

    @FXML
    private TableColumn<GradeTm, String> colGradeDesciption;

    @FXML
    private TableColumn<GradeTm, String> colStudentId;

    @FXML
    private TableColumn<ExamTm, String> colSubjectId;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblExamId;

    @FXML
    private Label lblExamiIDShow;

    @FXML
    private Label lblGrade;

    @FXML
    private Label lblGradeId;

    @FXML
    private Label lblStudentId;

    @FXML
    private Label lblStudentNamShow;

    @FXML
    private Label lblSubjectId;

    @FXML
    private TableView<ExamTm> tblExam;

    @FXML
    private TableView<GradeTm> tblGrade;

    @FXML
    private TextField txtDescription;

    @FXML
    private Label lblDateShow;



    @FXML
    private TextField txtExamDescription;

    private ExamModel examModel = new ExamModel();
    private GradeModel gradeModel = new GradeModel();
    private SubjectModel subjectModel = new SubjectModel();
    private StudentModel studentModel = new StudentModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colExamId.setCellValueFactory(new PropertyValueFactory<>("examId"));
        colSubjectId.setCellValueFactory(new PropertyValueFactory<>("subjectId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        colGradExamId.setCellValueFactory(new PropertyValueFactory<>("examId"));
        colGradId.setCellValueFactory(new PropertyValueFactory<>("gradeId"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        colGradeDesciption.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));


        refresh();
        gradeRefesh();
    }
    private void getAllGrades(){
        try {
            ArrayList<GradeDto> gradeDtos = gradeModel.getAllGrads();
            ObservableList<GradeTm> gradeTms = FXCollections.observableArrayList();

            for (GradeDto gradeDto : gradeDtos){
                GradeTm gradeTm = new GradeTm(
                        gradeDto.getGradeId(),
                        gradeDto.getExamId(),
                        gradeDto.getGrade(),
                        gradeDto.getDesc(),
                        gradeDto.getStudentId()
                );
                gradeTms.add(gradeTm);
            }
            tblGrade.setItems(gradeTms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getAllExams(){
        try {
            ArrayList<ExamDto> examDtos = examModel.getAllExams();
            ObservableList<ExamTm> examTms = FXCollections.observableArrayList();

            for (ExamDto examDto : examDtos){
                ExamTm examTm = new ExamTm(
                        examDto.getExamId(),
                        examDto.getSubjectId(),
                        examDto.getDesc(),
                        examDto.getDate()
                );
                examTms.add(examTm);
            }
            tblExam.setItems(examTms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getNextExamId(){
        try {
            String id = examModel.getNextExamId();
            lblExamIdshow.setText(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getNextGradeId(){
        try {
            String id = gradeModel.getNextGradeId();
            lblGradeId.setText(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void getSubjectIds(){
        try {
            ObservableList<String> allSubjectIds = subjectModel.getAllSubjectIds();
            cmbSubjectId.setItems(allSubjectIds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void getAllExamIds(){
        try {
            ObservableList<String> examIds = examModel.getAllExamIds();
            cmbExamId.setItems(examIds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getAllGradeIds(){
       String []grade = {"A","B","C","S","Fail"};
       ObservableList<String> grades = FXCollections.observableArrayList();
       grades.addAll(grade);
       cmbGrade.setItems(grades);
    }
    private void getAllStudentIds(){
        try {
            ObservableList<String> allStudentId = studentModel.getAllStudentId();
            cmbStudentId.setItems(allStudentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = lblExamIdshow.getText();

        try {
            boolean isDelete = examModel.deleteExam(id);
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Exam is delete....!").showAndWait();
                refresh();
            }else{
                new Alert(Alert.AlertType.ERROR,"Exam is not delete....!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnExamSaveOnAction(ActionEvent event) {
        String examId = lblExamIdshow.getText();
        String subId = cmbSubjectId.getValue();
        String desc = txtDescription.getText();
        Date date = Date.valueOf(datePicker.getValue());

        ExamDto examDto = new ExamDto(examId,subId,desc,date);

        try {
            boolean isSaved = examModel.saveExam(examDto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Exam is saved....!").showAndWait();
                refresh();
            }else {
                new Alert(Alert.AlertType.ERROR,"Exam is not saved....!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String examId = lblExamIdshow.getText();
        String subId = cmbSubjectId.getValue();
        String desc = txtDescription.getText();
        Date date = Date.valueOf(datePicker.getValue());

        ExamDto examDto = new ExamDto(examId,subId,desc,date);
        System.out.println(examDto.toString());

        try {
            boolean isUpdate = examModel.updateExam(examDto);
            if (isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Exam is update....!").showAndWait();
                refresh();
            }else {
                new Alert(Alert.AlertType.ERROR,"Exam is not update....!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnGradeDeleteOnAction(ActionEvent event) {
        String id = lblGradeId.getText();
        try {
            boolean isDelete = gradeModel.deleteGrade(id);
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Exam is Delete....!").showAndWait();
                gradeRefesh();
            }else{
                new Alert(Alert.AlertType.ERROR,"Exam is not Delete....!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnGradeSaveOnAction(ActionEvent event) {
        String gradeId = lblGradeId.getText();
        String examId = cmbExamId.getValue();
        String grade = cmbGrade.getValue();
        String desc = txtExamDescription.getText();
        String studentId = cmbStudentId.getValue();

        GradeDto gradeDto = new GradeDto(gradeId,examId,grade,desc,studentId);
        System.out.println("Controller obj +"+ gradeDto.toString());

        try {
            boolean isSaved = gradeModel.saveGrade(gradeDto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Grade is saved......!").showAndWait();
                gradeRefesh();
            }else{
                new Alert(Alert.AlertType.ERROR,"Grade is not saved......!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnGradeUpdateOnAction(ActionEvent event) {
        String gradeId = lblGradeId.getText();
        String examId = cmbExamId.getValue();
        String grade = cmbGrade.getValue();
        String desc = txtExamDescription.getText();
        String studentId = cmbStudentId.getValue();

        GradeDto gradeDto = new GradeDto(gradeId,examId,grade,desc,studentId);
        System.out.println("Controller obj +"+ gradeDto.toString());

        try {
            boolean isUpdate = gradeModel.updaetGrade(gradeDto);
            if (isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Grade is update......!").showAndWait();
                gradeRefesh();
            }else{
                new Alert(Alert.AlertType.ERROR,"Grade is not update......!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void cmbStudentIdOnAction(ActionEvent event) {
        String id = cmbStudentId.getValue();
        String studentName = null;
        try {
            studentName = studentModel.getStudentName(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        lblStudentNamShow.setText(studentName);


    }
    @FXML
    void datePickerOnAction(ActionEvent event) {
        Date date = Date.valueOf(datePicker.getValue());
        lblDateShow.setText(String.valueOf(date));

    }
    @FXML
    void tblExamOnCliked(MouseEvent event) {
        ExamTm examTm = tblExam.getSelectionModel().getSelectedItem();
        lblExamIdshow.setText(examTm.getExamId());
        cmbSubjectId.setValue(examTm.getSubjectId());
        txtDescription.setText(examTm.getDesc());
        datePicker.setValue(examTm.getDate().toLocalDate());
    }

    @FXML
    void tblGradeOnCliked(MouseEvent event) {
        GradeTm gradeTm = tblGrade.getSelectionModel().getSelectedItem();
        lblGradeId.setText(gradeTm.getGradeId());
        cmbExamId.setValue(gradeTm.getExamId());
        cmbGrade.setValue(gradeTm.getGrade());
        txtExamDescription.setText(gradeTm.getDesc());
        cmbStudentId.setValue(gradeTm.getStudentId());
    }
    private void refresh(){
        getAllExams();
        getNextExamId();
        getSubjectIds();
        getAllExamIds();
        getAllStudentIds();
        cmbSubjectId.setValue("");
        txtDescription.setText("");
    }
    private void gradeRefesh(){
        getAllGrades();
        getNextGradeId();
        getAllGradeIds();
        cmbExamId.setValue("");
        cmbGrade.setValue("");
        txtExamDescription.setText("");
        cmbStudentId.setValue("");
        lblStudentNamShow.setText("");
    }
}
