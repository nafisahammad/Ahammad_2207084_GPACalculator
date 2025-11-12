package com.example.ahammad_2207084_cvbuilder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private TableColumn<Course, String> courseName;

    @FXML
    private TableColumn<Course, String> teacher1;

    @FXML
    private TableColumn<Course, String> teacher2;

    @FXML
    private TableView<Course> table;

    @FXML
    private TextField txtCourseCode;

    @FXML
    private TextField txtCourseCredit;

    @FXML
    private TextField txtGrade;

    @FXML
    private TextField txtCourseName;

    @FXML
    private TextField txtTeacher1;

    @FXML
    private TextField txtTeacher2;

    @FXML
    private Label warning;

    @FXML
    private Button getResult;

    @FXML
    private TextField txtTotalCredit;

    @FXML
    private Label buttonWarning;

    @FXML
    public void initialize() {
        courseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseCredit.setCellValueFactory(new PropertyValueFactory<>("courseCredit"));
        grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        teacher1.setCellValueFactory(new PropertyValueFactory<>("teacher1"));
        teacher2.setCellValueFactory(new PropertyValueFactory<>("teacher2"));

        table.setItems(courseList);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        getResult.setDisable(true);

        txtTotalCredit.textProperty().addListener((obs, oldVal, newVal) -> checkCredits());

        courseList.addListener((javafx.collections.ListChangeListener<Course>) change -> checkCredits());
    }

    private void checkCredits() {
        double totalCreditInput = 0;
        try {
            totalCreditInput = Double.parseDouble(txtTotalCredit.getText().trim());
        } catch (NumberFormatException e) {
            getResult.setDisable(true);
            buttonWarning.setText("Enter a valid total credit");
            return;
        }

        double sumCourseCredits = 0;
        for (Course c : courseList) {
            try {
                sumCourseCredits += Double.parseDouble(c.getCourseCredit().trim());
            } catch (NumberFormatException ignored) {}
        }

        if (sumCourseCredits != totalCreditInput) {
            getResult.setDisable(true);
            buttonWarning.setText("Course credits do not match total credit");
        } else {
            getResult.setDisable(false);
            buttonWarning.setText("");
        }
    }

    private boolean isValidGrade(String grade) {
        switch (grade) {
            case "A+": case "A": case "A-":
            case "B+": case "B": case "B-":
            case "C+": case "C": case "C-":
            case "D": case "F":
                return true;
            default:
                return false;
        }
    }
    @FXML
    private void addCourse(ActionEvent event) {
        String courseCode = txtCourseCode.getText().trim();
        String courseCreditStr = txtCourseCredit.getText().trim();
        String grade = txtGrade.getText().trim().toUpperCase();
        String courseName = txtCourseName.getText().trim();
        String teacher1 = txtTeacher1.getText().trim();
        String teacher2 = txtTeacher2.getText().trim();

        if (courseCode.isEmpty() || courseCreditStr.isEmpty() || grade.isEmpty()
                || courseName.isEmpty() || teacher1.isEmpty() || teacher2.isEmpty()) {
            warning.setText("⚠ Please fill all the fields");
            return;
        }

        double courseCredit;
        try {
            courseCredit = Double.parseDouble(courseCreditStr);
            if (courseCredit <= 0) {
                warning.setText("⚠ Credit must be a positive number");
                return;
            }
        } catch (NumberFormatException e) {
            warning.setText("⚠ Invalid credit format (e.g., 3.0 or 1.5)");
            return;
        }

        if (!isValidGrade(grade)) {
            warning.setText("⚠ Invalid grade! Use A+, A, A-, B+, B, B-, C+, C, C-, D, or F");
            return;
        }

        Course newData = new Course(
                courseCode,
                String.valueOf(courseCredit),
                grade,
                courseName,
                teacher1,
                teacher2
        );

        courseList.add(newData);

        txtCourseCode.clear();
        txtCourseCredit.clear();
        txtGrade.clear();
        txtCourseName.clear();
        txtTeacher1.clear();
        txtTeacher2.clear();
        warning.setText("");
    }


    @FXML
    private void clearAll() {
        courseList.clear();
        txtCourseCode.clear();
        txtCourseCredit.clear();
        txtGrade.clear();
        txtCourseName.clear();
        txtTeacher1.clear();
        txtTeacher2.clear();
        txtTotalCredit.clear();
        warning.setText("");
        buttonWarning.setText("");
    }

    @FXML
    private void deleteSelected() {
        ObservableList<Course> selectedCourses = table.getSelectionModel().getSelectedItems();
        if (!selectedCourses.isEmpty()) {
            courseList.removeAll(selectedCourses);
            warning.setText("");
        } else {
            warning.setText("⚠ Please select one or more courses to delete");
        }
    }



    @FXML
    private void getResult(ActionEvent event) throws IOException {
        if (courseList.isEmpty()) {
            warning.setText("Course list is empty");
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
