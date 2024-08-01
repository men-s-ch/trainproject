package bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersDTO {
    private String user_id;
    private String userName;
    private String password;
    private String phone;
    private int  is_admin;
}
