package lk.ijse.gdse71.final_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class StudentFormController {

    @FXML
    private AnchorPane ancMain;
    @FXML
    private AnchorPane ancSide;

    @FXML
    private Button btnAttendenceManage;

    @FXML
    private Button btnCourseRegistration;

    @FXML
    private Button btnViewStudents;

    @FXML
    private Label lblStudentManage;

    @FXML
    void btnAttendenceManageOnAction(ActionEvent event) {

    }

    @FXML
    void btnCourseRegistrationOnAction(ActionEvent event) {

    }


    @FXML
    void btnViewStudentsOnAction(ActionEvent event) throws IOException {
        ancMain.getChildren().clear();
        Parent load = FXMLLoader.load(getClass().getResource("/view/StudentView.fxml"));
        ancMain.getChildren().add(load);
    }


}
