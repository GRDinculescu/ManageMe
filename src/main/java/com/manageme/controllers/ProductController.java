package com.manageme.controllers;

import com.manageme.models.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProductController {
    @FXML Label name;
    @FXML Label category;
    @FXML Label brand;
    @FXML Label price;
    @FXML Label quantity;

    public void setData(Product product){
        name.setText(product.getName());
        category.setText(product.getCategory());
        brand.setText(product.getBrand());
        price.setText(product.getPrice() + "");
        quantity.setText(product.getQuantity() + "");
    }

    public void onEditClick(ActionEvent actionEvent) {

    }
}
