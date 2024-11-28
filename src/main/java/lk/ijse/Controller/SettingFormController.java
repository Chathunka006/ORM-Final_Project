package lk.ijse.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SettingFormController {

    @FXML
    private AnchorPane anpBtns;

    @FXML
    private AnchorPane anpSetting;

    @FXML
    private Button btnChangePassword;

    @FXML
    void btnChangePasswordOnAction(ActionEvent event) throws IOException {
        AnchorPane changePasswordPane = FXMLLoader.load(this.getClass().getResource("/view/PasswordChangeForm.fxml"));

        anpBtns.getChildren().clear();
        anpBtns.getChildren().add(changePasswordPane);
    }

}
