package lk.ijse.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Bo.BoFactory;
import lk.ijse.Bo.Custom.UserBo;
import lk.ijse.Dao.Custom.UserDao;
import lk.ijse.Dao.DaoFactory;
import lk.ijse.Entity.User;
import lk.ijse.util.Regex;
import lk.ijse.util.TextFieldType;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PasswordChangeFormController {

    @FXML
    private AnchorPane anpChangePassword;

    @FXML
    private Button btnPasswordReset;

    @FXML
    private TextField txtConfirmPassword;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNewPassword;

    @FXML
    private TextField txtRole;

    @FXML
    private TextField txtSearch;

    ArrayList<User> users = new ArrayList<>();
    UserBo userBo = (UserBo) BoFactory.getBoFactory().getBoType(BoFactory.BoType.USER);
    UserDao userDao = (UserDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.USER);
    ObservableList<User> observableUserList = FXCollections.observableArrayList();

    public void initialize() throws IOException {
        getAllUsers();
    }

    private void getAllUsers() throws IOException {
        List<User> userList = userBo.getUserList();
        users = (ArrayList<User>) userList;
        observableUserList.addAll(users);
    }

    private void searchUsername() {
        String search = txtSearch.getText();
        boolean found = false;

        for (User user : observableUserList) {
            if (user.getUsername().equalsIgnoreCase(search)) {
                // Populate the fields if a match is found
                txtEmail.setText(user.getUser_email()); // Ensure `getUser_email()` exists in User class
                txtContact.setText(user.getUser_phone()); // Ensure `getUser_phone()` exists in User class
                txtRole.setText(user.getUser_role()); // Ensure `getRole()` exists in User class
                found = true;
                break;
            }
        }

        if (!found) {
            // Clear fields if no match is found
            txtEmail.clear();
            txtContact.clear();
            txtRole.clear();
            System.out.println("No user found with the provided username.");
        }
    }
    @FXML
    void btnPasswordResetOnAction(ActionEvent event) {
        String newPassword = txtNewPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if (newPassword.equals(confirmPassword)) {
            // Hash the password using BCrypt
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            // Find the user based on the search field
            String username = txtSearch.getText();
            for (User user : observableUserList) {
                if (user.getUsername().equalsIgnoreCase(username)) {
                    // Update the user's password
                    user.setPassword(hashedPassword);

                    // Save the updated user to the database
                    if (userDao.updateUser(user)) {
                        new Alert(Alert.AlertType.INFORMATION, "Password updated successfully!").show();
                        clearPasswordFields();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Failed to update the password.").show();
                    }
                    break;
                }
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match!").show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        searchUsername();
        txtNewPassword.requestFocus();
    }
    private void clearPasswordFields() {
        txtNewPassword.clear();
        txtConfirmPassword.clear();
    }

    public void txtNewPasswordOnAction(ActionEvent actionEvent) {
        txtConfirmPassword.requestFocus();
    }

    public void txtConfirmPasswordOnAction(ActionEvent actionEvent) {
        txtEmail.requestFocus();
    }

    public void txtEmailOnAction(ActionEvent actionEvent) {
        txtContact.requestFocus();
    }

    public void txtContactOnAction(ActionEvent actionEvent) {
        txtRole.requestFocus();
    }

    public void txtRoleOnAction(ActionEvent actionEvent) {
        btnPasswordResetOnAction(actionEvent);
    }

    public void txtUserNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFieldType.NAME, txtSearch);
    }

    public void txtNewPasswordOnKeyReleased(KeyEvent keyEvent) {
    }

    public void txtConfirmPasswordOnKeyReleased(KeyEvent keyEvent) {
    }

    public void txtEmailOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFieldType.NAME, txtEmail);
    }

    public void txtContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFieldType.CONTACT, txtContact);
    }


    public void txtRoleOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(TextFieldType.NAME, txtRole);
    }
    public boolean isValidated() {
        if(!Regex.setTextColor(TextFieldType.NAME,txtEmail)) return false;
        if(!Regex.setTextColor(TextFieldType.NAME,txtRole)) return false;
        if(!Regex.setTextColor(TextFieldType.CONTACT,txtContact)) return false;
        return true;
    }
}
