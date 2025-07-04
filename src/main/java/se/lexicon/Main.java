package se.lexicon;


public class Main {

    public static void main(String[] args) {


    Person lina = new Person("Lina", "Katt", "lina@exempel.se");
    Person alfred = new Person("Alfred", "Katt", "Alfred@exempel.se");

    System.out.println(lina.getSummary());
    System.out.println(alfred.getSummary());

    }
}