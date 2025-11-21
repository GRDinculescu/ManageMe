module org.example.proyectomarta {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;
    requires com.google.gson;
    requires org.example.proyectomarta;

    opens com.manageme to javafx.fxml;
    exports com.manageme;
    exports com.manageme.controllers;
    opens com.manageme.controllers to javafx.fxml;
    opens com.manageme.models to com.google.gson;
}