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
                new Alert(Alert.AlertType.INFORMATION,"Expense is Delete....!").showAndWait();
                refresh();
            }else{
                new Alert(Alert.AlertType.ERROR,"Expense is not Delete....!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = lblExpanseIdShow.getText();
        String desc = txtDescription.getText();
        double amount = Double.parseDouble(txtAmount.getText());
        Date date = Date.valueOf(lblDateShow.getText());
        String cate = cmbCategory.getValue();

        ExpenseDto expenseDto = new ExpenseDto(id,desc,amount,date,cate);

        try {
            boolean isSaved = expenseModel.saveExpense(expenseDto);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Expense is saved....!").showAndWait();
                refresh();
            }else{
                new Alert(Alert.AlertType.ERROR,"Expense is not saved....!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = lblExpanseIdShow.getText();
        String desc = txtDescription.getText();
        double amount = Double.parseDouble(txtAmount.getText());
        Date date = Date.valueOf(lblDateShow.getText());
        String cate = cmbCategory.getValue();

        ExpenseDto expenseDto = new ExpenseDto(id,desc,amount,date,cate);

        try {
            boolean isUpdate = expenseModel.expenseUpdate(expenseDto);
            if (isUpdate){
                new Alert(Alert.AlertType.INFORMATION,"Expense is update....!").showAndWait();
                refresh();
            }else{
                new Alert(Alert.AlertType.ERROR,"Expense is not update....!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void cmbCategoryOnAction(ActionEvent event) {

    }
    @FXML
    void tblExpenseOncliked(MouseEvent event) {
        ExpenseTm expenseTm =tblExpense.getSelectionModel().getSelectedItem();
        lblExpanseIdShow.setText(expenseTm.getExpenseId());
        txtDescription.setText(expenseTm.getDesc());
        txtAmount.setText(String.valueOf(expenseTm.getAmount()));
        lblDateShow.setText(String.valueOf(expenseTm.getDate()));
        cmbCategory.setValue(expenseTm.getCategory());

    }
    private void refresh(){
        getAllExpense();
        getNextExpenseId();
        setCategory();
        lblExpanseIdShow.setText("");
        txtDescription.setText("");
        txtAmount.setText("");
        lblDateShow.setText("");
        cmbCategory.setValue("");
    }
}
