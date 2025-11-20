package com.example.ahammad_2207084_cvbuilder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        DatabaseHelper.createTable();
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(root);

        stage.setMinWidth(1000);
        stage.setMinHeight(700);

        stage.setTitle("GPA Calculator");
        stage.setScene(scene);
        stage.show();
    }
}
