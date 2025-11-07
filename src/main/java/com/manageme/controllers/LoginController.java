package com.manageme.controllers;

import com.manageme.InventarioApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML VBox loginPane;
    @FXML VBox forgotPane;
    @FXML Button btnLogin;
    @FXML TextField tfdPasswd;
    @FXML Label lblPasswd;
    @FXML TextField tfdUser;
    @FXML Label lblUser;
    @FXML ImageView imgLoginLogo;
    @FXML TextField tfdUserForget;

    @FXML
    protected void onForgetPassword() throws IOException {
        loginPane.setVisible(false);
        loginPane.setManaged(false);
        forgotPane.setVisible(true);
        forgotPane.setManaged(true);
    }

    @FXML
    protected void onRememberedPassword() throws IOException {
        forgotPane.setVisible(false);
        forgotPane.setManaged(false);
        loginPane.setVisible(true);
        loginPane.setManaged(true);
    }

    @FXML
    protected void onLogin() throws IOException {
        String user = tfdUser.getText();
        String password = tfdPasswd.getText();

        // TODO: Change this later / can someone pls put that message?
        if (user.equals("admin") && password.equals("1234")) {
            // lblMsg.setText(" Bienvenido administrador!");
            FXMLLoader fxmlLoader = new FXMLLoader(InventarioApp.class.getResource("main-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene mainScene = new Scene(root, 1600, 1000);
            Stage stage = (Stage)  tfdUser.getScene().getWindow();
            stage.setScene(mainScene);
            stage.setMaximized(true);
            stage.show();

        } else if (user.equals("vendedor") && password.equals("abcd")) {
            // lblMsg.setText(" Bienvenido vendedor!");
        } else if (user.equals("cliente") && password.equals("pass")) {
            //  lblMsg.setText(" Bienvenido cliente!");
        } else {
            //lblMsg.setText(" Usuario o contraseña incorrectos");
        }
    }

}
