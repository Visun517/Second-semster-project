package lk.ijse.gdse71.final_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FinancialManagerDashBoardController implements Initializable {

    @FXML
    private AnchorPane ancMain;

    @FXML
    private VBox ancMidle;


    @FXML
    private Button btnCourseRegistration1;

    @FXML
    private Button btnCourseRegistration21;

    @FXML
    private Button btnDashBoard;

    @FXML
    private Button btnExpenseManage;

    @FXML
    private Button btnPaymentProccesing;

    @FXML
    private Button btnViewPaymentHistory;
    @FXML
    private Button btnReoprts;

    @FXML
    private Button btnSendMail;

    @FXML
    private Button btnCourseRegistration;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ancMain.getChildren().clear();
        Parent load = null;
        try {
            load = FXMLLoader.load(getClass().getResource("/view/PaymentProssecingViewfFrom.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ancMain.getChildren().add(load);
    }

    @FXML
    void btnDashBoardOnAction(ActionEvent event) {

    }

    @FXML
    void btnExpenseManageOnAction(ActionEvent event) throws IOException {
        ancMain.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/ExpenseMangeFromView.fxml"));
        ancMain.getChildren().add(load);
    }

    @FXML
    void btnPaymentProccesingOnAction(ActionEvent event) throws IOException {
        ancMain.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/PaymentProssecingViewfFrom.fxml"));
        ancMain.getChildren().add(load);
    }

    @FXML
    void btnViewPaymentHistoryOnAction(ActionEvent event) throws IOException {

    }

    public void btnCourseRegistrationOnAction(ActionEvent actionEvent) throws IOException {
        ancMain.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/CourseRegistrationFrom.fxml"));
        ancMain.getChildren().add(load);
    }
    @FXML
    void btnReoprtsOnAction(ActionEvent event) throws IOException {
        Parent load1 = FXMLLoader.load(getClass().getResource("/view/ReportsGenerateView.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(load1));
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
