package lk.ijse.Bo.Custom.Impl;

import lk.ijse.Bo.Custom.StudentBo;
import lk.ijse.Dao.Custom.StudentDao;
import lk.ijse.Dao.DaoFactory;
import lk.ijse.Dto.StudentDto;
import lk.ijse.Entity.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentBoImpl implements StudentBo {

    StudentDao studentDao = (StudentDao) DaoFactory.getDaoFactory().getDaoType(DaoFactory.DaoType.STUDENT);
    @Override
    public boolean save(StudentDto studentDto) throws IOException {
        Student student = new Student(
                studentDto.getStu_id(),
                studentDto.getStu_name(),
                studentDto.getStu_address(),
                studentDto.getStu_phone(),
                studentDto.getDate(),
                1,
                null,
                studentDto.getUser()
        );
        return studentDao.save(student);
    }

    @Override
    public boolean update(StudentDto studentDto) throws IOException {
        Student student = new Student(
                studentDto.getStu_id(),
                studentDto.getStu_name(),
                studentDto.getStu_address(),
                studentDto.getStu_phone(),
                studentDto.getDate(),
                1,
                null,
                studentDto.getUser()
        );
        return studentDao.update(student);
    }

    @Override
    public boolean delete(String id) throws IOException {
        return studentDao.delete(id);
    }

    @Override
    public StudentDto getStudent(String id) {
        return null;
    }

    @Override
    public List<Student> getStudentList() throws IOException {
        List<Student> studentList = new ArrayList<>();
        List<Student> students = studentDao.getAll();
        for (Student student : students) {
            studentList.add(new Student(
                    student.getStu_id(),
                    student.getStu_name(),
                    student.getStu_address(),
                    student.getStu_phone(),
                    student.getDate(),
                    1,
                    null,
                    null)
            );
        }
        return studentList;
    }
}
