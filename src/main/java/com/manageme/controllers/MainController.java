package com.manageme.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manageme.InventarioApp;
import com.manageme.models.Product;
import com.manageme.models.User;
import com.manageme.util.Functions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

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
        File documents = functions.getDocumentsFolder();

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
    public void onAddClick(ActionEvent actionEvent) {
        try {
            ProductAdminController controller = Functions.showAddProductMenu(ProductMenuType.ADD, "Agregar producto");

            // Obtenemos el producto
            Product product = controller.getProduct();

            if (product != null) {   // La parte para agregar el producto al scrollView
                FXMLLoader productLoader = new FXMLLoader(InventarioApp.class.getResource("product-layout.fxml"));
                Parent productLayout = productLoader.load();
                ProductController productController = productLoader.getController();

                productController.setData(product);
                productsView.getChildren().addFirst(productLayout);
            }
        } catch (Exception e) {
            System.out.println("\n");
            e.printStackTrace();
            Functions.showAlert("Error de ventana", "No se pudo cargar la ventana para agregar productos.", Alert.AlertType.ERROR);
        }
    }
}
