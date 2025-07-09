package se.lexicon;


import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {


    Person lina = new Person("Lina", "Katt", "lina@exempel.se", new AppUser("Lina", "123", AppRole.ROLE_APP_USER));
    Person alfred = new Person("Alfred", "Katt", "Alfred@exempel.se", new AppUser("Akfred", "1abc", AppRole.ROLE_APP_USER));
    Person jon = new Person("Jon", "Jonsson", "jon@exemle.se", new AppUser("Jon", "13399", AppRole.ROLE_APP_USER));

    System.out.println(lina);
    System.out.println(jon);
    System.out.println(alfred +"\n");

    Person p1 = new Person("Lina", "Katt", "lina@exempel.se", new AppUser("l", "2", AppRole.ROLE_APP_USER));
    Person p2 = new Person("Lina", "Katt", "lina@exempel.se", new AppUser("l", "3", AppRole.ROLE_APP_USER));

    System.out.println(p1.equals(p2));

    TodoItem tire = new TodoItem("Change tire", "Change both tires on the front", LocalDate.parse("2025-11-11"), lina);
    TodoItem windshield = new TodoItem("Replace windshield", "Replace windshield after damage, Covered by insurance", LocalDate.parse("2025-11-11"), alfred);

    System.out.println(tire);
    System.out.println(windshield+"\n");

    System.out.println(tire.getOverdueInfo());
    System.out.println(windshield.getOverdueInfo()+"\n");

    Person creator = new Person("Lina", "Katt", "lina@exempel.se", new AppUser("Lina", "123", AppRole.ROLE_APP_USER));

    TodoItem item1 = new TodoItem("Clean", "Clean the garage", LocalDate.now().plusDays(2), creator);
    TodoItem item2 = new TodoItem("Clean", "Clean the garage", LocalDate.now().plusDays(2), creator);

    System.out.println("item1.equals(item2): " + item1.equals(item2));  // should be true
        System.out.println("item1.hashCode(): " + item1.hashCode());
        System.out.println("item2.hashCode(): " + item2.hashCode()+"\n");

    TodoItemTask fixTire = new TodoItemTask(tire, jon);
    TodoItemTask fixWindshield = new TodoItemTask(windshield, jon);

    System.out.println(fixTire);
    System.out.println(fixWindshield +"\n");

    AppUser barbro = new AppUser("NtS","321", AppRole.ROLE_APP_USER);
    System.out.println(barbro.toString());

    AppUser user1 = new AppUser("Alice", "pass123", AppRole.ROLE_APP_USER);
    AppUser user2 = new AppUser("Alice", "diffPass", AppRole.ROLE_APP_USER);
    AppUser user3 = new AppUser("Bob", "bobPass", AppRole.ROLE_APP_ADMIN);


    System.out.println("user1: " + user1);
    System.out.println("user3: " + user3);


    System.out.println("user1 equals user2? " + user1.equals(user2)); // true (same name & role)
        System.out.println("user1 equals user3? " + user1.equals(user3)); // false

    }

}