package com.manageme;

import com.google.gson.Gson;
import com.manageme.controllers.LoginController;
import com.manageme.models.User;
import com.manageme.util.Functions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

public class InventarioApp extends Application {
    private final Functions functions = Functions.getFunctions();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventarioApp.class.getResource("login-view.fxml"));
        Parent root = fxmlLoader.load();

        LoginController lc = fxmlLoader.getController();
        lc.initData(functions.generateFiles());

        Scene scene = new Scene(root);
        stage.setTitle("Inicia sesión!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
