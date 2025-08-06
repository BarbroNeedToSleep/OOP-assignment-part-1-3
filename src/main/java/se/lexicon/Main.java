package se.lexicon;


import se.lexicon.model.AppRole;
import se.lexicon.model.AppUser;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;
import se.lexicon.model.TodoItemTask;
import se.lexicon.personDAO.PersonDAO;
import se.lexicon.personDAO.PersonDAOCollection;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {


//        AppUser user = AppUser.getInstance("testuser", "password123", AppRole.ROLE_APP_USER);
        Person person = Person.createPerson("Lina", "Andersson", "lina@test.se", AppUser.getInstance("testUser","123",AppRole.ROLE_APP_USER));


        System.out.println(person);

        AppUser credentials = person.getCredentials();
        System.out.println("Username: " + credentials.getUserName());
        System.out.println("Role: " + credentials.getRole());
    }

}