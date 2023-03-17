package todoapp.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import todoapp.business.ToDoService;
import todoapp.persistence.model.ToDo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(ToDoController.class)
class ToDoControllerTest {

    @MockBean
    private ToDoService toDoService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    public static String asJsonString(final ToDo obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }


    @Test
    void getAllToDos() {

    }

    @Test
    void addToDo() {
    }

    @Test
    void whenToDoAdded_ThenHttp200_ToDoReturned() throws Exception {
        String name = "To Do";
        String priority = "High";

        ToDo toDo = new ToDo(name, priority);

        MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.post("http://localhost9090/toDo/addToDo")
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"To Do\", \"priority\": \"High\"}")
                        .header("Content-Type","application/json"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentType());

    }

    @Test
    void markAsDone() {
    }

    @Test
    void markAsUndone() {
    }

    @Test
    void update() {
    }

    @Test
    void sortByPriority() {
    }

    @Test
    void sortByDueDate() {
    }

    @Test
    void filterByName() {
    }

    @Test
    void filterByPriority() {
    }

    @Test
    void filterByFlag() {
    }
}

