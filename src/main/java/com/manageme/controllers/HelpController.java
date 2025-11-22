package com.manageme.controllers;

import com.manageme.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;

public class HelpController {
    // Preguntas
    @FXML HBox custom;
    @FXML HBox q1;
    @FXML HBox q2;
    @FXML HBox q3;
    @FXML HBox q4;
    @FXML HBox q5;

    // Respuestas
    @FXML VBox form;
    @FXML VBox m1;
    @FXML VBox m2;
    @FXML VBox m3;
    @FXML VBox m4;
    @FXML VBox m5;

    Map<String , VBox> panels;

    @FXML VBox loginPane;
    @FXML VBox forgotPane;
    @FXML TextField tfdPasswd;
    @FXML TextField tfdUser;


    @FXML MenuBarController menubarController;
    @FXML VBox root;
    User user;

    void initData(User user){
        panels = Map.of(
                "custom", form,
                "q1", m1,
                "q2", m2,
                "q3", m3,
                "q4", m4,
                "q5", m5
        );

        // Todo: Hacer que las preguntas cambien según el usuario.
        //  Que le importa al usuario como agregar nuevos si no puede

        switch (user.getRol()){
            case "admin" -> {
                Node node = q1.getChildren().getFirst();
                ((Label) node).setText("Como agrego un usuario");
            }
            case "user" -> {}
            case "manager" -> {}
        }

        double screenFactor = root.getScene().getWidth() / 1080;
        root.setStyle("-fx-font-size: " + (14 * screenFactor) + "px");
        this.user = user;

        menubarController.initData(user); // Inicializar el menuBar (importante)
    }

    @FXML
    void handleEvent(MouseEvent event){
        HBox box = (HBox) event.getSource();

        panels.values().forEach(vbox -> {
            vbox.setVisible(false);
            vbox.setManaged(false);
        });

        VBox selected = panels.get(box.getId());
        if (selected != null){
            selected.setVisible(true);
            selected.setManaged(true);
        }
    }
}
