package lk.ijse.gdse71.final_project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse71.final_project.CrudUtil.CrudUtil;
import lk.ijse.gdse71.final_project.dto.ExpenseDto;
import lk.ijse.gdse71.final_project.dto.tm.ExpenseTm;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpenseModel {
    public ObservableList<ExpenseTm> getAllExpense() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from expense;");
        ObservableList<ExpenseTm> expenseTms = FXCollections.observableArrayList();

        while (resultSet.next()){
            ExpenseTm expenseTm = new ExpenseTm(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDate(4),
                    resultSet.getString(5)
            );
            expenseTms.add(expenseTm);
        }
        return expenseTms;
    }

    public String getNextexpenseId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select expense_id from expense order by expense_id desc limit 1;");

        while (resultSet.next()){
            String id = resultSet.getString(1);
            String subString = id.substring(1);
            int nextId = Integer.parseInt(subString)+1;
            return String.format("E%03d",nextId);
        }
        return "E001";
    }

    public boolean saveExpense(ExpenseDto expenseDto) throws SQLException {
        return CrudUtil.execute("insert into expense values(?,?,?,?,?)",
                    expenseDto.getExpenseId(),
                expenseDto.getDesc(),
                expenseDto.getAmount(),
                expenseDto.getDate(),
                expenseDto.getCategory()
        );
    }

    public boolean expenseUpdate(ExpenseDto expenseDto) throws SQLException {
        return CrudUtil.execute("UPDATE expense  SET description = ?, amount = ?, expense_date = ?,category = ? WHERE expense_id = ?;",
                expenseDto.getDesc(),
                expenseDto.getAmount(),
                expenseDto.getDate(),
                expenseDto.getCategory(),
                expenseDto.getExpenseId()
         );
    }

    public boolean deleteExpense(String id) throws SQLException {
        return CrudUtil.execute("delete from expense where expense_id = ?;",id);
    }
}
