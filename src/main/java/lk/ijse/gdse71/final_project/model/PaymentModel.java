package lk.ijse.gdse71.final_project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse71.final_project.CrudUtil.CrudUtil;
import lk.ijse.gdse71.final_project.db.DBConnection;
import lk.ijse.gdse71.final_project.dto.PaymentDto;
import lk.ijse.gdse71.final_project.dto.tm.PaymentTm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentModel {
    private RegistrationModel registrationModel = new RegistrationModel();

    public ObservableList<PaymentTm> getAllPayments() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from payment;");
        ObservableList<PaymentTm> paymentTms = FXCollections.observableArrayList();
        while (resultSet.next()){
            PaymentTm paymentTm = new PaymentTm(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDouble(6),
                    resultSet.getDate(7)
            );
            paymentTms.add(paymentTm);
        }
        return paymentTms;
    }

    public String getNextPaymentId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Payment_id from payment order by Payment_id desc limit 1;");

        while (resultSet.next()){
            String id = resultSet.getString(1);
            String subString = id.substring(1);
            int nextId = Integer.parseInt(subString)+1;
            return String.format("P%03d",nextId);
        }
        return "P001";
    }

    public boolean paymentSave(PaymentDto paymentDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            boolean isSaved = CrudUtil.execute("insert into payment values(?,?,?,?,?,?);",
                    paymentDto.getPaymentId(),
                    paymentDto.getStudentId(),
                    paymentDto.getStatus(),
                    paymentDto.getPayType(),
                    paymentDto.getReferenceNum(),
                    paymentDto.getAmount()
            );
            if (isSaved){
                boolean isPaymentReduce = registrationModel.paymentReduce(paymentDto.getStudentId(),paymentDto.getAmount());
                if (isPaymentReduce){
                    connection.commit();
                    return true;
                }

            }
            connection.rollback();
            return false;

        }catch (Exception e){
            e.printStackTrace();
            connection.rollback();
            return false;

        }finally {

            connection.setAutoCommit(true);
        }
    }

    public boolean paymentDelete(String paymentId, String studentId, double amount) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            boolean isDelete = CrudUtil.execute("delete from payment where Payment_id = ? ",
                    paymentId
            );
            if (isDelete){
                boolean isPaymentReduce = registrationModel.addPayment(studentId,amount);
                if (isPaymentReduce){
                    connection.commit();
                    return true;
                }

            }
            connection.rollback();
            return false;

        }catch (Exception e){
            e.printStackTrace();
            connection.rollback();
            return false;

        }finally {

            connection.setAutoCommit(true);
        }
    }

    public boolean paymentUpdate(PaymentDto paymentDto, double balance) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            boolean isUpdate = CrudUtil.execute("UPDATE payment SET Student_id =?, Status = ?,Pay_type = ?,Reference_num = ?,Amount = ?,payment_date = ? WHERE Payment_id = ?;",
                    paymentDto.getStudentId(),
                    paymentDto.getStatus(),
                    paymentDto.getPayType(),
                    paymentDto.getReferenceNum(),
                    paymentDto.getAmount(),
                    paymentDto.getPaymentDate(),
                    paymentDto.getPaymentId()
            );
            if (isUpdate){
                boolean isUpdateAmount;
                if (paymentDto.getAmount() < 0){
                    isUpdateAmount = registrationModel.addPayment(paymentDto.getStudentId(),balance);
                }else {
                    isUpdateAmount = registrationModel.paymentReduce(paymentDto.getStudentId(),balance);
                }
                if (isUpdateAmount){
                    connection.commit();
                    return true;
                }


            }
            connection.rollback();
            return false;
        }catch (Exception e){
            e.printStackTrace();
            connection.rollback();
            return false;

        }finally {

            connection.setAutoCommit(true);
        }
    }

    public double getAmountDue(String studentId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Amount from payment where Student_id = ? order by Reference_num desc limit 1;",studentId);

        while (resultSet.next()){
            return resultSet.getDouble(1);
        }
        return 0;
    }
}
