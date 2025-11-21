package com.manageme.models;

import java.util.Date;

public class User {
    String name;
    String lastname;
    int phone;
    Date bday;
    String email;
    String street;
    String rol;

    public User(String name, String lastname, int phone, Date bday, String email, String street, String rol) {
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.bday = bday;
        this.email = email;
        this.street = street;
        this.rol = rol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Date getBday() {
        return bday;
    }

    public void setBday(Date bday) {
        this.bday = bday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
