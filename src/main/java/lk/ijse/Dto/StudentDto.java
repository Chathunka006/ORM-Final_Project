package lk.ijse.Dto;

import lk.ijse.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentDto {
    private String stu_id;
    private String stu_name;
    private String stu_address;
    private String stu_phone;
    private Date date;
    private User user;
}
