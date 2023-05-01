package avlyakulov.timur.book.chapter_13.example.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BookDaoImpl implements BookDao {
    private final String SQL_SELECT_ALL_BOOKER = "SELECT name,book_name from persons.books";
    private final String SQL_SELECT_PERSONBOOK_BY_NAME =
            "SELECT name, book name from persons.books where name = ?";
    private final String SQL_SELECT_PERSONBOOK_BY_ID =
            "SELECT * from persons.books where id = ?";

    @Override
    public List<PersonBook> findPersonBookByName(String patterName) throws DaoException {
        List<PersonBook> persons = new ArrayList<>();
        try (Connection connection = GetConnectionToDB.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_PERSONBOOK_BY_NAME)) {
            preparedStatement.setString(1, patterName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PersonBook person = new PersonBook();
                person.setName(resultSet.getString("name"));
                person.setBookName(resultSet.getString("book_name"));
                persons.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return persons;
    }

    @Override
    public List<PersonBook> findAll() throws DaoException {
        List<PersonBook> persons = new ArrayList<>();
        try (Connection connection = GetConnectionToDB.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_BOOKER)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PersonBook person = new PersonBook();
                person.setName(resultSet.getString("name"));
                person.setBookName(resultSet.getString("book_name"));
                persons.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return persons;
    }

    @Override
    public PersonBook findEntityById(Integer id) throws DaoException {
        PersonBook personBook = new PersonBook();
        try (Connection connection = GetConnectionToDB.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_PERSONBOOK_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PersonBook person = new PersonBook();
                person.setName(resultSet.getString("name"));
                person.setBookName(resultSet.getString("book_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return personBook;
    }

    @Override
    public boolean delete(PersonBook personBook) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Integer id) throws DaoException {
        return false;
    }

    @Override
    public PersonBook update(PersonBook personBook) throws DaoException {
        return null;
    }
}