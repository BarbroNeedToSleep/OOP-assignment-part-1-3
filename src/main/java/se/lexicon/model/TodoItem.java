package se.lexicon.model;

import se.lexicon.dao.PeopleDAO;

import java.time.LocalDate;

public class TodoItem {


    private int id;
    private String title;
    private String taskDescription;
    private LocalDate deadLine;
    private boolean done = false;
    private int creator;

    public TodoItem(int id, String title, String taskDescription, LocalDate deadLine, boolean done, int creator){

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

    public int getId (){
        return id;
    }

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

    public void setId(int id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", deadLine=" + deadLine +
                ", done=" + done +
                ", creator=" + creator +
                '}';
    }


}
