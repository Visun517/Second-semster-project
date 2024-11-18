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
import javafx.stage.Modality;
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
    private ComboBox<String> cmbGender;

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
        refresh();
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
        String gender = cmbGender.getValue();

        if (txtName.getText().isEmpty() && txtAddress.getText().isEmpty() && txtEmail.getText().isEmpty() && cmbGender.getValue().isEmpty()){
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
            txtAddress.setStyle(txtAddress.getStyle()+";-fx-border-color: red;");
            showAlert(Alert.AlertType.ERROR,"Text feild are empty...!","Fill all text field...!");
            return;
        }

        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);

        if (!isValidName){
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input name is not valid....!");
            showAlert(Alert.AlertType.ERROR,"Incorrect name","Input valid name....!");
        }
        if (!isValidEmail){
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input name is not valid....!");
            showAlert(Alert.AlertType.ERROR,"Incorrect email","Input valid email....!");
        }

        if (isValidName && isValidEmail){
            boolean isSaved =  studentModel.saveStudent(new StudentDto(id,name,email,address,gender));

            if (isSaved){
                getAllStrudent();
                showAlert(Alert.AlertType.INFORMATION,"saved","Student is saved..!");
            }else{
                showAlert(Alert.AlertType.ERROR,"Not saved..","Student is not saved..!");
            }

        }

    }

    @FXML
    void btnUpadateOnAction(ActionEvent event) throws SQLException {
        String id = lblStudentIdShow.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();
        String gender = cmbGender.getValue();

        if (txtName.getText().isEmpty() && txtAddress.getText().isEmpty() && txtEmail.getText().isEmpty()){
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
            txtAddress.setStyle(txtAddress.getStyle()+";-fx-border-color: red;");
            showAlert(Alert.AlertType.ERROR,"Text feild are empty...!","Fill all text field...!");
            return;
        }
        if (cmbGender.getSelectionModel().getSelectedItem().isEmpty()) {
            showAlert(Alert.AlertType.ERROR,"Selection Required", "Please select a value from the ComboBox.");
            cmbGender.requestFocus(); // Focus on the ComboBox
            return;
        } else {
            System.out.println("Selected Value: " + cmbGender.getValue());
        }
        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);

        if (!isValidName){
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input name is not valid....!");
            showAlert(Alert.AlertType.ERROR,"Incorrect name","Input valid name....!");
        }
        if (!isValidEmail){
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input name is not valid....!");
            showAlert(Alert.AlertType.ERROR,"Incorrect email","Input valid email....!");
        }
        if (isValidName && isValidEmail){
            StudentDto studentDto = new StudentDto(id,name,email,address,gender);
            System.out.println("controller"+ studentDto.toString());

            boolean isSaved =  studentModel.studentUpdate(studentDto);

            if (isSaved){
                getAllStrudent();
                showAlert(Alert.AlertType.INFORMATION,"update","Student is update..!");
            }else{
                showAlert(Alert.AlertType.ERROR,"Not update..","Student is not update..!");
            }

        }
    }
    private void setGender(){
        String [] gender = {"Female","Male"};
        ObservableList<String> genders = FXCollections.observableArrayList();
        genders.addAll(gender);
        cmbGender.setItems(genders);
    }
    @FXML
    void tbnDeleteOnAction(ActionEvent event) {
        String id = lblStudentIdShow.getText();

        try {
            boolean isDelete = studentModel.studentDelete(id);

            if (isDelete){
                getAllStrudent();
                showAlert(Alert.AlertType.INFORMATION,"Delete","Student is Delete..!");
            }else {
                showAlert(Alert.AlertType.ERROR,"Not Delete","Student not delete....!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void SearchOnAction(MouseEvent event) {
        StudentTm studentTm = tblStudent.getSelectionModel().getSelectedItem();
        if (studentTm == null){
            showAlert(Alert.AlertType.ERROR,"Wrong row","You cliked wrong row....!");
            return;
        }
        if (studentTm != null){
            lblStudentIdShow.setText(studentTm.getStudent_id());
            txtName.setText(studentTm.getName());
            txtEmail.setText(studentTm.getEmail());
            txtAddress.setText(studentTm.getAddress());
            cmbGender.setValue(studentTm.getGender());
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
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
    }
    private void refresh(){
        try {
            getAllStrudent();
            getNextStudentId();
            setGender();
            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);
            txtName.setText("");
            txtAddress.setText("");
            txtEmail.setText("");
            cmbGender.setValue("");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void resetOnAction(ActionEvent event) {
        refresh();
    }

}
