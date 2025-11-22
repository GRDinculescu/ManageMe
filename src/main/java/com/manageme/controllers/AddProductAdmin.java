package com.manageme.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class AddProductAdmin {
    @FXML VBox root;

    public void onCloseClick(ActionEvent actionEvent) {
        root.setVisible(false);
        root.setManaged(false);
    }

    public void onDeleteClick(ActionEvent actionEvent) {
    }

    public void onSaveClick(ActionEvent actionEvent) {

    }
}
