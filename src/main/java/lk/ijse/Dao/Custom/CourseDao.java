package lk.ijse.Dao.Custom;

import lk.ijse.Dao.CrudDao;
import lk.ijse.Entity.Course;

import java.io.IOException;
import java.util.List;

public interface CourseDao extends CrudDao<Course> {
    String getCurrentId() throws IOException;

    List<String> getCourseId();

    List<String> getCourseIds();

    Course getCourseById(String courseId);
    int getProgramCount();
}
