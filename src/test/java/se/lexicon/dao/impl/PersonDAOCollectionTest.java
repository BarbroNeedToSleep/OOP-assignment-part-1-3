package se.lexicon.dao.impl;

import org.junit.jupiter.api.*;
import se.lexicon.model.AppRole;
import se.lexicon.model.AppUser;
import se.lexicon.model.Person;
import se.lexicon.dao.idSequencer.PersonIdSequencer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonDAOCollectionTest {

    private PersonDAOCollection dao;
    private Person person1;
    private Person person2;

    @BeforeAll
    public void init() {
        dao = new PersonDAOCollection();
    }

    @BeforeEach
    public void setup() {
        // Reset DAO and static states before each test
        dao = new PersonDAOCollection();
        resetPersonState();

        AppUser user1 = AppUser.getInstance("user1", "pass1", AppRole.ROLE_APP_USER);
        AppUser user2 = AppUser.getInstance("user2", "pass2", AppRole.ROLE_APP_USER);

        person1 = Person.createPerson("John", "Doe", "john@example.com", user1);
        person2 = Person.createPerson("Jane", "Smith", "jane@example.com", user2);

        dao.persist(person1);
    }

    @AfterEach
    public void tearDown() {
        resetPersonState();
        dao = null;
    }

    private void resetPersonState() {
        // Clear static email set and reset sequencer
        Person.removeEmail("john@example.com");
        Person.removeEmail("jane@example.com");
        PersonIdSequencer.getInstance().setCurrentId(0);
        AppUser.clearInstances();
    }

    @Test
    @DisplayName("Persist valid person")
    public void persistValidPerson() {
        Person persisted = dao.persist(person2);
        assertNotNull(persisted);
        assertEquals("jane@example.com", persisted.getEmail());
    }

    @Test
    @DisplayName("Persist null person throws IllegalArgumentException")
    public void persistNullThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> dao.persist(null));
        assertEquals("Person cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("FindById returns correct person")
    public void findByIdReturnsCorrectPerson() {
        Person found = dao.findById(person1.getId());
        assertNotNull(found);
        assertEquals(person1, found);
    }

    @Test
    @DisplayName("FindById with invalid id throws")
    public void findByIdInvalidThrows() {
        assertThrows(IllegalArgumentException.class, () -> dao.findById(0));
    }

    @Test
    @DisplayName("FindById returns null when not found")
    public void findByIdNotFoundReturnsNull() {
        Person found = dao.findById(999);
        assertNull(found);
    }

    @Test
    @DisplayName("FindByEmail returns correct person")
    public void findByEmailReturnsCorrectPerson() {
        Person found = dao.findByEmail("john@example.com");
        assertNotNull(found);
        assertEquals(person1, found);
    }

    @Test
    @DisplayName("FindByEmail ignores case and whitespace")
    public void findByEmailCaseInsensitive() {
        Person found = dao.findByEmail("  JOHN@EXAMPLE.COM ");
        assertNotNull(found);
        assertEquals(person1, found);
    }

    @Test
    @DisplayName("FindByEmail with null or empty throws")
    public void findByEmailInvalidThrows() {
        assertThrows(IllegalArgumentException.class, () -> dao.findByEmail(""));
        assertThrows(IllegalArgumentException.class, () -> dao.findByEmail(null));
    }

    @Test
    @DisplayName("FindByEmail returns null when not found")
    public void findByEmailNotFoundReturnsNull() {
        Person found = dao.findByEmail("noone@example.com");
        assertNull(found);
    }

    @Test
    @DisplayName("FindAll returns all persisted persons")
    public void findAllReturnsAll() {
        dao.persist(person2);
        List<Person> all = dao.findAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(person1));
        assertTrue(all.contains(person2));
    }

    @Test
    @DisplayName("Remove existing person by id")
    public void removeExistingPerson() {
        dao.remove(person1.getId());
        assertNull(dao.findById(person1.getId()));
    }

    @Test
    @DisplayName("Remove with invalid id throws")
    public void removeInvalidThrows() {
        assertThrows(IllegalArgumentException.class, () -> dao.remove(0));
    }

    @Test
    @DisplayName("Remove non-existing id does not throw and has no effect")
    public void removeNonExistingDoesNothing() {
        dao.remove(999);
        assertEquals(1, dao.findAll().size());
    }
}
