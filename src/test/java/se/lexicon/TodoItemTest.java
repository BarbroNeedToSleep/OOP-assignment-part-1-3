package se.lexicon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.model.AppRole;
import se.lexicon.model.AppUser;
import se.lexicon.dao.idSequencer.PersonIdSequencer;
import se.lexicon.dao.idSequencer.ToDoItemIdSequencer;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.time.LocalDate;

public class TodoItemTest {

    private Person person;
    private TodoItem todoItem;

    @BeforeEach
    public void setup() {
        ToDoItemIdSequencer.getInstance().reset();
        PersonIdSequencer.getInstance().reset();
        person = new Person("Lina", "Katt", "lina@example.se", AppUser.getInstance("Lina", "123", AppRole.ROLE_APP_USER));
        todoItem = new TodoItem("Change tire", "Change both tires on the front", LocalDate.parse("2025-09-30"), person);
    }

    @Test
    void setTitleUpdateTitleCorrectly() {

        // Scenario: Check that the setTitle changes the String

        todoItem.setTitle("Change windshield");
        Assertions.assertEquals("Change windshield", todoItem.getTitle());
    }

    @Test
    void setTitleWithString(){

        // Scenario: A string value entry in the setTitle method that is a valid input.

        String actualValue = todoItem.getTitle();

        String expectedValue = "Change tire";
        Assertions.assertEquals(expectedValue, actualValue, "Title should match");
    }


    @Test
    void setTitleWithNullThrowsException() {

        //Scenario: A value of NULL is entered to the setTitle method and an exception should be thrown

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
           todoItem.setTitle(null);
        });

    }

    @Test
    void setTitleWithEmptyThrowsException() {

        //Scenario: A value of empty is entered to the setTitle method and an exception should be thrown

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            todoItem.setTitle(" ");
        });

    }

    @Test
    void setTaskDescriptionCorrectly() {

        // Scenario: Check that the setTaskDescription changes the String

        todoItem.setTaskDescription("Cut the tiers");
        Assertions.assertEquals("Cut the tiers", todoItem.getTaskDescription());
    }

    @Test
    void setTaskDescriptionWithString(){

        // Scenario: A string value entry in the setTaskDescription method that is a valid input.

        String actualValue = todoItem.getTaskDescription();

        String expectedValue = "Change both tires on the front";
        Assertions.assertEquals(expectedValue, actualValue, "The task description should match");
    }

    @Test
    void setTaskDescriptionWithNullThrowsException() {

        //Scenario: A value of NULL is entered to the setTaskDescription method and an exception should be thrown

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            todoItem.setTaskDescription(null);
        });

    }

    @Test
    void setTaskDescriptionWithEmptyThrowsException() {

        //Scenario: A value of empty is entered to the setTaskDescription method and an exception should be thrown

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            todoItem.setTaskDescription(" ");
        });

    }

    @Test
    void setDeadLineCorrectly() {

        // Scenario: Check that the setDeadLine changes the LocalDate deadline

        todoItem.setDeadLine(LocalDate.parse("2025-12-30"));
        Assertions.assertEquals(LocalDate.parse("2025-12-30"), todoItem.getDeadLine());
    }

    @Test
    void setDeadLineWithNullThrowsException() {

        //Scenario: A value of NULL is entered to the setDeadLine method and an exception should be thrown

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            todoItem.setDeadLine(null);
        });

    }

    @Test
    void setDoneUpdatesStatusCorrectly() {

        // Scenario: setDone should update the done field to true
        todoItem.setDone(true);
        Assertions.assertTrue(todoItem.isDone());
    }

    @Test
    void setDeadLineWithPastDateThrowsException() {

        // Scenario: A date in the past is entered and should throw an exception

        LocalDate pastDate = LocalDate.now().minusDays(1);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            todoItem.setDeadLine(pastDate);
        });
    }


    @Test
    void setCreatorWithEmptyThrowsException() {

        //Scenario: A value of empty is entered to the setCreator method and an exception should be thrown

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            todoItem.setCreator(null);
        });

    }


    @Test
    void getIDCountsAsExpectedTest (){

        //Scenario: The value of ID counts up when a new TodoItem is created

        int expected = 1;

        int actualValue = todoItem.getId();

        Assertions.assertEquals(expected, actualValue, "The maximum value should be 1");

        Person personTest01 = new Person("Alfred", "Katt", "alfred@example.se", AppUser.getInstance("Alfred", "1abc", AppRole.ROLE_APP_USER));
        TodoItem secondTodo = new TodoItem("Buff the car", "Jump on the car", LocalDate.parse("2025-10-30"), personTest01);

        int expected02 = 2;

        int actualValue02 = secondTodo.getId();

        Assertions.assertEquals(expected02, actualValue02, "The maximum value should be 2");

    }

    @Test
    void getSummaryReturnsCorrectSummary() {

        //Scenario: Return the value of the summery created of the todoItem

        todoItem.setDone(true);

        String summary = todoItem.toString();

        String expected = "Booking information -  Id number: " + todoItem.getId()
                + ", Job : Change tire"
                + ", Job description: Change both tires on the front"
                + ", Due date: 2025-09-30"
                + ", Done: Yes"
                + ", Customer " + person.getFirstName();

        Assertions.assertEquals(expected, summary);
    }

    @Test
    void twoTodoItemsWithSameDataButDifferentIdsShouldNotBeEqual() {

        TodoItem first = new TodoItem("Test", "Desc", LocalDate.now().plusDays(1), person);
        TodoItem second = new TodoItem("Test", "Desc", LocalDate.now().plusDays(1), person);

        Assertions.assertNotEquals(first, second, "Even with same content, IDs differ so they are not equal");
    }


}
