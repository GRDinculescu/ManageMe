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
                        "Soy admin?", "Sí, tienes permisos de administrador",
                        "Ayuda", "Puedes consultar la documentación de administración o contactar soporte",
                        "Que tal el dia?", "Todo funciona según lo esperado en el panel de administración",
                        "Por que no va nada? D:", "Verifica los logs y la configuración, algunos procesos pueden estar pendientes, o se incluirán en futuros parches",
                        "Que puedo hacer como admin", "Ver, borrar, editar; tanto productos como usuarios."
                ),
                "user", Map.of(
                        "Como veo mis productos?", "Accede a la sección 'Mis Productos' desde el menú principal",
                        "Puedo editar mi perfil?", "Sí, en la opción 'Editar Perfil' puedes actualizar tu información",
                        "Como hago un pedido?", "Selecciona los productos deseados y pulsa 'Realizar Pedido'",
                        "Que hago si olvido mi contraseña?", "Usa la opción 'Recuperar Contraseña' para restablecerla de forma segura",
                        "Puedo cancelar un pedido?", "Solo si el pedido no ha sido procesado aún; verifica su estado antes"
                ),
                "manager", Map.of(
                        "Como veo el estado de pedidos?", "En la sección 'Gestión de Pedidos' puedes filtrar por estado",
                        "Puedo asignar tareas a un empleado?", "Sí, selecciona el pedido y pulsa 'Asignar a Empleado'",
                        "Como genero un informe de ventas?", "Ve a 'Informes' y selecciona 'Ventas por período'",
                        "Que hago si un producto no está en stock?", "Marca el producto como 'Agotado' y notifica al responsable",
                        "Puedo cambiar el precio de un producto?", "Desde 'Gestión de Productos' puedes actualizar el precio de forma segura"
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
