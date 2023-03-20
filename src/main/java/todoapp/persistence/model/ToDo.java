package todoapp.persistence.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ToDo {

    private String id;
    private String name;
    private String priority;
    private LocalDate dueDate;
    private LocalDateTime creationDate;
    private LocalDateTime doneDate;
    private boolean doneUndoneFlag;
    private Duration timeToComplete;

    public ToDo() {
    }

    public ToDo(String name, String priority, LocalDate dueDate) {
        this.name = name;
        this.priority = priority;
        this.dueDate = dueDate;
        this.creationDate = java.time.LocalDateTime.now();
        this.doneDate = null;
        this.doneUndoneFlag = false;
        this.timeToComplete = null;
    }

    public ToDo(String name, String priority) {
        this.name = name;
        this.priority = priority;
        this.dueDate = null;
        this.creationDate = java.time.LocalDateTime.now();
        this.doneDate = null;
        this.doneUndoneFlag = false;
        this.timeToComplete = null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(LocalDateTime doneDate) {
        this.doneDate = doneDate;
    }

    public boolean isDoneUndoneFlag() {
        return doneUndoneFlag;
    }

    public void setDoneUndoneFlag(boolean doneUndoneFlag) {
        this.doneUndoneFlag = doneUndoneFlag;
    }

    public Duration getTimeToComplete() {
        return timeToComplete;
    }

    public void setTimeToComplete(Duration timeToComplete) {
        this.timeToComplete = timeToComplete;
    }

    @Override
    public String toString() {
        return "Model.ToDo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", priority='" + priority + '\'' +
                ", dueDate=" + dueDate +
                ", creationDate=" + creationDate +
                ", doneDate=" + doneDate +
                ", doneUndoneFlag=" + doneUndoneFlag +
                '}';
    }
}
