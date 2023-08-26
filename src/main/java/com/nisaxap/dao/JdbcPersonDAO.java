package com.nisaxap.dao;

import com.nisaxap.models.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPersonDAO {
    //can use this class with jdbc
    private final JdbcTemplate jdbcTemplate;

    public JdbcPersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        // get      /persons          read
        List<Person> people = new ArrayList<>();
        Statement statement = null;
        String SQL = "";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            statement = connection.createStatement();
            SQL = "SELECT * FROM Person";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                person.setAge(resultSet.getInt("age"));

                people.add(person);
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return people;
    }

    public Person show(int id) {
        // get      /persons/:id      read by id
        Person person = null;
        String SQL = "";
        PreparedStatement preparedStatement = null;
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            SQL = "SELECT * FROM Person WHERE id=?";
            preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            person = new Person();

            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setEmail(resultSet.getString("email"));
            person.setAge(resultSet.getInt("age"));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        return person;
    }
 
    public void save(Person person) {
        // post     /persons          great
        PreparedStatement preparedStatement = null;
        String SQL = "";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            SQL = "INSERT INTO Person VALUES(1, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void update(int id, Person updatedPerson) {
        // patch    /persons/:id      update by id
        PreparedStatement preparedStatement = null;
        String SQL = "";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            SQL = "UPDATE Person SET name=?, age=?, email=? WHERE id=?";
            preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setString(1, updatedPerson.getName());
            preparedStatement.setInt(2, updatedPerson.getAge());
            preparedStatement.setString(3, updatedPerson.getEmail());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void delete(int id) {
        // delete   /persons/:id      delete by id
        PreparedStatement preparedStatement = null;
        String SQL = "";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            SQL = "DELETE FROM Person WHERE id=?";
            preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
