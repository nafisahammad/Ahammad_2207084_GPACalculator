package com.example.ahammad_2207084_cvbuilder;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public class ResultController {
    @FXML
    private TableColumn<Course, String> resultCourseCode;

    @FXML
    private TableColumn<Course, String> resultCourseCredit;

    @FXML
    private TableColumn<Course, String> resultGrade;

    @FXML
    private TableView<Course> resultTable;

    @FXML
    public void initialize() {
        resultCourseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        resultCourseCredit.setCellValueFactory(new PropertyValueFactory<>("courseCredit"));
        resultGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));

        resultTable.setSelectionModel(null);
    }

    public void getResult(ObservableList<Course> courseList) {

        resultTable.setItems(courseList);
    }
    @FXML
    private void toBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gpa-input.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
