package se.lexicon.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppUserTest {

    @BeforeEach
    public void setup() {
        AppUser.clearInstances();


    }


    @Test
    @DisplayName("Create AppUser successfully with valid inputs")
    public void createAppUserValidInputsSuccess() {
        AppUser user = AppUser.getInstance("Lina", "123", AppRole.ROLE_APP_USER);
        assertNotNull(user);
        assertEquals("Lina", user.getUserName());
        assertEquals("123", user.getPassword());
        assertEquals(AppRole.ROLE_APP_USER, user.getRole());
    }

    @Test
    @DisplayName("Throw exception when username already exists")
    public void createAppUserDuplicateUsernameThrowsException() {
        AppUser.getInstance("Lina", "123", AppRole.ROLE_APP_USER);
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> AppUser.getInstance("Lina", "456", AppRole.ROLE_APP_USER));
        assertEquals("AppUser with username 'Lina' already exists.", ex.getMessage());
    }

    @Test
    @DisplayName("hasUser returns true if user exists")
    public void hasUserReturnsTrueWhenUserExists() {
        AppUser.getInstance("Lina", "123", AppRole.ROLE_APP_USER);
        assertTrue(AppUser.hasUser("Lina"));
    }

    @Test
    @DisplayName("hasUser returns false if user does not exist")
    public void hasUserReturnsFalseWhenUserDoesNotExist() {
        assertFalse(AppUser.hasUser("NonExistingUser"));
    }



}
