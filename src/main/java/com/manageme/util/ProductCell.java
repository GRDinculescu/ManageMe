package com.manageme.util;

import com.manageme.InventarioApp;
import com.manageme.controllers.ProductController;
import com.manageme.models.Product;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

public class ProductCell extends ListCell<Product> {
    private FXMLLoader loader;
    private ProductController controller;

    @Override
    protected void updateItem(Product product, boolean empty) {
        super.updateItem(product, empty);

        if (empty || product == null){
            setGraphic(null);
            return;
        }

        if (loader == null){
            loader = new FXMLLoader(InventarioApp.class.getResource("product-layout.fxml"));

            try {
                Parent root = loader.load();
                controller = loader.getController();
                setGraphic(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        controller.setData(product);
    }
}
