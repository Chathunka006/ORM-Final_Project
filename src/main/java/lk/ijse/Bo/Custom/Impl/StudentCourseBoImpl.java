package lk.ijse.Bo.Custom.Impl;

import lk.ijse.Bo.Custom.StudentCourseBo;
import lk.ijse.Dao.Custom.StudentCourseDao;
import lk.ijse.Dao.DaoFactory;
import lk.ijse.Entity.Student_Course;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentCourseBoImpl implements StudentCourseBo {
    StudentCourseDao studentCourseDao = (StudentCourseDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.STUDENT_COURSE);
    @Override
    public List<Student_Course> getStudentCourseList() throws IOException {
        List<Student_Course> studentCourseList = new ArrayList<>();
        List<Student_Course> studentCourses = studentCourseDao.getAll();
        for (Student_Course studentCourse : studentCourses) {
            studentCourseList.add(new Student_Course(
                    studentCourse.getStudent_course_id(),
                    studentCourse.getRegistration_date(),
                    studentCourse.getStudent(),
                    studentCourse.getCourse(),
                    new ArrayList<>()
            ));
        }
        return studentCourseList;
    }
}
