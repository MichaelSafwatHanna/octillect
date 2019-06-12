package octillect.database.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javafx.collections.FXCollections;

import octillect.database.documents.TaskDocument;
import octillect.database.documents.TaskDocument.SubTaskMap;
import octillect.database.firebase.FirestoreAPI;
import octillect.models.*;
import octillect.models.builders.TaskBuilder;

public class TaskRepository implements Repository<Task> {

    private static TaskRepository ourInstance = new TaskRepository();

    public static TaskRepository getInstance() {
        return ourInstance;
    }

    private TaskRepository() {
    }


    @Override
    public void add(Task task) {
        TaskDocument document = new TaskDocument();
        document.setId(task.getId());
        document.setName(task.getName());
        document.setDescription(task.getDescription());
        document.setIsCompleted(task.getIsCompleted());
        document.setDueDate(task.getDueDate());
        document.setCreationDate(task.getCreationDate());
        document.setCreatorId(task.getCreator().getId());

        ArrayList<String> assigneesIds = new ArrayList<>();
        for (Collaborator assignee : task.getAssignees()) {
            assigneesIds.add(assignee.getId());
        }
        document.setAssigneesIds(assigneesIds);

        ArrayList<HashMap<String, String>> subTasks = new ArrayList<>();
        for (Task subTask : task.<Task>getChildren()) {
            SubTaskMap subTaskMap = new SubTaskMap(subTask.getId(), subTask.getName(), subTask.getIsCompleted());
            subTasks.add(subTaskMap.getMap());
        }
        document.setSubTasks(subTasks);

        ArrayList<String> tagsIds = new ArrayList<>();
        for (Tag tag : task.getTags()) {
            tagsIds.add(tag.getId());
        }
        document.setTagsIds(tagsIds);

        FirestoreAPI.getInstance().insertDocument(FirestoreAPI.TASKS, document.getId(), document);
    }

    @Override
    public Task get(String taskId) {

        TaskDocument document = FirestoreAPI.getInstance().selectDocument(FirestoreAPI.TASKS, taskId).toObject(TaskDocument.class);

        Task task = new TaskBuilder().with($ -> {
            $.id = document.getId();
            $.name = document.getName();
            $.description = document.getDescription();
            $.isCompleted = document.getIsCompleted();
            $.dueDate = document.getDueDate();
            $.creationDate = document.getCreationDate();
            $.creator = BoardRepository.getInstance().getCollaborator(document.getCreatorId());

            ArrayList<Collaborator> assignees = new ArrayList<>();
            for (String assigneeID : document.getAssigneesIds()) {
                assignees.add(BoardRepository.getInstance().getCollaborator(assigneeID));
            }
            $.assignees = FXCollections.observableArrayList(assignees);

            ArrayList<Task> subTasks = new ArrayList<>();
            for (HashMap<String, String> subTaskMap : document.getSubTasks()) {
                Task subTask = new TaskBuilder().with($_subTask -> {
                    $_subTask.id = subTaskMap.get("id");
                    $_subTask.name = subTaskMap.get("name");
                    $_subTask.isCompleted = Boolean.valueOf(subTaskMap.get("isCompleted"));
                }).build();
                subTasks.add(subTask);
            }
            $.subTasks = FXCollections.observableArrayList(subTasks);

            ArrayList<Tag> tags = new ArrayList<>();
            for (String tagId : document.getTagsIds()) {
                tags.add(TagRepository.getInstance().get(tagId));
            }
            $.tags = FXCollections.observableArrayList(tags);


        }).build();

        return task;
    }

    @Override
    public void delete(Task task) {
        FirestoreAPI.getInstance().deleteDocument(FirestoreAPI.TASKS, task.getId());
    }

    public void updateName(String taskId, String name) {
        FirestoreAPI.getInstance().updateAttribute(FirestoreAPI.TASKS, taskId, "name", name);
    }

    public void updateDescription(String taskId, String description) {
        FirestoreAPI.getInstance().updateAttribute(FirestoreAPI.TASKS, taskId, "description", description);
    }

    public void updateIsCompleted(String taskId, boolean isCompleted) {
        FirestoreAPI.getInstance().updateAttribute(FirestoreAPI.TASKS, taskId, "isCompleted", isCompleted);
    }

    public void updateDueDate(String taskId, Date dueDate) {
        FirestoreAPI.getInstance().updateAttribute(FirestoreAPI.TASKS, taskId, "dueDate", dueDate);
    }

    public void addTagId(String taskId, String tagId) {
        FirestoreAPI.getInstance().appendArrayElement(FirestoreAPI.TASKS, taskId, "tagsIds", tagId);
    }

    public void updateTagsIds(String taskId, ArrayList<String> tagsIds) {
        FirestoreAPI.getInstance().updateAttribute(FirestoreAPI.TASKS, taskId, "tagsIds", tagsIds);
    }

    public void deleteTagId(String taskId, String tagId) {
        FirestoreAPI.getInstance().deleteArrayElement(FirestoreAPI.TASKS, taskId, "tagsIds", tagId);
    }

    public void addAssigneeId(String taskId, String assigneeId) {
        FirestoreAPI.getInstance().appendArrayElement(FirestoreAPI.TASKS, taskId, "assigneesIds", assigneeId);
    }

    public void updateAssigneesIds(String taskId, ArrayList<String> assigneeIds) {
        FirestoreAPI.getInstance().updateAttribute(FirestoreAPI.TASKS, taskId, "assigneesIds", assigneeIds);
    }

    public void deleteAssigneeId(String taskId, String assigneeId) {
        FirestoreAPI.getInstance().deleteArrayElement(FirestoreAPI.TASKS, taskId, "assigneesIds", assigneeId);
    }

    public void addSubTask(String parentTaskId, Task subTask) {
        SubTaskMap subTaskMap = new SubTaskMap(subTask.getId(), subTask.getName(), subTask.getIsCompleted());
        FirestoreAPI.getInstance().appendArrayElement(FirestoreAPI.TASKS, parentTaskId, "subTasks", subTaskMap.getMap());
    }

    public void deleteSubTask(String parentTaskId, Task subTask) {
        SubTaskMap subTaskMap = new SubTaskMap(subTask.getId(), subTask.getName(), subTask.getIsCompleted());
        FirestoreAPI.getInstance().deleteArrayElement(FirestoreAPI.TASKS, parentTaskId, "subTasks", subTaskMap.getMap());
    }

    public void updateCreatorId(String taskId, String userId) {
        FirestoreAPI.getInstance().updateAttribute(FirestoreAPI.TASKS, taskId, "creatorId", userId);
    }

}
