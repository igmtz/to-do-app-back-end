package todoapp.business;

import todoapp.persistence.model.ToDo;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

//@Repository
public interface ToDoRepository {
    Collection<ToDo> findAll();
    ToDo save(ToDo toDo);
    ToDo getById(String id);
    void deleteById(String id);
}
