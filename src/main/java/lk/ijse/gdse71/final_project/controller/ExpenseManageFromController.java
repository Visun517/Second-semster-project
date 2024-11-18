package lk.ijse.gdse71.final_project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.final_project.dto.ExamDto;
import lk.ijse.gdse71.final_project.dto.ExpenseDto;
import lk.ijse.gdse71.final_project.dto.tm.ExpenseTm;
import lk.ijse.gdse71.final_project.model.ExpenseModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ResourceBundle;

public class ExpenseManageFromController implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ComboBox<String> cmbCategory;

    @FXML
    private TableColumn<ExpenseTm, Double> colAmount;

    @FXML
    private TableColumn<ExpenseTm, String> colCategory;

    @FXML
    private TableColumn<ExpenseTm, String> colDescription;

    @FXML
    private TableColumn<ExpenseTm, Date> colExpenseDate;

    @FXML
    private TableColumn<ExpenseTm, String> colExpenseId;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblCategory;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDateShow;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblExpanseIdShow;

    @FXML
    private Label lblExpenseId;

    @FXML
    private TableView<ExpenseTm> tblExpense;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtDescription;

    @FXML
    private Button btnReset;

    private ExpenseModel expenseModel = new ExpenseModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colExpenseId.setCellValueFactory(new PropertyValueFactory<>("expenseId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("desc"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colExpenseDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        lblDateShow.setText(LocalDate.now().toString());
        refresh();
    }
    private void getAllExpense(){
        try {
            ObservableList<ExpenseTm> expenseTms = expenseModel.getAllExpense();
            tblExpense.setItems(expenseTms);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getNextExpenseId(){
        try {
            String id = expenseModel.getNextexpenseId();
            lblExpanseIdShow.setText(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void setCategory(){
        String []cate = {"Supplies","Software","Utilities","Travel","Maintenance"};
        ObservableList<String> cates = FXCollections.observableArrayList();
        cates.addAll(cate);
        cmbCategory.setItems(cates);
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = lblExpanseIdShow.getText();
        try {
            boolean isDelete = expenseModel.deleteExpense(id);
            if (isDelete){
                showAlert(Alert.AlertType.INFORMATION,"Delete", "Delete successfully....!");
                refresh();
            }else{
                showAlert(Alert.AlertType.ERROR,"Delete", "Delete unsuccessfully....!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = lblExpanseIdShow.getText();
        String desc = txtDescription.getText();
        Date date = Date.valueOf(lblDateShow.getText());
        String cate = cmbCategory.getValue();
        String amount1 = txtAmount.getText();

        if (txtDescription.getText().isEmpty()&&txtAmount.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Text feild are empty...!","Fill all text field...!");
            return;
        }
        if (cmbCategory.getSelectionModel().getSelectedItem().isEmpty()) {
            showAlert(Alert.AlertType.ERROR,"Selection Required", "Please select a value from the ComboBox.");
            cmbCategory.requestFocus(); // Focus on the ComboBox
            return;
        } else {
            System.out.println("Selected Value: " + cmbCategory.getValue());
        }
        txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: #7367F0;");
        txtAmount.setStyle(txtAmount.getStyle() + ";-fx-border-color: #7367F0;");

        String descriptionPattern = "^[A-Za-z0-9.,()&\\-_ ]+$";
        String amountPattern = "^[0-9]+(\\.[0-9]{1,2})?$";

        boolean isValidDescription = desc.matches(descriptionPattern);
        boolean isValidAmount = amount1.matches(amountPattern);

        if (!isValidDescription) {
            txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input Description is invalid...!");
            showAlert(Alert.AlertType.ERROR,"Invalid Description", "Please enter a valid Description.");
            return;
        }
        if (!isValidAmount) {
            txtAmount.setStyle(txtAmount.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input Amount is invalid....!");
            showAlert(Alert.AlertType.ERROR,"Invalid Amount", "Please enter a valid Amount.");
            return;
        }
        double amount = Double.parseDouble(txtAmount.getText());
        if (isValidDescription && isValidAmount){
            ExpenseDto expenseDto = new ExpenseDto(id,desc,amount,date,cate);

            try {
                boolean isSaved = expenseModel.saveExpense(expenseDto);
                if (isSaved){
                    showAlert(Alert.AlertType.INFORMATION,"Saved", "Saved successfully....!");
                    refresh();
                }else{
                    showAlert(Alert.AlertType.ERROR,"Saved", "Saved unsuccessfully....!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = lblExpanseIdShow.getText();
        String desc = txtDescription.getText();
        Date date = Date.valueOf(lblDateShow.getText());
        String cate = cmbCategory.getValue();
        String amount1 = txtAmount.getText();

        if (txtDescription.getText().isEmpty()&&txtAmount.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Text feild are empty...!","Fill all text field...!");
            return;
        }
        if (cmbCategory.getSelectionModel().getSelectedItem().isEmpty()) {
            showAlert(Alert.AlertType.ERROR,"Selection Required", "Please select a value from the ComboBox.");
            cmbCategory.requestFocus(); // Focus on the ComboBox
            return;
        } else {
            System.out.println("Selected Value: " + cmbCategory.getValue());
        }
        txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: #7367F0;");
        txtAmount.setStyle(txtAmount.getStyle() + ";-fx-border-color: #7367F0;");

        String descriptionPattern = "^[A-Za-z0-9.,()&\\-_ ]+$";
        String amountPattern = "^[0-9]+(\\.[0-9]{1,2})?$";

        boolean isValidDescription = desc.matches(descriptionPattern);
        boolean isValidAmount = amount1.matches(amountPattern);

        if (!isValidDescription) {
            txtDescription.setStyle(txtDescription.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input Description is invalid...!");
            showAlert(Alert.AlertType.ERROR,"Invalid Description", "Please enter a valid Description.");
            return;
        }
        if (!isValidAmount) {
            txtAmount.setStyle(txtAmount.getStyle() + ";-fx-border-color: red;");
            System.out.println("Input Amount is invalid....!");
            showAlert(Alert.AlertType.ERROR,"Invalid Amount", "Please enter a valid Amount.");
            return;
        }
        double amount = Double.parseDouble(txtAmount.getText());

        if (isValidDescription && isValidAmount){
            ExpenseDto expenseDto = new ExpenseDto(id,desc,amount,date,cate);

            try {
                boolean isUpdate = expenseModel.expenseUpdate(expenseDto);
                if (isUpdate){
                    showAlert(Alert.AlertType.INFORMATION,"Update", "Update successfully....!");
                    refresh();
                }else{
                    showAlert(Alert.AlertType.ERROR,"Update", "Update unsuccessfully....!");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void cmbCategoryOnAction(ActionEvent event) {

    }
    @FXML
    void tblExpenseOncliked(MouseEvent event) {
        ExpenseTm expenseTm =tblExpense.getSelectionModel().getSelectedItem();
        if (expenseTm == null){
            showAlert(Alert.AlertType.ERROR,"Wrong row","You cliked wrong row....!");
            return;
        }
        lblExpanseIdShow.setText(expenseTm.getExpenseId());
        txtDescription.setText(expenseTm.getDesc());
        txtAmount.setText(String.valueOf(expenseTm.getAmount()));
        lblDateShow.setText(String.valueOf(expenseTm.getDate()));
        cmbCategory.setValue(expenseTm.getCategory());
        btnSave.setDisable(true);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);

    }
    private void refresh(){
        getAllExpense();
        getNextExpenseId();
        setCategory();
        txtDescription.setText("");
        txtAmount.setText("");
        cmbCategory.setValue("");
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
