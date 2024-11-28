package lk.ijse.EntityTm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentTm {
    private String pay_id;
    private String status;
    private double upfront_amount;
    private double balance_amount;
    private String stu_id;
    private String cou_id;
    private Long student_course_id;
    private Button btnRemove;
}
