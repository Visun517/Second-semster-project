package lk.ijse.gdse71.final_project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.final_project.dto.SubjectDto;
import lk.ijse.gdse71.final_project.dto.tm.SubjectTm;
import lk.ijse.gdse71.final_project.model.SemesterModel;
import lk.ijse.gdse71.final_project.model.SubjectModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SubjectViewFromController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnOpenLectureManage;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbSemesterId;

    @FXML
    private TableColumn<SubjectTm, String> colSemesterId;

    @FXML
    private TableColumn<SubjectTm, String> colSubjectDesc;

    @FXML
    private TableColumn<SubjectTm, String> colSubjectId;

    @FXML
    private TableColumn<SubjectTm, String> colSubjectName;

    @FXML
    private Label lblSemesterId;

    @FXML
    private Label lblSemesterNameShow;

    @FXML
    private Label lblSubjectDesc;

    @FXML
    private Label lblSubjectId;

    @FXML
    private Label lblSubjectIdShow;

     @FXML
    private Label lblSubjectName;

    @FXML
    private TableView<SubjectTm> tblSubject;

    @FXML
    private TextField txtSubjectDesc;

    @FXML
    private TextField txtSubjectName;

    private SubjectModel subjectModel = new SubjectModel();
    private SemesterModel semesterModel = new SemesterModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSubjectId.setCellValueFactory(new PropertyValueFactory<>("subjectId"));
        colSubjectName.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        colSubjectDesc.setCellValueFactory(new PropertyValueFactory<>("subDesc"));
        colSemesterId.setCellValueFactory(new PropertyValueFactory<>("semesterId"));
        refresh();
    }
    public void getAllSubjects(){
        try {
            ArrayList<SubjectDto> subjectDtos = subjectModel.getAllSubjects();
            ObservableList<SubjectTm> subjectTms = FXCollections.observableArrayList();

            for (SubjectDto subjectDto : subjectDtos){
                SubjectTm subjectTm = new SubjectTm(
                        subjectDto.getSubjectId(),
                        subjectDto.getSubjectName(),
                        subjectDto.getSubDesc(),
                        subjectDto.getSemesterId()
                );
                subjectTms.add(subjectTm);
            }
            tblSubject.setItems(subjectTms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void getNextSubjectId(){
        try {
            String id = subjectModel.getNextSubjectId();
            lblSubjectIdShow.setText(id);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void getAllSemesterIds(){
        try {
            ObservableList<String> ids = semesterModel.getAllSemesterIds();
            cmbSemesterId.setItems(ids);

        } catch (SQLException e) {

        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = lblSubjectIdShow.getText();
        try {
            boolean isDelete = semesterModel.deleteSubject(id);
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Subject is Delete.....!").showAndWait();
                refresh();
            }else {
                new Alert(Alert.AlertType.ERROR,"Subject is not Delete.....!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String subId = lblSubjectIdShow.getText();
        String subName = txtSubjectName.getText();
        String sudDesc = txtSubjectDesc.getText();
        String semId = cmbSemesterId.getValue();

        SubjectDto subjectDto = new SubjectDto(subId,subName,sudDesc,semId);

        try {
            boolean isSaved = semesterModel.saveSubject(subjectDto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Subject is saved.....!").showAndWait();
                refresh();
            }else {
                new Alert(Alert.AlertType.ERROR,"Subject is not saved.....!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String subId = lblSubjectIdShow.getText();
        String subName = txtSubjectName.getText();
        String sudDesc = txtSubjectDesc.getText();
        String semId = cmbSemesterId.getValue();

        SubjectDto subjectDto = new SubjectDto(subId,subName,sudDesc,semId);

        try {
            boolean isSaved = semesterModel.updateSubject(subjectDto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Subject is update.....!").showAndWait();
                refresh();
            }else {
                new Alert(Alert.AlertType.ERROR,"Subject is not update.....!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void tblSubjectOncliked(MouseEvent event) {
        SubjectTm subjectTm = tblSubject.getSelectionModel().getSelectedItem();
        lblSubjectIdShow.setText(subjectTm.getSubjectId());
        txtSubjectName.setText(subjectTm.getSubjectName());
        txtSubjectDesc.setText(subjectTm.getSubDesc());
        cmbSemesterId.setValue(subjectTm.getSemesterId());

    }

    @FXML
    void btnOpenLectureManageOnAction(ActionEvent event) {

    }

    @FXML
    void cmbSemesterIdOnAction(ActionEvent event) {
        String id = cmbSemesterId.getValue();
        try {
            String name = subjectModel.getSemsterName(id);
            lblSemesterNameShow.setText(name);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void refresh(){
        getAllSubjects();
        getNextSubjectId();
        getAllSemesterIds();
        txtSubjectName.setText("");
        txtSubjectDesc.setText("");
        cmbSemesterId.setValue("");
    }
}
