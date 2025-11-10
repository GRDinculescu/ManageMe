package com.manageme.controllers;

import com.manageme.InventarioApp;
import javafx.application.Platform;
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
import java.util.HashMap;

public class LoginController {
    @FXML Label errMsg;
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
    protected void onRememberPassword() throws IOException {
        forgotPane.setVisible(false);
        forgotPane.setManaged(false);
        loginPane.setVisible(true);
        loginPane.setManaged(true);
    }

    @FXML
    protected void onLogin() throws IOException {
        // Todo: Cambiar esto por una base de datos, o algo asi
        HashMap<String , String> users = new HashMap<>();
        users.put("admin", "1111");
        users.put("user", "1111");
        users.put("manager", "1111");

        String user = tfdUser.getText();
        // Todo: Cambiar el password a hash, si nos da tiempo :)
        String password = tfdPasswd.getText();

        if (!users.containsKey(user)) {
            errMsg.setText("No se encontró el usuario");
            return;
        }

        if (!users.get(user).equals(password)){
            errMsg.setText("Contraseña incorrecta");
            return;
        }

        switch (user) {
            case "admin" -> {
                // lblMsg.setText(" Bienvenido administrador!");
                FXMLLoader fxmlLoader = new FXMLLoader(InventarioApp.class.getResource("main-view.fxml"));
                Parent root = fxmlLoader.load();
                Scene mainScene = new Scene(root);
                Stage stage = (Stage) tfdUser.getScene().getWindow();

                MainController mainController = fxmlLoader.getController();
                mainController.initData(user);

                stage.hide();
                stage.setScene(mainScene);
                stage.show();
                Platform.runLater(() -> {
                    stage.setMaximized(true);
                    stage.requestFocus();
                    stage.toFront();
                });
            }
            case "manager" -> {}
            case "user" -> {}
        }
    }

}
