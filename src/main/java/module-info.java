module lk.ijse.gdse71.final_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires java.desktop;

    opens lk.ijse.gdse71.final_project.dto.tm to javafx.base;
    opens lk.ijse.gdse71.final_project.controller to javafx.fxml;
    exports lk.ijse.gdse71.final_project;
}