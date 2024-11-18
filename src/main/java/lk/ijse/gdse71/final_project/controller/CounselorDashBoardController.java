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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CounselorDashBoardController implements Initializable {

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
    private Button btnReports;

    @FXML
    private Button btnSendMail;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ancMidle.getChildren().clear();
        Parent load = null;
        try {
            load = FXMLLoader.load(getClass().getResource("/view/StudentMangeFrom.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ancMidle.getChildren().add(load);
    }

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
    void btnReportsOnAction(ActionEvent event) throws IOException {
        Parent load1 = FXMLLoader.load(getClass().getResource("/view/ReportsGenerateView.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(load1));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    @FXML
    void btnSendMailOnAction(ActionEvent event) throws IOException {
//        ancMidle.getChildren().clear();
//        Parent load = FXMLLoader.load(getClass().getResource("/view/SendMailViewFrom.fxml"));
//        ancMidle.getChildren().add(load);

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
