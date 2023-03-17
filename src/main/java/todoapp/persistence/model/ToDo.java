package todoapp.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String priority;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd ")
    private LocalDate dueDate;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd ")
    private LocalDate creationDate;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd ")
    private LocalDate doneDate;
    private boolean doneUndoneFlag;

    public ToDo() {
    }

    public ToDo(String name, String priority, LocalDate dueDate) {
        this.name = name;
        this.priority = priority;
        this.dueDate = dueDate;
        this.creationDate = java.time.LocalDate.now();
        this.doneDate = null;
        this.doneUndoneFlag = false;
    }

    public ToDo(String name, String priority) {
        this.name = name;
        this.priority = priority;
        this.dueDate = null;
        this.creationDate = java.time.LocalDate.now();
        this.doneDate = null;
        this.doneUndoneFlag = false;
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(LocalDate doneDate) {
        this.doneDate = doneDate;
    }

    public boolean isDoneUndoneFlag() {
        return doneUndoneFlag;
    }

    public void setDoneUndoneFlag(boolean doneUndoneFlag) {
        this.doneUndoneFlag = doneUndoneFlag;
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
