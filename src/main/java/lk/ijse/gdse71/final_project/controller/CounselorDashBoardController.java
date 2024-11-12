package lk.ijse.gdse71.final_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CounselorDashBoardController {

    @FXML
    private AnchorPane ancMain;

    @FXML
    private Button btnAttendence;

    @FXML
    private Button btnDashBoard;

    @FXML
    private Button btnstudentView;

    @FXML
    private Button btnCourseRegistration;

    @FXML
    private AnchorPane ancMidle;

    @FXML
    private Button btnLogOut;

    @FXML
    void btnAttendenceOnAction(ActionEvent event) throws IOException {
        ancMidle.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/AttendenceViewFrom.fxml"));
        ancMidle.getChildren().add(load);
    }

    @FXML
    void btnDashBoardOnAction(ActionEvent event) throws IOException {

    }

    @FXML
    void btnstudentViewOnAction(ActionEvent event) throws IOException {
        ancMidle.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/StudentMangeFrom.fxml"));
        ancMidle.getChildren().add(load);
    }

    @FXML
    void btnCourseRegistrationOnAction(ActionEvent event) throws IOException {
        ancMidle.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/CourseRegistrationFrom.fxml"));
        ancMidle.getChildren().add(load);
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
