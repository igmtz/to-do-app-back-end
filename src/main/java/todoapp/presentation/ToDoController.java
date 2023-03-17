package todoapp.presentation;

import todoapp.business.ToDoService;
import todoapp.persistence.model.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/toDo")
public class ToDoController {

    private final ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Collection<ToDo>> getAllToDos() {
        Collection<ToDo> toDos = toDoService.findAllToDos();
        return new ResponseEntity<>(toDos, HttpStatus.OK);
    }

    @PostMapping("/addToDo")
    public ResponseEntity<ToDo> addToDo(@RequestBody ToDo toDo){
        ToDo newToDo = toDoService.addToDo(toDo);
        return new ResponseEntity<>(newToDo, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ToDo> deleteToDo(@PathVariable("id") String id) {
        toDoService.deleteToDo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/done")
    public ResponseEntity<ToDo> markToDoAsDone(@PathVariable("id") String id) {
        ToDo toDo = toDoService.markToDoAsDone(id);
        return new ResponseEntity<>(toDo, HttpStatus.OK);
    }

    @PutMapping("/{id}/undone")
    public ResponseEntity<ToDo> markAsUndone(@PathVariable("id") String id) {
        ToDo toDo = toDoService.markToDoAsUndone(id);
        return new ResponseEntity<>(toDo, HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ToDo> updateToDo(@PathVariable("id") String id, @RequestBody ToDo toDo){
        ToDo toDoUpdated = toDoService.updateToDo(id, toDo);
        return new ResponseEntity<>(toDoUpdated, HttpStatus.OK);
    }
    
    @GetMapping("/getSortedByPriority")
    public ResponseEntity<List<ToDo>> sortToDoByPriority() {
        List<ToDo> toDosSorted = toDoService.sortToDosByPriority();
        return new ResponseEntity<>(toDosSorted, HttpStatus.OK);
    }

    @GetMapping("/getSortedByDueDate")
    public ResponseEntity<List<ToDo>> sortToDoByDueDate() {
        List<ToDo> toDosSorted = toDoService.sortToDosByDueDate();
        return new ResponseEntity<>(toDosSorted, HttpStatus.OK);
    }

    @GetMapping("/filterByName")
    public ResponseEntity<List<ToDo>> filterToDoByName(String name) {
        List<ToDo> toDosFiltered = toDoService.filterToDosByName(name);
        return new ResponseEntity<>(toDosFiltered, HttpStatus.OK);
    }

    @GetMapping("/filterByPriority")
    public ResponseEntity<List<ToDo>> filterToDoByPriority(String priority) {
        List<ToDo> toDosFiltered = toDoService.filterToDosByPriority(priority);
        return new ResponseEntity<>(toDosFiltered, HttpStatus.OK);
    }

    @GetMapping("/filterByFlag")
    public ResponseEntity<List<ToDo>> filterToDoByFlag(Boolean flag) {
        List<ToDo> toDosFiltered = toDoService.filterToDosByFlag(flag);
        return new ResponseEntity<>(toDosFiltered, HttpStatus.OK);
    }

    }
