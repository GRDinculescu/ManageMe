package com.manageme.controllers;

import com.manageme.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Map;

public class UsersController {
    @FXML MenuBarController menubarController;
    @FXML VBox root;
    User user;

    void initData(User user){
        double screenFactor = root.getScene().getWidth() / 1080;
        root.setStyle("-fx-font-size: " + (14 * screenFactor) + "px");
        this.user = user;
        menubarController.initData(user); // Inicializar el menuBar (importante)
    }



}
