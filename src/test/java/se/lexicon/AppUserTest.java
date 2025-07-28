package se.lexicon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppUserTest {


    private AppUser appUser;

    @BeforeEach
    public void setup(){

        appUser =  new AppUser("Lina", "123", AppRole.ROLE_APP_USER);

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





}
