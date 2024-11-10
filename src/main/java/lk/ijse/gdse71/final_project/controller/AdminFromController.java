package lk.ijse.gdse71.final_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AdminFromController {

    @FXML
    private AnchorPane ancMain;

    @FXML
    private Button btnAttendenceMange;

    @FXML
    private Button btnCourseAssitance;

    @FXML
    private Button btnCourseMange;

    @FXML
    private Button btnExamManagment;

    @FXML
    private Button btnGenerateReports;

    @FXML
    private Button btnLectureMange;

    @FXML
    private Button btnLectureScheduling;

    @FXML
    private Button btnPaymentHistory;

    @FXML
    private Button btnPaymentProcessing;

    @FXML
    private Button btnStudnentReocordManage;

    @FXML
    private Button btnSubjectManage;

    @FXML
    void btnStudnentReocordManageOnAction(ActionEvent event) throws IOException {
        ancMain.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/StudentMangeFrom.fxml"));
        ancMain.getChildren().add(load);

    }
    @FXML
    void btnCourseAssitanceOnAction(ActionEvent event) throws IOException {
        ancMain.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/CourseRegistrationFrom.fxml"));
        ancMain.getChildren().add(load);

    }

    @FXML
    void btnAttendenceMangeOnAction(ActionEvent event) throws IOException {
        ancMain.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/AttendenceViewFrom.fxml"));
        ancMain.getChildren().add(load);
    }


    @FXML
    void btnCourseMangeOnAction(ActionEvent event) throws IOException {
        ancMain.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/CourseViewFrom.fxml"));
        ancMain.getChildren().add(load);
    }

    @FXML
    void btnSubjectManageOnAction(ActionEvent event) throws IOException {
        ancMain.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/SubjectViewFrom.fxml"));
        ancMain.getChildren().add(load);
    }


    @FXML
    void btnLectureSchedulingOnAction(ActionEvent event) throws IOException {
        ancMain.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/LectureScheduliingViewFrom.fxml"));
        ancMain.getChildren().add(load);
    }


    @FXML
    void btnCourseAssitanceOnActionn(MouseEvent event) throws IOException {

    }



    @FXML
    void btnExamManagmentOnAction(ActionEvent event) {

    }

    @FXML
    void btnGenerateReportsOnAction(ActionEvent event) {

    }

    @FXML
    void btnPaymentHistoryOnAction(ActionEvent event) {

    }

    @FXML
    void btnPaymentProcessingOnAction(ActionEvent event) {

    }



}
