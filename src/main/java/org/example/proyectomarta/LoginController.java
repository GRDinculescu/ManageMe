package org.example.proyectomarta;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class LoginController {
    @FXML Button btnLogin;
    @FXML TextField tfdPasswd;
    @FXML Label lblPasswd;
    @FXML TextField tfdUser;
    @FXML Label lblUser;
    @FXML ImageView imgLoginLogo;
    @FXML TextField tfdUserForget;
    @FXML GridPane loginPane;
    @FXML GridPane forgotPane;

    @FXML
    protected void onForgetPassword() throws IOException {
        loginPane.setVisible(false);
        loginPane.setManaged(false);
        forgotPane.setVisible(true);
        forgotPane.setManaged(true);
    }
}
