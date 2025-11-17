package com.example.ahammad_2207084_cvbuilder;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;

public class ResultController {
    @FXML
    private Label gpa;

    @FXML
    private TableColumn<Course, String> resultCourseCode;

    @FXML
    private TableColumn<Course, String> resultCourseCredit;

    @FXML
    private TableColumn<Course, String> resultGrade;

    @FXML
    private TableColumn<Course, String> resultCourseName;

    @FXML
    private TableColumn<Course, String> resultTeacher1;

    @FXML
    private TableColumn<Course, String> resultTeacher2;

    @FXML
    private TableView<Course> resultTable;


    @FXML
    public void initialize() {
        resultCourseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        resultCourseCredit.setCellValueFactory(new PropertyValueFactory<>("courseCredit"));
        resultGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        resultCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        resultTeacher1.setCellValueFactory(new PropertyValueFactory<>("teacher1"));
        resultTeacher2.setCellValueFactory(new PropertyValueFactory<>("teacher2"));

        resultTable.setSelectionModel(null);
    }

    public void setResult(CourseModel myCourseModel) {
        double gpaValue= myCourseModel.calculateGPA();
        resultTable.setItems(myCourseModel.getCourseList());
        gpa.setText(String.format("%.2f", gpaValue));
    }


    @FXML
    private void toBack(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gpa-input.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.show();
    }
}
