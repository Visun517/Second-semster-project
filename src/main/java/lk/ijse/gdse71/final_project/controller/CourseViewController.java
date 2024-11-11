package lk.ijse.gdse71.final_project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.final_project.dto.CourseDto;
import lk.ijse.gdse71.final_project.dto.tm.CourseTm;
import lk.ijse.gdse71.final_project.model.CourseModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CourseViewController implements Initializable {
    @FXML
    public Label lblPayment;
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<CourseTm, String> colCourseId;

    @FXML
    private TableColumn<CourseTm,String> colCourseName;

    @FXML
    private TableColumn<CourseTm, Integer> colDuration;

    @FXML
    private TableColumn<CourseTm, Double> colPayment;

    @FXML
    private Label lblCourseId;

    @FXML
    private Label lblCourseIdShow;

    @FXML
    private Label lblCourseName;

    @FXML
    private Label lblCourseNameShow;

    @FXML
    private Label lblDuration;

    @FXML
    private Label lblDurationShow;

    @FXML
    private TextField txtCourseName;

    @FXML
    private TextField txtDuration;
    @FXML
    private TextField txtPayment;

    @FXML
    private TableView<CourseTm> tblCourse;

    private CourseModel courseModel = new CourseModel();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));

        refresh();
    }
    public void getAllCourses(){
        try {
            ArrayList<CourseDto> courseDtos = courseModel.getAllCourses();
            ObservableList<CourseTm> courseTms = FXCollections.observableArrayList();

            for (CourseDto courseDto : courseDtos ){
                CourseTm courseTm = new CourseTm(
                        courseDto.getCourseId(),
                        courseDto.getCourseName(),
                        courseDto.getDuration(),
                        courseDto.getPayment()
                );
                courseTms.add(courseTm);
            }
            tblCourse.setItems(courseTms);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = lblCourseIdShow.getText();
        try {
            boolean isDelete = courseModel.deleteCourse(id);
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Successfully Delete......!").showAndWait();
                refresh();
            }else{
                new Alert(Alert.AlertType.ERROR,"Delete unsuccessfully......!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = lblCourseIdShow.getText();
        String name = txtCourseName.getText();
        int duration = Integer.parseInt(txtDuration.getText());
        double payment = Double.parseDouble(txtPayment.getText());

        CourseDto courseDto = new CourseDto(id,name,duration,payment);

        try {
            boolean isSaved = courseModel.savaCourse(courseDto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Course is saved......!").showAndWait();
                refresh();
            }else{
                new Alert(Alert.AlertType.ERROR,"Course is not saved......!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void getNextcourseId(){
        try {
            String id = courseModel.getNextCourseId();
            lblCourseIdShow.setText(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = lblCourseIdShow.getText();
        String name = txtCourseName.getText();
        int duration = Integer.parseInt(txtDuration.getText());
        double payment = Double.parseDouble(txtPayment.getText());

        CourseDto courseDto = new CourseDto(id,name,duration,payment);

        try {
            boolean isUpdate = courseModel.updatecourse(courseDto);
            if (isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Course is update......!").showAndWait();
                refresh();
            }else{
                new Alert(Alert.AlertType.ERROR,"Course is not update......!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void tblCourseOnCliked(MouseEvent event) {
        CourseTm courseTm = tblCourse.getSelectionModel().getSelectedItem();
        lblCourseIdShow.setText(courseTm.getCourseId());
        txtCourseName.setText(courseTm.getCourseName());
        txtDuration.setText(String.valueOf(courseTm.getDuration()));
        txtPayment.setText(String.valueOf(courseTm.getPayment()));

    }
    public void refresh(){
        getNextcourseId();
        txtCourseName.setText("");
        txtDuration.setText("");
        txtPayment.setText("");
        getAllCourses();
    }

}
