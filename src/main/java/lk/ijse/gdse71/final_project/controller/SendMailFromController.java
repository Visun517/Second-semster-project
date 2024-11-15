package lk.ijse.gdse71.final_project.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.gdse71.final_project.model.StudentModel;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendMailFromController implements Initializable {

    @FXML
    private Button btnSendMail;

    @FXML
    private ComboBox<String> cmbAtudentId;

    @FXML
    private Label lblEmailshow;

    @FXML
    private Label lblStudentNameShow;

    @FXML
    private TextField txtBody;

    @FXML
    private TextField txtSubject;

    private final StudentModel studentModel = new StudentModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getStudentIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to load all student IDs to ComboBox
    private void getStudentIds() throws SQLException {
        ObservableList<String> allStudentId = studentModel.getAllStudentId();
        cmbAtudentId.setItems(allStudentId);
    }

    // On selecting student ID, show student name and email
    @FXML
    void cmbAtudentIdOnAction(ActionEvent event) {
        String id = cmbAtudentId.getValue();
        try {
            String studentName = studentModel.getStudentName(id);
            lblStudentNameShow.setText(studentName);

            String mail = studentModel.getStudentMail(id);
            lblEmailshow.setText(mail);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load student details.").show();
        }
    }

    // Method to send email
    @FXML
    void btnSendMailOnAction(ActionEvent event) throws MessagingException {
        // Get recipient email, subject, and body from user input
        String recipientEmail = lblEmailshow.getText();
        String subject = txtSubject.getText();
        String body = txtBody.getText();

        // Input validation
        if (recipientEmail.isEmpty() || subject.isEmpty() || body.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields before sending email!").show();
            return;
        }

        // Sender's email and app password (Replace these with your details)
        final String FROM_EMAIL = "visunpraboda999@gmail.com";
        final String PASSWORD = "bfjy iewp ndan indd"; // Gmail app password

        // Call method to send email
        sendEmailWithGmail(FROM_EMAIL, PASSWORD, recipientEmail, subject, body);

    }

    // Method to send email using Gmail SMTP
    private void sendEmailWithGmail(String fromEmail, String password, String toEmail, String subject, String messageBody) throws MessagingException {
        // SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        Message message = prepareMassage(session, fromEmail, toEmail, subject, messageBody);
        if (message != null) {
            Transport.send(message);
            new Alert(Alert.AlertType.INFORMATION, "Email sent successfully..!").showAndWait();
        } else {
            new Alert(Alert.AlertType.ERROR, "Email send unsuccessfully..!").showAndWait();
        }
    }

    // Method to prepare the message
    private Message prepareMassage(Session session, String fromEmail, String toEmail, String subject, String messageBody) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(toEmail)});
            message.setSubject(subject);
            message.setText(messageBody);

            return message;
        } catch (MessagingException e) {
            Logger.getLogger(SendMailFromController.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
