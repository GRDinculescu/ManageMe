package com.manageme.controllers;

import com.manageme.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Map;

public class HelpController {
    // Preguntas
    @FXML HBox custom;

    // Respuestas
    @FXML VBox form;

    @FXML Label username;

    @FXML VBox questions;
    @FXML VBox answer;
    Map<String , VBox> panels;
    Map<String , Map<String, String>> mamap;

    @FXML VBox loginPane;
    @FXML VBox forgotPane;
    @FXML TextField tfdPasswd;
    @FXML TextField tfdUser;


    @FXML MenuBarController menubarController;
    @FXML VBox root;
    User user;

    void initData(User user){
        mamap = Map.of(
                "admin", Map.of(
                        "Soy admin?", "Porfavor, deja este trabajo",
                        "Ayuda", "Nel",
                        "Que tal el dia?", "Tan solo que estas hablando con la app de gestion?",
                        "Por que no va nada? D:", "Se positivo, si has llegado hasta aqui significa que algo si que funciona",
                        "Que nota se merecen los que han echo esto?", "9/11" // TODO: No quiero que se me olvide que esto esta aqui
                        ),
                "user", Map.of(
                        "Pregunta", "Respuesta"
                ),
                "manager", Map.of(
                        "Pregunta", "Respuesta"
                )
        );

        Map<String, String> questionsT = mamap.get(user.getRol());

        for (String key : questionsT.keySet()) {
            HBox h = new HBox();
            h.getStyleClass().add("question");
            h.setOnMouseClicked(e -> handleEvent(key));

            h.setPadding(new Insets(10, 10, 10, 50));

            Label lbl = new Label(key);

            ImageView img = new ImageView(new Image(
                    getClass().getResource("/drawable/keyboard_arrow_right.png").toString()
            ));

            h.getChildren().add(lbl);
            h.getChildren().add(img);

            questions.getChildren().add(h);
        }

        username.setText(user.getRol());

        double screenFactor = root.getScene().getWidth() / 1080;
        root.setStyle("-fx-font-size: " + (14 * screenFactor) + "px");
        this.user = user;

        menubarController.initData(user); // Inicializar el menuBar (importante)
    }

    @FXML
    void handleEvent(String question){

        form.setVisible(false);
        form.setManaged(false);
        answer.setVisible(true);
        answer.setManaged(true);

        Label lbl = new Label(mamap.get(user.getRol()).get(question));
        lbl.getStyleClass().add("answer");
        lbl.prefWidth(700);
        lbl.setWrapText(true);

        answer.getChildren().clear();
        answer.getChildren().add(lbl);

    }

    @FXML
    void form(MouseEvent event){
        form.setVisible(true);
        form.setManaged(true);
        answer.setVisible(false);
        answer.setManaged(false);
    }
}
