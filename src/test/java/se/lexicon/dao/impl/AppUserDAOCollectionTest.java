package se.lexicon.dao.impl;

import org.junit.jupiter.api.*;
import se.lexicon.dao.impl.AppUserDAOCollection;
import se.lexicon.model.AppUser;
import se.lexicon.model.AppRole;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppUserDAOCollectionTest {

    private AppUserDAOCollection dao;
    private AppUser user1;
    private AppUser user2;

    @BeforeAll
    public void beforeAll() {
        dao = new AppUserDAOCollection();
    }

    @BeforeEach
    public void setup() {
        AppUser.clearInstances();    // Clear static instances before each test
        dao = new AppUserDAOCollection();  // Reset DAO to empty state

        user1 = AppUser.getInstance("user1", "pass1", AppRole.ROLE_APP_USER);
        user2 = AppUser.getInstance("user2", "pass2", AppRole.ROLE_APP_USER);

        dao.persist(user1);
    }

    @AfterEach
    public void tearDown() {
        AppUser.clearInstances();    // Clear again after test for safety
        dao = null;
    }


    @Test
    @DisplayName("Persist valid AppUser")
    public void persistValidAppUser() {
        AppUser persisted = dao.persist(user2);
        assertNotNull(persisted);
        assertEquals(user2.getUserName(), persisted.getUserName());
    }

    @Test
    @DisplayName("Persist null AppUser throws IllegalArgumentException")
    public void persistNullThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            dao.persist(null);
        });
        assertEquals("App user cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("Creating AppUser with existing username throws exception")
    public void creatingDuplicateUserThrows() {
        assertThrows(IllegalStateException.class, () -> {
            AppUser.getInstance("user1", "somepass", AppRole.ROLE_APP_USER);
        });
    }

    @Test
    @DisplayName("FindByUsername returns correct user")
    public void findByUsernameReturnsUser() {
        AppUser found = dao.findByUsername("user1");
        assertNotNull(found);
        assertEquals(user1.getUserName(), found.getUserName());
    }

    @Test
    @DisplayName("FindByUsername returns null if user not found")
    public void findByUsernameReturnsNull() {
        AppUser found = dao.findByUsername("nonexistent");
        assertNull(found);
    }

    @Test
    @DisplayName("FindAll returns all users")
    public void findAllReturnsAll() {
        dao.persist(user2);
        List<AppUser> allUsers = dao.findAll();
        assertEquals(2, allUsers.size());
        assertTrue(allUsers.contains(user1));
        assertTrue(allUsers.contains(user2));
    }

    @Test
    @DisplayName("Remove existing user returns user")
    public void removeExistingUser() {
        AppUser removed = dao.remove(user1);
        assertNotNull(removed);
        assertEquals(user1.getUserName(), removed.getUserName());
    }

    @Test
    @DisplayName("Remove null user throws IllegalArgumentException")
    public void removeNullUserThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            dao.remove(null);
        });
        assertEquals("App user cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("Remove non-existing user returns null")
    public void removeNonExistingUserReturnsNull() {
        AppUser removed = dao.remove(user2);
        assertNull(removed);
    }
}
