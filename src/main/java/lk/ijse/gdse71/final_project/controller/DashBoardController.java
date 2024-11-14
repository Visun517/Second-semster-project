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

        if (adminDto.getRole().equals("counselor")||adminDto.getRole().equals("Counselor")){
            ancMain.getChildren().clear();
            Parent load = FXMLLoader.load(getClass().getResource("/view/CounselorDashBoardView.fxml"));
            ancMain.getChildren().add(load);
        } else if (adminDto.getRole().equals("Admin")||adminDto.getRole().equals("admin")) {
            ancMain.getChildren().clear();
            Parent load = FXMLLoader.load(getClass().getResource("/view/AdminDashBoardViewFrom.fxml"));
            ancMain.getChildren().add(load);
        }else if (adminDto.getRole().equals("Academic")||adminDto.getRole().equals("academic")) {
            ancMain.getChildren().clear();
            Parent load = FXMLLoader.load(getClass().getResource("/view/AcademicAdministerDashBoardView.fxml"));
            ancMain.getChildren().add(load);
        }else if (adminDto.getRole().equals("financial")||adminDto.getRole().equals("Financial")) {
            ancMain.getChildren().clear();
            Parent load = FXMLLoader.load(getClass().getResource("/view/FinancialManagerDashBoardView.fxml"));
            ancMain.getChildren().add(load);
        }
    }
}
