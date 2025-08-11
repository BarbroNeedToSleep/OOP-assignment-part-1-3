package se.lexicon.model;

import se.lexicon.dao.idSequencer.PersonIdSequencer;

import java.util.HashSet;
import java.util.Set;

public class Person {

    private static final Set<String> emails = new HashSet<>();

    private final int id;
    private String firstName;
    private String lastName;
    private String email;
    private AppUser credentials;


    private Person(String firstName, String lastName, String email, AppUser credentials){
        this.id = PersonIdSequencer.getInstance().nextId();
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setCredentials(credentials);
    }

    public static Person createPerson(String firstName, String lastName, String email, AppUser credentials) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        String normalizedEmail = email.trim().toLowerCase();

        synchronized (emails) {
            if (emails.contains(normalizedEmail)) {
                throw new IllegalArgumentException("Email already exists: " + normalizedEmail);
            }
            emails.add(normalizedEmail);
        }

        return new Person(firstName, lastName, normalizedEmail, credentials);
    }

    //Setters

    public void setFirstName(String firstName){

        if (firstName == null|| firstName.trim().isEmpty()){
            throw new IllegalArgumentException("First name cannot be null or empty");
        }

        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        if (lastName == null || lastName.trim().isEmpty()){
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        this.lastName = lastName;
    }

   private void setEmail(String email){
        if (email == null || email.trim().isEmpty()){
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        this.email = email;
    }

 private void setCredentials(AppUser credentials) {

        if (credentials == null){
            throw new IllegalArgumentException("Credentials cannot be null");
        }
        this.credentials = credentials;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public AppUser getCredentials() {
        return credentials;
    }

    public static void removeEmail(String email) {
        synchronized (emails) {
            emails.remove(email.toLowerCase().trim());
        }
    }

    @Override
    public String toString(){
        StringBuilder summary = new StringBuilder();
        summary.append("Person Info- ID: ").append(id)
                .append(", Name: ").append(firstName)
                .append(" ").append(lastName)
                .append(", Email : ").append(email);
        return summary.toString();

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Person person = (Person) obj;


        return this.id == person.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);

    }


    // Testing Utilities

    public int getId (){
        return id;
    }

}
