package se.lexicon.personDAO;

import se.lexicon.model.Person;

import java.util.List;

public interface PersonDAO {

    Person persist(Person person);

    Person findById (Integer id);

    Person findByEmail (String email);

    List<Person> findAll();

    void remove(Integer id);

}
