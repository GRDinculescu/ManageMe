package com.manageme.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manageme.InventarioApp;
import com.manageme.models.Category;
import com.manageme.models.Product;
import com.manageme.models.User;
import com.manageme.util.Functions;
import com.manageme.util.ProductCell;
import com.manageme.util.ProductObervable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private final Functions functions = Functions.getFunctions();
    @FXML ListView<Product> productsView;
    @FXML VBox root;
    @FXML VBox categories;
    @FXML VBox filtres;

    @FXML MenuBarController menubarController;

    private boolean viewFiltres = false;

    private User user;

    public void initialize(){
        menubarController.initData(user); // Inicializar el menuBar (importante)
        productsView.setItems(ProductObervable.getInstance().getProducts());
        productsView.setCellFactory(list -> new ProductCell());
        loadCategories();
    }

    void loadCategories(){
        List<Category> categories = Functions.readJson(Category.class, Functions.getCategoriesFile());
        if (categories != null){
            List<Category[]> categoryMatrix = new ArrayList<>();
            int index = 0;
            for (Category c : categories){
                if (index == 0){
                    Category[] cat = new Category[3];
                    cat[0] = c;
                    categoryMatrix.add(cat);
                } else {
                    categoryMatrix.getLast()[index] = c;
                }
                index++;
                if (index == 3) index = 0;
            }

            for (Category[] cm : categoryMatrix){
                HBox hBox = new HBox();
                hBox.setSpacing(20);

                for (Category c : cm){
                    Button b = new Button(c.getName());
                    b.getStyleClass().addAll(("h-150, w-150, ts-3, bold, round-3, bc-lblue").split(", "));
                    hBox.getChildren().add(b);
                }

                this.categories.getChildren().add(hBox);
            }
        }
    }

    void initData(User user){
        this.user = user;
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
                productLoader.load();
                ProductController productController = productLoader.getController();

                productController.setData(product);
            }

            Functions.sendNotification("Se ha agregado el producto", Functions.getMainUser().getName());
        } catch (Exception e) {
            System.out.println("\n");
            e.printStackTrace();
            Functions.showAlert("Error de ventana", "No se pudo cargar la ventana para agregar productos.", Alert.AlertType.ERROR);
        }
    }
}
