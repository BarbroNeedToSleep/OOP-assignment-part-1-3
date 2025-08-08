package se.lexicon;

import org.junit.jupiter.api.*;
import se.lexicon.model.AppRole;
import se.lexicon.model.AppUser;
import se.lexicon.dao.idSequencer.PersonIdSequencer;
import se.lexicon.model.Person;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    private Person person;
    private AppUser appUser;

    @BeforeEach
    public void setup() {

        PersonIdSequencer.getInstance().reset();
        appUser = AppUser.getInstance("Lina", "123", AppRole.ROLE_APP_USER);
        person = Person.createPerson("Lina", "Katt", "lina@example.se",appUser);
    }

    @AfterEach
    public void tearDown() {
        AppUser.clearInstances();
        Person.removeEmail("lina@example.se");
    }

    @Test
    @DisplayName("Create person successfully with valid inputs")
    public void createPersonValidInputsSuccess() {
        assertNotNull(person);
        assertEquals("Lina", person.getFirstName());
        assertEquals("Katt", person.getLastName());
        assertEquals("lina@example.se", person.getEmail());
        assertEquals(appUser, person.getCredentials());
        assertTrue(person.getId() > 0);
    }

    @Test
    @DisplayName("Should change the first name of the person created")
    public void setFirstNameUpdatesNameCorrectly() {
        person.setFirstName("Alfred");
        assertEquals("Alfred", person.getFirstName());
    }

    @Test
    @DisplayName("Should change the last name of the person created")
    public void setLastNameUpdatesNameCorrectly() {
        person.setLastName("Andersson");
        assertEquals("Andersson", person.getLastName());
    }

    @Test
    @DisplayName("Create person with duplicate email should throw exception")
    public void createPersonDuplicateEmailThrowsException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Person.createPerson("Test", "User", "lina@example.se", appUser);
        });
        assertTrue(exception.getMessage().contains("Email already exists"));
    }

    @Test
    @DisplayName("Should allow creating a person with same email after removing it")
    public void createPersonAfterRemovingEmailSucceeds() {
        Person.removeEmail("lina@example.se");
        Person newPerson = Person.createPerson("Lina", "Katt", "lina@example.se", appUser);
        assertNotNull(newPerson);
        assertEquals("lina@example.se", newPerson.getEmail());
    }

    @Test
    @DisplayName("Two persons with different ids are not equal")
    public void equalsDifferentIdsNotEqual() {
        Person anotherPerson = Person.createPerson("Anna", "Smith", "anna@example.se", appUser);
        assertNotEquals(person, anotherPerson);
    }

    @Test
    @DisplayName("Same person equals itself")
    public void equalsSameObjectEqual() {
        assertEquals(person, person);
    }

    @Test
    @DisplayName("HashCode is consistent for the same person")
    public void hashCodeSamePersonConsistent() {
        int firstHash = person.hashCode();
        int secondHash = person.hashCode();
        assertEquals(firstHash, secondHash);
    }


}