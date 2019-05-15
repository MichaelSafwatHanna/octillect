package octillect.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class Task extends TaskBase {

    private boolean isCompleted;
    private Date dueDate;
    private Date creationDate;
    private Contributor creator;
    private ObservableList<Contributor> assignees = FXCollections.observableArrayList();
    private ObservableList<Tag> tags = FXCollections.observableArrayList();


    public Task(String id, String name, String description, boolean isCompleted,
                Date dueDate, Date creationDate, Contributor creator, ObservableList<Contributor> assignees,
                ObservableList<TaskBase> subTasks, ObservableList<Tag> tags) {
        super(id, name, description, subTasks);
        this.isCompleted  = isCompleted;
        this.dueDate      = dueDate;
        this.creationDate = creationDate;
        this.creator      = creator;
        this.assignees    = assignees;
        this.tags = tags;
    }


    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean completed) {
        isCompleted = completed;
    }


    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }


    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    public Contributor getCreator() {
        return creator;
    }

    public void setCreator(Contributor creator) {
        this.creator = creator;
    }


    public ObservableList<Contributor> getAssignees() {
        return assignees;
    }

    public void setAssignees(ObservableList<Contributor> assignees) {
        this.assignees = assignees;
    }


    public ObservableList<Tag> getTags() { return tags; }

    public void setTags(ObservableList<Tag> tags) { this.tags = tags; }

}
