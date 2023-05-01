package avlyakulov.timur.book.chapter_13.example.dao;

import java.util.List;

public class BookerDao extends AbstractDao<PersonBook> {
    @Override
    public List<PersonBook> findAll() {
        return null;
    }

    @Override
    public PersonBook findEntityById(long id) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public boolean delete(PersonBook entity) {
        return false;
    }

    @Override
    public boolean create(PersonBook entity) {
        return false;
    }

    @Override
    public PersonBook update(PersonBook entity) {
        return null;
    }
}
