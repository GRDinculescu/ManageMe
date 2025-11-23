package com.manageme.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manageme.models.Notification;
import com.manageme.models.Product;
import com.manageme.InventarioApp;
import com.manageme.controllers.ProductAdminController;
import com.manageme.controllers.ProductMenuType;
import com.manageme.models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class Functions {
    private static File documentsFolder;
    private static File usersFile;
    private static File productsFile;
    private static File notificationsFile;

    public static File getUsersFile() {
        return usersFile;
    }

    public static File getProductsFile() {
        return productsFile;
    }

    public static File getNotificationsFile() {
        return notificationsFile;
    }

    private static Functions instance;
    private Functions() {}

    public static Functions getFunctions(){
        if (instance == null) instance = new Functions();
        return instance;
    }

    public static <T> List<T> readJson(Class<T> clazz, File file){
        Gson gson = new Gson();
        Type type = TypeToken.getParameterized(List.class, clazz).getType();
        try (FileReader fr = new FileReader(file)) {
            return gson.fromJson(fr, type);
        } catch (IOException e) {
            Functions.showAlert("Error de archivo", "No se pudo cargar el archivo.", Alert.AlertType.ERROR);
            return new ArrayList<>();
        }
    }

    public static <T> void writeJson(List<T> list, File file){
        Gson gson = new Gson();
        try (FileWriter fw = new FileWriter(file)) {
            gson.toJson(list, fw);
        } catch (IOException e) {
            Functions.showAlert("Error de archivo", "No se pudo guardar el archivo.", Alert.AlertType.ERROR);
        }
    }

    public static Alert showAlert(String title, String msg, Alert.AlertType alertType){
        System.err.println(msg);
        Alert alert = new Alert(alertType);
        alert.setContentText(msg);
        alert.setHeaderText(title);
        alert.showAndWait();
        return alert;
    }

    public void generateFiles() throws IOException {
        // Genera nuestra carpeta en documentos
        if (documentsFolder == null) documentsFolder = new File(System.getProperty("user.home"), "Documents/ManageMe");
        if(documentsFolder.mkdir()) System.out.printf("Carpeta generada en %s%n", documentsFolder.getAbsolutePath());

        // Genera el archivo de productos
        productsFile = new File(documentsFolder,"products.json");
        if (productsFile.createNewFile())
            System.out.println("Generated users file");

        // Genera el archivo de notificaciones
        notificationsFile = new File(documentsFolder,"notifications.json");
        if (notificationsFile.createNewFile())
            System.out.println("Generated notifications file");

        // Genera el archivo de usuarios
        usersFile = new File(documentsFolder,"users.json");
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
    }


    public static ProductAdminController showAddProductMenu(ProductMenuType type, String title) throws IOException {
        return showAddProductMenu(type, title, null);
    }

    public static ProductAdminController showAddProductMenu(ProductMenuType type, String title, Product product) throws IOException {
        // Aqui se carga el layout para agregar productos
        FXMLLoader loader = new FXMLLoader(InventarioApp.class.getResource("product-admin-control-layout.fxml"));
        Parent root = loader.load();
        ProductAdminController controller = loader.getController();
        Stage modalStage = new Stage();

        controller.setEditMode(type);
        if (product != null) controller.setInfo(product);

        modalStage.initModality(Modality.APPLICATION_MODAL); // Que sea modal
        modalStage.setTitle(title); // Titulo
        Scene scene = new Scene(root); // Creamos una escena
        // Le agregamos los estilos
        scene.getStylesheets().add(Objects.requireNonNull(InventarioApp.class.getResource("styles/styles.css")).toExternalForm());
        modalStage.setScene(scene);
        modalStage.showAndWait();

        return controller;
    }

    public void sendNotification(String notification, String userName) {
        List<Notification> notifications = readJson(Notification.class, notificationsFile);
        if (notifications == null) return;
        notifications.add(new Notification(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")), userName, notification));
        writeJson(notifications, notificationsFile);
    }

    public File getDocumentsFolder() {
        return documentsFolder;
    }

    public void setDocumentsFolder(File documentsFolder) {
        Functions.documentsFolder = documentsFolder;
    }
}