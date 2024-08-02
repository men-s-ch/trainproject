package users.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static final String formatPhoneNumber(String phone) {
        if (phone == null || phone.isEmpty()) {
            System.out.println("잘못된 형식의 번호입니다. 다시 입력해주세요.");
            return null;
        }

        if (phone.length() == 11 && phone.matches("\\d{11}")) {
            return phone.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return userName;
    }
}
