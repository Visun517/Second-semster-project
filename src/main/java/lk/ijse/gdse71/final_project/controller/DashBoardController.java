package lk.ijse.gdse71.final_project.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.gdse71.final_project.dto.AdminDto;

import java.io.IOException;

public class DashBoardController {

    @FXML
    private Label lblDashBoar;
    @FXML
    private AnchorPane ancMain;

    private AdminDto adminDto;

    @FXML
    public void initialize(AdminDto adminDto) throws IOException {
        this.adminDto = adminDto;

        if (adminDto.getRole().equals("counselor")){
            ancMain.getChildren().clear();
            Parent load = FXMLLoader.load(getClass().getResource("/view/StudentManageForm.fxml"));
            ancMain.getChildren().add(load);
        } else if (adminDto.getRole().equals("Admin")) {
            ancMain.getChildren().clear();
            Parent load = FXMLLoader.load(getClass().getResource("/view/AdminViewFrom.fxml"));
            ancMain.getChildren().add(load);
        }
    }
}
