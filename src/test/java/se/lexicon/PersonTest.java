package se.lexicon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.lexicon.idSequencer.PersonIdSequencer;

public class PersonTest {

    private Person person;

    @BeforeEach
    public void setup() {

        PersonIdSequencer.getInstance().reset();
        person = new Person("Lina", "Katt", "lina@example.se", new AppUser("Lina", "123", AppRole.ROLE_APP_USER));
    }

    @Test
    void setFirstNameUpdatesNameCorrectly() {

        // Scenario: Check that the setFirstName changes the String

        person.setFirstName("Alfred");
        Assertions.assertEquals("Alfred", person.getFirstName());
    }

    @Test
    void setFirstNameWithString(){

        // Scenario: A string value entry in the setFirstName method that is a valid input.

            String actualValue = person.getFirstName();

            String expectedValue = "Lina";
            Assertions.assertEquals(expectedValue, actualValue, "First names should match");
        }


    @Test
    void setFirstNameWithNullThrowsException() {

        //Scenario: A value of NULL is entered to the setFirstName method

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            person.setFirstName(null);
        });

    }

    @Test
    void setFirstNameWithEmptyThrowsException() {

        //Scenario: A value of empty is entered to the setFirstName method

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            person.setFirstName(" ");
        });

    }

    @Test
    void setLastNameUpdatesNameCorrectly() {

        // Scenario: Check that the setLastName changes the String

        person.setLastName("Cat");
        Assertions.assertEquals("Cat", person.getLastName());
    }

    @Test
    void setLastNameWithString(){

        // Scenario: A string value entry in the setLastName method that is a valid input.

        String actualValue = person.getLastName();

        String expectedValue = "Katt";
        Assertions.assertEquals(expectedValue, actualValue, "Last names should match");
    }

    @Test
    void setLastNameWithNullThrowsException() {

        //Scenario: A value of NULL is entered to the setLastName method

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            person.setLastName(null);
        });

    }

    @Test
    void setLastNameWithEmptyThrowsException() {

        //Scenario: A value of empty is entered to the setLastName method

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            person.setLastName(" ");
        });

    }

    @Test
    void setEmailUpdatesNameCorrectly() {

        // Scenario: Check that the setEmail changes the String

        person.setEmail("cat@test.se");
        Assertions.assertEquals("cat@test.se", person.getEmail());
    }

    @Test
    void setEmailWithString(){

        // Scenario: A string value entry in the setEmail method that is a valid input.

        String actualValue = person.getEmail();

        String expectedValue = "lina@example.se";
        Assertions.assertEquals(expectedValue, actualValue, "Email should match");
    }

    @Test
    void setEmailWithNullThrowsException() {

        //Scenario: A value of NULL is entered to the setEmail method

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            person.setEmail(null);
        });

    }

    @Test
    void setEmailWithEmptyThrowsException() {

        //Scenario: A value of empty is entered to the setEmail method

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            person.setEmail(" ");
        });

    }

    @Test
    void getIDCountsAsExpectedTest (){

        //Scenario: The value of ID counts up when a new Person is created

        int expected = 1;

        int actualValue = person.getId();

        Assertions.assertEquals(expected, actualValue, "The maximum value should be 1");

        Person personTest01 = new Person("Alfred", "Katt", "alfred@example.se", new AppUser("Alfred", "321", AppRole.ROLE_APP_USER));

        int expected02 = 2;

        int actualValue02 = personTest01.getId();

        Assertions.assertEquals(expected02, actualValue02, "The maximum value should be 2");

    }

    @Test
    void getSummaryReturnsCorrectString() {

        //Scenario: Return the value of the summery created of the Person

        int id = person.getId();
        String expected = "Person Info- ID: " + id + ", Name: Lina Katt, Email : lina@example.se";

        String actual = person.toString();

        Assertions.assertEquals(expected, actual, "Summary should match expected format and content");
    }

    @Test
    void twoPersonsWithSameDataButDifferentIdsShouldNotBeEqual() {
        Person person1 = new Person("Lina", "Katt", "lina@example.se", new AppUser("Lina", "123", AppRole.ROLE_APP_USER));
        Person person2 = new Person("Lina", "Katt", "lina@example.se", new AppUser("Lina", "123", AppRole.ROLE_APP_USER));

        Assertions.assertNotEquals(person1, person2, "Persons with same data but different IDs should not be equal");
    }


}

