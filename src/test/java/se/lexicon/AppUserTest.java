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





}
