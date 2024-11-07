package lk.ijse.gdse71.final_project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.gdse71.final_project.dto.StudentDto;
import lk.ijse.gdse71.final_project.dto.tm.StudentTm;
import lk.ijse.gdse71.final_project.model.StudentModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentManageFromController implements Initializable {

    @FXML
    private Button btnAddNote;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<StudentTm, String> colAddress;

    @FXML
    private TableColumn<StudentTm, String> colEmail;

    @FXML
    private TableColumn<StudentTm, String> colGender;

    @FXML
    private TableColumn<StudentTm, String> colName;

    @FXML
    private TableColumn<StudentTm, String> colStudentId;

    @FXML
    private TextField lblAddNotes;

    @FXML
    private Label lblAddnotes;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblGender;

    @FXML
    private Label lblName;

    @FXML
    private Label lblStudentId;

    @FXML
    private TableColumn<StudentTm, String> lblanddNotes;

    @FXML
    private Button btnDelete;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtAddress;

    @FXML
    private Label lblStudentIdShow;

    @FXML
    private TextField txtName;

    @FXML
    private TableView<StudentTm> tblStudent;

    private StudentModel studentModel = new StudentModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("Student_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));

        try {
            getAllStrudent();
            getNextStudentId();
            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void getAllStrudent() throws SQLException {
        ArrayList<StudentDto> studentDtos = studentModel.getAllStudent();
        ObservableList<StudentTm> studentTms = FXCollections.observableArrayList();

        for (StudentDto studentDto : studentDtos) {
            StudentTm studentTm = new StudentTm(
                    studentDto.getStudent_id(),
                    studentDto.getName(),
                    studentDto.getEmail(),
                    studentDto.getAddress(),
                    studentDto.getGender()
            );
            studentTms.add(studentTm);
        }
        tblStudent.setItems(studentTms);
    }

    public void getNextStudentId() throws SQLException {
        String nextStudentId = studentModel.getNextStudentId();
        lblStudentIdShow.setText(nextStudentId);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {

        String id = lblStudentIdShow.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String gender = txtGender.getText();

        boolean isSaved =  studentModel.saveStudent(new StudentDto(id,name,address,email,gender));

        if (isSaved){
            getAllStrudent();
            new Alert(Alert.AlertType.INFORMATION,"Student is saved..!").showAndWait();
        }else{
            new Alert(Alert.AlertType.ERROR,"Student is not saved..!").showAndWait();
        }

    }

    @FXML
    void btnUpadateOnAction(ActionEvent event) throws SQLException {
        String id = lblStudentIdShow.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String gender = txtGender.getText();

        boolean isSaved =  studentModel.studentUpdate(new StudentDto(id,name,address,email,gender));

        if (isSaved){
            getAllStrudent();
            new Alert(Alert.AlertType.INFORMATION,"Student is update..!").showAndWait();
        }else{
            new Alert(Alert.AlertType.ERROR,"Student is not update..!").showAndWait();
        }

    }

    @FXML
    void resetOnAction(ActionEvent event) {

    }

    @FXML
    void tbnDeleteOnAction(ActionEvent event) {
        String id = lblStudentIdShow.getText();

        try {
            boolean isDelete = studentModel.studentDelete(id);

            if (isDelete){
                getAllStrudent();
                new Alert(Alert.AlertType.INFORMATION,"Student is Delete..!").showAndWait();
            }else {
                new Alert(Alert.AlertType.ERROR,"Student is not Delete..!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void SearchOnAction(MouseEvent event) {
        StudentTm studentTm = tblStudent.getSelectionModel().getSelectedItem();
        System.out.println(studentTm.toString());
        if (studentTm != null){
            lblStudentIdShow.setText(studentTm.getStudent_id());
            txtName.setText(studentTm.getName());
            txtAddress.setText(studentTm.getAddress());
            txtEmail.setText(studentTm.getEmail());
            txtGender.setText(studentTm.getGender());
        }
        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);

    }
    @FXML
    void btnAddNoteOnAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/AddNoteView.fxml"));
        Scene scene = new Scene(load);
        Stage stage= new Stage();
        stage.setScene(scene);
        stage.show();
    }



}
