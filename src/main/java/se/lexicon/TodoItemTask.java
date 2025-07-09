package se.lexicon;




public class TodoItemTask {

    private  static int itemTaskID =0;

    private int ID;
    private boolean assigned;
    private TodoItem todoItem;
    private Person assignee;

public TodoItemTask(TodoItem todoItem, Person assignee){

    this.ID = getNextItemTaskID();
    this.todoItem = todoItem;
    setAssignee(assignee);


}


    //Getter

    public int getItemTaskID(){
        return ID;
    }

    private int getNextItemTaskID(){
        return ++itemTaskID;
    }

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

        if (assignee == null) {
            throw new IllegalArgumentException("Assignee cannot be null");
        }

        this.assignee = assignee;
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
        summary.append("Task ID: ").append(ID)
                .append(", Task: ").append(todoItem.toString())
                .append(", Is assigned to ").append(assignee.getFirstName());
        return summary.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        TodoItemTask other = (TodoItemTask) obj;

        if (ID != other.ID) return false;
        if (assigned != other.assigned) return false;
        return todoItem != null ? todoItem.equals(other.todoItem) : other.todoItem == null;
    }

    @Override
    public int hashCode() {
        int result = ID;
        result = 31 * result + (assigned ? 1 : 0);
        result = 31 * result + (todoItem != null ? todoItem.hashCode() : 0);
        // Exclude assignee
        return result;
    }

    // Testing Utilities


    public static void resetIdCounterForTesting() {
        itemTaskID  = 0;
    }


}
