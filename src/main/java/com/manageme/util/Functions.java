package com.manageme.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manageme.models.Product;
import com.manageme.models.User;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Functions {
    private File documents;
    private static Functions instance;
    private Functions() {}

    public static Functions getFunctions(){
        if (instance == null) instance = new Functions();
        return instance;
    }

    public static void showAlert(String title, String msg, Alert.AlertType alertType){
        System.err.println(msg);
        Alert alert = new Alert(alertType);
        alert.setContentText(msg);
        alert.setHeaderText(title);
        alert.show();
    }

    public File generateFiles() throws IOException {
        // Genera nuestra carpeta en documentos
        if (documents == null) documents = new File(System.getProperty("user.home"), "Documents/ManageMe");
        if(documents.mkdir()) System.out.printf("Carpeta generada en %s%n", documents.getAbsolutePath());

        // Genera el archivo de usuarios
        File usersFile = new File(documents,"users.json");
        if (usersFile.createNewFile()) {
            System.out.println("Generated users file");

            // Inserta el admin
            Gson gson = new Gson();
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, 2000);
            c.set(Calendar.MONTH, 10);
            c.set(Calendar.DAY_OF_MONTH, 11);

            List<User> users = new ArrayList<>();
            users.add(
                    new User(
                            "admin",
                            "admin lastname",
                            123456789,
                            c.getTime(),
                            "admin@admin.com",
                            "C/admin",
                            "admin",
                            "1"
                    )
            );

            try (FileWriter fw = new FileWriter(usersFile)) {
                gson.toJson(users, fw);
            }
        }
        return usersFile;
    }

    private <T> List<T> loadJSON(String fileT, Class<T> clazz) {
        File file = new File(documents, fileT);
        List<T> data = null;
        try {
            if (file.createNewFile())
                System.out.println("Generated file");

            try (FileReader fr = new FileReader(file)) {
                Gson gson = new Gson();
                Type type = TypeToken.getParameterized(List.class, clazz).getType();
                data = gson.fromJson(fr, type);
            } catch (IOException e) {
                Functions.showAlert("Error de archivo", "No se pudo cargar los datos.", Alert.AlertType.ERROR);
            }
        } catch (IOException e) {
            Functions.showAlert("Error de archivo", "No se pudo generar el archivo.", Alert.AlertType.ERROR);
        }

        return (data != null) ? data : new ArrayList<>();
    }

    private <T> boolean saveJSON(String fileT, List<T> data) {
        File file = new File(documents, fileT);
        try {
            if (file.createNewFile())
                System.out.println("Generated file");

            try (FileWriter fw = new FileWriter(file)) {
                Gson gson = new Gson();
                gson.toJson(data, fw);
                return true;
            } catch (IOException e) {
                Functions.showAlert("Error de archivo", "No se pudo guardar los datos.", Alert.AlertType.ERROR);
            }
        } catch (IOException e) {
            Functions.showAlert("Error de archivo", "No se pudo generar el archivo.", Alert.AlertType.ERROR);
        }

        return false;
    }

    public File getDocuments() {
        return documents;
    }

    public void setDocuments(File documents) {
        this.documents = documents;
    }
}