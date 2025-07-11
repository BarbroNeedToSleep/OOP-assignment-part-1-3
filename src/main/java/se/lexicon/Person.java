package se.lexicon;

public class Person {

    private static int personIdCounter = 0;

    private int ID;
    private String firstName;
    private String lastName;
    private String email;
    private AppUser credentials;

    public Person(String firstName, String lastName, String email, AppUser credentials){
        this.ID = getNextId();
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setCredentials(credentials);
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

    public void setEmail(String email){
        if (email == null || email.trim().isEmpty()){
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        this.email = email;
    }

    public void setCredentials(AppUser credentials) {

        if (credentials == null){
            throw new IllegalArgumentException("Credentials cannot be null");
        }
        this.credentials = credentials;
    }

    // Getters

    public int getNextId() {
        return ++personIdCounter;
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

    @Override
    public String toString(){
        StringBuilder summary = new StringBuilder();
        summary.append("Person Info- ID: ").append(ID)
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

        return firstName.equals(person.firstName) && lastName.equals(person.lastName) && email.equals(person.email);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }


    // Testing Utilities

    public int getID (){
        return ID;
    }

    public static void resetIdCounterForTesting() {
        personIdCounter = 0;
    }
}
