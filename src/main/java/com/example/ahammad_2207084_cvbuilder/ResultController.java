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

    public void calculate(ObservableList<Course> courseList) {

        resultTable.setItems(courseList);

        double totalCredits = 0;
        double totalGradePoints = 0;

        for (Course c : courseList) {
            double credit = Double.parseDouble(c.getCourseCredit().trim());
            double gradePoint = getGradePoint(c.getGrade());

            totalCredits += credit;
            totalGradePoints += gradePoint * credit;
        }

        if (totalCredits == 0) return;
        double gpaValue = totalGradePoints / totalCredits;
        gpa.setText(String.format("%.2f", gpaValue));
    }

    private static double getGradePoint(String grade) {
        switch (grade.toUpperCase()) {
            case "A+": return 4.0;
            case "A":  return 3.75;
            case "A-": return 3.5;
            case "B+": return 3.25;
            case "B":  return 3.0;
            case "B-": return 2.75;
            case "C+": return 2.5;
            case "C":  return 2.25;
            case "C-": return 2.0;
            case "D":  return 1.0;
            case "F":  return 0.0;
            default:   return 0.0;
        }
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
