package lk.ijse.gdse71.final_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class FinancialManagerDashBoardController {

    @FXML
    private AnchorPane ancMidle;

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
    void btnDashBoardOnAction(ActionEvent event) {

    }

    @FXML
    void btnExpenseManageOnAction(ActionEvent event) throws IOException {
        ancMidle.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/ExpenseMangeFromView.fxml"));
        ancMidle.getChildren().add(load);
    }

    @FXML
    void btnPaymentProccesingOnAction(ActionEvent event) throws IOException {
        ancMidle.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/PaymentProssecingViewfFrom.fxml"));
        ancMidle.getChildren().add(load);
    }

    @FXML
    void btnViewPaymentHistoryOnAction(ActionEvent event) throws IOException {

    }

    public void btnCourseRegistrationOnAction(ActionEvent actionEvent) {

    }
}
