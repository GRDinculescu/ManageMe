package com.manageme.controllers;

import com.google.gson.Gson;
import com.manageme.models.Product;
import com.manageme.util.Functions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ProductAdminController {
    private final Functions functions = Functions.getFunctions();
    @FXML Button addButton, editButton, deleteButton;

    @FXML ComboBox<String> brand, subcat, cat, supplier;
    @FXML TextArea desc;
    @FXML TextField quantity, price, name;
    @FXML VBox root;

    private String oldBrand, oldSubCategory, oldCategory,
        oldSupplier, oldDesc, oldQuantity, oldPrice, oldName;

    public void initialize(){
        getOldValues();
    }

    @FXML
    public void onCloseClick(){
        if (checkChanges()) {
            Alert alert = Functions.showAlert("Cambios sin guardar",
                    "Aun hay cambios que no has guardado. Seguro que quieres salir",
                    Alert.AlertType.INFORMATION);
            ButtonType buttonYes = new ButtonType("Si");
            ButtonType buttonNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonYes, buttonNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonYes){
                ((Stage) root.getScene().getWindow()).close();
            }
        }
    }

    @FXML
    public void onDeleteClick() {
        System.out.println("BORRADO");
    }

    @FXML
    public void onAddClick() {
        if (generateProduct()) return;

        File productFile = Functions.getProductsFile();
        if (productFile != null){
            Gson gson = new Gson();
            List<Product> products = Functions.readJson(Product.class, productFile);

            if (products == null) products = new ArrayList<>();
            products.add(product);

            Functions.writeJson(products, productFile);
        }
        onCloseClick();
    }

    @FXML
    public void onEditClick(ActionEvent actionEvent) {
        Product oldProduct = product;

        if (generateProduct()) return;

        File productFile = Functions.getProductsFile();
        List<Product> products = Functions.readJson(Product.class, productFile);
        if (products != null){
            products.remove(oldProduct);
            products.addFirst(product);
            Functions.writeJson(products, productFile);
        }

        onCloseClick();
    }

    // ================================
    // Funciones que no estan en el xml
    // ================================
    private boolean generateProduct(){
        double sellPrice;
        int quant;

        try {
            sellPrice = Double.parseDouble(price.getText());
        } catch (Exception e){
            Functions.showAlert("Error de valor", "Debes introducir un numero decimal.", Alert.AlertType.ERROR);
            return true;
        }
        try {
            quant = Integer.parseInt(quantity.getText());
        } catch (Exception e){
            Functions.showAlert("Error de valor", "Debes introducir un numero entero.", Alert.AlertType.ERROR);
            return true;
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
        return false;
    }

    private File generateProductFile(){
        String path = functions.getDocumentsFolder().getAbsolutePath();
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

    private void getOldValues(){
        oldBrand = brand.getValue();
        oldSubCategory = subcat.getValue();
        oldCategory = cat.getValue();
        oldSupplier = supplier.getValue();
        oldDesc = desc.getText();
        oldQuantity = quantity.getText();
        oldPrice = price.getText();
        oldName = name.getText();
    }

    private boolean checkChanges(){
        // Comparar ComboBoxes y TextFields/TextArea
        boolean brandChanged = !Objects.equals(oldBrand, brand.getValue());
        boolean subCategoryChanged = !Objects.equals(oldSubCategory, subcat.getValue());
        boolean categoryChanged = !Objects.equals(oldCategory, cat.getValue());
        boolean supplierChanged = !Objects.equals(oldSupplier, supplier.getValue());
        boolean descChanged = !Objects.equals(oldDesc, desc.getText());
        boolean quantityChanged = !Objects.equals(oldQuantity, quantity.getText());
        boolean priceChanged = !Objects.equals(oldPrice, price.getText());
        boolean nameChanged = !Objects.equals(oldName, name.getText());

        // Si alguno cambió, devolvemos true
        return brandChanged || subCategoryChanged || categoryChanged || supplierChanged
                || descChanged || quantityChanged || priceChanged || nameChanged;
    }

    private Product product;

    public Product getProduct(){
        return product;
    }

    public void setEditMode(ProductMenuType type){
        if (type == ProductMenuType.EDIT) {
            addButton.setVisible(false);
            addButton.setManaged(false);
        } else if (type == ProductMenuType.ADD) {
            editButton.setManaged(false);
            editButton.setVisible(false);
            deleteButton.setVisible(false);
            deleteButton.setManaged(false);
        }
    }

    public void setInfo(Product product){
        brand.setValue(product.getBrand());
        subcat.setValue(product.getSubcategory());
        cat.setValue(product.getCategory());
        supplier.setValue(product.getSupplier());
        desc.setText(product.getDescription());
        quantity.setText(product.getQuantity() + "");
        price.setText(product.getPrice() + "");
        name.setText(product.getName());
    }
}
