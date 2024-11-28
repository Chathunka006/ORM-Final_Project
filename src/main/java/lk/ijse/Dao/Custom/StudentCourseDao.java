package lk.ijse.Dao.Custom;

import lk.ijse.Dao.CrudDao;
import lk.ijse.Entity.Student_Course;

public interface StudentCourseDao extends CrudDao<Student_Course> {
    Student_Course getStudentCourseById(Long value);
}
