package lk.ijse.gdse71.final_project.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.gdse71.final_project.CrudUtil.CrudUtil;
import lk.ijse.gdse71.final_project.dto.StudentDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentModel {

    public String getNextStudentId() throws SQLException {
       ResultSet resultSet = CrudUtil.execute("select Student_id from student order by Student_id desc limit 1;");

       while (resultSet.next()){
           String id = resultSet.getString(1);
           String subString = id.substring(1);
           int nextId = Integer.parseInt(subString)+1;
           return String.format("S%03d",nextId);
       }
       return "S001";
    }

    public ArrayList<StudentDto> getAllStudent() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select * from student");

        ArrayList<StudentDto> studentDtos = new ArrayList<>();

        while (resultSet.next()){
            StudentDto studentDto = new StudentDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
            studentDtos.add(studentDto);
        }
        return studentDtos;
    }

    public boolean saveStudent(StudentDto studentDto) throws SQLException {
        System.out.println("model "+studentDto.toString());

        return  CrudUtil.execute(" INSERT INTO student VALUES(?,?,?,?,?,?);"
                ,studentDto.getStudent_id(),
                studentDto.getName(),
                studentDto.getEmail(),
                studentDto.getAddress(),
                studentDto.getGender(),
                null);
    }

    public boolean studentUpdate(StudentDto studentDto) throws SQLException {

        return CrudUtil.execute("UPDATE student SET Name = ?,Email = ?,Address = ?,Gender = ?, add_notes = ? WHERE Student_id = ?;",
                studentDto.getName(),
                studentDto.getEmail(),
                studentDto.getAddress(),
                studentDto.getGender(),
                null,
                studentDto.getStudent_id()
                );
    }

    public boolean studentDelete(String id) throws SQLException {

        return  CrudUtil.execute("DELETE FROM student WHERE Student_id = ?",id);
    }

    public  ObservableList<String> getAllStudentId() throws SQLException {

        ResultSet resultSet = CrudUtil.execute("select Student_id from student;");

        ObservableList<String> ids = FXCollections.observableArrayList();

        while (resultSet.next()){
            String id = resultSet.getString(1);
            ids.add(id);
        }
        return  ids;
    }

    public String getStudentName(String id) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Name from student where Student_id = ?;",id);

        while (resultSet.next()){
            return resultSet.getString(1);
        }

        return null;
    }

    public String getStudentNote(String id) throws SQLException {

        ResultSet resultSet = CrudUtil.execute("select add_notes from student where Student_id = ?;",id);

        while (resultSet.next()){
            return resultSet.getString(1);
        }

        return null;
    }

    public boolean saveNote(String id,String note) throws SQLException {
        return CrudUtil.execute("UPDATE student SET add_notes = ? WHERE Student_id = ?;",note,id);
    }

    public String getStudentMail(String id) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("select Email from student where student_id = ?;",id);

        while (resultSet.next()){
            return  resultSet.getString(1);
        }
        return null;
    }
}

