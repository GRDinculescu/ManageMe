package com.manageme.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.manageme.models.Product;
import com.manageme.util.Functions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddProductAdmin {
    private final Functions functions = Functions.getFunctions();

    @FXML ComboBox<String> brand;
    @FXML ComboBox<String> subcat;
    @FXML ComboBox<String> cat;
    @FXML ComboBox<String> supplier;
    @FXML TextArea desc;
    @FXML TextField quantity;
    @FXML TextField price;
    @FXML TextField name;
    @FXML VBox root;

    public void onCloseClick(ActionEvent actionEvent) {
        root.setVisible(false);
        root.setManaged(false);
    }

    public void onDeleteClick(ActionEvent actionEvent) {
    }

    public void onSaveClick(ActionEvent actionEvent) {
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

        Product product = new Product(
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
    }

    private File generateProductFile(){
        String path = functions.getDocuments().getAbsolutePath();
        try {
            File productsFile = new File(path, "products.json");
            if (productsFile.createNewFile()) System.out.println("Archivo de productos generado en: " + path);
            return productsFile;
        } catch (IOException e){
            Functions.showAlert("Error de archivo",
                    "No se pudo generar el archivo de productos en " + path,
                    Alert.AlertType.ERROR);
        }
        return null;
    }
}
