package se.lexicon.dao;

import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.util.Collection;

public interface TodoItemsDAO {

    TodoItem create(TodoItem todoItem);

    Collection<TodoItem> findAll();

    TodoItem findById(int id);

    Collection<TodoItem> findByDoneStatus(boolean done);

    Collection<TodoItem> findByAssignee(int id);

    Collection<TodoItem> findByAssignee(Person person);

    Collection<TodoItem> findByUnassignedTodoItems();

    TodoItem update(TodoItem todoItem);

    boolean deleteById(int id);

}
