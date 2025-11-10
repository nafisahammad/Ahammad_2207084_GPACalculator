package com.example.ahammad_2207084_cvbuilder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    public void startButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("gpa-input.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("GPA Calculator");
        stage.setScene(scene);
        stage.show();
    }
}
