package lk.ijse.Bo.Custom.Impl;

import lk.ijse.Bo.Custom.CourseBo;
import lk.ijse.Dao.Custom.CourseDao;
import lk.ijse.Dao.DaoFactory;
import lk.ijse.Dto.CourseDto;
import lk.ijse.Entity.Course;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CourseBoImpl implements CourseBo {
    CourseDao courseDao = (CourseDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.COURSE);

    @Override
    public boolean save(CourseDto courseDto) throws IOException {
        Course course = new Course(
                courseDto.getCourse_id(),
                courseDto.getCourse_name(),
                courseDto.getDuration(),
                courseDto.getCourse_fee(),
                null
        );
        return courseDao.save(course);
    }

    @Override
    public boolean update(CourseDto courseDto) throws IOException {
        Course course = new Course(
                courseDto.getCourse_id(),
                courseDto.getCourse_name(),
                courseDto.getDuration(),
                courseDto.getCourse_fee(),
                null
        );
        return courseDao.update(course);
    }

    @Override
    public boolean delete(String id) throws IOException {
        return courseDao.delete(id);
    }

    @Override
    public CourseDto getCourse(String id) throws IOException {
        Course byId = courseDao.findById(id);
        CourseDto courseDto = new CourseDto();
        courseDto.setCourse_id(byId.getCourse_id());
        courseDto.setCourse_name(byId.getCourse_name());
        courseDto.setDuration(byId.getDuration());
        courseDto.setCourse_fee(byId.getCourse_fee());
        return courseDto;
    }

    @Override
    public List<Course> getCourseList() throws IOException {
        List<Course> courseList = new ArrayList<>();
        List<Course> courses = courseDao.getAll();
        for (Course course : courses) {
            courseList.add(new Course(
                    course.getCourse_id(),
                    course.getCourse_name(),
                    course.getDuration(),
                    course.getCourse_fee(),
                    null
            ));
        }
        return courseList;
    }
}
