package lk.ijse.gdse71.final_project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse71.final_project.CrudUtil.CrudUtil;
import lk.ijse.gdse71.final_project.dto.AdminDto;
import lk.ijse.gdse71.final_project.dto.tm.AdminTm;

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

    public ObservableList<AdminTm> getAllAdmins() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select  * from admin;");
        ObservableList<AdminTm> adminTms = FXCollections.observableArrayList();

        while (resultSet.next()){
            AdminTm adminTm = new AdminTm(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            adminTms.add(adminTm);
        }
        return adminTms;
    }

    public boolean saveAdmin(AdminDto adminDto) throws SQLException {
        return CrudUtil.execute("insert into admin values(?,?,?,?,?)",
                adminDto.getAdminId(),
                adminDto.getUserName(),
                adminDto.getEmail(),
                adminDto.getPassword(),
                adminDto.getRole()
        );
    }

    public boolean adminUpdate(AdminDto adminDto) throws SQLException {
        return CrudUtil.execute("UPDATE admin SET User_name = ?, Email = ?, Password = ?,Role = ? WHERE Admin_id = ?;",
                adminDto.getUserName(),
                adminDto.getEmail(),
                adminDto.getPassword(),
                adminDto.getRole(),
                adminDto.getAdminId()
        );
    }

    public boolean deleteAdmin(String id) throws SQLException {
        return CrudUtil.execute("delete from admin where Admin_id = ?;",id);
    }

    public String getAdminPass(String pass) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Password from admin where Password = ?;",pass);

        while (resultSet.next()){
            String id  = resultSet.getString(1);
            return  id;
        }
        return null;
    }
}
