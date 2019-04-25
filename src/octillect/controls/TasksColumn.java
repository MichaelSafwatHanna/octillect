package octillect.controls;

import java.util.Collections;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import octillect.models.Column;

public class TasksColumn extends ListCell<Column> {

    @FXML private VBox tasksColumnVBox;
    @FXML private Label columnNameLabel;
    @FXML private ListView tasksListView;

    public TasksColumn() {

        setOnDragDetected(event -> {
            if (getItem() != null) {
                Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);

                SnapshotParameters parameters = new SnapshotParameters();
                parameters.setFill(Color.TRANSPARENT);

                Image snapshot = snapshot(parameters, null);
                dragboard.setDragView(snapshot, event.getX(), event.getY());

                ClipboardContent content = new ClipboardContent();
                content.putString(getItem().getId()); // Save the Source Column ID
                dragboard.setContent(content);
            }
        });

        setOnDragOver(event -> {
            if (event.getGestureSource() instanceof TasksColumn
                    && event.getGestureSource() != this && getItem() != null) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        });

        setOnDragEntered(event -> {
            if (event.getGestureSource() instanceof TasksColumn
                    && event.getGestureSource() != this) {
                setOpacity(0.32); // Set Opacity of Column on mouse enter to 0.32
            }
        });

        setOnDragExited(event -> {
            if (event.getGestureSource() instanceof TasksColumn
                    && event.getGestureSource() != this) {
                setOpacity(1); // Reset Opacity of Column on mouse exit to 1
            }
        });

        setOnDragDropped(event -> {
            if (event.getGestureSource() instanceof TasksColumn
                    && event.getDragboard().hasString()) {
                int sourceIndex = ((TasksColumn) event.getGestureSource()).getIndex();
                int targetIndex = ((TasksColumn) event.getGestureTarget()).getIndex();
                Collections.swap(getListView().getItems(), sourceIndex, targetIndex);
            }
        });

    }

    @Override
    public void updateItem(Column columnItem, boolean empty) {

        super.updateItem(columnItem, empty);

        if (empty || columnItem == null) {
            setGraphic(null);
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/octillect/views/TasksColumnView.fxml"));
            fxmlLoader.setController(this);
            tasksColumnVBox = fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* TODO: Populate the TasksColumn view here */
        columnNameLabel.setText(columnItem.getName());

        // Populate the TasksColumn's tasksListView with columnItem's tasks
        tasksListView.setItems(columnItem.getTasks());
        tasksListView.setCellFactory(param -> new TaskCell());

        setGraphic(tasksColumnVBox);

    }

    public ListView getTasksListView() {
        return tasksListView;
    }
}