package se.lexicon;


import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {


//    Person lina = new Person("Lina", "Katt", "lina@exempel.se");
//    Person alfred = new Person("Alfred", "Katt", "Alfred@exempel.se");
//    Person jon = new Person("Jon", "Jonsson", "jon@exemle.se");
//
//    System.out.println(lina.getSummary());
//    System.out.println(jon.getSummary());
//    System.out.println(alfred.getSummary()+"\n");
//
//    TodoItem tire = new TodoItem("Change tire", "Change both tires on the front", LocalDate.parse("2025-07-08"), lina);
//    TodoItem windshield = new TodoItem("Replace windshield", "Replace windshield after damage, Covered by insurance", LocalDate.parse("2025-07-15"), alfred);
//
//    System.out.println(tire.getSummary());
//    System.out.println(windshield.getSummary()+"\n");
//
//    System.out.println(tire.getOverdueInfo());
//    System.out.println(windshield.getOverdueInfo()+"\n");
//
//    TodoItemTask fixTire = new TodoItemTask(tire, jon);
//    TodoItemTask fixWindshield = new TodoItemTask(windshield, jon);
//
//    System.out.println(fixTire.getSummary());
//    System.out.println(fixWindshield.getSummary()+"\n");

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