package com.example.ahammad_2207084_cvbuilder;

public class Course {
    private String courseCode;
    private String courseCredit;
    private String grade;

    public Course(String courseCode, String courseCredit, String grade) {
        this.courseCode = courseCode;
        this.courseCredit = courseCredit;
        this.grade = grade;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseCredit() {
        return courseCredit;
    }

    public String getGrade() {
        return grade;
    }
}
