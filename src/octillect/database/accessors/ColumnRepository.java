package octillect.database.accessors;

import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;

import javafx.collections.FXCollections;

import octillect.database.documents.ColumnDocument;
import octillect.database.firebase.FirestoreAPI;
import octillect.models.Column;
import octillect.models.Task;
import octillect.models.builders.ColumnBuilder;

public class ColumnRepository {

    // Add new column data to database.
    public static void add(Column column) {
        ColumnDocument document = new ColumnDocument();
        document.setId(column.getId());
        document.setName(column.getName());

        if (column.getTasks() != null) {
            ArrayList<String> tasksIds = new ArrayList<>();
            for (Task task : column.getTasks()) {
                tasksIds.add(task.getId());
            }
            document.setTasksIds(tasksIds);
        }
        FirestoreAPI.insertDocument(FirestoreAPI.COLUMNS, document.getId(), document);
    }

    public static Column get(String columnId) {
        Column column = null;
        ColumnDocument document = ((DocumentSnapshot) FirestoreAPI.selectDocument(FirestoreAPI.COLUMNS, columnId)).toObject(ColumnDocument.class);

        if (document != null) {

            column = new ColumnBuilder().with($ -> {
                $.id = document.getId();
                $.name = document.getName();

                if (document.getTasksIds() != null) {
                    ArrayList<Task> tasksIds = new ArrayList<>();
                    for (String taskId : document.getTasksIds()) {
                        tasksIds.add(TaskRepository.get(taskId));
                    }
                    $.tasks = FXCollections.observableArrayList(tasksIds);

                }
            }).build();

        }
        return column;
    }
}
