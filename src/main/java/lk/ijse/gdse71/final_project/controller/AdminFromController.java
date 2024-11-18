package lk.ijse.gdse71.final_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminFromController implements Initializable {

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
    private Button btnExpenseManage;

    @FXML
    private Button btnAdminMange;

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
            load = FXMLLoader.load(getClass().getResource("/view/StudentMangeFrom.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ancMain.getChildren().add(load);

    }

    @FXML
    void btnStudnentReocordManageOnAction(ActionEvent event) throws IOException {
        ancMain.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/StudentMangeFrom.fxml"));
        ancMain.getChildren().add(load);

    }
    @FXML
    void btnAdminMangeOnAction(ActionEvent event) throws IOException {
        ancMain.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/AdminManageFrom.fxml"));
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
    void btnExamManagmentOnAction(ActionEvent event) throws IOException {
        ancMain.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/ExamAndGradeViewFrom.fxml"));
        ancMain.getChildren().add(load);
    }
    @FXML
    void btnPaymentProcessingOnAction(ActionEvent event) throws IOException {
        ancMain.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/PaymentProssecingViewfFrom.fxml"));
        ancMain.getChildren().add(load);
    }
    @FXML
    void btnExpenseManageOnAction(ActionEvent event) throws IOException {
        ancMain.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/ExpenseMangeFromView.fxml"));
        ancMain.getChildren().add(load);
    }
    @FXML
    void btnPaymentHistoryOnAction(ActionEvent event) throws IOException {

    }

    @FXML
    void btnCourseAssitanceOnActionn(MouseEvent event) throws IOException {

    }


    @FXML
    void btnGenerateReportsOnAction(ActionEvent event) throws IOException {
        Parent load1 = FXMLLoader.load(getClass().getResource("/view/ReportsGenerateView.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(load1));
        stage.show();
    }
    @FXML
    void btnReportOnAction(ActionEvent event) throws IOException {

    }

    @FXML
    void btnSendMailOnAction(ActionEvent event) throws IOException {
//        ancMain.getChildren().clear();
//        Parent load = FXMLLoader.load(getClass().getResource("/view/SendMailViewFrom.fxml"));
//        ancMain.getChildren().add(load);

        Parent load1 = FXMLLoader.load(getClass().getResource("/view/SendMailViewFrom.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(load1));
        stage.show();

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
