package se.lexicon.dao.impl;

import se.lexicon.dao.PersonDAO;
import se.lexicon.model.Person;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PersonDAOCollection implements PersonDAO {

    private List<Person> persons = new ArrayList<>();

    @Override
    public Person persist(Person person) {

        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null");
        }

        persons.add(person);
        System.out.println("Person saved: " + person);
        return person;

    }

    @Override
    public Person findById(Integer id) {

        if (id == null || id <= 0){
            throw new IllegalArgumentException("Id cannot be null or negative.");
        }
        for (Person person : persons) {
            if (person.getId() == id) {
                return person;
            }
        }
        System.out.println("No person found with that id.");

        return null;
    }

    @Override
    public Person findByEmail(String email) {

        if (email == null || email.trim().isEmpty()){
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }

        String trimEmail = email.trim();

        for (Person person : persons) {
            if (person.getEmail().equalsIgnoreCase(trimEmail)) {
                return person;

            }
        }
        System.out.println("No person with that email found.");
        return null;

    }

    @Override
    public List<Person> findAll() {
        return new ArrayList<>(persons);
    }

    @Override
    public void remove(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id cannot be null or negative.");
        }

        Iterator<Person> iterator = persons.iterator();
        while (iterator.hasNext()) {
            Person person = iterator.next();
            if (person.getId() == id) {
                iterator.remove();
                System.out.println("Person with ID " + id + " removed.");
                return;
            }
        }

        System.out.println("No person found with ID: " + id);
    }


}
