package com.manageme.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manageme.InventarioApp;
import com.manageme.models.Product;
import com.manageme.models.User;
import com.manageme.util.Functions;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class MainController {
    private final Functions functions = Functions.getFunctions();
    @FXML VBox productsView;
    @FXML VBox root;
    @FXML VBox categories;
    @FXML VBox filtres;

    @FXML MenuBarController menubarController;

    private boolean viewFiltres = false;

    private User user;

    void initData(User user){
        this.user = user;
        double screenFactor = root.getScene().getWidth() / 1080;
        root.setStyle("-fx-font-size: " + (14 * screenFactor) + "px");
        menubarController.initData(user); // Inicializar el menuBar (importante)
        showProducts();
    }

    private void showProducts(){
        File documents = functions.getDocuments();

        Gson gson = new Gson();
        try (FileReader fr = new FileReader(new File(documents, "products.json"))) {
            Type listType = new TypeToken<List<Product>>(){}.getType();
            List<Product> products = gson.fromJson(fr, listType);

            for (Product p : products){
                FXMLLoader loader = new FXMLLoader(InventarioApp.class.getResource("product-layout.fxml"));
                Parent root = loader.load();
                ProductController controller = loader.getController();

                controller.setData(p);
                productsView.getChildren().addFirst(root);
            }
        } catch (Exception e){
            Functions.showAlert("Error de archivo", "No se ha podido cargar el archivo de productos", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onFilterClick(ActionEvent actionEvent) {
        viewFiltres = !viewFiltres;

        categories.setManaged(!viewFiltres);
        categories.setVisible(!viewFiltres);
        filtres.setManaged(viewFiltres);
        filtres.setVisible(viewFiltres);
    }

    @FXML
    public void onAddClick(ActionEvent actionEvent) throws IOException {
        // Aqui se carga el layout para agregar productos
        FXMLLoader loader = new FXMLLoader(InventarioApp.class.getResource("add-product-layout.fxml"));
        Parent root = loader.load();
        AddProductAdminController controller = loader.getController();
        Stage modalStage = new Stage();

        controller.setDeleteButtonVisible(false); // Se desabilita el boton borrar

        modalStage.initModality(Modality.APPLICATION_MODAL); // Que sea modal
        modalStage.setTitle("Agregar producto"); // Titulo
        Scene scene = new Scene(root); // Creamos una escena
        // Le agregamos los estilos
        scene.getStylesheets().add(Objects.requireNonNull(InventarioApp.class.getResource("styles/styles.css")).toExternalForm());
        modalStage.setScene(scene);
        modalStage.showAndWait();

        // Obtenemos el producto
        Product product = controller.getProduct();

        {   // La parte para agregar el producto al scrollView
            FXMLLoader productLoader = new FXMLLoader(InventarioApp.class.getResource("product-layout.fxml"));
            Parent productLayout = productLoader.load();
            ProductController productController = productLoader.getController();

            productController.setData(product);
            productsView.getChildren().addFirst(productLayout);
        }
    }
}
