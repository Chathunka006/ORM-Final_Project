package lk.ijse.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "course")
public class Course {
    @Id
    private String course_id;
    private String course_name;
    private String duration;
    private double course_fee;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Student_Course> studentCourses;

}
