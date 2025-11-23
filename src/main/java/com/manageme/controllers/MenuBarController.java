
package com.manageme.controllers;

import com.manageme.InventarioApp;
import com.manageme.models.User;
import com.manageme.util.Functions;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuBarController {
    @FXML
    HBox root;
    User user;
    @FXML ContextMenu menuDropdown;
    @FXML ContextMenu userDropdown;
    @FXML ImageView menuIcon;
    @FXML ImageView userIcon;

    void initData(User user){
        double screenFactor = root.getScene().getWidth() / 1080;
        root.setStyle("-fx-font-size: " + (14 * screenFactor) + "px");
        this.user = user;

        menuDropdown = new ContextMenu(
                new MenuItem("Inicio"),
                new MenuItem("Usuarios"),
                new MenuItem("Ayuda")
        );
        userDropdown = new ContextMenu(
                new MenuItem("Perfil"),
                new MenuItem("Cerrar sesión")
        );
        menuDropdown.getItems().get(0).setOnAction(e -> onLogoClicked());
        menuDropdown.getItems().get(1).setOnAction(e -> onUsersClicked());
        menuDropdown.getItems().get(2).setOnAction(e -> onHelpClicked());
        userDropdown.getItems().get(1).setOnAction(e -> onLogout());
    }

    @FXML
    public void onHelpClicked() {
        FXMLLoader fxmlLoader = new FXMLLoader(InventarioApp.class.getResource("help-view.fxml"));
        Parent rootT = null;
        try {
            rootT = fxmlLoader.load();
        } catch (IOException e) {
            Functions.showAlert("Nav error", "Fallo cargando esa pagina", Alert.AlertType.ERROR);
            return;
        }
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

    @FXML
    public void onLogoClicked() {
        FXMLLoader fxmlLoader = new FXMLLoader(InventarioApp.class.getResource("main-view.fxml"));
        Parent rootT = null;
        try {
            rootT = fxmlLoader.load();
        } catch (IOException e) {
            Functions.showAlert("Nav error", "Fallo cargando esa pagina", Alert.AlertType.ERROR);
            return;
        }
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

    @FXML
    public void onLogout() {
        FXMLLoader fxmlLoader = new FXMLLoader(InventarioApp.class.getResource("login-view.fxml"));
        Parent rootT = null;
        try {
            rootT = fxmlLoader.load();
        } catch (IOException e) {
            Functions.showAlert("Nav error", "Fallo cargando esa pagina", Alert.AlertType.ERROR);
            return;
        }

        Functions functions = Functions.getFunctions();

        LoginController lc = fxmlLoader.getController();
        try {
            lc.initData(functions.generateFiles());
        } catch (IOException e) { // FIXME: no deberia poner eso ahi @tesmes71 revisa esto
            Functions.showAlert("Functions error", "Y yo que se que es esto, ayuda", Alert.AlertType.ERROR);
            return;
        }

        Scene scene = new Scene(rootT);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setTitle("Inicia sesión!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    @FXML
    public void onUsersClicked() {
        FXMLLoader fxmlLoader = new FXMLLoader(InventarioApp.class.getResource("users-view.fxml"));
        Parent rootT = null;
        try {
            rootT = fxmlLoader.load();
        } catch (IOException e) {
            Functions.showAlert("Nav error", "Fallo cargando esa pagina", Alert.AlertType.ERROR);
            return;
        }
        Scene mainScene = new Scene(rootT);
        Stage stage = (Stage) root.getScene().getWindow();

        UsersController controller = fxmlLoader.getController();
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


    @FXML private void onMenuClicked() {
        hideAll();
        menuDropdown.show(menuIcon, Side.BOTTOM, 0, 0);
    }
    @FXML private void onUserClicked() {
        hideAll();
        userDropdown.show(userIcon, Side.BOTTOM, 0, 0);
    }
    private void hideAll() { menuDropdown.hide(); userDropdown.hide(); }


}
