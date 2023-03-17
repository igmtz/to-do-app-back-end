package todoapp.persistence;

import org.springframework.stereotype.Component;
import todoapp.business.ToDoRepository;
import todoapp.persistence.exception.ToDoNotFoundException;
import todoapp.persistence.model.ToDo;

import java.util.*;

@Component
public class MemoryImpl implements ToDoRepository {

    private final Map<String, ToDo> storedToDos = new HashMap<>();

    @Override
    public ArrayList<ToDo> findAll() {
        return new ArrayList<>(storedToDos.values());
    }

    @Override
    public ToDo save(ToDo toDo) {
        if (storedToDos.containsKey(toDo.getId())) {
            storedToDos.replace(toDo.getId(), toDo);
            return toDo;
        } else {
            ToDo newToDo = new ToDo(toDo.getName(), toDo.getPriority(), toDo.getDueDate());
            newToDo.setId(UUID.randomUUID().toString());
            storedToDos.put(newToDo.getId(), newToDo);
            return newToDo;
        }
    }

    @Override
    public ToDo getById(String id) {
        return storedToDos.entrySet().stream().filter(toDo -> toDo.getValue().getId().equals(id)).findAny().orElseThrow(
                () -> new ToDoNotFoundException("The car with the id" + id + " was not found")
        ).getValue();
    }

    @Override
    public void deleteById(String id) {
        storedToDos.remove(id);
    }


}
