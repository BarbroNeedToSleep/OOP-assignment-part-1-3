package se.lexicon.dao.impl;

import se.lexicon.dao.PeopleDAO;
import se.lexicon.db.MySQLDatabaseConnection;
import se.lexicon.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PeopleDAOImpl implements PeopleDAO {

    @Override
    public Person create(Person person) {
        String sql = "INSERT INTO person (firstName, lastName ) VALUES (?, ?)";

        try (Connection connection = MySQLDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());

            int rows = preparedStatement.executeUpdate();

            if (rows == 0) {
                System.err.println("❌ Insert failed: no rows affected.");
                return null;
            }

            try (ResultSet key = preparedStatement.getGeneratedKeys()) {
                if (key.next()) {
                    person.setId(key.getInt(1));
                }
            }

        }catch (SQLException e) {
            System.err.println("❌ SQL error in save: " + e.getMessage());
            System.err.println("SQL state: " + e.getSQLState());
            System.err.println("Error code: " + e.getErrorCode());
            return null;
        }

        return person;
    }

    @Override
    public Collection<Person> findAll() {

        List<Person> personList = new ArrayList<>();
        String sql = "SELECT id, firstName, lastName FROM person";

        try (Connection connection = MySQLDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Person person= new Person (
                            resultSet.getInt("id"),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName")

                    );

                    personList.add(person);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ SQL error in findAll: " + e.getMessage());
            System.err.println("SQL state: " + e.getSQLState());
            System.err.println("Error code: " + e.getErrorCode());
        }


        return personList;

    }

    @Override
    public Person findById(int id) {

        String sql = "SELECT id, firstname, lastName FROM person WHERE id = ?";

        try (Connection connection = MySQLDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Person person = new Person(
                            resultSet.getInt("id"),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName")
                    );
                    return person;
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ SQL error in findById(" + id + "): " + e.getMessage());
            System.err.println("SQL state: " + e.getSQLState());
            System.err.println("Error code: " + e.getErrorCode());
        }

        return null;
    }



    @Override
    public Collection<Person> findByName(String name) {

        List<Person> personList02 = new ArrayList<>();

        String searchName = "%" + name.trim().toLowerCase() + "%";

        String sql = "SELECT id, firstName, lastName FROM person WHERE LOWER(firstName) LIKE ? OR LOWER(lastName) LIKE ?";

        try (Connection connection = MySQLDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {


            preparedStatement.setString(1, searchName);
            preparedStatement.setString(2, searchName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Person person = new Person(
                            resultSet.getInt("id"),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName")
                    );

                    personList02.add(person);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ SQL error in findByName(" + name + "): " + e.getMessage());
            System.err.println("SQL state: " + e.getSQLState());
            System.err.println("Error code: " + e.getErrorCode());
        }


        return personList02;

    }

    @Override
    public Person update(Person person) {

        String sql = "UPDATE person SET firstName = ?, lastName = ? WHERE id = ?";

        try (Connection connection = MySQLDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setInt(3, person.getId());

            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Person with ID " + person.getId() + " updated successfully.");
            } else {
                System.out.println("❌ No person found with ID " + person.getId());
                return null;
            }

        }catch (SQLException e) {
            System.err.println("❌ SQL error in update: " + e.getMessage());
            System.err.println("SQL state: " + e.getSQLState());
            System.err.println("Error code: " + e.getErrorCode());
        }

        return person;
    }

    @Override
    public boolean deleteById(int id) {

        String sql = "DELETE FROM person WHERE id = ?";

        try (Connection connection = MySQLDatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Person with ID " + id + " deleted successfully.");

                return true;
            } else {
                System.out.println("❌ No person found with ID " + id);

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
