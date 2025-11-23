package com.example.ahammad_2207084_GPACalculator;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
        import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HistoryController {

    @FXML
    private TableView<HistoryEntry> historyTable;
    @FXML
    private TableColumn<HistoryEntry, String> colRoll;
    @FXML
    private TableColumn<HistoryEntry, String> colSemester;
    @FXML
    private TableColumn<HistoryEntry, Double> colGPA;
    @FXML
    private TableColumn<HistoryEntry, Void> colShowList;
    @FXML
    private TextField txtRollSearch;
    @FXML
    private TextField txtSemesterSearch;

    private ObservableList<HistoryEntry> masterData;
    private FilteredList<HistoryEntry> filteredData;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();


    public static class HistoryEntry {
        private final SimpleStringProperty roll;
        private final SimpleStringProperty semester;
        private final SimpleDoubleProperty gpa;
        private final CourseModel courseModel;

        public HistoryEntry(String roll, String semester, double gpa, CourseModel courseModel) {
            this.roll = new SimpleStringProperty(roll);
            this.semester = new SimpleStringProperty(semester);
            this.gpa = new SimpleDoubleProperty(gpa);
            this.courseModel = courseModel;
        }

        public String getRoll() { return roll.get(); }
        public String getSemester() { return semester.get(); }
        public double getGpa() { return gpa.get(); }
        public CourseModel getCourseModel() { return courseModel; }
    }

    @FXML
    public void initialize() {
        DatabaseHelper.createTable();
        loadHistoryDataFromDB();

        colRoll.setCellValueFactory(cellData -> cellData.getValue().roll);
        colSemester.setCellValueFactory(cellData -> cellData.getValue().semester);
        colGPA.setCellValueFactory(cellData -> cellData.getValue().gpa.asObject());

        setupSearchFiltering();

        setupShowListButtonColumn();
    }

    private void loadHistoryDataFromDB() {
        executor.execute(() -> {
            List<HistoryEntry> entries = DatabaseHelper.loadAllEntries();
            javafx.application.Platform.runLater(() -> {
                masterData = FXCollections.observableArrayList(entries);
                if (filteredData == null) {
                    setupSearchFiltering();
                } else {
                    filteredData = new FilteredList<>(masterData, p -> true);
                    historyTable.setItems(new SortedList<>(filteredData));
                }
            });
        });
    }

    private void setupSearchFiltering() {
        if (masterData == null) {
            masterData = FXCollections.observableArrayList();
        }

        filteredData = new FilteredList<>(masterData, p -> true);

        SortedList<HistoryEntry> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(historyTable.comparatorProperty());

        historyTable.setItems(sortedData);
    }

    private void setupShowListButtonColumn() {
        Callback<TableColumn<HistoryEntry, Void>, TableCell<HistoryEntry, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<HistoryEntry, Void> call(final TableColumn<HistoryEntry, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Show List");

                    {
                        btn.getStyleClass().addAll("show-list-button");

                        btn.setOnAction(event -> {
                            HistoryEntry entry = getTableView().getItems().get(getIndex());
                            showDetails(entry);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox box = new HBox(btn);
                            box.setAlignment(javafx.geometry.Pos.CENTER);
                            setGraphic(box);
                        }
                    }
                };
            }
        };

        colShowList.setCellFactory(cellFactory);
    }

    private void showDetails(HistoryEntry entry) {
        Dialog<Void> dialog = new Dialog<>();
        DialogPane dp = dialog.getDialogPane();

        dp.getStylesheets().add(
                getClass().getResource("dialog.css").toExternalForm()
        );
        dp.getStyleClass().add("custom-dialog-pane");

        dialog.setTitle("Course Details for " + entry.getSemester());
        dialog.setHeaderText("Roll: " + entry.getRoll() +
                " | GPA: " + String.format("%.2f", entry.getGpa()));

        TableView<Course> detailTable = new TableView<>();
        detailTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        detailTable.setPrefWidth(700);
        detailTable.setPrefHeight(400);

        TableColumn<Course, String> codeCol = new TableColumn<>("Course Code");
        codeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCourseCode()));

        TableColumn<Course, String> nameCol = new TableColumn<>("Course Name");
        nameCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCourseName()));

        TableColumn<Course, String> creditCol = new TableColumn<>("Credit");
        creditCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCourseCredit()));

        TableColumn<Course, String> teacher1Col = new TableColumn<>("Teacher 1");
        teacher1Col.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTeacher1()));

        TableColumn<Course, String> teacher2Col = new TableColumn<>("Teacher 2");
        teacher2Col.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTeacher2()));

        TableColumn<Course, String> gradeCol = new TableColumn<>("Grade");
        gradeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getGrade()));

        detailTable.getColumns().addAll(
                codeCol, nameCol, creditCol, teacher1Col, teacher2Col, gradeCol
        );

        detailTable.setItems(entry.getCourseModel().getCourseList());

        dp.setPrefWidth(750);
        dp.setPrefHeight(480);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setContent(detailTable);

        dialog.showAndWait();
    }



    @FXML
    private void searchByRoll() {
        String searchText = txtRollSearch.getText().trim().toLowerCase();

        txtSemesterSearch.setText("");

        filteredData.setPredicate(entry -> {
            if (searchText.isEmpty()) {
                return true;
            }
            return entry.getRoll().toLowerCase().contains(searchText);
        });
    }

    @FXML
    private void searchBySemester() {
        String searchText = txtSemesterSearch.getText().trim().toLowerCase();

        txtRollSearch.setText("");

        filteredData.setPredicate(entry -> {
            if (searchText.isEmpty()) {
                return true;
            }
            return entry.getSemester().toLowerCase().contains(searchText);
        });
    }

    @FXML
    private void resetSearch() {
        txtRollSearch.setText("");
        txtSemesterSearch.setText("");
        filteredData.setPredicate(p -> true);
    }

    @FXML
    private void clearHistory() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        DialogPane dp = alert.getDialogPane();

        dp.getStylesheets().add(
                getClass().getResource("alert.css").toExternalForm()
        );
        dp.getStyleClass().add("dialog-pane");
        alert.setTitle("Confirm Clear History");
        alert.setHeaderText("You are about to clear all GPA history.");
        alert.setContentText("Are you sure you want to proceed?");

        alert.getDialogPane().getStyleClass().add("dialog-pane");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                executor.execute(() -> {
                    DatabaseHelper.clearAllEntries();
                    javafx.application.Platform.runLater(() -> {
                        masterData.clear();
                        resetSearch();
                    });
                });
            }
        });
    }

    @FXML
    private void toBack(ActionEvent event) throws IOException {
        ResultController resultController = new ResultController();
        resultController.toBack(event);
    }
}