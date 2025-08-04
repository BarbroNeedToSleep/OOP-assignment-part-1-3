package se.lexicon;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.model.AppRole;
import se.lexicon.model.AppUser;

public class AppUserTest {


    private AppUser appUser;

    @BeforeEach
    public void setup(){

        appUser =  AppUser.getInstance("Lina", "123", AppRole.ROLE_APP_USER);

    }

    @AfterEach
    public void tearDown() {
        AppUser.clearInstances();
    }

    @Test
    public void testValidAppUserNameCreation() {

        //Scenario: Check the name creation for the app user.

        Assertions.assertEquals("Lina", appUser.getUserName());
    }

    @Test
    public void testValidAppUserPasswordCreation() {

        //Scenario: Check the password creation for the app user.

        Assertions.assertEquals("123", appUser.getPassword());

    }

    @Test
    public void testValidAppUserRoleCreation() {

        //Scenario: Check the role creation for the app user.

        Assertions.assertEquals(AppRole.ROLE_APP_USER, appUser.getRole());
    }

    @Test
    public void testDuplicateAppUserThrowsException() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            AppUser.getInstance("Lina", "newpass", AppRole.ROLE_APP_ADMIN);
        });
    }



}
