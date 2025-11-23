package com.manageme.util;

import com.manageme.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ProductObervable {
    private static final ProductObervable instance = new ProductObervable();
    private final ObservableList<Product> products = FXCollections.observableArrayList();

    private ProductObervable(){}
    public static ProductObervable getInstance() {
        return instance;
    }

    public ObservableList<Product> getProducts(){
        return products;
    }

    public void addProduct(Product p){
        products.add(p);
    }

    public void removeProduct(Product p){
        products.remove(p);
    }
}
