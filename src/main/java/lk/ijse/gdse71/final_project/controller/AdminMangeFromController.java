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

    @FXML
    private Button btnReset;

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
                showAlert(Alert.AlertType.INFORMATION,"Delete", "Admin is delete....!");
                refresh();
            }else{
                showAlert(Alert.AlertType.ERROR,"Delete", "Admin is delete....!");
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
        if (txtUserName.getText().isEmpty() &&  txtEmail.getText().isEmpty() && txtPasswrod.getText().isEmpty() && cmbRole.getValue().isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Text feild are empty...!","Fill all text field...!");
            return;
        }

        txtUserName.setStyle(txtUserName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPasswrod.setStyle(txtPasswrod.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPass = password.matches(passwordRegex);

        if (!isValidName) {
            txtUserName.setStyle(txtUserName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input name is invalid...!");
            showAlert(Alert.AlertType.ERROR,"Invalid Name", "Please enter a valid name (only letters and spaces are allowed).");
            return;
        }
        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input email is invalid....!");
            showAlert(Alert.AlertType.ERROR,"Invalid Email", "Please enter a valid email address.");
            return;
        }
        if (!isValidPass) {
            txtPasswrod.setStyle(txtPasswrod.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input password is invalid....!");
            showAlert(Alert.AlertType.ERROR,"Invalid Password", "Password must be at least 8 characters long and include an uppercase letter, a lowercase letter, a digit, and a special character.");
            return;
        }
        if (isValidName && isValidEmail && isValidPass){
            AdminDto adminDto = new AdminDto(id,name,email,password,role);

            try {
                boolean isSaved = adminModel.saveAdmin(adminDto);
                if (isSaved){
                    showAlert(Alert.AlertType.INFORMATION,"Saved", "Admin is Saved....!");
                    refresh();
                }else{
                    showAlert(Alert.AlertType.ERROR,"Saved", "Admin is not saved....!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = lblAdminIdShow.getText();
        String name = txtUserName.getText();
        String email = txtEmail.getText();
        String password = txtPasswrod.getText();
        String role = cmbRole.getValue();
        if (txtUserName.getText().isEmpty() &&  txtEmail.getText().isEmpty() && txtPasswrod.getText().isEmpty() && cmbRole.getValue().isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Text feild are empty...!","Fill all text field...!");
            return;
        }

        txtUserName.setStyle(txtUserName.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");
        txtPasswrod.setStyle(txtPasswrod.getStyle() + ";-fx-border-color: #7367F0;");

        String namePattern = "^[A-Za-z ]+$";
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPass = password.matches(passwordRegex);

        if (!isValidName) {
            txtUserName.setStyle(txtUserName.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input name is invalid...!");
            showAlert(Alert.AlertType.ERROR,"Invalid Name", "Please enter a valid name (only letters and spaces are allowed).");
            return;
        }
        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input email is invalid....!");
            showAlert(Alert.AlertType.ERROR,"Invalid Email", "Please enter a valid email address.");
            return;
        }
        if (!isValidPass) {
            txtPasswrod.setStyle(txtPasswrod.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input password is invalid....!");
            showAlert(Alert.AlertType.ERROR,"Invalid Password", "Password must be at least 8 characters long and include an uppercase letter, a lowercase letter, a digit, and a special character.");
            return;
        }
        if (isValidName && isValidEmail && isValidPass){
            AdminDto adminDto = new AdminDto(id,name,email,password,role);

            try {
                boolean isSaved = adminModel.adminUpdate(adminDto);
                if (isSaved){
                    showAlert(Alert.AlertType.INFORMATION,"Update", "Admin is Update....!");
                    refresh();
                }else{
                    showAlert(Alert.AlertType.ERROR,"Update", "Admin is not Update....!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }
    @FXML
    void tblAdminOnCliked(MouseEvent event) {
        AdminTm adminTm = tblAdmin.getSelectionModel().getSelectedItem();
        if (adminTm == null){
            showAlert(Alert.AlertType.ERROR,"Wrong row","You cliked wrong row....!");
            return;
        }
        lblAdminIdShow.setText(adminTm.getAdminId());
        txtUserName.setText(adminTm.getUserName());
        txtEmail.setText(adminTm.getEmail());
        txtPasswrod.setText(adminTm.getPassword());
        cmbRole.setValue(adminTm.getRole());
        btnSave.setDisable(true);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
    }
    private void refresh(){
        getAllAdmins();
        getNextAdminId();
        setRoles();
        txtUserName.setText("");
        txtEmail.setText("");
        txtPasswrod.setText("");
        cmbRole.setValue("");
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }
    @FXML
    void btnResetOnAction(ActionEvent event) {
        refresh();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
