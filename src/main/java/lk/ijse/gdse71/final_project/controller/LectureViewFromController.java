package lk.ijse.gdse71.final_project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.final_project.dto.LectureDto;
import lk.ijse.gdse71.final_project.dto.tm.LectureTm;
import lk.ijse.gdse71.final_project.model.LectureModel;
import lk.ijse.gdse71.final_project.model.SubjectModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LectureViewFromController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbSubjectId;

    @FXML
    private TableColumn<LectureTm, String> colLectureId;

    @FXML
    private TableColumn<LectureTm, String> colLectureTltle;

    @FXML
    private TableColumn<LectureTm, String> colName;

    @FXML
    private TableColumn<LectureTm, String> colSubjectId;

    @FXML
    private Label lblLectureId;

    @FXML
    private Label lblLectureIdShow;

    @FXML
    private Label lblLectureTitle;

    @FXML
    private Label lblName;

    @FXML
    private Label lblSubjectId;

    @FXML
    private Label lblSubjectNameShow;

    @FXML
    private TableView<LectureTm> tblLecture;

    @FXML
    private TextField txtLectureTitle;

    @FXML
    private TextField txtName;

    @FXML
    private Button btnReset;

    private LectureModel lectureModel = new LectureModel();
    private SubjectModel subjectModel = new SubjectModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colLectureId.setCellValueFactory(new PropertyValueFactory<>("lectureId"));
        colSubjectId.setCellValueFactory(new PropertyValueFactory<>("subjectId"));
        colLectureTltle.setCellValueFactory(new PropertyValueFactory<>("lecTitle"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        refresh();
    }

    public void getAllLectures() {
        try {
            ArrayList<LectureDto> lectureDtos = lectureModel.getAllLectures();
            ObservableList<LectureTm> lectureTms = FXCollections.observableArrayList();

            for (LectureDto lectureDto : lectureDtos) {
                LectureTm lectureTm = new LectureTm(
                        lectureDto.getLectureId(),
                        lectureDto.getSubjectId(),
                        lectureDto.getLecTitle(),
                        lectureDto.getName()
                );
                lectureTms.add(lectureTm);
            }
            tblLecture.setItems(lectureTms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getNextLectureId() {
        try {
            String id = lectureModel.getNextLectureId();
            lblLectureIdShow.setText(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAllSubjectIds() {
        try {
            ObservableList<String> ids = subjectModel.getAllSubjectIds();
            cmbSubjectId.setItems(ids);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = lblLectureIdShow.getText();

        try {
            boolean isDelete = lectureModel.lectureDelete(id);
            if (isDelete){
                showAlert(Alert.AlertType.INFORMATION,"Delete..", "Successfully delete..!");
                refresh();
            }else {
                showAlert(Alert.AlertType.ERROR,"Delete..", "unsuccessfully delete..!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String lecId = lblLectureIdShow.getText();
        String subId = cmbSubjectId.getValue();
        String lecTitle = txtLectureTitle.getText();
        String name = txtName.getText();

        if (txtLectureTitle.getText().isEmpty() &&  txtName.getText().isEmpty() && cmbSubjectId.getValue().isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Text feild are empty...!","Fill all text field...!");
            return;
        }
        txtLectureTitle.setStyle(txtLectureTitle.getStyle() + ";-fx-border-color: #7367F0;");
        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");

        String lectureTitlePattern = "^[A-Za-z ]+$";
        String namePattern =  "^[A-Za-z ]+$";

        boolean isValidTitle = lecTitle.matches(lectureTitlePattern);
        boolean isValidName = name.matches(namePattern);

        if (!isValidTitle) {
            txtLectureTitle.setStyle(txtLectureTitle.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input title is invalid...!");
            showAlert(Alert.AlertType.ERROR,"Invalid title", "Please enter a valid title (only letters and spaces are allowed).");
            return;
        }
        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input name is invalid....!");
            showAlert(Alert.AlertType.ERROR,"Invalid Name", "Please enter a valid Name.");
            return;
        }
        if (isValidTitle && isValidName){
            LectureDto lectureDto = new LectureDto(lecId, subId, lecTitle, name);

            try {
                boolean isSaved = lectureModel.lectureSave(lectureDto);
                if (isSaved) {
                    showAlert(Alert.AlertType.INFORMATION,"Save", "Successfully Save..!");
                    refresh();
                } else {
                    showAlert(Alert.AlertType.ERROR,"Save", "unsuccessfully Save..!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String lecId = lblLectureIdShow.getText();
        String subId = cmbSubjectId.getValue();
        System.out.println(subId);
        String lecTitle = txtLectureTitle.getText();
        String name = txtName.getText();

        LectureDto lectureDto = new LectureDto(lecId,subId,lecTitle,name);
        System.out.println(lectureDto);

        try {
            boolean isUpdate = lectureModel.lectureUpdate(lectureDto);
            if (isUpdate){
                showAlert(Alert.AlertType.INFORMATION,"Update", "Successfully Update..!");
                refresh();
            }else{
                showAlert(Alert.AlertType.ERROR,"Update", "unsuccessfully Update..!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbSubjectIdOnAction(ActionEvent event) {
        String id = cmbSubjectId.getValue();
        try {
            String name = subjectModel.getSubjectName(id);
            lblSubjectNameShow.setText(name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void tblLectureOnCliked(MouseEvent event) {
        LectureTm lectureTm = tblLecture.getSelectionModel().getSelectedItem();
        if (lectureTm == null){
            showAlert(Alert.AlertType.ERROR,"Wrong row","You cliked wrong row....!");
            return;
        }
        lblLectureIdShow.setText(lectureTm.getLectureId());
        cmbSubjectId.setValue(lectureTm.getSubjectId());
        txtLectureTitle.setText(lectureTm.getLecTitle());
        txtName.setText(lectureTm.getName());
        btnSave.setDisable(true);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }
    @FXML
    void btnResetOnAction(ActionEvent event) {
        refresh();
    }
    public void refresh() {
        getAllLectures();
        getNextLectureId();
        getAllSubjectIds();
        cmbSubjectId.setValue("");
        txtLectureTitle.setText("");
        txtName.setText("");
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
