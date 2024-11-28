package lk.ijse.Dto;

import lk.ijse.Entity.Student_Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentDto {
    private String pay_id;
    private String pay_date;
    private double pay_amount;
    private String status;
    private double balance_amount;
    private Student_Course student_course;
}
