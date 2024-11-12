package lk.ijse.gdse71.final_project.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExpenseDto {
    private String expenseId;
    private String desc;
    private double amount;
    private Date date;
    private String category;
}
