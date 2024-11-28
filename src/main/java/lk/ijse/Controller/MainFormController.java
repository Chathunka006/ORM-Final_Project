package lk.ijse.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.Dao.Custom.UserDao;
import lk.ijse.Dao.DaoFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainFormController {

    public Button btnSettings;
    @FXML
    private AnchorPane anpMain;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnProgram;

    @FXML
    private Button btnStudent;

    @FXML
    private Button btnUser;

    @FXML
    private Label lblCurrentUser;

    @FXML
    private Label lblDate;
    private boolean userAllowed;

    private boolean studentAllowed;

    private boolean paymentAllowed;

    private boolean programAllowed;

    private boolean settingsAllowed;
    UserDao userDao = (UserDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.USER);
    String username;

    public void initialize() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd           HH:mm:ss");

        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            String formattedDateTime = LocalDateTime.now().format(formatter);
            lblDate.setText(formattedDateTime);
        }));

        clock.setCycleCount(Timeline.INDEFINITE); // Run indefinitely
        clock.play();
        loadDashboardForm();
    }
    public void loadDashboardForm() throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/DashboardForm.fxml"));

        anpMain.getChildren().clear();
        anpMain.getChildren().add(dashboardPane);
    }
    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        AnchorPane dashboardPane = FXMLLoader.load(this.getClass().getResource("/view/DashboardForm.fxml"));

        anpMain.getChildren().clear();
        anpMain.getChildren().add(dashboardPane);
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) throws IOException {
        AnchorPane paymentPane = FXMLLoader.load(this.getClass().getResource("/view/PaymentForm.fxml"));

        anpMain.getChildren().clear();
        anpMain.getChildren().add(paymentPane);

    }

    @FXML
    void btnProgramOnAction(ActionEvent event) throws IOException {
        AnchorPane coursePane = FXMLLoader.load(this.getClass().getResource("/view/CourseForm.fxml"));

        anpMain.getChildren().clear();
        anpMain.getChildren().add(coursePane);
    }

    @FXML
    void btnStudentOnAction(ActionEvent event) throws IOException {

        AnchorPane studentPane = FXMLLoader.load(this.getClass().getResource("/view/StudentForm.fxml"));

        anpMain.getChildren().clear();
        anpMain.getChildren().add(studentPane);
    }

    @FXML
    void btnUserOnAction(ActionEvent event) throws IOException {
        AnchorPane userPane = FXMLLoader.load(this.getClass().getResource("/view/UserForm.fxml"));

        anpMain.getChildren().clear();
        anpMain.getChildren().add(userPane);
    }

    public void btnSettingsOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane settingPane = FXMLLoader.load(this.getClass().getResource("/view/SettingForm.fxml"));

        anpMain.getChildren().clear();
        anpMain.getChildren().add(settingPane);
    }
    public void setUsername(String username) {
        this.username = username;
        String userRole = getUserRole(username);
        setUserRole(userRole);
    }

    private String getUserRole(String username) {
        return userDao.getUserRole(username); // Ensure getUserRole(username) exists in UserDao and returns the role as a String
    }

    private void setUserRole(String userRole) {
        if (userRole != null) {
            lblCurrentUser.setText(userRole); // Display the user role on the label
        } else {
            lblCurrentUser.setText("Role not found");
        }
    }

    public void setAccess(String userRole) {
        if (userRole == null) {
            lblCurrentUser.setText("No role assigned");
            disableAllButtons();
            return;
        }

        lblCurrentUser.setText(userRole);

        resetAccessFlags();

        System.out.println("User Role: " + userRole);

        switch (userRole) {
            case "Admin":
                enableAllAccess();
                break;

            case "Coordinator":
                studentAllowed = true;
                break;

            default:
                System.out.println("Unknown role: " + userRole);
                break;
        }

        updateButtonStates();
    }

    private void enableAllAccess() {
        userAllowed = true;
        studentAllowed = true;
        paymentAllowed = true;
        programAllowed = true;
        settingsAllowed = true;
    }

    private void resetAccessFlags() {
        userAllowed = false;
        studentAllowed = false;
        paymentAllowed = false;
        programAllowed = false;
        settingsAllowed = false;
    }

    private void updateButtonStates() {
        btnUser.setDisable(!userAllowed);
        btnStudent.setDisable(!studentAllowed);
        btnPayment.setDisable(!paymentAllowed);
        btnProgram.setDisable(!programAllowed);
        btnSettings.setDisable(!settingsAllowed);
    }

    private void disableAllButtons() {
        btnUser.setDisable(true);
        btnStudent.setDisable(true);
        btnPayment.setDisable(true);
        btnProgram.setDisable(true);
        btnSettings.setDisable(true);
    }
}
