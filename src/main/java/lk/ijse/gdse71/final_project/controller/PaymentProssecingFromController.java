package lk.ijse.gdse71.final_project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.final_project.dto.PaymentDto;
import lk.ijse.gdse71.final_project.dto.tm.PaymentTm;
import lk.ijse.gdse71.final_project.model.PaymentModel;
import lk.ijse.gdse71.final_project.model.RegistrationModel;
import lk.ijse.gdse71.final_project.model.StudentModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PaymentProssecingFromController implements Initializable {

    @FXML
    private Button btnPaymentDelete;

    @FXML
    private Button btnPaymentUpdate;

    @FXML
    private Button btnSavePayment;

    @FXML
    private ComboBox<String> cmbPayType;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private ComboBox<String> cmbStudentId;

    @FXML
    private TableColumn<PaymentTm, Double> colAmount;

    @FXML
    private TableColumn<PaymentTm, String> colPayType;

    @FXML
    private TableColumn<PaymentTm, String> colPaymentId;

    @FXML
    private TableColumn<PaymentTm, String> colReferenceType;

    @FXML
    private TableColumn<PaymentTm, String> colStatus;

    @FXML
    private TableColumn<PaymentTm, String> colStudentId;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblPayType;

    @FXML
    private Label lblPaymentId;

    @FXML
    private Label lblPaymentIdShow;

    @FXML
    private Label lblReferenceNum;

    @FXML
    private Label lblStatus;

    @FXML
    private Label lblStudentId;

    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtReferenceNum;

    @FXML
    private Label lblStudentNameShow;

    @FXML
    private Label lblStudentPaymentshow;

    @FXML
    private Button btnReset;

    private PaymentModel paymentModel = new PaymentModel();
    private StudentModel studentModel = new StudentModel();
    private RegistrationModel registrationModel = new RegistrationModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colStudentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPayType.setCellValueFactory(new PropertyValueFactory<>("payType"));
        colReferenceType.setCellValueFactory(new PropertyValueFactory<>("referenceNum"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        refresh();
    }
    private void getAllPaymnets(){
        try {
            ObservableList<PaymentTm> paymentTms = paymentModel.getAllPayments();
            tblPayment.setItems(paymentTms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getNextPaymentId(){
        try {
            String id = paymentModel.getNextPaymentId();
            lblPaymentIdShow.setText(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getAllStudentIds(){
        try {
            ObservableList<String> allStudentId = studentModel.getAllStudentId();
            cmbStudentId.setItems(allStudentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setStatus(){
        String [] status = {"Paid","Pending","Overdue"};
        ObservableList<String> st = FXCollections.observableArrayList();
        st.addAll(status);
        cmbStatus.setItems(st);
    }
    private void setPayType(){
        String [] payType = {" Credit Card","Bank Transfer","Cash"};
        ObservableList<String> st = FXCollections.observableArrayList();
        st.addAll(payType);
        cmbPayType.setItems(st);
    }
    @FXML
    void btnPaymentDeleteOnAction(ActionEvent event) throws SQLException {
        String paymentId = lblPaymentIdShow.getText();
        String studentId = cmbStudentId.getValue();
        double amount = Double.parseDouble(txtAmount.getText());

        boolean isDelete = paymentModel.paymentDelete(paymentId,studentId,amount);
        if (isDelete){
            new Alert(Alert.AlertType.INFORMATION,"Payment is delete....!").showAndWait();
            refresh();
        }else {
            new Alert(Alert.AlertType.ERROR,"Payment is not delete....!").showAndWait();
        }

    }

    @FXML
    void btnPaymentUpdateOnAction(ActionEvent event) {
        String paymentId = lblPaymentIdShow.getText();
        String studentId = cmbStudentId.getValue();
        String status = cmbStatus.getValue();
        String payType = cmbPayType.getValue();
        String reference = txtReferenceNum.getText();
        String amount1 = txtAmount.getText();
        if (cmbStudentId.getValue().isEmpty()&&cmbStatus.getValue().isEmpty()&&cmbPayType.getValue().isEmpty()&&txtReferenceNum.getText().isEmpty()&&txtAmount.getText().isEmpty()){
            showAlert("Text feild are empty...!","Fill all text field...!");
            return;
        }
        txtReferenceNum.setStyle(txtReferenceNum.getStyle() + ";-fx-border-color: #7367F0;");
        txtAmount.setStyle(txtAmount.getStyle() + ";-fx-border-color: #7367F0;");

        String referencePAttern = "^REF[0-9]{3,}$";
        String amountPattern = "^[0-9]+(\\.[0-9]{1,2})?$";

        boolean isValidReference = reference.matches(referencePAttern);
        boolean isValidAmount = amount1.matches(amountPattern);

        if (!isValidReference) {
            txtReferenceNum.setStyle(txtReferenceNum.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input Reference number is invalid...!");
            showAlert("Invalid Reference number", "Please enter a valid Reference number");
            return;
        }
        if (!isValidAmount) {
            txtAmount.setStyle(txtAmount.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input Amount is invalid....!");
            showAlert("Invalid Amount", "Please enter a valid Amount.");
            return;
        }
        double vvvv;
        try {
             vvvv = paymentModel.getAmountDue(studentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        double balance = Double.parseDouble(txtAmount.getText()) - vvvv;
        double amount = Double.parseDouble(txtAmount.getText());
        System.out.println("payment table eke thiyena gana "+vvvv);
        System.out.println(amount);
        System.out.println(balance);

        if (isValidReference&&isValidAmount){

            PaymentDto paymentDto = new PaymentDto(paymentId,studentId,status,payType,reference,amount);

            try {
                boolean isUpdate = paymentModel.paymentUpdate(paymentDto,balance);
                if (isUpdate){
                    new Alert(Alert.AlertType.INFORMATION,"Payment is update....!").showAndWait();
                    refresh();
                }else{
                    new Alert(Alert.AlertType.ERROR,"Payment is not update....!").showAndWait();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void btnSavePaymentOnAction(ActionEvent event) {
        String paymentId = lblPaymentIdShow.getText();
        String studentId = cmbStudentId.getValue();
        String status = cmbStatus.getValue();
        String payType = cmbPayType.getValue();
        String reference = txtReferenceNum.getText();
        String amount = txtAmount.getText();

        if (cmbStudentId.getValue().isEmpty()&&cmbStatus.getValue().isEmpty()&&cmbPayType.getValue().isEmpty()&&txtReferenceNum.getText().isEmpty()&&txtAmount.getText().isEmpty()){
            showAlert("Text feild are empty...!","Fill all text field...!");
            return;
        }
        txtReferenceNum.setStyle(txtReferenceNum.getStyle() + ";-fx-border-color: #7367F0;");
        txtAmount.setStyle(txtAmount.getStyle() + ";-fx-border-color: #7367F0;");

        String referencePAttern = "^REF[0-9]{3,}$";
        String amountPattern = "^[0-9]+(\\.[0-9]{1,2})?$";

        boolean isValidReference = reference.matches(referencePAttern);
        boolean isValidAmount = amount.matches(amountPattern);

        if (!isValidReference) {
            txtReferenceNum.setStyle(txtReferenceNum.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input Reference number is invalid...!");
            showAlert("Invalid Reference number", "Please enter a valid Reference number");
            return;
        }
        if (!isValidAmount) {
            txtAmount.setStyle(txtAmount.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input Amount is invalid....!");
            showAlert("Invalid Amount", "Please enter a valid Amount.");
            return;
        }
        double amount1 = Double.parseDouble(txtAmount.getText());

        if (isValidReference&&isValidAmount){

            PaymentDto paymentDto = new PaymentDto(paymentId,studentId,status,payType,reference,amount1);

            try {
                boolean isSaved = paymentModel.paymentSave(paymentDto);
                System.out.println(isSaved);
                if (isSaved){
                    new Alert(Alert.AlertType.INFORMATION,"Payment is saved....!").showAndWait();
                    refresh();
                }else{
                    new Alert(Alert.AlertType.ERROR,"Payment is not saved....!").showAndWait();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void cmbStudentIdOnAction(ActionEvent event) {
        String id = cmbStudentId.getSelectionModel().getSelectedItem();
        try {
            String name = studentModel.getStudentName(id);
            lblStudentNameShow.setText(name);
            double amountDue = registrationModel.getAmountDue(id);
            lblStudentPaymentshow.setText(String.valueOf(amountDue));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void tblPaymentOnCliked(MouseEvent event) {
        PaymentTm paymentTm = tblPayment.getSelectionModel().getSelectedItem();
        if (paymentTm == null){
            showAlert("Wrong row","You cliked wrong row....!");
            return;
        }
        lblPaymentIdShow.setText(paymentTm.getPaymentId());
        cmbStudentId.setValue(paymentTm.getStudentId());
        cmbStatus.setValue(paymentTm.getStatus());
        cmbPayType.setValue(paymentTm.getPayType());
        txtReferenceNum.setText(paymentTm.getReferenceNum());
        txtAmount.setText(String.valueOf(paymentTm.getAmount()));
        btnSavePayment.setDisable(true);
        btnPaymentDelete.setDisable(false);
        btnPaymentUpdate.setDisable(false);

    }
    private void refresh(){
        getAllPaymnets();
        getNextPaymentId();
        getAllStudentIds();
        setStatus();
        setPayType();
        cmbStudentId.setValue("");
        cmbStatus.setValue("");
        cmbPayType.setValue("");
        txtReferenceNum.setText("");
        txtAmount.setText("");
        lblStudentNameShow.setText("");
        btnSavePayment.setDisable(false);
        btnPaymentDelete.setDisable(true);
        btnPaymentUpdate.setDisable(true);
    }
    @FXML
    void btnResetOnAction(ActionEvent event) {
        refresh();
    }
    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
