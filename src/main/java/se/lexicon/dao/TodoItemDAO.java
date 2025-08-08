package se.lexicon.dao;

import se.lexicon.model.TodoItem;

import java.time.LocalDate;
import java.util.List;

public interface TodoItemDAO {

    TodoItem persist (TodoItem todoItem);

    TodoItem findById(Integer id);

    List<TodoItem> findAll();

    List<TodoItem> findAllByDoneStatus(boolean done);

    List<TodoItem> findByTitleContains(String title);

    List<TodoItem> findByPersonId(Integer id);

    List<TodoItem> findByDeadlineBefore(LocalDate date);

    List<TodoItem> findByDeadlineAfter(LocalDate date);

    void remove (Integer id);



}
