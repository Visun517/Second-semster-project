package lk.ijse.gdse71.final_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Appinitializer extends Application {
    public static void main(String[] args) {
        launch(args);

    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent load = FXMLLoader.load(getClass().getResource("/view/LoginFromView.fxml"));
        Scene scene = new Scene(load);
        stage.setTitle("UGC university");
        stage.setScene(scene);
        stage.show();

        Image image = new Image(getClass().getResourceAsStream("/image/Screenshot 2024-11-12 232120.png"));
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
