package com.manageme;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class InventarioApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventarioApp.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 500);
        stage.setTitle("Inicia sesion!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        // Quitar el menu de olvide la contraseña
        GridPane forgot = (GridPane) scene.lookup("#forgotPane");
        forgot.setVisible(false);
        forgot.setManaged(false);
    }
}
