package avlyakulov.timur.book.chapter_13.example.dao;

import com.google.protobuf.ServiceException;

public class SomeService {
    public void doService() throws ServiceException {
        //1. init dao
        BookDao bookDao = new BookDaoImpl();
        //2. transaction intialization for Dao objects
        EntityTransaction transaction = new EntityTransaction();
    }
}