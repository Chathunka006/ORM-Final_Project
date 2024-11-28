package lk.ijse.Bo.Custom;

import lk.ijse.Bo.SuperBo;
import lk.ijse.Entity.Student_Course;

import java.io.IOException;
import java.util.List;

public interface StudentCourseBo extends SuperBo {
    public List<Student_Course> getStudentCourseList() throws IOException;

}
