module org.example.proyectomarta {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;

    opens org.example.proyectomarta to javafx.fxml;
    exports org.example.proyectomarta;
}