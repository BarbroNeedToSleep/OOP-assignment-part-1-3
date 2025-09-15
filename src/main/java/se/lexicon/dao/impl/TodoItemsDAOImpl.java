package se.lexicon.dao.impl;

import se.lexicon.dao.TodoItemsDAO;
import se.lexicon.db.MySQLDatabaseConnection;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TodoItemsDAOImpl implements TodoItemsDAO {


    @Override
    public TodoItem create(TodoItem todoItem) {

        String sql = "INSERT INTO todo_item (title, description, deadline, done, assignee_id ) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = MySQLDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setString(1, todoItem.getTitle());
            preparedStatement.setString(2, todoItem.getTaskDescription());
            preparedStatement.setDate(3, java.sql.Date.valueOf(todoItem.getDeadLine()));
            preparedStatement.setBoolean(4, todoItem.isDone());
            preparedStatement.setInt(5, todoItem.getCreator());

            int rows = preparedStatement.executeUpdate();

            if (rows == 0) {
                System.err.println("❌ Insert failed: no rows affected.");
                return null;
            }

            try (ResultSet key = preparedStatement.getGeneratedKeys()) {
                if (key.next()) {
                    todoItem.setId(key.getInt(1));
                }
            }

        }catch (SQLException e) {
            System.err.println("❌ SQL error in save: " + e.getMessage());
            System.err.println("SQL state: " + e.getSQLState());
            System.err.println("Error code: " + e.getErrorCode());
            return null;
        }

        return todoItem;

    }

    @Override
    public Collection<TodoItem> findAll() {

        List<TodoItem> todoItemsList = new ArrayList<>();
        String sql = "SELECT todo_id, title, description, deadline, done, assignee_id FROM todo_item";

        try (Connection connection = MySQLDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {

                    TodoItem todoItem = new TodoItem (
                            resultSet.getInt("todo_id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getDate("deadline").toLocalDate(),
                            resultSet.getBoolean("done"),
                            resultSet.getInt("assignee_id")
                    );


                    todoItemsList.add(todoItem);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ SQL error in findAll: " + e.getMessage());
            System.err.println("SQL state: " + e.getSQLState());
            System.err.println("Error code: " + e.getErrorCode());
        }


        return todoItemsList;
    }

    @Override
    public TodoItem findById(int id) {

        String sql = "SELECT todo_id, title, description, deadline, done, assignee_id FROM todo_item WHERE todo_id = ?";

        try (Connection connection = MySQLDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    TodoItem todoItem = new TodoItem (
                            resultSet.getInt("todo_id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getDate("deadline").toLocalDate(),
                            resultSet.getBoolean("done"),
                            resultSet.getInt("assignee_id")
                    );
                    return todoItem;
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ SQL error in findById(" + id + "): " + e.getMessage());
            System.err.println("SQL state: " + e.getSQLState());
            System.err.println("Error code: " + e.getErrorCode());
        }

        System.out.println("❌ No TodoItem found with ID " + id);
        return null;
    }


    @Override
    public Collection<TodoItem> findByDoneStatus(boolean done) {
        List<TodoItem> todoItemsList = new ArrayList<>();
        String sql = "SELECT todo_id, title, description, deadline, done, assignee_id FROM todo_item WHERE done = ?";

        try (Connection connection = MySQLDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setBoolean(1, done);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    TodoItem todoItem = new TodoItem(
                            resultSet.getInt("todo_id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getDate("deadline").toLocalDate(),
                            resultSet.getBoolean("done"),
                            resultSet.getInt("assignee_id")
                    );

                    todoItemsList.add(todoItem);
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ SQL error in findByDoneStatus(" + done + "): " + e.getMessage());
            System.err.println("SQL state: " + e.getSQLState());
            System.err.println("Error code: " + e.getErrorCode());
        }

        return todoItemsList;
    }

    @Override
    public Collection<TodoItem> findByAssignee(int id) {
        List<TodoItem> todoItemsList = new ArrayList<>();
        String sql = "SELECT todo_id, title, description, deadline, done, assignee_id FROM todo_item WHERE assignee_id = ?";

        try (Connection connection = MySQLDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    TodoItem todoItem = new TodoItem(
                            resultSet.getInt("todo_id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getDate("deadline").toLocalDate(),
                            resultSet.getBoolean("done"),
                            resultSet.getInt("assignee_id")
                    );

                    todoItemsList.add(todoItem);
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ SQL error in findByAssignee(" + id + "): " + e.getMessage());
            System.err.println("SQL state: " + e.getSQLState());
            System.err.println("Error code: " + e.getErrorCode());
        }

        return todoItemsList;
    }


    @Override
    public Collection<TodoItem> findByAssignee(Person person) {
        if (person == null) {
            return List.of();
        }
        return findByAssignee(person.getId());
    }


    @Override
    public Collection<TodoItem> findByUnassignedTodoItems() {
        List<TodoItem> todoItemsList = new ArrayList<>();
        String sql = "SELECT todo_id, title, description, deadline, done, assignee_id FROM todo_item WHERE assignee_id IS NULL";

        try (Connection connection = MySQLDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    TodoItem todoItem = new TodoItem(
                            resultSet.getInt("todo_id"),
                            resultSet.getString("title"),
                            resultSet.getString("description"),
                            resultSet.getDate("deadline").toLocalDate(),
                            resultSet.getBoolean("done"),
                            0
                    );

                    todoItemsList.add(todoItem);
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ SQL error in findByUnassignedTodoItems: " + e.getMessage());
            System.err.println("SQL state: " + e.getSQLState());
            System.err.println("Error code: " + e.getErrorCode());
        }

        return todoItemsList;
    }


    @Override
    public TodoItem update(TodoItem todoItem) {

        String sql = "UPDATE todo_item SET title = ?, description = ?, deadline = ?, done = ?, assignee_id = ? WHERE todo_id = ?";

        try (Connection connection = MySQLDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, todoItem.getTitle());
            preparedStatement.setString(2, todoItem.getTaskDescription());
            preparedStatement.setDate(3, java.sql.Date.valueOf(todoItem.getDeadLine()));
            preparedStatement.setBoolean(4, todoItem.isDone());
            preparedStatement.setInt(5, todoItem.getCreator());
            preparedStatement.setInt(6, todoItem.getId());

            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ TodoItem with ID " + todoItem.getId() + " updated successfully.");
                return todoItem;
            } else {
                System.out.println("❌ No TodoItem found with ID " + todoItem.getId());
                return null;
            }

        } catch (SQLException e) {
            System.err.println("❌ SQL error in update: " + e.getMessage());
            System.err.println("SQL state: " + e.getSQLState());
            System.err.println("Error code: " + e.getErrorCode());
            return null;
        }
    }


    @Override
    public boolean deleteById(int id) {

        String sql = "DELETE FROM todo_item WHERE todo_id = ?";

        try (Connection connection = MySQLDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ TodoItem with ID " + id + " deleted successfully.");
                return true;
            } else {
                System.out.println("❌ No TodoItem found with ID " + id);
                return false;
            }

        } catch (SQLException e) {
            System.err.println("❌ SQL error in deleteById: " + e.getMessage());
            System.err.println("SQL state: " + e.getSQLState());
            System.err.println("Error code: " + e.getErrorCode());
            return false;
        }

    }

}
