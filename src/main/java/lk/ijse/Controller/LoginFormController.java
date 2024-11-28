package lk.ijse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Dao.Custom.UserDao;
import lk.ijse.Dao.DaoFactory;

import java.io.IOException;

public class LoginFormController {

    public Button btnForgotPassword;
    @FXML
    private AnchorPane anpDashboard;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    UserDao userDao = (UserDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.USER);
    String username;
    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        username = txtUsername.getText();
        String password = txtPassword.getText();

        boolean isAuthenticated = userDao.checkCredential(username, password);

        if (isAuthenticated) {
            navigateToTheDashboard();
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid username or password!").show();
        }
    }
    private void navigateToTheDashboard() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainForm.fxml"));
        AnchorPane anchorPane = loader.load();

        MainFormController mainFormController = loader.getController();
        String userRole = userDao.getUserRole(username);  // Fetch the role from the database
        mainFormController.setUsername(username);
        mainFormController.setAccess(userRole);  // Pass the correct role

        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
        stage.show();

        anpDashboard.getScene().getWindow().hide();
    }

    public void btnForgotPasswordOnAction(ActionEvent actionEvent) {
    }

    public void txtUserNameOnKeyReleased(KeyEvent keyEvent) {
    }

    public void txtPasswordOnKeyReleased(KeyEvent keyEvent) {

    }

    public void txtUserNameOnAction(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void txtPasswordOnAction(ActionEvent actionEvent) throws IOException {
        btnLoginOnAction(actionEvent);
    }
}
