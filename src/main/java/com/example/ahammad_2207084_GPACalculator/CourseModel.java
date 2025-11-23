package com.example.ahammad_2207084_GPACalculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class CourseModel {
    private ObservableList<Course> courseList = FXCollections.observableArrayList();

    public ObservableList<Course> getCourseList() {
        return courseList;
    }

    public CourseModel() {}
    public CourseModel(CourseModel courseModel) {
        this.courseList = courseModel.getCourseList();
    }
    public CourseModel(List<Course> courseList) {
        this.courseList = (ObservableList<Course>) courseList;
    }
    public CourseModel(ObservableList<Course> courseList) {
        this.courseList = courseList;
    }

    public double calculateGPA() {

        double totalCredits = 0;
        double totalGradePoints = 0;

        for (Course c : courseList) {
            double credit = Double.parseDouble(c.getCourseCredit().trim());
            double gradePoint = getGradePoint(c.getGrade());

            totalCredits += credit;
            totalGradePoints += gradePoint * credit;
        }

        if (totalCredits == 0) return 0;
        return totalGradePoints / totalCredits;

    }

    private static double getGradePoint(String grade) {
        switch (grade) {
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
}
