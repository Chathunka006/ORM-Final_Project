package lk.ijse.Dao.Custom;

import lk.ijse.Dao.CrudDao;
import lk.ijse.Entity.User;

import java.io.IOException;
import java.util.List;

public interface UserDao extends CrudDao<User> {
    String getCurrentId() throws IOException;

    List<String> getUserId();

    User getUserById(String value);
    boolean checkCredential(String username, String password);

    boolean updateUser(User user);

    String getUserRole(String username);
    int getUserCount();
}
