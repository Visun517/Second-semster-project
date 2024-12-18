package lk.ijse.gdse71.final_project.model;

import lk.ijse.gdse71.final_project.CrudUtil.CrudUtil;
import lk.ijse.gdse71.final_project.dto.RegistrationDto;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegistrationModel {
    public ArrayList<RegistrationDto> getAllRegistrations() throws SQLException {

        ResultSet resultSet = CrudUtil.execute("select * from registration");
        ArrayList<RegistrationDto> registrationDtos = new ArrayList<>();

        while (resultSet.next()){
            RegistrationDto registrationDto = new RegistrationDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4),
                    resultSet.getDouble(5)
            );
            registrationDtos.add(registrationDto);
        }
        return registrationDtos;
    }

    public String getNextRegistrationId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Registration_id from registration order by Registration_id desc limit 1;");

        while (resultSet.next()){
            String id = resultSet.getString(1);
            String subString = id.substring(1);
            int nextId = Integer.parseInt(subString)+1;
            return String.format("R%03d",nextId);
        }
        return "R001";
    }

    public boolean registerStudent(RegistrationDto registrationDto) throws SQLException {
        System.out.println(registrationDto.getStudentId());
        return CrudUtil.execute("INSERT INTO registration (Registration_id, Student_id, Course_id, Registration_date,Amount_due) VALUES (?,?,?,?,?);",
                registrationDto.getRegistrationId(),
                registrationDto.getStudentId(),
                registrationDto.getCourseId(),
                registrationDto.getRegistrationDate(),
                registrationDto.getFullPayment()

        );
    }

    public boolean updateStudent(RegistrationDto registrationDto) throws SQLException {
        return CrudUtil.execute("UPDATE registration SET Student_id = ?, Course_id = ?,Registration_date = ?,Amount_due = ? WHERE Registration_id = ?;",
                registrationDto.getStudentId(),
                registrationDto.getCourseId(),
                registrationDto.getRegistrationDate(),
                registrationDto.getFullPayment(),
                registrationDto.getRegistrationId()
        );
    }

    public boolean delete(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM registration WHERE Registration_id = ? ;",id);
    }

    public boolean paymentReduce(String studentId, double amount) throws SQLException {
        return CrudUtil.execute("UPDATE registration  SET Amount_due = Amount_due - ? WHERE Student_id = ?;",
                amount,
                studentId
        );
    }

    public boolean addPayment(String studentId, double amount) throws SQLException {
        return CrudUtil.execute("UPDATE registration  SET Amount_due = Amount_due + ? WHERE Student_id = ?;",
                amount,
                studentId
        );
    }

    public double getAmountDue(String id) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Amount_due from registration where Student_id = ?;",id);

        while (resultSet.next()){
            return resultSet.getDouble(1);
        }
        return 0;
    }
}
