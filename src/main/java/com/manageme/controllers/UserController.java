package com.manageme.controllers;

import com.manageme.models.Product;
import com.manageme.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserController {

    @FXML Label name;
    @FXML Label lastname;
    @FXML Label rol;
    @FXML Label phone;
    @FXML Label bday;
    @FXML Label email;
    @FXML Label street;
    @FXML Label password;

    public void setData(User user){
        name.setText("Nombre: "+user.getName());
        lastname.setText("Apellido: "+user.getLastname());
        rol.setText("Rol: "+user.getRol());
        phone.setText("Teléfono: "+user.getPhone());
        bday.setText("F.Nac: "+user.getBday());
        email.setText("Correo: "+user.getEmail());
        street.setText("Domicilio: "+user.getStreet());
        password.setText("Contraseña: "+"*".repeat(user.getPassword().length()));
    }



}
