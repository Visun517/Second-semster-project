package lk.ijse.gdse71.final_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AcademicDashBoardController {

    @FXML
    private AnchorPane ancMain;

    @FXML
    private AnchorPane ancMidle;

    @FXML
    private VBox ancSide;

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
    private Button btnLogOut;

    @FXML
    void btnCourseManageOnAction(ActionEvent event) throws IOException {
        ancMain.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/CourseViewFrom.fxml"));
        ancMain.getChildren().add(load);

    }

    @FXML
    void btnCourseRegistrationOnAction(ActionEvent event) throws IOException {
        ancMain.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/CourseRegistrationFrom.fxml"));
        ancMain.getChildren().add(load);
    }

    @FXML
    void btnDashBoardOnAction(ActionEvent event) {

    }

    @FXML
    void btnExamGradingOnAction(ActionEvent event) throws IOException {
        ancMain.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/ExamAndGradeViewFrom.fxml"));
        ancMain.getChildren().add(load);
    }

    @FXML
    void btnLectureSchedulingOnAction(ActionEvent event) throws IOException {
        ancMain.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/LectureScheduliingViewFrom.fxml"));
        ancMain.getChildren().add(load);
    }

    @FXML
    void btnSubjectLectureManageOnAction(ActionEvent event) throws IOException {
        ancMain.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/SubjectViewFrom.fxml"));
        ancMain.getChildren().add(load);
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) ancMain.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginFromView.fxml"));
        Parent root = loader.load();
        Stage login = new Stage();
        login.setScene(new Scene(root));
        login.show();
    }

}
