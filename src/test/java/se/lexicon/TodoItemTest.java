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
//        ToDoItemIdSequencer.getInstance().reset();
//        PersonIdSequencer.getInstance().reset();
//        person = new Person("Lina", "Katt", "lina@example.se", AppUser.getInstance("Lina", "123", AppRole.ROLE_APP_USER));
//        todoItem = new TodoItem("Change tire", "Change both tires on the front", LocalDate.parse("2025-09-30"), person);
    }




}
