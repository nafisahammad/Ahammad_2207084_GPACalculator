package com.example.ahammad_2207084_cvbuilder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class InputController {

    private ObservableList<Course> courseList = FXCollections.observableArrayList();

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

        table.setItems(courseList);
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

            courseList.add(newData);

            txtCourseCode.clear();
            txtCourseCredit.clear();
            txtGrade.clear();
        } else {
            System.out.println("Fields should not be empty.");
        }
    }
    @FXML
    private void getResult(ActionEvent event) throws IOException {
        if (courseList.isEmpty()) {
            System.out.println("Fields should not be empty.");
        }
        else  {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("results.fxml"));
            Parent root = loader.load();

            ResultController resultController = loader.getController();

            resultController.getResult(courseList);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        }
    }
}
