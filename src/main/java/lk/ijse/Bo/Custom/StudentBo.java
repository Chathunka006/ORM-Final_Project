package lk.ijse.Bo.Custom;

import lk.ijse.Bo.SuperBo;
import lk.ijse.Dto.StudentDto;
import lk.ijse.Entity.Student;

import java.io.IOException;
import java.util.List;

public interface StudentBo extends SuperBo {
    public boolean save(StudentDto studentDto) throws IOException;
    public boolean update(StudentDto studentDto) throws IOException;
    public boolean delete(String id) throws IOException;
    public StudentDto getStudent(String id);
    public List<Student> getStudentList() throws IOException;
}
