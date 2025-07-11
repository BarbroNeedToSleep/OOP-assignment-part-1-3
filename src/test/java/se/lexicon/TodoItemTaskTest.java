package se.lexicon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class TodoItemTaskTest {

    private Person person;
    private TodoItem todoItem;
    private TodoItemTask todoItemTask;

    @BeforeEach
    public void setup() {
        TodoItem.resetIdCounterForTesting();
        Person.resetIdCounterForTesting();
        TodoItemTask.resetIdCounterForTesting();
        person = new Person("jon", "Jonsson", "jon@test.se");
        todoItem = new TodoItem("Change tire", "Change both tires on the front", LocalDate.parse("2025-09-30"), person);
        todoItemTask = new TodoItemTask(todoItem, person);
    }

    @Test
    void getIDCountsAsExpectedTest (){

        //Scenario: The value of ID counts up when a new todoItemTask is created

        int expected = 1;

        int actualValue = todoItemTask.getItemTaskID();

        Assertions.assertEquals(expected, actualValue, "The maximum value should be 1");

        Person personTest01 = new Person("Alfred", "Katt", "alfred@example.se");
        TodoItem secondTodo = new TodoItem("Buff the car", "Jump on the car", LocalDate.parse("2025-10-30"), personTest01);
        TodoItemTask todoItemTask01= new TodoItemTask(secondTodo , personTest01);

        int expected02 = 2;

        int actualValue02 = todoItemTask01.getItemTaskID();

        Assertions.assertEquals(expected02, actualValue02, "The maximum value should be 2");

    }

    @Test
    void setAssigneeShouldAssignedPersonAndSetAssignedTrue() {

        //Scenario: Assignee should be changed and the assigned boolean should be changed to true

        Person personTest01 = new Person("Alfred", "Katt", "alfred@example.se");


        todoItemTask.setAssignee(personTest01);


        Assertions.assertEquals(personTest01, todoItemTask.getAssignee(), "Assignee should be updated");
        Assertions.assertTrue(todoItemTask.isAssigned(), "Assigned should be true when assignee is not null");
    }

    @Test
    void setAssigneeToNullShouldSetAssignedFalse() {

        //Scenario: When the setAssignee is set to null the assigned boolean should be set to false

        todoItemTask.setAssignee(null);

        // Assert
        Assertions.assertNull(todoItemTask.getAssignee(), "Assignee should be null");
        Assertions.assertFalse(todoItemTask.isAssigned(), "Assigned should be false when assignee is null");
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

        int id = todoItemTask.getItemTaskID();
        String expected = "Task ID: " + id
                + ", Task: " + todoItem.getSummary()
                + ", Is assigned to " + person.getSummary();

        String actual = todoItemTask.getSummary();
        Assertions.assertEquals(expected, actual, "Summary should match expected format and content");
    }



}

