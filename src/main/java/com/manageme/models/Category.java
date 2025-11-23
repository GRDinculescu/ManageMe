package com.manageme.models;

import java.util.List;

public class Category {
    List<String> subcategories;
    String name;

    public Category(String name, List<String> subcategories) {
        this.subcategories = subcategories;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "subcategories=" + subcategories +
                ", name='" + name + '\'' +
                '}';
    }

    public List<String> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<String> subcategories) {
        this.subcategories = subcategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
