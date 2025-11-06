package org.example.proyectomarta;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class MainController {
    @FXML
    GridPane loginPane;
    @FXML
    GridPane forgotPane;

    @FXML
    protected void onForgetPassword() throws IOException {
        loginPane.setVisible(false);
        loginPane.setManaged(false);
        forgotPane.setVisible(true);
        forgotPane.setManaged(true);
    }
}
