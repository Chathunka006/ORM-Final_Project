package lk.ijse.Bo.Custom;

import lk.ijse.Bo.SuperBo;
import lk.ijse.Dto.CourseDto;
import lk.ijse.Entity.Course;

import java.io.IOException;
import java.util.List;

public interface CourseBo extends SuperBo {
    public boolean save(CourseDto courseDto) throws IOException;
    public boolean update(CourseDto courseDto) throws IOException;
    public boolean delete(String id) throws IOException;
    public CourseDto getCourse(String id) throws IOException;
    public List<Course> getCourseList() throws IOException;
}
