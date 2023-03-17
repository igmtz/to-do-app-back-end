package todoapp.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import todoapp.persistence.model.ToDo;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;

    @Autowired
    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public ToDo addToDo(ToDo toDo) {
        return toDoRepository.save(toDo);
    }

    public Collection<ToDo> findAllToDos() {
        return toDoRepository.findAll();
    }

    public void deleteToDo(String id) {
        toDoRepository.deleteById(id);
    }

    public ToDo updateToDo(String id, ToDo toDo) {
        ToDo pastToDo = toDoRepository.getById(id);
        pastToDo.setName(toDo.getName());
        pastToDo.setPriority(toDo.getPriority());
        pastToDo.setDueDate(toDo.getDueDate());
        return toDoRepository.save(pastToDo);
    }

    public ArrayList<ToDo> filterToDosByPriority(String priority) {
        return toDoRepository.findAll().stream().filter(toDo -> toDo.getPriority().equals(priority)).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<ToDo> filterToDosByName(String name) {

        return toDoRepository.findAll().stream().filter(toDo -> Pattern.compile(Pattern.quote(name), Pattern.CASE_INSENSITIVE).matcher(toDo.getName()).find()).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<ToDo> filterToDosByFlag(Boolean flag) {
        return toDoRepository.findAll().stream().filter(toDo -> toDo.isDoneUndoneFlag() == flag).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<ToDo> sortToDosByDueDate() {
        return toDoRepository.findAll().stream().sorted(Comparator.comparing(ToDo::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<ToDo> sortToDosByPriority() {
        final String[] priority = new String[]{"High", "Medium", "Low"};
        ArrayList<ToDo> newList = new ArrayList<>();

        for (String i : priority) {
            for (ToDo j : toDoRepository.findAll()) {
                if (i.equals(j.getPriority())) {
                    newList.add(j);
                }
            }
        }
        return  newList;
    }

    public ToDo markToDoAsUndone(String id) {
        ToDo toDo = toDoRepository.getById(id);
        if(toDo.isDoneUndoneFlag()) {
            toDo.setDoneDate(null);
            toDo.setDoneUndoneFlag(false);
        } else {
            System.out.println("No Error");
        }
        return toDo;
    }

    public ToDo markToDoAsDone(String id) {
        ToDo toDo = toDoRepository.getById(id);
        toDo.setDoneUndoneFlag(true);
        toDo.setDoneDate(java.time.LocalDate.now());
        return toDo;
    }
}
