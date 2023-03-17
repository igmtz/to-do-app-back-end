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

    //Aqui se puede usar la interface List
    public List<ToDo> sortToDosByPriority() {
        //Enums podria ser una alternativa para definir los tipos de Priority
        final String[] priorities = new String[]{"High", "Medium", "Low"};
        ArrayList<ToDo> newList = new ArrayList<>();

        //Hay otras estrategias para implementar un ordenamiento en las coleciones.
        //https://www.google.com/search?q=sort+collection+java&rlz=1C5CHFA_enMX965MX965&oq=sort+collection+java&aqs=chrome.0.0i512l3j0i22i30l7.2394j0j7&sourceid=chrome&ie=UTF-8
        for (String priority : priorities) {//No esta del todo mal usar j, i, etc como variables en loops y asi, pero es mas facil de leer si le das mejores nombres
            for (ToDo todo : toDoRepository.findAll()) {//Respecto a un FOR dentro de otro FOR, tienes contexto de la complejidad computacional de esto man?
                if (priority.equals(todo.getPriority())) {
                    newList.add(todo);
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
            //Una libreria de logging podria ser util aqui
            //Aunque no me queda claro este ELSE
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
