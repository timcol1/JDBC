package avlyakulov.timur.book.chapter_13.example.dao;

import java.util.List;

public interface BookDao extends BaseDao<Integer, PersonBook> {
    List<PersonBook> findPersonBookByName(String patterName) throws DaoException;
}
