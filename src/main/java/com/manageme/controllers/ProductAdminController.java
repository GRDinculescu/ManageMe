package com.manageme.controllers;

import com.google.gson.Gson;
import com.manageme.models.Product;
import com.manageme.util.Functions;
import com.manageme.util.ProductObervable;
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

    public boolean onCloseClick(){
        if (checkChanges()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Datos sin guardar");
            alert.setContentText("Aun hay datos sin guardar.\n¿Seguro que quieres salir?");
            ButtonType buttonYes = new ButtonType("Si");
            ButtonType buttonNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonYes, buttonNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonYes){
                ((Stage) root.getScene().getWindow()).close();
                return true;
            }
        }
        return false;
    }

    @FXML
    public void onDeleteClick() {
        File productsFile = Functions.getProductsFile();
        List<Product> products = Functions.readJson(Product.class, productsFile);
        if (products != null && product != null){
            products.remove(
                    products.stream().filter(p -> p.getName().equalsIgnoreCase(product.getName()))
                            .findFirst()
                            .orElse(null)
            );
            Functions.writeJson(products, productsFile);
            ProductObervable.getInstance().removeProduct(
                    ProductObervable.getInstance().getProducts().stream().filter(p -> p.getName().equalsIgnoreCase(product.getName()))
                            .findFirst()
                            .orElse(null)
            );
        }
        ((Stage) root.getScene().getWindow()).close();
    }

    @FXML
    public void onAddClick() {
        if (generateProduct()) return;

        File productFile = Functions.getProductsFile();
        if (productFile != null){
            List<Product> products = Functions.readJson(Product.class, productFile);

            if (products == null) products = new ArrayList<>();
            boolean alreadyIn = products.stream()
                    .anyMatch(p -> p.getName().equalsIgnoreCase(product.getName()));

            if (!alreadyIn){
                products.add(product);
                ProductObervable.getInstance().addProduct(product);
                Functions.writeJson(products, productFile);
            } else {
                Functions.showAlert("Objecto existente",
                        "El producto que quieres insertar ya esta en la lista",
                        Alert.AlertType.WARNING);
            }
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

            ProductObervable.getInstance().removeProduct(oldProduct);
            ProductObervable.getInstance().addProduct(product);

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
        this.product = product;

        if (product != null){
            // Rellenar campos con los datos del producto existente
            brand.setValue(product.getBrand());
            subcat.setValue(product.getSubcategory());
            cat.setValue(product.getCategory());
            supplier.setValue(product.getSupplier());
            desc.setText(product.getDescription());
            quantity.setText(product.getQuantity() + "");
            price.setText(product.getPrice() + "");
            name.setText(product.getName());

            // Guardar valores viejos
            oldBrand = product.getBrand();
            oldSubCategory = product.getSubcategory();
            oldCategory = product.getCategory();
            oldSupplier = product.getSupplier();
            oldDesc = product.getDescription();
            oldQuantity = product.getQuantity() + "";
            oldPrice = product.getPrice() + "";
            oldName = product.getName();
        } else {
            // Nuevo producto: limpiar campos y no guardar "viejos"
            brand.setValue(null);
            subcat.setValue(null);
            cat.setValue(null);
            supplier.setValue(null);
            desc.clear();
            quantity.clear();
            price.clear();
            name.clear();

            oldBrand = oldSubCategory = oldCategory = oldSupplier = oldDesc = oldQuantity = oldPrice = oldName = null;
        }
    }

}
