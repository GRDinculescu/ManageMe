package com.manageme.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manageme.InventarioApp;
import com.manageme.models.User;
import com.manageme.util.Functions;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

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

    private final Functions functions = Functions.getFunctions();

    @FXML
    protected void onForgetPassword() throws IOException { // TODO: Habria que hacer que esto haga algo no? Quiza una notificacion al admin?
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
        Gson gson = new Gson();

        String username = tfdUser.getText();
        String password = tfdPasswd.getText();

        List<User> users = Functions.readJson(User.class, Functions.getUsersFile());
        if (users == null) return;

        // Obtengo el usuario que coincida, si no hay, es null
        User user = users.stream()
                .filter(u -> u.getName().equals(username))
                .findFirst()
                .orElse(null);

        if (user == null) { // Verificamos si existe
            Functions.showAlert("Error de login", "No se encontró el usuario", Alert.AlertType.ERROR);
            return;
        }

        if (!user.getPassword().equals(password)) { // Verificamos si la contraseña coincide
            Functions.showAlert("Error de login", "La contraseña no coincide", Alert.AlertType.ERROR);
            return;
        }

        Functions.setMainUser(user);

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
}
