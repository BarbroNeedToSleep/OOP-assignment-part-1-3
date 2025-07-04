package se.lexicon;


import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {


    Person lina = new Person("Lina", "Katt", "lina@exempel.se");
    Person alfred = new Person("Alfred", "Katt", "Alfred@exempel.se");

    System.out.println(lina.getSummary());
    System.out.println(alfred.getSummary()+"\n");

    TodoItem tire = new TodoItem("Change tire", "Change both tires on the front", LocalDate.parse("2025-07-08"), lina);
    TodoItem windshield = new TodoItem("Replace windshield", "Replace windshield after damage, Covered by insurance", LocalDate.parse("2025-07-15"), alfred);

    System.out.println(tire.getSummary());
    System.out.println(windshield.getSummary()+"\n");

    System.out.println(tire.getOverdueInfo());
    System.out.println(windshield.getOverdueInfo()+"\n");

    }

}