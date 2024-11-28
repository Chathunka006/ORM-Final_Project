package lk.ijse.Dao.Custom.Impl;

import lk.ijse.Config.FactoryConfiguration;
import lk.ijse.Dao.Custom.StudentDao;
import lk.ijse.Entity.Student;
import lk.ijse.Entity.Student_Course;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    @Override
    public boolean save(Student object) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(object);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Student student) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;
        boolean isUpdated = false;

        try {
            transaction = session.beginTransaction();
            session.update(student);  // Use update() for updating existing records
            transaction.commit();
            isUpdated = true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Rollback transaction on error
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return isUpdated;
    }

    @Override
    public boolean delete(String id) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery<Student> nativeQuery = session.createNativeQuery
                ("update student set status = 0 where stu_id = :id");
        nativeQuery.setParameter("id", id);
        nativeQuery.executeUpdate();
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Student findById(String id) {
        return null;
    }

    @Override
    public List<Student> getAll() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery query = session.createNativeQuery("SELECT * FROM student where status = 1");
        query.addEntity(Student.class);
        List<Student> resultList = query.getResultList();
        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public String getCurrentId() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String nextId = "";
        Object student = session.createQuery("SELECT s.stu_id FROM Student s ORDER BY s.stu_id DESC LIMIT 1").uniqueResult();
        if (student != null) {
            String courseId = student.toString();
            String prefix = "S";
            String[] split = courseId.split(prefix);
            int idNum = Integer.parseInt(split[1]);
            nextId = prefix + String.format("%03d", ++idNum);

        } else {
            return "S001";
        }

        transaction.commit();
        session.close();
        return nextId;
    }

    @Override
    public Student getStudentById(String text) {
        Session session = null;
        Transaction transaction = null;
        Student student = null;

        try {
            session = FactoryConfiguration.getInstance().getSession();
            transaction = session.beginTransaction();

            NativeQuery<Student> query = session.createNativeQuery("SELECT * FROM student WHERE stu_id = :id", Student.class);
            query.setParameter("id", text);

            student = query.uniqueResult();

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

        return student;
    }

    @Override
    public void saveStudentCourseDetails(Student_Course studentCourse) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(studentCourse);
        transaction.commit();
        session.close();
    }

    @Override
    public boolean isStudentRegisteredForCourse(String stuId, String courseId) throws IOException {
        boolean isRegistered = false;
        Session session = FactoryConfiguration.getInstance().getSession();

        try {
            // Create the query to check if the student-course combination exists
            String hql = "SELECT 1 FROM Student_Course sc WHERE sc.student.stu_id = :stuId AND sc.course.course_id = :courseId";
            Query query = session.createQuery(hql);
            query.setParameter("stuId", stuId);
            query.setParameter("courseId", courseId);

            // Check if the query returns any results
            isRegistered = query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return isRegistered;
    }

    @Override
    public int getStudentCount() {
        int studentCount = 0;
        Session session = null;

        try {
            // Get the session from the factory
            session = FactoryConfiguration.getInstance().getSession();
            session.beginTransaction();

            // HQL query to count the number of courses
            String hql = "SELECT COUNT(s) FROM Student s where status = 1";
            Query<Long> query = session.createQuery(hql, Long.class);

            // Get the result and cast to int
            Long countResult = query.uniqueResult();
            if (countResult != null) {
                studentCount = countResult.intValue();
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

        return studentCount;

    }
}
