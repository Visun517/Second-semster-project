package lk.ijse.gdse71.final_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AcademicDashBoardController implements Initializable {

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
    private Button btnReport;

    @FXML
    private Button btnSendMail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ancMain.getChildren().clear();
        Parent load = null;
        try {
            load = FXMLLoader.load(getClass().getResource("/view/CourseViewFrom.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ancMain.getChildren().add(load);
    }


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
    void btnReportOnAction(ActionEvent event) throws IOException {
        Parent load1 = FXMLLoader.load(getClass().getResource("/view/ReportsGenerateView.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(load1));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    void btnSendMailOnAction(ActionEvent event) throws IOException {
//        ancMain.getChildren().clear();
//        Parent load = FXMLLoader.load(getClass().getResource("/view/SendMailViewFrom.fxml"));
//        ancMain.getChildren().add(load);

        Parent load1 = FXMLLoader.load(getClass().getResource("/view/SendMailViewFrom.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(load1));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }




    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) ancMain.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginFromView.fxml"));
        Parent root = loader.load();
        Stage login = new Stage();
        Image image = new Image(getClass().getResourceAsStream("/image/Screenshot 2024-11-12 232120.png"));
        login.getIcons().add(image);
        login.setScene(new Scene(root));
        login.show();
    }


}
