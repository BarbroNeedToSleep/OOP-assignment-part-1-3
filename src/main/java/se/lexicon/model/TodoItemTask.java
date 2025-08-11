package se.lexicon.model;


import se.lexicon.dao.idSequencer.ToDoItemTaskIdSequencer;

public class TodoItemTask {


    private int id;
    private boolean assigned;
    private TodoItem todoItem;
    private Person assignee;

public TodoItemTask(TodoItem todoItem, Person assignee){

    this.id = ToDoItemTaskIdSequencer.getInstance().nextId();
    setTodoItem(todoItem);
    this.assignee = assignee; // null allowed
    setAssigned();


}


    //Getter

    public boolean isAssigned(){
        return assigned;
    }

    public TodoItem getTodoItem() {
        return todoItem;
    }

    public Person getAssignee() {
        return assignee;
    }


    //Setters


    private void setAssigned() {

    this.assigned = this.assignee != null;

    }

    public void setAssignee(Person assignee) {
        this.assignee = assignee; // null allowed so it can be sett later
        setAssigned();
    }


     public void setTodoItem(TodoItem todoItem) {

         if (todoItem == null) {
             throw new IllegalArgumentException("TodoItem cannot be null");
         }

    this.todoItem = todoItem;
     }

     @Override
    public String toString(){
        StringBuilder summary = new StringBuilder();
        summary.append("Task ID: ").append(id)
                .append(", Task: ").append(todoItem.toString())
                .append(", Is assigned to ").append(assignee != null ? assignee.getFirstName() : "No one");

         return summary.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        TodoItemTask other = (TodoItemTask) obj;

        if (id != other.id) return false;
        if (assigned != other.assigned) return false;
        return todoItem != null ? todoItem.equals(other.todoItem) : other.todoItem == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (assigned ? 1 : 0);
        result = 31 * result + (todoItem != null ? todoItem.hashCode() : 0);

        return result;
    }

    // Testing Utilities


    public int getId (){
        return id;
    }


}
