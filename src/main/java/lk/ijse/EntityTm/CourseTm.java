package lk.ijse.EntityTm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseTm {
    private String course_id;
    private String course_name;
    private String duration;
    private double course_fee;
}
