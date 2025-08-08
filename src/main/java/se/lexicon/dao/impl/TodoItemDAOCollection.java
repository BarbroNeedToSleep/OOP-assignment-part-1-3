package se.lexicon.dao.impl;


import se.lexicon.dao.TodoItemDAO;
import se.lexicon.model.TodoItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TodoItemDAOCollection implements TodoItemDAO {

    private List<TodoItem> todoItems = new ArrayList<>();


    @Override
    public TodoItem persist(TodoItem todoItem) {

        if (todoItem == null) {
            throw new IllegalArgumentException("Todo Item cannot be null");
        }

        todoItems.add(todoItem);
        System.out.println("Todo Item saved: " + todoItem);
        return todoItem;
    }

    @Override
    public TodoItem findById(Integer id) {

        if (id == null || id <= 0){
            throw new IllegalArgumentException("Id cannot be null or negative.");
        }
        for (TodoItem todoItem: todoItems) {
            if (todoItem.getId() == id) {
                return todoItem;
            }
        }
        System.out.println("No todo Item found with that id.");

        return null;

    }

    @Override
    public List<TodoItem> findAll() {
        return new ArrayList<>(todoItems);
    }

    @Override
    public List<TodoItem> findAllByDoneStatus(boolean done) {

        return todoItems.stream()
                .filter(item -> item.isDone() == done)
                .collect(Collectors.toList());

    }

    @Override
    public List<TodoItem> findByTitleContains(String title) {
        return todoItems.stream()
                .filter(item -> item.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }


    @Override
    public List<TodoItem> findByPersonId(Integer id) {

        return todoItems.stream()
                .filter(item -> item.getCreator() != null &&
                        Objects.equals(item.getCreator().getId(), id))
                .collect(Collectors.toList());
    }



    @Override
    public List<TodoItem> findByDeadlineBefore(LocalDate date) {
        return todoItems.stream()
                .filter(item -> item.getDeadLine().isBefore(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoItem> findByDeadlineAfter(LocalDate date) {
        return todoItems.stream()
                .filter(item -> item.getDeadLine().isAfter(date))
                .collect(Collectors.toList());
    }

    @Override
    public void remove(Integer id) {

            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Id cannot be null or negative.");
            }

            Iterator<TodoItem> iterator = todoItems.iterator();
            while (iterator.hasNext()) {
                TodoItem todoItem = iterator.next();
                if (todoItem.getId() == id) {
                    iterator.remove();
                    System.out.println("Todo item with ID " + id + " is removed.");
                    return;
                }
            }

            System.out.println("No Todo item found with ID: " + id);

    }
}
