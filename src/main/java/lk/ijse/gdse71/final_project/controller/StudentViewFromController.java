package lk.ijse.gdse71.final_project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.final_project.dto.StudentDto;
import lk.ijse.gdse71.final_project.dto.tm.StudentTm;
import lk.ijse.gdse71.final_project.model.StudentModel;
import lombok.ToString;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StudentViewFromController implements Initializable {

    @FXML
    private Button btmGenerateReport;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnMailSend;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<StudentTm, String> colEmail;

    @FXML
    private TableColumn<StudentTm, String> colGender;

    @FXML
    private TableColumn<StudentTm, String> colName;

    @FXML
    private TableColumn<StudentTm, String> colStudentId;

    @FXML
    private TableColumn<StudentTm, String> coladdress;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblGender;

    @FXML
    private Label lblName;

    @FXML
    private Label lblStudentIDShow;

    @FXML
    private Label lblStudentId;

    @FXML
    private TableView<StudentTm> tblStudent;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtName;

    private StudentModel studentModel = new StudentModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        try {
            getNextStudentId();
            loadTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btmGenerateReportOnAction(ActionEvent event) {

    }

    @FXML
    void btnMailSendOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void onClickTable(MouseEvent event) {

    }

    private void loadTable() throws SQLException {
        ArrayList<StudentDto> studentDtos = studentModel.getAllStudent();

        ObservableList<StudentTm> studentTms = FXCollections.observableArrayList();

        for (StudentDto studentDto : studentDtos){
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

    private void getNextStudentId() throws SQLException {
       String id =  studentModel.getNextStudentId();
        lblStudentIDShow.setText(id);
    }



}
