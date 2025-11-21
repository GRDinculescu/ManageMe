package com.manageme.util;

import javafx.scene.control.Alert;

public class Functions {
    public void showAlert(String title, String msg, Alert.AlertType alertType){
        System.err.println(msg);
        Alert alert = new Alert(alertType);
        alert.setContentText(msg);
        alert.setHeaderText(title);
        alert.show();
    }
}