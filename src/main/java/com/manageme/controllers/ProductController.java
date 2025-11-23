package com.manageme.controllers;

import com.manageme.models.Product;
import com.manageme.util.Functions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ProductController {
    @FXML TextArea description;
    @FXML Label subcategory;
    @FXML Label supplier;
    @FXML Label name;
    @FXML Label category;
    @FXML Label brand;
    @FXML Label price;
    @FXML Label quantity;

    private Product product;

    public void setData(Product product){
        this.product = product;
        name.setText(product.getName());
        category.setText(product.getCategory());
        subcategory.setText(product.getSubcategory());

        supplier.setText(product.getSupplier());
        brand.setText(product.getBrand());

        price.setText(product.getPrice() + "€");
        quantity.setText(product.getQuantity() + "/uds");

        description.setText(product.getDescription());
    }

    public void onEditClick(ActionEvent actionEvent) {
        try {
            ProductAdminController controller =
                    Functions.showAddProductMenu(ProductMenuType.EDIT, "Editar producto", product);

        } catch (Exception e){
            Functions.showAlert("Error de ventana", "No se pudo cargar la ventana para editar el producto", Alert.AlertType.ERROR);
        }
    }

    public void onMoreClick(ActionEvent actionEvent) {
        TextArea textArea = new TextArea(description.getText());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefSize(400, 300);
        textArea.setStyle("-fx-padding: 10;");

        Stage window = new Stage();
        window.setTitle("Descripcion");
        window.setScene(new Scene(textArea));
        window.show();
    }
}
