package avlyakulov.timur.book.chapter_13.example.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class EntityTransaction {
    private Connection connection;

    public void initTransaction(AbstractDao dao, AbstractDao... daos) {
        if (connection == null) {
            //connectino = // code -> create connection or take connectin from pool
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        dao.setConnection(connection);
        for (AbstractDao daoElement : daos)
            daoElement.setConnection(connection);
    }

    public void endTransaction() {
        //connection check code of null
        try {
            //connection check for commit
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        connection = null;
    }
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
