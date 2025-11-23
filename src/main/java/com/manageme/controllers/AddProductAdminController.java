package com.manageme.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manageme.models.Product;
import com.manageme.util.Functions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class AddProductAdminController {
    private final Functions functions = Functions.getFunctions();
    @FXML Button deleteButton;

    @FXML ComboBox<String> brand;
    @FXML ComboBox<String> subcat;
    @FXML ComboBox<String> cat;
    @FXML ComboBox<String> supplier;
    @FXML TextArea desc;
    @FXML TextField quantity;
    @FXML TextField price;
    @FXML TextField name;
    @FXML VBox root;

    private Product product;

    public Product getProduct(){
        return product;
    }

    @FXML
    public void onCloseClick(){
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    public void onDeleteClick() {
        System.out.println("BORRADO");
    }

    @FXML
    public void onSaveClick() {
        double sellPrice;
        int quant;

        try {
            sellPrice = Double.parseDouble(price.getText());
        } catch (Exception e){
            Functions.showAlert("Error de valor", "Debes introducir un numero decimal.", Alert.AlertType.ERROR);
            return;
        }
        try {
            quant = Integer.parseInt(quantity.getText());
        } catch (Exception e){
            Functions.showAlert("Error de valor", "Debes introducir un numero entero.", Alert.AlertType.ERROR);
            return;
        }

        product = new Product(
                name.getText(),
                sellPrice,
                quant,
                desc.getText(),
                supplier.getValue(),
                cat.getValue(),
                subcat.getValue(),
                brand.getValue()
        );

        File productFile = generateProductFile();
        if (productFile != null){
            Gson gson = new Gson();
            Type productsType = new TypeToken<List<Product>>(){}.getType();
            List<Product> products;
            try (FileReader fr = new FileReader(productFile)) {
                products = gson.fromJson(fr, productsType);
            } catch (IOException e) {
                Functions.showAlert("Error de archivo", "No se pudo cargar los productos.", Alert.AlertType.ERROR);
                return;
            }

            if (products == null) products = new ArrayList<>();
            products.add(product);

            try (FileWriter fw = new FileWriter(productFile)) {
                gson.toJson(products, fw);
            } catch (IOException e) {
                Functions.showAlert("Error de archivo", "No se pudo guardar el archivo de productos.", Alert.AlertType.ERROR);
            }
        }
        onCloseClick();
    }

    private File generateProductFile(){
        String path = functions.getDocuments().getAbsolutePath();
        try {
            File productsFile = new File(path, "products.json");
            if (productsFile.createNewFile()) System.out.println("Archivo de productos generado en: " + path);
            return productsFile;
        } catch (IOException e){
            functions.showAlert("Error de archivo",
                    "No se pudo generar el archivo de productos en " + path,
                    Alert.AlertType.ERROR);
        }
        return null;
    }

    public void setDeleteButtonVisible(boolean visible){
        deleteButton.setVisible(visible);
    }
}
