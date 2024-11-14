package lk.ijse.gdse71.final_project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse71.final_project.dto.CourseDto;
import lk.ijse.gdse71.final_project.dto.tm.CourseTm;
import lk.ijse.gdse71.final_project.model.CourseModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CourseViewController implements Initializable {
    @FXML
    public Label lblPayment;
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<CourseTm, String> colCourseId;

    @FXML
    private TableColumn<CourseTm,String> colCourseName;

    @FXML
    private TableColumn<CourseTm, Integer> colDuration;

    @FXML
    private TableColumn<CourseTm, Double> colPayment;

    @FXML
    private Label lblCourseId;

    @FXML
    private Label lblCourseIdShow;

    @FXML
    private Label lblCourseName;

    @FXML
    private Label lblCourseNameShow;

    @FXML
    private Label lblDuration;

    @FXML
    private Label lblDurationShow;

    @FXML
    private TextField txtCourseName;

    @FXML
    private TextField txtDuration;
    @FXML
    private TextField txtPayment;

    @FXML
    private TableView<CourseTm> tblCourse;

    @FXML
    private Button btnReset;

    private final CourseModel courseModel = new CourseModel();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));

        refresh();
    }
    public void getAllCourses(){
        try {
            ArrayList<CourseDto> courseDtos = courseModel.getAllCourses();
            ObservableList<CourseTm> courseTms = FXCollections.observableArrayList();

            for (CourseDto courseDto : courseDtos ){
                CourseTm courseTm = new CourseTm(
                        courseDto.getCourseId(),
                        courseDto.getCourseName(),
                        courseDto.getDuration(),
                        courseDto.getPayment()
                );
                courseTms.add(courseTm);
            }
            tblCourse.setItems(courseTms);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = lblCourseIdShow.getText();
        try {
            boolean isDelete = courseModel.deleteCourse(id);
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION,"Successfully Delete......!").showAndWait();
                refresh();
            }else{
                new Alert(Alert.AlertType.ERROR,"Delete unsuccessfully......!").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


@FXML
void btnSaveOnAction(ActionEvent event) {
    String id = lblCourseIdShow.getText();
    String name = txtCourseName.getText();
    String durationText = txtDuration.getText();
    String paymentText = txtPayment.getText();

    if (name.isEmpty() || durationText.isEmpty() || paymentText.isEmpty()) {
        showAlert("Empty Fields", "Please fill in all text fields.");
        txtCourseName.setStyle(txtCourseName.getStyle() + ";-fx-border-color: red;");
        txtDuration.setStyle(txtDuration.getStyle() + ";-fx-border-color: red;");
        txtPayment.setStyle(txtPayment.getStyle() + ";-fx-border-color: red;");
        return;
    }

    String courseNamePattern = "^[A-Za-z0-9 ]+$";
    String durationPattern = "^[1-9][0-9]*$";
    String paymentPattern = "^[0-9]+(\\.[0-9]{1,2})?$";

    boolean isValidName = name.matches(courseNamePattern);
    boolean isValidDuration = durationText.matches(durationPattern);
    boolean isValidPayment = paymentText.matches(paymentPattern);

    if (!isValidName) {
        txtCourseName.setStyle(txtCourseName.getStyle() + ";-fx-border-color: red;");
        showAlert("Invalid Course Name", "Please enter a valid course name (letters, numbers, spaces only).");
        txtCourseName.requestFocus();
        return;
    } else {
        txtCourseName.setStyle(txtCourseName.getStyle() + ";-fx-border-color: blue;");
    }

    if (!isValidDuration) {
        txtDuration.setStyle(txtDuration.getStyle() + ";-fx-border-color: red;");
        showAlert("Invalid Duration", "Please enter a valid duration in months (positive whole numbers only).");
        txtDuration.requestFocus();
        return;
    } else {
        txtDuration.setStyle(txtDuration.getStyle() + ";-fx-border-color: blue;");
    }

    if (!isValidPayment) {
        txtPayment.setStyle(txtPayment.getStyle() + ";-fx-border-color: red;");
        showAlert("Invalid Payment Amount", "Please enter a valid payment amount (e.g., 1500, 1200.50).");
        txtPayment.requestFocus();
        return;
    } else {
        txtPayment.setStyle(txtPayment.getStyle() + ";-fx-border-color: blue;");
    }

    int duration = Integer.parseInt(durationText);
    double payment = Double.parseDouble(paymentText);

    if (isValidName && isValidDuration && isValidPayment) {
        CourseDto courseDto = new CourseDto(id, name, duration, payment);

        try {
            boolean isSaved = courseModel.savaCourse(courseDto);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Course is saved successfully!").showAndWait();
                refresh();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save course.").showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

    public void getNextcourseId(){
        try {
            String id = courseModel.getNextCourseId();
            lblCourseIdShow.setText(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = lblCourseIdShow.getText();
        String name = txtCourseName.getText();
        String durationText = txtDuration.getText();
        String paymentText = txtPayment.getText();

        if (name.isEmpty() || durationText.isEmpty() || paymentText.isEmpty()) {
            showAlert("Empty Fields", "Please fill in all text fields.");
            txtCourseName.setStyle(txtCourseName.getStyle() + ";-fx-border-color: red;");
            txtDuration.setStyle(txtDuration.getStyle() + ";-fx-border-color: red;");
            txtPayment.setStyle(txtPayment.getStyle() + ";-fx-border-color: red;");
            return;
        }

        String courseNamePattern = "^[A-Za-z0-9 ]+$";
        String durationPattern = "^[1-9][0-9]*$";
        String paymentPattern = "^[0-9]+(\\.[0-9]{1,2})?$";

        boolean isValidName = name.matches(courseNamePattern);
        boolean isValidDuration = durationText.matches(durationPattern);
        boolean isValidPayment = paymentText.matches(paymentPattern);

        if (!isValidName) {
            txtCourseName.setStyle(txtCourseName.getStyle() + ";-fx-border-color: red;");
            showAlert("Invalid Course Name", "Please enter a valid course name (letters, numbers, spaces only).");
            txtCourseName.requestFocus();
            return;
        } else {
            txtCourseName.setStyle(txtCourseName.getStyle() + ";-fx-border-color: blue;");
        }

        if (!isValidDuration) {
            txtDuration.setStyle(txtDuration.getStyle() + ";-fx-border-color: red;");
            showAlert("Invalid Duration", "Please enter a valid duration in months (positive whole numbers only).");
            txtDuration.requestFocus();
            return;
        } else {
            txtDuration.setStyle(txtDuration.getStyle() + ";-fx-border-color: blue;");
        }

        if (!isValidPayment) {
            txtPayment.setStyle(txtPayment.getStyle() + ";-fx-border-color: red;");
            showAlert("Invalid Payment Amount", "Please enter a valid payment amount (e.g., 1500, 1200.50).");
            txtPayment.requestFocus();
            return;
        } else {
            txtPayment.setStyle(txtPayment.getStyle() + ";-fx-border-color: blue;");
        }

        int duration = Integer.parseInt(durationText);
        double payment = Double.parseDouble(paymentText);

        if (isValidName && isValidDuration && isValidPayment){

            CourseDto courseDto = new CourseDto(id, name, duration, payment);

            try {
                boolean isUpdate = courseModel.updatecourse(courseDto);
                if (isUpdate){
                    new Alert(Alert.AlertType.INFORMATION,"Course is update......!").showAndWait();
                    refresh();
                }else{
                    new Alert(Alert.AlertType.ERROR,"Course is not update......!").showAndWait();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @FXML
    void tblCourseOnCliked(MouseEvent event) {
        CourseTm courseTm = tblCourse.getSelectionModel().getSelectedItem();
        if (courseTm == null){
            showAlert("Wrong row","You cliked wrong row....!");
            return;
        }
        lblCourseIdShow.setText(courseTm.getCourseId());
        txtCourseName.setText(courseTm.getCourseName());
        txtDuration.setText(String.valueOf(courseTm.getDuration()));
        txtPayment.setText(String.valueOf(courseTm.getPayment()));
        btnSave.setDisable(true);
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);

    }
    public void refresh(){
        getNextcourseId();
        txtCourseName.setText("");
        txtDuration.setText("");
        txtPayment.setText("");
        getAllCourses();
        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }
    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void btnResetOnAction(ActionEvent event) {
        refresh();
    }

}
