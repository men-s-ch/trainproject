package users.service;

import users.bean.UsersDTO;
import users.dao.UsersDAO;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserUpdate implements Train {
    @Override
    public void execute() {
        System.out.println();
        Scanner scan = new Scanner(System.in);

        System.out.print("아이디 검색 : ");
        String user_Id = scan.next();

        UsersDAO usersDAO = UsersDAO.getInstance();
        UsersDTO usersDTO = usersDAO.getUsers(user_Id);

        if (usersDTO == null) {
            System.out.println("검색한 아이디가 없습니다.");
            return;
        }
        System.out.println(usersDTO);

        System.out.println();
        System.out.print("새로운 비밀번호를 입력하세요 : ");
        String password = scan.next();

        System.out.print("새로운 핸드폰 번호를 입력하세요 : ");
       String phone = scan.next();
        String formattedPhone = UsersDTO.formatPhoneNumber(phone);
        if (formattedPhone == null) {
            System.out.println("잘못된 형식의 번호입니다. 다시 입력해주세요.");
        }

        Map<String,String> map = new HashMap<>();
        map.put("password",password);
        map.put("phone",formattedPhone);
        map.put("user_id",user_Id);

        int su = usersDAO.userUpdate(map);

        System.out.println(su + "개의 회원정보를 수정하였습니다.");
    }
}
