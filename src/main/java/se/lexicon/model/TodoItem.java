package se.lexicon.model;

import se.lexicon.idSequencer.ToDoItemIdSequencer;

import java.time.LocalDate;

public class TodoItem {


    private int id;
    private String title;
    private String taskDescription;
    private LocalDate deadLine;
    private boolean done = false;
    private Person creator;

    public TodoItem(String title, String taskDescription, LocalDate deadLine, Person creator){

        this.id = ToDoItemIdSequencer.getInstance().nextId();
        setTitle(title);
        setTaskDescription(taskDescription);
        setDeadLine(deadLine);
        setCreator(creator);


    }

    // Getters


    public String getTitle(){
        return title;
    }

    public String getTaskDescription(){
        return taskDescription;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public boolean isDone() {
        return done;
    }

    public Person getCreator() {
        return creator;
    }

    //Setters


    public void setTitle(String title) {

        if (title == null || title.trim().isEmpty()){
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = title;
    }

    public void setTaskDescription(String taskDescription) {

        if (taskDescription == null || taskDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }

        this.taskDescription = taskDescription;
    }

    public void setDeadLine(LocalDate deadLine) {

        if (deadLine == null){
            throw new IllegalArgumentException("Date cannot be left empty");
        }

        if (deadLine.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("The task is overdue");
        }

        this.deadLine = deadLine;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setCreator(Person creator) {
        if (creator == null ){
            throw new IllegalArgumentException("Creator cannot be null");
        }

        this.creator = creator;
    }

    public boolean isOverdue() {
        return deadLine.isBefore(LocalDate.now());
    }

    public String getOverdueInfo(){

        StringBuilder info = new StringBuilder();
        info.append("Job: ").append(title)
                .append(", Id number: ").append(id)
                .append(" Overdue: ").append(done ? "Yes" : "No");

        return info.toString();
    }

    @Override
    public String toString(){

        StringBuilder info = new StringBuilder();
        info.append("Booking information -  Id number: ").append(id)
                .append(", Job : ").append(title)
                .append(", Job description: ").append(taskDescription)
                .append(", Due date: ").append(deadLine)
                .append(", Done: ").append(done ? "Yes" : "No")
                .append(", Customer ").append(creator.getFirstName());


        return info.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        TodoItem todoItem = (TodoItem) obj;

        return id == todoItem.id;
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
