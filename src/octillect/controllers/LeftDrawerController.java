package octillect.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import octillect.models.Column;
import octillect.models.Project;
import octillect.models.Task;
import octillect.models.builders.ColumnBuilder;
import octillect.models.builders.ProjectBuilder;
import octillect.models.builders.TaskBuilder;

public class LeftDrawerController {

    @FXML private JFXDrawer leftDrawer;
    @FXML private JFXButton addNewProjectButton;
    @FXML private ListView<Project> userProjectsListView;

    private ProjectController projectController;
    private StackPane rootStackPane;

    @FXML
    public void initialize() {

        /* TODO: Remove this temporary projects */

        Project firstProject = new ProjectBuilder().with($ -> {

            Column column1 = new ColumnBuilder().with($_column -> {
                Task task1 = new TaskBuilder().with($_task -> { $_task.id = "111"; $_task.name = "T1"; }).build();
                Task task2 = new TaskBuilder().with($_task -> { $_task.id = "112"; $_task.name = "T2"; }).build();
                $_column.id    = "11";
                $_column.name  = "To-do";
                $_column.tasks = FXCollections.observableArrayList(task1, task2);
            }).build();

            Column column2 = new ColumnBuilder().with($_column -> {
                Task task3 = new TaskBuilder().with($_task -> { $_task.id = "121"; $_task.name = "T3"; }).build();
                Task task4 = new TaskBuilder().with($_task -> { $_task.id = "122"; $_task.name = "T4"; }).build();
                $_column.id    = "12";
                $_column.name  = "Done";
                $_column.tasks = FXCollections.observableArrayList(task3, task4);
            }).build();

            $.name = "Project 1";
            $.columns = FXCollections.observableArrayList(column1, column2);

        }).build();

        Project secondProject = new ProjectBuilder().with($ -> {

            Column column1 = new ColumnBuilder().with($_column -> {
                Task task1 = new TaskBuilder().with($_task -> { $_task.id = "211"; $_task.name = "X1"; }).build();
                Task task2 = new TaskBuilder().with($_task -> { $_task.id = "212"; $_task.name = "X2"; }).build();
                $_column.id    = "21";
                $_column.name  = "Front-end";
                $_column.tasks = FXCollections.observableArrayList(task1, task2);
            }).build();

            Column column2 = new ColumnBuilder().with($_column -> {
                Task task3 = new TaskBuilder().with($_task -> { $_task.id = "221"; $_task.name = "X3"; }).build();
                Task task4 = new TaskBuilder().with($_task -> { $_task.id = "222"; $_task.name = "X4"; }).build();
                $_column.id    = "22";
                $_column.name  = "Back-end";
                $_column.tasks = FXCollections.observableArrayList(task3, task4);
            }).build();

            $.name = "Project 2";
            $.columns = FXCollections.observableArrayList(column1, column2);

        }).build();

        /* TODO: Load signed in user projects here */
        ObservableList<Project> userProjects = FXCollections.observableArrayList(firstProject, secondProject);

        userProjectsListView.setItems(userProjects);
        userProjectsListView.setCellFactory(param -> new ListCell<Project>() {
            @Override
            protected void updateItem(Project item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item == null){
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
    }

    public void handleAddNewProjectButtonAction(ActionEvent actionEvent) {
        NewProjectDialogController newProjectDialogController = new NewProjectDialogController();
        newProjectDialogController.showNewProjectDialog(rootStackPane);
    }

    public void handleUserProjectsListViewMouseClicked(MouseEvent mouseEvent) {
        projectController.loadProject(userProjectsListView.getSelectionModel().getSelectedItem());
    }

    public JFXDrawer getLeftDrawer() {
        return leftDrawer;
    }

    public void setProjectController(ProjectController projectController) {
        this.projectController = projectController;
    }

    public void setRootStackPane(StackPane rootStackPane) {
        this.rootStackPane = rootStackPane;
    }

}