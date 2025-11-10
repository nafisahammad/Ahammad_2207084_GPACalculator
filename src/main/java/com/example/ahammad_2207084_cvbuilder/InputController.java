package com.example.ahammad_2207084_cvbuilder;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class InputController {

    @FXML
    private TableColumn<Course, String> courseCode;

    @FXML
    private TableColumn<Course, String> courseCredit;

    @FXML
    private TableColumn<Course, String> grade;

    @FXML
    private TableView<Course> table;

    @FXML
    private TextField txtCourseCode;

    @FXML
    private TextField txtCourseCredit;

    @FXML
    private TextField txtGrade;

    @FXML
    public void initialize() {
        courseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseCredit.setCellValueFactory(new PropertyValueFactory<>("courseCredit"));
        grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        
        table.setItems(FXCollections.observableArrayList());
    }

    @FXML
    private void addCourse(ActionEvent event) {
        if (!txtCourseCode.getText().isEmpty() &&
                !txtCourseCredit.getText().isEmpty() &&
                !txtGrade.getText().isEmpty()) {

            Course newData = new Course(
                    txtCourseCode.getText(),
                    txtCourseCredit.getText(),
                    txtGrade.getText()
            );

            table.getItems().add(newData);

            txtCourseCode.clear();
            txtCourseCredit.clear();
            txtGrade.clear();
        } else {
            System.out.println("Fields should not be empty.");
        }
    }
}
