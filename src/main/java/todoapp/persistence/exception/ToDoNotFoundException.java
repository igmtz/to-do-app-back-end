package todoapp.persistence.exception;

//Por qué usar una RuntimeException?
//Que otros tipos de excepciones tenemos?
public class ToDoNotFoundException extends RuntimeException{
    public ToDoNotFoundException(String message) {
        super((message));
    }
}
