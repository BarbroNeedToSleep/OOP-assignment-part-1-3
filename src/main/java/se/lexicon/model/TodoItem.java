package se.lexicon.model;

import java.time.LocalDate;

public class TodoItem {


    private int id;
    private String title;
    private String taskDescription;
    private LocalDate deadLine;
    private boolean done = false;
    private int creator;

    public TodoItem(int id, String title, String taskDescription, LocalDate deadLine, int creator){

        this.id = id;
        setTitle(title);
        setTaskDescription(taskDescription);
        setDeadLine(deadLine);
        this.creator = creator;

    }

    public TodoItem(String title, String taskDescription, LocalDate deadLine, int creator){

        setTitle(title);
        setTaskDescription(taskDescription);
        setDeadLine(deadLine);
        this.creator = creator;

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

    public int getCreator() {
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

    public void setCreator(int creator) {
        if (creator < 0) {  // negative IDs are invalid
            throw new IllegalArgumentException("Creator cannot be negative");
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


    //TODO fix code after findById is done
//    @Override
//    public String toString() {
//        // Suppose you have a personDAO available
//        Person person = "classdoaIMplhere)".findById(creator).orElse(null);
//        String customerName = (person != null) ? person.getFirstName() : "N/A";
//
//        StringBuilder info = new StringBuilder();
//        info.append("Booking information - Id number: ").append(id)
//                .append(", Job: ").append(title)
//                .append(", Job description: ").append(taskDescription)
//                .append(", Due date: ").append(deadLine)
//                .append(", Done: ").append(done ? "Yes" : "No")
//                .append(", Customer: ").append(customerName);
//
//        return info.toString();
//    }

}
