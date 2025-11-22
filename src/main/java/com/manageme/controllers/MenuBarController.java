package com.manageme.controllers;

import com.manageme.InventarioApp;
import com.manageme.models.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuBarController {
    @FXML
    HBox root;
    User user;

    void initData(User user){
        double screenFactor = root.getScene().getWidth() / 1080;
        root.setStyle("-fx-font-size: " + (14 * screenFactor) + "px");
        this.user = user;
    }

    @FXML
    public void onHelpClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventarioApp.class.getResource("help-view.fxml"));
        Parent rootT = fxmlLoader.load();
        Scene mainScene = new Scene(rootT);
        Stage stage = (Stage) root.getScene().getWindow();

        HelpController controller = fxmlLoader.getController();
        controller.initData(user);

        stage.hide();
        stage.setScene(mainScene);
        stage.show();
        Platform.runLater(() -> {
            stage.setMaximized(true);
            stage.requestFocus();
            stage.toFront();
        });
    }

    public void onLogoClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventarioApp.class.getResource("main-view.fxml"));
        Parent rootT = fxmlLoader.load();
        Scene mainScene = new Scene(rootT);
        Stage stage = (Stage) root.getScene().getWindow();

        MainController controller = fxmlLoader.getController();
        controller.initData(user);

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
