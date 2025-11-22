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
    @FXML VBox addMenuAdmin;
    @FXML VBox root;
    @FXML VBox categories;
    @FXML VBox filtres;

    private boolean viewFiltres = false;

    private User user;

    void initData(User user){
        this.user = user;
        double screenFactor = root.getScene().getWidth() / 1080;
        root.setStyle("-fx-font-size: " + (14 * screenFactor) + "px");
    }

    @FXML
    public void onFilterClick(ActionEvent actionEvent) {
        viewFiltres = !viewFiltres;

        categories.setManaged(!viewFiltres);
        categories.setVisible(!viewFiltres);
        filtres.setManaged(viewFiltres);
        filtres.setVisible(viewFiltres);
    }

    public void onAddClick(ActionEvent actionEvent) {
        if (user.getRol().equalsIgnoreCase("admin") ||
            user.getRol().equalsIgnoreCase("manager")){

            addMenuAdmin.setManaged(true);
            addMenuAdmin.setVisible(true);
        }
    }
}
