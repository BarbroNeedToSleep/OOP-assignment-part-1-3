package se.lexicon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PersonTest {

    @Test
    void setFirstNameWhitName(){


        //Test logic

        // Scenario: A string value entry.

            Person person = new Person("Lina", "Katt", "lina@example.se");

            //person.setFirstName("Anna"); // Update first name
            String actualValue = person.getFirstName();

            String expectedValue = "Lina";
            Assertions.assertEquals(expectedValue, actualValue, "First names should match");
        }

    }

