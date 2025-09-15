package se.lexicon;


import se.lexicon.dao.PeopleDAO;
import se.lexicon.dao.TodoItemsDAO;
import se.lexicon.dao.impl.PeopleDAOImpl;
import se.lexicon.dao.impl.TodoItemsDAOImpl;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.time.LocalDate;
import java.util.Collection;

public class Main {

    public static void main(String[] args) {
//
//        PeopleDAO peopleDAO = new PeopleDAOImpl();
//
//        // --- Create People ---
//        Person ada = peopleDAO.create(new Person("Ada", "Lovelace"));
//        Person alan = peopleDAO.create(new Person("Alan", "Turing"));
//
//        System.out.println("Created People:");
//        System.out.println(ada);
//        System.out.println(alan);
//
//        // --- List all People ---
//        System.out.println("\nAll People:");
//        Collection<Person> allPeople = peopleDAO.findAll();
//        allPeople.forEach(System.out::println);
//
//        // --- Find Person by ID ---
//        System.out.println("\nFind by ID " + ada.getId() + ":");
//        Person findAda = peopleDAO.findById(ada.getId());
//        System.out.println(findAda);
//
//        // --- Find by name ---
//        System.out.println("\nFind by name 'Ada':");
//        Collection<Person> peopleNamedAda = peopleDAO.findByName("Ada");
//        peopleNamedAda.forEach(System.out::println);
//
//        // --- Update Person ---
//        ada.setLastName("Byron");
//        peopleDAO.update(ada);
//        System.out.println("\nAfter update:");
//        peopleDAO.findAll().forEach(System.out::println);
//
//        // --- Delete Person ---
//        peopleDAO.deleteById(alan.getId());
//        System.out.println("\nAfter delete:");
//        peopleDAO.findAll().forEach(System.out::println);

        PeopleDAOImpl peopleDAO = new PeopleDAOImpl();
        TodoItemsDAOImpl todoDAO = new TodoItemsDAOImpl();

        // --- Create People ---
        Person ada = peopleDAO.create(new Person("Ada", "Lovelace"));
        Person alan = peopleDAO.create(new Person("Alan", "Turing"));

        System.out.println("Created people:");
        Collection<Person> allPeople = peopleDAO.findAll();
        allPeople.forEach(System.out::println);

        // --- Create TodoItems ---
        TodoItem todo1 = todoDAO.create(new TodoItem("Finish Homework", "Math exercises", LocalDate.now().plusDays(1), ada.getId()));
        TodoItem todo2 = todoDAO.create(new TodoItem("Read Book", "Read Turing biography", LocalDate.now().plusDays(3), alan.getId()));

        System.out.println("\nCreated TodoItems:");
        todoDAO.findAll().forEach(System.out::println);

        // --- Update TodoItem ---
        todo1.setDone(true);
        todoDAO.update(todo1);

        System.out.println("\nTodoItems after update:");
        todoDAO.findAll().forEach(System.out::println);

        // --- Find by assignee ---
        System.out.println("\nTodoItems assigned to Ada:");
        todoDAO.findByAssignee(ada).forEach(System.out::println);

        // --- Delete a TodoItem ---
        todoDAO.deleteById(todo2.getId());
        System.out.println("\nTodoItems after deleting todo2:");
        todoDAO.findAll().forEach(System.out::println);

    }
}
