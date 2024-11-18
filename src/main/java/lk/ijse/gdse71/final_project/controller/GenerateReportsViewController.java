package lk.ijse.gdse71.final_project.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import lk.ijse.gdse71.final_project.db.DBConnection;
import lk.ijse.gdse71.final_project.dto.AdminDto;
import lk.ijse.gdse71.final_project.model.StudentModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class GenerateReportsViewController implements Initializable {

    @FXML
    private Button btnAcademicReports;

    @FXML
    private Button btnFinancialReports;

    @FXML
    private Button btnStudentReports;

    @FXML
    private ComboBox<String> cmbStudentId;

    @FXML
    private Label lblStudentId;

    @FXML
    private Label lblStudetNameShow;

    private StudentModel studentModel = new StudentModel();
    private AdminDto adminDto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<String> allStudentId = studentModel.getAllStudentId();
            cmbStudentId.setItems(allStudentId);
            System.out.println("A");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void getAdmin(AdminDto adminDto) {
        this.adminDto = adminDto;
        System.out.println(this.adminDto.toString());
    }

    @FXML
    void btnAcademicReportsOnAction(ActionEvent event) {
        System.out.println("btnAcademicReportsOnAction");

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/reports/AcedemicReports.jrxml"
                            ));

            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("Student_id",cmbStudentId.getValue());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "DB error...!").show();
        }
    }

    @FXML
    void btnFinancialReportsOnAction(ActionEvent event) {
        System.out.println("btnFinancialReportsOnAction");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass()
                            .getResourceAsStream("/reports/FinancialReports.jrxml"
                            ));

            Connection connection = DBConnection.getInstance().getConnection();

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("Student_id",cmbStudentId.getValue());

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to generate report...!").show();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "DB error...!").show();
        }
    }

    @FXML
    void cmbStudentIdOnAction(ActionEvent event) {
        String id = cmbStudentId.getValue();
        try {
            String studentName = studentModel.getStudentName(id);
            lblStudetNameShow.setText(studentName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
