package lk.ijse.gdse71.final_project.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.gdse71.final_project.model.StudentModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SendMailFromController implements Initializable {

    @FXML
    private Button btnSendMail;

    @FXML
    private ComboBox<String> cmbAtudentId;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblEmailshow;

    @FXML
    private Label lblStudentId;

    @FXML
    private Label lblStudentNameShow;

    @FXML
    private TextField txtBody;

    @FXML
    private TextField txtSubject;

    private StudentModel studentModel = new StudentModel();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getStudentIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getStudentIds() throws SQLException {
        ObservableList<String> allStudentId = studentModel.getAllStudentId();
        cmbAtudentId.setItems(allStudentId);

    }
    @FXML
    void cmbAtudentIdOnAction(ActionEvent event) {
        String id = cmbAtudentId.getValue();

        String studentName = null;
        try {
            studentName = studentModel.getStudentName(id);
            lblStudentNameShow.setText(studentName);

            String mail = studentModel.getStudentMail(id);
            lblEmail.setText(mail);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void lblStudentIdOnAction(ActionEvent event) {

    }
}
