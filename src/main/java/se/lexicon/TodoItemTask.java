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

    public int getID(){
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
        this.assignee = assignee;
        setAssigned();
    }

     public void setTodoItem(TodoItem todoItem) {
            this.todoItem = todoItem;
     }

    public String getSummary(){
        StringBuilder summery = new StringBuilder();
        summery.append("Task ID: ").append(ID)
                .append(", Task: ").append(todoItem.getSummary())
                .append(", Is assigned to ").append(assignee.getSummary());
        return summery.toString();
    }


}
