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
                new Alert(Alert.AlertType.INFORMATION, "Lecture delete Successfully.!").showAndWait();
                refresh();
            }else {
                new Alert(Alert.AlertType.ERROR, "Lecture delete not Successfully.!").showAndWait();
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

        LectureDto lectureDto = new LectureDto(lecId, subId, lecTitle, name);

        try {
            boolean isSaved = lectureModel.lectureSave(lectureDto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Lecture Save Successfully.!").showAndWait();
                refresh();
            } else {
                new Alert(Alert.AlertType.ERROR, "Lecture Save not Successfully.!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
                new Alert(Alert.AlertType.INFORMATION,"Lecture Update Successfully.!").showAndWait();
                refresh();
            }else{
                new Alert(Alert.AlertType.ERROR,"Lecture Update not Successfully.!").showAndWait();
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
        //System.out.println(lectureTm);
        lblLectureIdShow.setText(lectureTm.getLectureId());
        cmbSubjectId.setValue(lectureTm.getSubjectId());
        txtLectureTitle.setText(lectureTm.getLecTitle());
        txtName.setText(lectureTm.getName());
    }

    public void refresh() {
        getAllLectures();
        getNextLectureId();
        getAllSubjectIds();
        cmbSubjectId.setValue("");
        txtLectureTitle.setText("");
        txtName.setText("");
    }

}
