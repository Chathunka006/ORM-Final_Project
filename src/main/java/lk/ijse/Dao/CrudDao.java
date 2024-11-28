package lk.ijse.Dao;

import java.io.IOException;
import java.util.List;

public interface CrudDao<T> extends SuperDao {
    public boolean save(T object) throws IOException;
    public boolean update(T object) throws IOException;
    public boolean delete(String id) throws IOException;
    public T findById(String id) throws IOException;
    public List<T> getAll() throws IOException;
}
