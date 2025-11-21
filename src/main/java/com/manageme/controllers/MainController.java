package com.manageme.controllers;

import com.manageme.InventarioApp;
import com.manageme.models.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML VBox categories;
    @FXML VBox filtres;
    @FXML Label username;

    private boolean viewFiltres = false;

    void initData(User user){
        Parent root = categories.getParent();
        double screenFactor = root.getScene().getWidth() / 1080;
        root.setStyle("-fx-font-size: " + (14 * screenFactor) + "px");
    }

    public void onHelpClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventarioApp.class.getResource("help-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene mainScene = new Scene(root);
        Stage stage = (Stage) username.getScene().getWindow();

        HelpController helpController = fxmlLoader.getController();
        helpController.initData(username.getText());

        stage.hide();
        stage.setScene(mainScene);
        stage.show();
        Platform.runLater(() -> {
            stage.setMaximized(true);
            stage.requestFocus();
            stage.toFront();
        });
    }

    public void onFilterClick(ActionEvent actionEvent) {
        viewFiltres = !viewFiltres;

        categories.setManaged(!viewFiltres);
        categories.setVisible(!viewFiltres);
        filtres.setManaged(viewFiltres);
        filtres.setVisible(viewFiltres);
    }
}
