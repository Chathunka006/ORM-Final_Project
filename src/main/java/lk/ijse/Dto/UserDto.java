package lk.ijse.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private String user_id;
    private String username;
    private String password;
    private String user_email;
    private String user_phone;
    private String user_role;
}
