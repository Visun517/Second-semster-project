package lk.ijse.gdse71.final_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.gdse71.final_project.dto.AdminDto;
import lk.ijse.gdse71.final_project.model.AdminModel;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class LoginFromController {

    @FXML
    private AnchorPane ancMain;

    @FXML
    private AnchorPane ancSide;

    @FXML
    private Button btuLogin;

    @FXML
    private Button btuSignIn;

    @FXML
    private Label lblCourse;

    @FXML
    private Label lblHaveNot;

    @FXML
    private Label lblRegistration;

    @FXML
    private Label lblSystem;

    @FXML
    private Label lblUniversity;

    @FXML
    private Label lblWelcome;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private Pane paneLogin;

    private final AdminModel adminModel = new AdminModel();
    private AdminDto adminDto;

    @FXML
    void loginOnAction(ActionEvent event) {
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        try {
            adminDto = adminModel.checkAdmin(userName);

            if (adminDto != null && adminDto.getPassword().equals(password)) {
                Stage stage = (Stage) ancMain.getScene().getWindow();
                stage.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBoardView.fxml"));
                Parent dashboardRoot = loader.load();
                DashBoardController dashBoardController = loader.getController();
               // System.out.println(adminDto.toString());
                dashBoardController.initialize(adminDto);

                Stage dashboardStage = new Stage();
                dashboardStage.setScene(new Scene(dashboardRoot));
                dashboardStage.show();
            } else {
                JOptionPane.showMessageDialog(null, "USERNAME OR PASSWORD Invalid", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException | IOException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }

    @FXML
    void signInOnAction(ActionEvent event) throws IOException {
        ancMain.getChildren().clear();
        AnchorPane signInPane = FXMLLoader.load(getClass().getResource("/view/SignInFromView.fxml"));
        ancMain.getChildren().add(signInPane);
    }

    public void txtUserNameOnAction(ActionEvent actionEvent) {
        String userName = txtUserName.getText();
        try {
            AdminDto adminDto = adminModel.checkAdmin(userName);
            if (adminDto != null) {
                txtUserName.setStyle(";-fx-border-color: null;");
                txtPassword.requestFocus();
            } else {
                txtUserName.setStyle(";-fx-border-color: red;");
            }
        } catch (Exception e) {
            System.out.println("Error checking username: " + e.getMessage());
        }
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        loginOnAction(actionEvent);
    }
}
