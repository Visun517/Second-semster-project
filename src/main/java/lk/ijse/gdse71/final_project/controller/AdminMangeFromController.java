package lk.ijse.gdse71.final_project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.final_project.dto.AdminDto;
import lk.ijse.gdse71.final_project.dto.tm.AdminTm;
import lk.ijse.gdse71.final_project.model.AdminModel;
import lombok.ToString;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminMangeFromController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbRole;

    @FXML
    private TableColumn<AdminTm, String> colAdminId;

    @FXML
    private TableColumn<AdminTm, String> colEmail;

    @FXML
    private TableColumn<AdminTm, String> colPassword;

    @FXML
    private TableColumn<AdminTm, String> colRole;

    @FXML
    private TableColumn<AdminTm, String> colUserName;

    @FXML
    private Label lblAdmin;

    @FXML
    private Label lblAdminIdShow;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblPassword;

    @FXML
    private Label lblRole;

    @FXML
    private Label lblUserName;

    @FXML
    private TableView<AdminTm> tblAdmin;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPasswrod;

    @FXML
    private TextField txtUserName;

    private AdminModel adminModel = new AdminModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colAdminId.setCellValueFactory(new PropertyValueFactory<>("adminId"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        refresh();
    }
    private void getAllAdmins(){
        try {
            ObservableList<AdminTm> adminTms = adminModel.getAllAdmins();
            tblAdmin.setItems(adminTms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getNextAdminId(){
        try {
            String id = adminModel.getNextAdminId();
            lblAdminIdShow.setText(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setRoles(){
        String []role = {"Academic","counselor","financial"};
        ObservableList<String> roles = FXCollections.observableArrayList();
        roles.addAll(role);
        cmbRole.setItems(roles);
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = lblAdminIdShow.getText();
        try {
            boolean isDelete = adminModel.deleteAdmin(id);
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Admin is delete....!").showAndWait();
                refresh();
            }else{
                new Alert(Alert.AlertType.ERROR,"Admin is not delete....!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = lblAdminIdShow.getText();
        String name = txtUserName.getText();
        String email = txtEmail.getText();
        String password = txtPasswrod.getText();
        String role = cmbRole.getValue();

        AdminDto adminDto = new AdminDto(id,name,email,password,role);

        try {
            boolean isSaved = adminModel.saveAdmin(adminDto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Admin is saved....!").showAndWait();
                refresh();
            }else{
                new Alert(Alert.AlertType.ERROR,"Admin is not saved....!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = lblAdminIdShow.getText();
        String name = txtUserName.getText();
        String email = txtEmail.getText();
        String password = txtPasswrod.getText();
        String role = cmbRole.getValue();

        AdminDto adminDto = new AdminDto(id,name,email,password,role);

        try {
            boolean isSaved = adminModel.adminUpdate(adminDto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Admin is saved....!").showAndWait();
                refresh();
            }else{
                new Alert(Alert.AlertType.ERROR,"Admin is not saved....!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void tblAdminOnCliked(MouseEvent event) {
        AdminTm adminTm = tblAdmin.getSelectionModel().getSelectedItem();
        lblAdminIdShow.setText(adminTm.getAdminId());
        txtUserName.setText(adminTm.getUserName());
        txtEmail.setText(adminTm.getEmail());
        txtPasswrod.setText(adminTm.getPassword());
        cmbRole.setValue(adminTm.getRole());
    }
    private void refresh(){
        getAllAdmins();
        getNextAdminId();
        setRoles();
        txtUserName.setText("");
        txtEmail.setText("");
        txtPasswrod.setText("");
        cmbRole.setValue("");
    }
}
