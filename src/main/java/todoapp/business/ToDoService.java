package todoapp.business;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import todoapp.persistence.exception.ToDoNotFoundException;
import todoapp.persistence.model.ToDo;

import java.time.Duration;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ToDoService {

    private final ToDoRepository toDoRepository;

    @Autowired
    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public ToDo addToDo(ToDo toDo) {

        if(toDo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The object can't be null");
        }
        if(toDo.getName() == null | toDo.getPriority() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The name or the priority parameter can't be null");
        }

        if (toDo.getName().isEmpty() | toDo.getName().length() > 120) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The name parameter can't be empty or have more than 120 characters length");
        }

        return toDoRepository.save(toDo);
    }

    public List<ToDo> findAllToDos() {
        return toDoRepository.findAll();
    }

    public void deleteToDo(String id) {
        try {
            toDoRepository.deleteById(id);
        } catch (ToDoNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, illegalArgumentException.getMessage());
        }
    }

    public ToDo updateToDo(String id, ToDo toDo) {
        if(toDo.getName() == null | toDo.getPriority() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The name or the priority parameter can't be null");
        }

        if (toDo.getName().isEmpty() | toDo.getName().length() > 120) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The name parameter can't be empty or have more than 120 characters length");
        }

        try {
            ToDo pastToDo = toDoRepository.getById(id);
            pastToDo.setName(toDo.getName());
            pastToDo.setPriority(toDo.getPriority());
            pastToDo.setDueDate(toDo.getDueDate());
            return toDoRepository.save(pastToDo);
        } catch (ToDoNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (IllegalArgumentException illegalArgumentException) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, illegalArgumentException.getMessage());
        }
    }

    public List<List<ToDo>> pagination(String name, String priority, Boolean doneUnDoneFlag, String priorityOrder, String dateOrder) {
        List<ToDo> totalElements = this.getElementsSortedAndFiltered(name, priority, doneUnDoneFlag, priorityOrder, dateOrder);

        int pageSize = 10;
        int numberOfPages = (int) Math.ceil((double) totalElements.size()/pageSize);

        List<List<ToDo>> pages = new ArrayList<>();

        for( int page = 0; page < numberOfPages; page++) {
            int minIndex = page * pageSize;
            int maxIndex = Math.min(minIndex + pageSize, totalElements.size());
            List<ToDo> sublist = totalElements.subList(minIndex, maxIndex);
            pages.add(sublist);
        }

        return pages;
    }

    public List<ToDo> getElementsSortedAndFiltered(String name, String priority, Boolean doneUnDoneFlag, String priorityOrder, String dateOrder) {
        List<ToDo> initialList = this.findAllToDos();

        List<ToDo> filteredPriority = this.filterToDosByPriority(initialList, priority);
        List<ToDo> filteredByName = this.filterToDosByName(filteredPriority, name);
        List<ToDo> filteredByFlag = this.filterToDosByFlag(filteredByName, doneUnDoneFlag);

        return this.stableSort(filteredByFlag, priorityOrder, dateOrder);
    }

    public List<ToDo> filterToDosByPriority(List<ToDo> prevList, String priority) {
        if (priority.equals("default")) {
            return prevList;
        }
        return prevList.stream().filter(toDo -> toDo.getPriority().equals(priority)).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<ToDo> filterToDosByName(List<ToDo> prevList, String name) {
        if (name.equals("")) {
            return prevList;
        }
        return prevList.stream().filter(toDo -> Pattern.compile(Pattern.quote(name), Pattern.CASE_INSENSITIVE).matcher(toDo.getName()).find()).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<ToDo> filterToDosByFlag(List<ToDo> prevList, Boolean flag) {
        if (flag == null) {
            return prevList;
        }
        return prevList.stream().filter(toDo -> toDo.isDoneUndoneFlag() == flag).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<ToDo> stableSort(List<ToDo> list, String priorityOrder, String dateOrder) {
        List<ToDo> sortedByDate = sortToDosByDueDate(list, dateOrder);
        return sortToDosByPriority(sortedByDate, priorityOrder);
    }

    public List<ToDo> sortToDosByDueDate(List<ToDo> list, String value) {
        switch (value) {
            case "asc" -> {
                return list.stream().sorted(Comparator.comparing(ToDo::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toCollection(ArrayList::new));
            }
            case "desc" -> {
                return list.stream().sorted(Comparator.comparing(ToDo::getDueDate, Comparator.nullsLast(Comparator.reverseOrder()))).collect(Collectors.toCollection(ArrayList::new));
            }
            default -> {
                return list;
            }
        }
    }

    public List<ToDo> sortToDosByPriority(List<ToDo> list, String value) {
        List<ToDo> sortedToDos = new ArrayList<>(list);
        List<String> priorities = Arrays.asList("High", "Medium", "Low");
        sortedToDos.sort((o1, o2) -> {
            int p1 = priorities.indexOf(o1.getPriority());
            int p2 = priorities.indexOf(o2.getPriority());
            return switch (value) {
                case "asc" -> Integer.compare(p1, p2);
                case "desc" -> Integer.compare(p2, p1);
                default -> 0;
            };
        });

        return sortedToDos;
    }

    public ToDo markToDoAsUndone(String id) {
        try {
            ToDo toDo = toDoRepository.getById(id);

            toDo.setDoneDate(null);
            toDo.setDoneUndoneFlag(false);
            toDo.setTimeToComplete(null);

            return toDo;

        } catch (ToDoNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (IllegalArgumentException illegalArgumentException) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, illegalArgumentException.getMessage());
        }
    }

    public ToDo markToDoAsDone(String id) {
        try {
            ToDo toDo = toDoRepository.getById(id);

            toDo.setDoneUndoneFlag(true);
            toDo.setDoneDate(java.time.LocalDateTime.now());
            toDo.setTimeToComplete(Duration.between(toDo.getCreationDate(), toDo.getDoneDate()).toMinutes());

            return toDo;

        } catch (ToDoNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (IllegalArgumentException illegalArgumentException) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, illegalArgumentException.getMessage());
        }
    }

    public List<Long> getStatistics() {
        List<ToDo> allToDosDone = this.filterToDosByFlag(this.findAllToDos(), true);
        List<ToDo> toDosWithHighPriority = this.filterToDosByPriority(allToDosDone, "High");
        List<ToDo> toDosWithMediumPriority = this.filterToDosByPriority(allToDosDone, "Medium");
        List<ToDo> toDosWithLowPriority = this.filterToDosByPriority(allToDosDone, "Low");

        Long averageAll = Double.valueOf(Math.floor(allToDosDone.stream().mapToLong(ToDo::getTimeToComplete).average().orElse(0))).longValue();
        Long averageHigh = Double.valueOf(Math.round(toDosWithHighPriority.stream().mapToLong(ToDo::getTimeToComplete).average().orElse(0))).longValue();
        Long averageMedium = Double.valueOf(Math.round(toDosWithMediumPriority.stream().mapToLong(ToDo::getTimeToComplete).average().orElse(0))).longValue();
        Long averageLow = Double.valueOf(Math.round(toDosWithLowPriority.stream().mapToLong(ToDo::getTimeToComplete).average().orElse(0))).longValue();

        List<Long> statistics = new ArrayList<>();
        statistics.add(averageAll); statistics.add(averageHigh); statistics.add(averageMedium); statistics.add(averageLow);

        return statistics;

    }

    public void deleteAllToDos() {
        toDoRepository.deleteAll();
    }
}
