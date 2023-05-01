package avlyakulov.timur.book.chapter_13.example.dao;

import avlyakulov.timur.book.chapter_13.example.conn.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDao<T extends Entity> {
    protected Connection connection;

    public abstract List<T> findAll();

    public abstract T findEntityById(long id);

    public abstract boolean delete(long id);

    public abstract boolean delete(T entity);

    public abstract boolean create(T entity);

    public abstract T update(T entity);

    public void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            // log
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
