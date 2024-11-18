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
    private Button btnResetExam;

    @FXML
    private Button btnResetGrade;



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
                showAlert(Alert.AlertType.INFORMATION,"Exam Delete", "successfully Delete..");
                refresh();
            }else{
                showAlert(Alert.AlertType.ERROR,"Exam Delete", "unsuccessfully Delete..");
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

        if (cmbSubjectId.getValue().isEmpty() &&  txtDescription.getText().isEmpty() && datePicker.getEditor().getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Text Exam feild are empty...!","Fill all text field...!");
            return;
        }
        Date date = Date.valueOf(datePicker.getValue());
        validateDatePicker();

        txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: #7367F0;");

        String descriptionPattern = "^[A-Za-z ]+$";

        boolean isValidDescription = desc.matches(descriptionPattern);

        if (!isValidDescription) {
            txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input Description is invalid...!");
            showAlert(Alert.AlertType.ERROR,"Invalid Description", "Please enter a valid Description..");
            return;
        }
        if (isValidDescription){
            ExamDto examDto = new ExamDto(examId,subId,desc,date);

            try {
                boolean isSaved = examModel.saveExam(examDto);
                if (isSaved){
                    showAlert(Alert.AlertType.INFORMATION,"Exam Save", "successfully Save..");
                    refresh();
                }else {
                    showAlert(Alert.AlertType.ERROR,"Exam Save", "unsuccessfully Save..");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String examId = lblExamIdshow.getText();
        String subId = cmbSubjectId.getValue();
        String desc = txtDescription.getText();

        if (cmbSubjectId.getValue().isEmpty() &&  txtDescription.getText().isEmpty() && datePicker.getEditor().getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Text Exam feild are empty...!","Fill all text field...!");
            return;
        }
        Date date = Date.valueOf(datePicker.getValue());
        validateDatePicker();

        txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: #7367F0;");

        String descriptionPattern = "^[A-Za-z ]+$";

        boolean isValidDescription = desc.matches(descriptionPattern);

        if (!isValidDescription) {
            txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input Description is invalid...!");
            showAlert(Alert.AlertType.ERROR,"Invalid Description", "Please enter a valid Description..");
            return;
        }
        if (isValidDescription){
            ExamDto examDto = new ExamDto(examId,subId,desc,date);
            System.out.println(examDto.toString());

            try {
                boolean isUpdate = examModel.updateExam(examDto);
                if (isUpdate){
                    showAlert(Alert.AlertType.INFORMATION,"Exam Update", "successfully Update..");
                    refresh();
                }else {
                    showAlert(Alert.AlertType.ERROR,"Exam Update", "unsuccessfully Update..");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
    @FXML
    void btnGradeDeleteOnAction(ActionEvent event) {
        String id = lblGradeId.getText();
        try {
            boolean isDelete = gradeModel.deleteGrade(id);
            if (isDelete){
                showAlert(Alert.AlertType.INFORMATION,"Grade Delete", "successfully Delete..");
                gradeRefesh();
            }else{
                showAlert(Alert.AlertType.INFORMATION,"Grade Delete", "unsuccessfully Delete..");
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

        if (cmbExamId.getValue().isEmpty()&&cmbGrade.getValue().isEmpty()&&txtExamDescription.getText().isEmpty()&&cmbStudentId.getValue().isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Text Exam feild are empty...!","Fill all text field...!");
            return;
        }
        txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: #7367F0;");

        String descriptionPattern = "^[A-Za-z ]+$";

        boolean isValidDescription = desc.matches(descriptionPattern);

        if (!isValidDescription) {
            txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input Description is invalid...!");
            showAlert(Alert.AlertType.ERROR,"Invalid Description", "Please enter a valid Description..");
            return;
        }
        if (isValidDescription){
            GradeDto gradeDto = new GradeDto(gradeId,examId,grade,desc,studentId);
            System.out.println("Controller obj +"+ gradeDto.toString());

            try {
                boolean isSaved = gradeModel.saveGrade(gradeDto);
                if (isSaved){
                    showAlert(Alert.AlertType.INFORMATION,"Grade Save", "successfully Save..");
                    gradeRefesh();
                }else{
                    showAlert(Alert.AlertType.ERROR,"Grade Save", "unsuccessfully Save..");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    void btnGradeUpdateOnAction(ActionEvent event) {
        String gradeId = lblGradeId.getText();
        String examId = cmbExamId.getValue();
        String grade = cmbGrade.getValue();
        String desc = txtExamDescription.getText();
        String studentId = cmbStudentId.getValue();

        if (cmbExamId.getValue().isEmpty()&&cmbGrade.getValue().isEmpty()&&txtExamDescription.getText().isEmpty()&&cmbStudentId.getValue().isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Text Exam feild are empty...!","Fill all text field...!");
            return;
        }
        txtExamDescription.setStyle(txtExamDescription.getStyle() + ";-fx-border-color: #7367F0;");

        String descriptionPattern = "^[A-Za-z ]+$";

        boolean isValidDescription = desc.matches(descriptionPattern);

        if (!isValidDescription) {
            txtExamDescription.setStyle(txtExamDescription.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input Description is invalid...!");
            showAlert(Alert.AlertType.ERROR,"Invalid Description", "Please enter a valid Description..");
            return;
        }

        if (isValidDescription){
            GradeDto gradeDto = new GradeDto(gradeId,examId,grade,desc,studentId);
            System.out.println("Controller obj +"+ gradeDto.toString());

            try {
                boolean isUpdate = gradeModel.updaetGrade(gradeDto);
                if (isUpdate){
                    showAlert(Alert.AlertType.INFORMATION,"Grade Update", "successfully Update..");
                    gradeRefesh();
                }else{
                    showAlert(Alert.AlertType.ERROR,"Grade Update", "unsuccessfully Update..");
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
        if (examTm == null){
            showAlert(Alert.AlertType.ERROR,"Wrong row","You cliked wrong row....!");
            return;
        }
        lblExamIdshow.setText(examTm.getExamId());
        cmbSubjectId.setValue(examTm.getSubjectId());
        txtDescription.setText(examTm.getDesc());
        datePicker.setValue(examTm.getDate().toLocalDate());
        btnExamSave.setDisable(true);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }

    @FXML
    void tblGradeOnCliked(MouseEvent event) {
        GradeTm gradeTm = tblGrade.getSelectionModel().getSelectedItem();
        if (gradeTm == null){
            showAlert(Alert.AlertType.ERROR,"Wrong row","You cliked wrong row....!");
            return;
        }
        lblGradeId.setText(gradeTm.getGradeId());
        cmbExamId.setValue(gradeTm.getExamId());
        cmbGrade.setValue(gradeTm.getGrade());
        txtExamDescription.setText(gradeTm.getDesc());
        cmbStudentId.setValue(gradeTm.getStudentId());
        btnGradeSave.setDisable(true);
        btnGradeDelete.setDisable(false);
        btnGradeUpdate.setDisable(false);
    }
    private void refresh(){
        getAllExams();
        getNextExamId();
        getSubjectIds();
        getAllExamIds();
        getAllStudentIds();
        cmbSubjectId.setValue("");
        txtDescription.setText("");
        btnExamSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
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
        btnGradeSave.setDisable(false);
        btnGradeDelete.setDisable(true);
        btnGradeUpdate.setDisable(true);
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void btnResetExamOnAction(ActionEvent event) {
        refresh();
    }

    @FXML
    void btnResetGradeOnAction(ActionEvent event) {
        gradeRefesh();
    }
}
