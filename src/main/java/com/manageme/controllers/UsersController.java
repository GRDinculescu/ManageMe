package com.manageme.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manageme.InventarioApp;
import com.manageme.models.Product;
import com.manageme.models.User;
import com.manageme.util.Functions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class UsersController {
    @FXML MenuBarController menubarController;
    @FXML VBox root;
    @FXML VBox usersView;
    User user;
    Functions functions;

    void initData(User user){
        double screenFactor = root.getScene().getWidth() / 1080;
        root.setStyle("-fx-font-size: " + (14 * screenFactor) + "px");
        this.user = user;
        menubarController.initData(user); // Inicializar el menuBar (importante)
        functions = Functions.getFunctions();
        showUsers();
    }


    private void showUsers(){
        List<User> users = Functions.readJson(User.class, Functions.getUsersFile());

        try {
            for (User u : users){
                FXMLLoader loader = new FXMLLoader(InventarioApp.class.getResource("user-layout.fxml"));
                Parent root = null;
                root = loader.load();

                UserController controller = loader.getController();

                controller.setData(u);
                usersView.getChildren().add(root);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
