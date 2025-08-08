package se.lexicon.dao.impl;

import se.lexicon.dao.TodoItemTaskDAO;
import se.lexicon.model.TodoItemTask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class TodoItemTaskDAOCollection implements TodoItemTaskDAO {

    private List<TodoItemTask> todoItemTasks = new ArrayList<>();

    @Override
    public TodoItemTask persist(TodoItemTask todoItemTask) {

        if (todoItemTask == null) {
            throw new IllegalArgumentException("Todo item task cannot be null");
        }

        todoItemTasks.add(todoItemTask);
        System.out.println("Todo item Task saved: " + todoItemTask);
        return todoItemTask;

    }

    @Override
    public TodoItemTask findById(Integer id) {

        if (id == null || id <= 0){
            throw new IllegalArgumentException("Id cannot be null or negative.");
        }
        for (TodoItemTask todoItemTask: todoItemTasks) {
            if (todoItemTask.getId() == id) {
                return todoItemTask;
            }
        }
        System.out.println("No todo item task found with that id.");

        return null;

    }

    @Override
    public List<TodoItemTask> findAll() {
        return new ArrayList<>(todoItemTasks);
    }

    @Override
    public List<TodoItemTask> findByAssignedStatus(boolean status) {

        return todoItemTasks.stream()
                .filter(item -> item.isAssigned() == status)
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoItemTask> findByPersonId(Integer id) {


        if (id == null || id <= 0){
            throw new IllegalArgumentException("Id cannot be null or negative.");
        }

        List<TodoItemTask> foundTask = new ArrayList<>();

            for (TodoItemTask todoItemTask : todoItemTasks) {
            if (todoItemTask.getAssignee() != null && todoItemTask.getAssignee().getId() == id) {
                foundTask.add(todoItemTask);
            }
        }

        if (foundTask.isEmpty()) {
            System.out.println("No task with that person id was found.");
        }

        return foundTask;
    }

    @Override
    public void remove(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Id cannot be null or negative.");
        }

        Iterator<TodoItemTask> iterator = todoItemTasks.iterator();
        while (iterator.hasNext()) {
            TodoItemTask todoItemTask = iterator.next();
            if (todoItemTask.getId() == id) {
                iterator.remove();
                System.out.println("Todo item task with ID " + id + " is removed.");
                return;
            }
        }

        System.out.println("No Todo item task found with ID: " + id);

    }
}
