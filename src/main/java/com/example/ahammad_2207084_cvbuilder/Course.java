package com.example.ahammad_2207084_cvbuilder;

public class Course {
    private String courseCode;
    private String courseCredit;
    private String grade;
    private String courseName;
    private String Teacher1;
    private String Teacher2;

    public Course(String courseCode, String courseCredit, String grade, String courseName, String teacher1, String teacher2) {
        this.courseCode = courseCode;
        this.courseCredit = courseCredit;
        this.grade = grade;
        this.courseName = courseName;
        Teacher1 = teacher1;
        Teacher2 = teacher2;
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

    public String getCourseName() {
        return courseName;
    }

    public String getTeacher1() {
        return Teacher1;
    }

    public String getTeacher2() {
        return Teacher2;
    }
}
