package com.manageme.controllers;

import com.google.gson.Gson;
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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

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

    private final Functions functions = new Functions();

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
        Properties properties = new Properties();

        String username = tfdUser.getText();
        String password = tfdPasswd.getText();

        try (FileReader fr = new FileReader("users.properties")) {
            properties.load(fr); // Cargamos el archivo de usuarios

            if (!properties.containsKey(username)) { // Verificamos si existe
                functions.showAlert("Login error", "No se encontró el usuario", Alert.AlertType.ERROR);
                return;
            }

            if (!properties.getProperty(username).equals(password)) { // Verificamos si la contraseña coincide
                functions.showAlert("Login error", "La contraseña no coincide", Alert.AlertType.ERROR);
                return;
            }
        } catch (FileNotFoundException e) { // Si no se encuentra el archivo de usuarios
            functions.showAlert("File error", "No se encontró el archivo de usuario", Alert.AlertType.ERROR);
            return;
        }

        Gson gson = new Gson();
        User user;
        try (FileReader fr = new FileReader("users.json")) {
            user = gson.fromJson(fr, User.class);
        } catch (FileNotFoundException e) {// Si no se encuentra el archivo de usuarios
            functions.showAlert("File error", "No se encontró el archivo de usuario", Alert.AlertType.ERROR);
            return;
        }

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
