package se.lexicon;

public class Person {

    private static int personIdCounter = 0;

    private int ID;
    private String firstName;
    private String lastName;
    private String email;

    public Person(String firstName, String lastName, String email){
        this.ID = getNextId();
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }

    //Setters

    public void setFirstName(String firstName){

        if (firstName == null|| firstName.trim().isEmpty()){
            throw new IllegalArgumentException("First name cannot be null null or empty");
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


    //Getters

    public int getNextId() {
        return++personIdCounter;
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

    public String getSummary(){
        StringBuilder summery = new StringBuilder();
        summery.append("Person Info- ID: ").append(ID)
                .append(", Name: ").append(firstName)
                .append(" ").append(lastName)
                .append(", Email : ").append(email);
        return summery.toString();
    }
}
