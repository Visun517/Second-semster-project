package lk.ijse.gdse71.final_project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.gdse71.final_project.dto.AdminDto;
import lk.ijse.gdse71.final_project.model.AdminModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignInFromController implements Initializable {

    @FXML
    private AnchorPane ancMain;

    @FXML
    private Label lblIdShow;

    @FXML
    private AnchorPane ancSide;

    @FXML
    private Button btuSingIn;

    @FXML
    private Label lblCourse;

    @FXML
    private Label lblRegistration;

    @FXML
    private Label lblSystem;

    @FXML
    private Label lblUniversity;

    @FXML
    private Label lblWellcom;

    @FXML
    private Pane paneLogin;

    @FXML
    private TextField txtAdminId;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private ComboBox<String> cmdRole;

    @FXML
    private Label lblChooseRole;

    private AdminModel adminModel = new AdminModel();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] roleArray = {"Academic", "Counselor", "Financial"};
        ObservableList<String> roleList = FXCollections.observableArrayList();
        roleList.addAll(roleArray);
        cmdRole.setItems(roleList);
        try {
            getNextAdminId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSingOnAction(ActionEvent event) throws SQLException, IOException {
        String id = lblIdShow.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String role = lblChooseRole.getText();

        AdminDto adminDto1 = new AdminDto(id, name, email, password, role);
        boolean isSaved = adminModel.addAdmin(adminDto1);

        if (isSaved) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBoardView.fxml"));
            Parent load = loader.load();
            DashBoardController dashBoardController = loader.getController();
            dashBoardController.initialize(adminDto1);

            Stage dashboardStage = new Stage();
            dashboardStage.setScene(new Scene(load));
            dashboardStage.show();
            new Alert(Alert.AlertType.INFORMATION, "Added successfully..!").showAndWait();

        } else {
            new Alert(Alert.AlertType.ERROR, "Added not successfully..!").showAndWait();
        }

    }

    public void getNextAdminId() throws SQLException {
        String id = adminModel.getNextAdminId();
        lblIdShow.setText(id);
    }

    @FXML
    void cmbRoleOnAction(ActionEvent event) {
        lblChooseRole.setText(cmdRole.getSelectionModel().getSelectedItem());
    }


}
