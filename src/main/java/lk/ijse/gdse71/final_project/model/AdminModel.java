package lk.ijse.gdse71.final_project.model;

import lk.ijse.gdse71.final_project.CrudUtil.CrudUtil;
import lk.ijse.gdse71.final_project.dto.AdminDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminModel {


    public AdminDto checkAdmin(String userName) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM admin WHERE User_name = ?", userName);

        while (resultSet.next()){
            AdminDto adminDto = new AdminDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            return adminDto;
        }
        return null;
    }

    public boolean addAdmin(AdminDto adminDto) throws SQLException {
        return CrudUtil.execute("INSERT INTO admin values(?,?,?,?,?)",
                adminDto.getAdminId(),
                adminDto.getUserName(),
                adminDto.getEmail(),
                adminDto.getPassword(),
                adminDto.getRole()
        );
    }

    public String getNextAdminId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Admin_id from admin order by Admin_id desc limit 1;");

        while (resultSet.next()){
            String id = resultSet.getString(1);
            String subString = id.substring(1);
            int next = Integer.parseInt(subString) + 1;
            return String.format("A%03d", next);
        }
        return "A001";
    }
}
