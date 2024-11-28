package lk.ijse.Dao.Custom.Impl;

import lk.ijse.Config.FactoryConfiguration;
import lk.ijse.Dao.Custom.UserDao;
import lk.ijse.Entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean save(User object) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(object);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(User object) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(object);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<User> nativeQuery = session.createNativeQuery("delete from user where user_id = :id");
        nativeQuery.setParameter("id", id);
        nativeQuery.executeUpdate();
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public User findById(String id) {
        return null;
    }

    @Override
    public List<User> getAll() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery query = session.createNativeQuery("SELECT * FROM user");
        query.addEntity(User.class);
        List<User> resultList = query.getResultList();
        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public String getCurrentId() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String nextId = "";
        Object user = session.createQuery("SELECT U.user_id  FROM User U ORDER BY U.user_id DESC LIMIT 1").uniqueResult();
        if (user != null) {
            String userId = user.toString();
            String prefix = "U";
            String[] split = userId.split(prefix);
            int idNum = Integer.parseInt(split[1]);
            nextId = prefix + String.format("%03d", ++idNum);

        } else {
            return "U001";
        }

        transaction.commit();
        session.close();
        return nextId;
    }

    @Override
    public List<String> getUserId() {
        Session session = null;
        Transaction transaction = null;
        List<String> userIds = new ArrayList<>();

        try {
            session = FactoryConfiguration.getInstance().getSession();
            transaction = session.beginTransaction();

            userIds = session.createQuery("SELECT u.user_id FROM User u", String.class).list();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return userIds;
    }

    @Override
    public User getUserById(String value) {
        Session session = null;
        Transaction transaction = null;
        User user = null;

        try {
            session = FactoryConfiguration.getInstance().getSession();
            transaction = session.beginTransaction();

            NativeQuery<User> query = session.createNativeQuery("SELECT * FROM user U WHERE U.user_id = :id", User.class);
            query.setParameter("id",value);

            user = query.uniqueResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return user;
    }

    @Override
    public boolean checkCredential(String username, String pw) {
        Session session = null;
        try {
            session = FactoryConfiguration.getInstance().getSession();
            session.beginTransaction();

            String hql = "SELECT u.password FROM User u WHERE u.username = :username";
            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("username", username);

            List<String> resultList = query.getResultList();

            if (resultList.size() == 1) {
                String dbPw = resultList.get(0);
                return BCrypt.checkpw(pw, dbPw);
            }
            return false; // Username not found or password mismatch

        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean updateUser(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            // Get the session and start a transaction
            session = FactoryConfiguration.getInstance().getSession();
            transaction = session.beginTransaction();

            // Update the user entity in the database
            session.update(user);

            // Commit the transaction
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback if an error occurs
            }
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close(); // Close the session
            }
        }
    }

    @Override
    public String getUserRole(String username) {
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            String hql = "SELECT u.user_role FROM User u WHERE u.username = :username";
            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("username", username);

            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Handle or log the exception appropriately
        }
    }

    @Override
    public int getUserCount() {
        int userCount = 0;
        Session session = null;

        try {
            // Get the session from the factory
            session = FactoryConfiguration.getInstance().getSession();
            session.beginTransaction();

            // HQL query to count the number of courses
            String hql = "SELECT COUNT(u) FROM User u";
            Query<Long> query = session.createQuery(hql, Long.class);

            // Get the result and cast to int
            Long countResult = query.uniqueResult();
            if (countResult != null) {
                userCount = countResult.intValue();
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace(); // For debugging
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return userCount;

    }
}
