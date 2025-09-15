package se.lexicon.model;


public class Person {


    private int id;
    private String firstName;
    private String lastName;


    public Person(String firstName, String lastName){
        setFirstName(firstName);
        setLastName(lastName);
    }

    public Person(int id, String firstName, String lastName){
        this.id = id;
        setFirstName(firstName);
        setLastName(lastName);
    }

    //Setters

    public void setId(int id) {
        this.id = id;
    }

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

    //Getters

    public int getId (){
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}
