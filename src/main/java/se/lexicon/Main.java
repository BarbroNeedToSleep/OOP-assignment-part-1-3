package se.lexicon;


import se.lexicon.model.AppRole;
import se.lexicon.model.AppUser;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;
import se.lexicon.model.TodoItemTask;
import se.lexicon.personDAO.PersonDAO;
import se.lexicon.personDAO.PersonDAOCollection;
import se.lexicon.todoItemDAO.TodoItemDAOCollection;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        // ===== Create Users and Persons =====
        AppUser user1 = AppUser.getInstance("alice123", "password", AppRole.ROLE_APP_USER);
        AppUser user2 = AppUser.getInstance("bob456", "secret", AppRole.ROLE_APP_ADMIN);

        Person alice = Person.createPerson("Alice", "Smith", "alice@example.com", user1);
        Person bob = Person.createPerson("Bob", "Johnson", "bob@example.com", user2);

        // ===== Create DAO =====
        TodoItemDAOCollection dao = new TodoItemDAOCollection();

        // ===== Create TodoItems =====
        TodoItem task1 = new TodoItem("Buy groceries", "Milk, Bread, Eggs", LocalDate.now().plusDays(2), alice);
        TodoItem task2 = new TodoItem("Clean house", "Living room and kitchen", LocalDate.now().plusDays(1), alice);
        TodoItem task3 = new TodoItem("Prepare report", "Due next week", LocalDate.now().plusDays(7), bob);
        TodoItem task4 = new TodoItem("Call plumber", "Fix kitchen sink", LocalDate.now().plusDays(5), bob);

        // Mark one as done
        task2.setDone(true);

        // ===== Persist tasks =====
        dao.persist(task1);
        dao.persist(task2);
        dao.persist(task3);
        dao.persist(task4);

        // ===== Find by ID =====
        System.out.println("\nFind by ID 1:");
        System.out.println(dao.findById(task1.getId()));

        // ===== Find all =====
        System.out.println("\nAll tasks:");
        dao.findAll().forEach(System.out::println);

        // ===== Find all by done status =====
        System.out.println("\nTasks that are DONE:");
        dao.findAllByDoneStatus(true).forEach(System.out::println);

        System.out.println("\nTasks that are NOT done:");
        dao.findAllByDoneStatus(false).forEach(System.out::println);

        // ===== Find by title contains =====
        System.out.println("\nTasks containing 'clean':");
        dao.findByTitleContains("clean").forEach(System.out::println);

        // ===== Find by person ID =====
        System.out.println("\nTasks created by Alice:");
        dao.findByPersonId(alice.getId()).forEach(System.out::println);

        // ===== Deadline filters =====
        System.out.println("\nTasks with deadline before 5 days from now:");
        dao.findByDeadlineBefore(LocalDate.now().plusDays(5)).forEach(System.out::println);

        System.out.println("\nTasks with deadline after today:");
        dao.findByDeadlineAfter(LocalDate.now()).forEach(System.out::println);

        // ===== Remove a task =====
        System.out.println("\nRemoving task 3 (Prepare report)");
        dao.remove(task3.getId());

        System.out.println("\nAll tasks after removal:");
        dao.findAll().forEach(System.out::println);
    }

}