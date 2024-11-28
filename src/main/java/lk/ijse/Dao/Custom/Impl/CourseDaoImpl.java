package lk.ijse.Dao.Custom.Impl;

import lk.ijse.Config.FactoryConfiguration;
import lk.ijse.Dao.Custom.CourseDao;
import lk.ijse.Entity.Course;
import lk.ijse.Entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements CourseDao {
    @Override
    public boolean save(Course object) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(object);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Course object) throws IOException {
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
        NativeQuery<User> nativeQuery = session.createNativeQuery("delete from course where course_id = :id");
        nativeQuery.setParameter("id", id);
        nativeQuery.executeUpdate();
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Course findById(String id) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        Course course = null;

        try {
            transaction = session.beginTransaction();

            NativeQuery<Course> nativeQuery = session.createNativeQuery("SELECT * FROM course WHERE course_id = :id", Course.class);
            nativeQuery.setParameter("id", id);

            course = nativeQuery.uniqueResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return course;
    }

    @Override
    public List<Course> getAll() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery query = session.createNativeQuery("SELECT * FROM course");
        query.addEntity(Course.class);
        List<Course> resultList = query.getResultList();
        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public String getCurrentId() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String nextId = "";
        Object course = session.createQuery("SELECT C.course_id  FROM Course C ORDER BY C.course_id DESC LIMIT 1").uniqueResult();
        if (course != null) {
            String courseId = course.toString();
            String prefix = "C";
            String[] split = courseId.split(prefix);
            int idNum = Integer.parseInt(split[1]);
            nextId = prefix + String.format("%03d", ++idNum);

        } else {
            return "C001";
        }

        transaction.commit();
        session.close();
        return nextId;
    }

    @Override
    public List<String> getCourseId() {
        Session session = null;
        Transaction transaction = null;
        List<String> courseIds = new ArrayList<>();

        try {
            session = FactoryConfiguration.getInstance().getSession();
            transaction = session.beginTransaction();

            courseIds = session.createQuery("SELECT c.course_id FROM Course c", String.class).list();

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
        return courseIds;
    }

    @Override
    public List<String> getCourseIds() {
        Session session = null;
        Transaction transaction = null;
        List<String> courseIds = new ArrayList<>();

        try {
            session = FactoryConfiguration.getInstance().getSession();
            transaction = session.beginTransaction();

            courseIds = session.createQuery("SELECT c.course_id FROM Course c", String.class).list();

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
        return courseIds;
    }

    @Override
    public Course getCourseById(String courseId) {
        Session session = null;
        Transaction transaction = null;
        Course course = null;

        try {
            session = FactoryConfiguration.getInstance().getSession();
            transaction = session.beginTransaction();

            NativeQuery<Course> query = session.createNativeQuery("SELECT * FROM course WHERE course_id = :id", Course.class);
            query.setParameter("id", courseId);

            course = query.uniqueResult(); // Execute query and set the result to customer

            transaction.commit(); // Commit the transaction if successful
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback transaction if an error occurs
            }
            e.printStackTrace(); // Log the exception for debugging
        } finally {
            if (session != null) {
                session.close(); // Ensure session is closed
            }
        }

        return course;
    }

    @Override
    public int getProgramCount() {
        int courseCount = 0;
        Session session = null;

        try {
            // Get the session from the factory
            session = FactoryConfiguration.getInstance().getSession();
            session.beginTransaction();

            // HQL query to count the number of courses
            String hql = "SELECT COUNT(c) FROM Course c";
            Query<Long> query = session.createQuery(hql, Long.class);

            // Get the result and cast to int
            Long countResult = query.uniqueResult();
            if (countResult != null) {
                courseCount = countResult.intValue();
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

        return courseCount;
    }
}
