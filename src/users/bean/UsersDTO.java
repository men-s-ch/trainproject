package users.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    private String user_Id;
    private String userName;
    private String password;
    private String phone;
    private int  is_admin;


    @Override
    public String toString() {
        return userName;
    }
}
