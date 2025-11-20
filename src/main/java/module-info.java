module com.example.ahammad_2207084_cvbuilder {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires javafx.graphics;
    requires java.desktop;
    requires javafx.base;
    requires java.sql;

    opens com.example.ahammad_2207084_cvbuilder to javafx.fxml;
    exports com.example.ahammad_2207084_cvbuilder;
}