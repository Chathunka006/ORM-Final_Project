package lk.ijse.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "student_course")
public class Student_Course {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long student_course_id;
        private Date registration_date;

        @ManyToOne
        @JoinColumn(name = "student_id")
        private Student student;

        @ManyToOne
        @JoinColumn(name = "course_id")
        private Course course;

        @OneToMany(mappedBy = "student_course", cascade = CascadeType.ALL)
        private List<Payment> payments;
}
