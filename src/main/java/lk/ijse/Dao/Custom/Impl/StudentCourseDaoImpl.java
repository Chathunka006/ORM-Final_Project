package lk.ijse.Dao.Custom.Impl;

import lk.ijse.Config.FactoryConfiguration;
import lk.ijse.Dao.Custom.StudentCourseDao;
import lk.ijse.Entity.Student_Course;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.io.IOException;
import java.util.List;

public class StudentCourseDaoImpl implements StudentCourseDao {
    @Override
    public boolean save(Student_Course object) throws IOException {
        return false;
    }

    @Override
    public boolean update(Student_Course object) throws IOException {
        return false;
    }

    @Override
    public boolean delete(String id) throws IOException {
        return false;
    }

    @Override
    public Student_Course findById(String id) throws IOException {
        return null;
    }

    @Override
    public List<Student_Course> getAll() throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        NativeQuery query = session.createNativeQuery("SELECT * FROM student_course");
        query.addEntity(Student_Course.class);
        List<Student_Course> resultList = query.getResultList();
        transaction.commit();
        session.close();
        return resultList;
    }

    @Override
    public Student_Course getStudentCourseById(Long value) {
        Session session = null;
        Transaction transaction = null;
        Student_Course student_course = null;

        try {
            session = FactoryConfiguration.getInstance().getSession();
            transaction = session.beginTransaction();

            NativeQuery<Student_Course> query = session.createNativeQuery
                    ("SELECT * FROM student_course WHERE student_course_id = :id", Student_Course.class);
            query.setParameter("id", value);

            student_course = query.uniqueResult(); // Execute query and set the result to customer

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

        return student_course;
    }
}
