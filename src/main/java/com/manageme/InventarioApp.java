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
    @Override
    public void start(Stage stage) throws IOException {
        // Genera nuestra carpeta en documentos
        File documents = new File(System.getProperty("user.home"), "Documents/ManageMe");
        if(documents.mkdir()) System.out.printf("Carpeta generada en %s%n", documents.getAbsolutePath());

        // Genera el archivo de usuarios
        File usersFile = new File(documents,"users.json");
        if (usersFile.createNewFile()) {
            System.out.println("Generated users file");

            // Inserta el admin
            Gson gson = new Gson();
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, 2000);
            c.set(Calendar.MONTH, 10);
            c.set(Calendar.DAY_OF_MONTH, 11);

            List<User> users = new ArrayList<>();
            users.add(
                new User(
                        "admin",
                        "admin lastname",
                        123456789,
                        c.getTime(),
                        "admin@admin.com",
                        "C/admin",
                        "admin",
                        "1"
                )
            );

            try (FileWriter fw = new FileWriter(usersFile)) {
                gson.toJson(users, fw);
            }
        }

        FXMLLoader fxmlLoader = new FXMLLoader(InventarioApp.class.getResource("login-view.fxml"));
        Parent root = fxmlLoader.load();

        LoginController lc = fxmlLoader.getController();
        lc.initData(usersFile);

        Scene scene = new Scene(root);
        stage.setTitle("Inicia sesión!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
