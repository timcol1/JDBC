package avlyakulov.timur.book.chapter_13.example.dao;

import avlyakulov.timur.book.chapter_13.example.conn.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface BaseDao<K, T extends Entity> {
    List<T> findAll() throws DaoException;

    T findEntityById(K id) throws DaoException;

    boolean delete(T t) throws DaoException;

    boolean delete(K id) throws DaoException;

    T update(T t) throws DaoException;

    default void close(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null)
                preparedStatement.close();
        } catch (SQLException e) {
            //log
        }
    }

    default void close(Connection connection) {
        try {
            if (connection != null)
                connection.close(); // or connection return code to the pool
        } catch (SQLException e) {
            //log
        }
    }
}