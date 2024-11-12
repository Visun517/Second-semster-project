package lk.ijse.gdse71.final_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AcademicDashBoardController {

    @FXML
    private AnchorPane ancMidle;

    @FXML
    private Button btnCourseManage;

    @FXML
    private Button btnCourseRegistration1;

    @FXML
    private Button btnCourseRegistration21;

    @FXML
    private Button btnDashBoard;

    @FXML
    private Button btnExamGrading;

    @FXML
    private Button btnLectureScheduling;

    @FXML
    private Button btnSubjectLectureManage;

    @FXML
    void btnCourseManageOnAction(ActionEvent event) throws IOException {
        ancMidle.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/CourseViewFrom.fxml"));
        ancMidle.getChildren().add(load);

    }

    @FXML
    void btnCourseRegistrationOnAction(ActionEvent event) throws IOException {
        ancMidle.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/CourseRegistrationFrom.fxml"));
        ancMidle.getChildren().add(load);
    }

    @FXML
    void btnDashBoardOnAction(ActionEvent event) {

    }

    @FXML
    void btnExamGradingOnAction(ActionEvent event) throws IOException {
        ancMidle.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/ExamAndGradeViewFrom.fxml"));
        ancMidle.getChildren().add(load);
    }

    @FXML
    void btnLectureSchedulingOnAction(ActionEvent event) throws IOException {
        ancMidle.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/LectureScheduliingViewFrom.fxml"));
        ancMidle.getChildren().add(load);
    }

    @FXML
    void btnSubjectLectureManageOnAction(ActionEvent event) throws IOException {
        ancMidle.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/SubjectViewFrom.fxml"));
        ancMidle.getChildren().add(load);
    }

}
