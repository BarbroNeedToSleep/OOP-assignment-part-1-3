package se.lexicon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.appUserDAO.AppRole;
import se.lexicon.appUserDAO.AppUser;
import se.lexicon.idSequencer.PersonIdSequencer;
import se.lexicon.idSequencer.ToDoItemIdSequencer;
import se.lexicon.idSequencer.ToDoItemTaskIdSequencer;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;
import se.lexicon.model.TodoItemTask;

import java.time.LocalDate;

public class TodoItemTaskTest {

    private Person person;
    private TodoItem todoItem;
    private TodoItemTask todoItemTask;

    @BeforeEach
    public void setup() {
        ToDoItemIdSequencer.getInstance().reset();
        PersonIdSequencer.getInstance().reset();
        ToDoItemTaskIdSequencer.getInstance().reset();
        person = new Person("jon", "Jonsson", "jon@test.se", new AppUser("Jon", "13399", AppRole.ROLE_APP_USER));
        todoItem = new TodoItem("Change tire", "Change both tires on the front", LocalDate.parse("2025-09-30"), person);
        todoItemTask = new TodoItemTask(todoItem, person);
    }

    @Test
    void getIDCountsAsExpectedTest (){

        //Scenario: The value of ID counts up when a new todoItemTask is created

        int expected = 1;

        int actualValue = todoItemTask.getId();

        Assertions.assertEquals(expected, actualValue, "The maximum value should be 1");

        Person personTest01 = new Person("Alfred", "Katt", "alfred@example.se", new AppUser("Alfred", "1abc", AppRole.ROLE_APP_USER));
        TodoItem secondTodo = new TodoItem("Buff the car", "Jump on the car", LocalDate.parse("2025-10-30"), personTest01);
        TodoItemTask todoItemTask01= new TodoItemTask(secondTodo , personTest01);

        int expected02 = 2;

        int actualValue02 = todoItemTask01.getId();

        Assertions.assertEquals(expected02, actualValue02, "The maximum value should be 2");

    }

    @Test
    void setAssigneeShouldAssignedPersonAndSetAssignedTrue() {

        //Scenario: Assignee should be changed and the assigned boolean should be changed to true

        Person personTest01 = new Person("Alfred", "Katt", "alfred@example.se", new AppUser("Alfred", "1abc", AppRole.ROLE_APP_USER));


        todoItemTask.setAssignee(personTest01);


        Assertions.assertEquals(personTest01, todoItemTask.getAssignee(), "Assignee should be updated");
        Assertions.assertTrue(todoItemTask.isAssigned(), "Assigned should be true when assignee is not null");
    }

    @Test
    void setTodoItemWithValidObjectShouldUpdateTodoItem() {

        //Scenario:Check that the setTodoItem changes the String

        TodoItem newTodo = new TodoItem("Wash car", "Wash and dry the car", LocalDate.parse("2025-11-01"), person);

        todoItemTask.setTodoItem(newTodo);

        Assertions.assertEquals(newTodo, todoItemTask.getTodoItem(), "TodoItem should be updated to be equal");
    }

    @Test
    void setTodoItemWithNullThrowsException() {

        // Scenario: Setting null as the todoItem should throw an exception

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            todoItemTask.setTodoItem(null);
        });
    }

    @Test
    void getSummaryReturnsCorrectString() {

        //Scenario: Return the value of the summery for the todoItemTask, the todoItem and the Person assigned to the task

        int id = todoItemTask.getId();
        String expected = "Task ID: " + id
                + ", Task: " + todoItem.toString()
                + ", Is assigned to " + person.getFirstName();

        String actual = todoItemTask.toString();
        Assertions.assertEquals(expected, actual, "Summary should match expected format and content");
    }

    @Test
    void twoTasksWithSameDataButDifferentIdsShouldNotBeEqual() {
        TodoItemTask task1 = new TodoItemTask(todoItem, person);
        TodoItemTask task2 = new TodoItemTask(todoItem, person);

        Assertions.assertNotEquals(task1, task2, "Each task should be unique by ID even if data matches");
    }



}

