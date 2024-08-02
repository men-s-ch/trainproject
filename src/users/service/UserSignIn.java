package users.service;

import users.bean.UsersDTO;
import users.dao.UsersDAO;

import java.util.Scanner;

public class UserSignIn implements Train {
    @Override
    public void execute() {
        LoginService loginService = new LoginService();
        System.out.println();
        Scanner scan = new Scanner(System.in);
        System.out.print("아이디를 입력하세요 : ");
        String user_Id = scan.next();
        System.out.print("비밀번호를 입력하세요 : ");
        String password = scan.next();
        System.out.println();
        UsersDAO usersDAO = UsersDAO.getInstance();
        UsersDTO user = usersDAO.signIn(user_Id, password);

        if (user != null) {
            if (user.getIs_admin() == 1) {
                System.out.println("관리자 권한으로 로그인하셨습니다.");
                loginService.adminlogin();
            } else {
                System.out.println(user.getUserName() + "님이 로그인하셨습니다.");
                loginService.userlogin();
            }
        } else {
            System.out.println("아이디또는 비밀번호가 일치하지 않습니다.");
            System.out.print("아이디,비밀번호를 찾으시겠습니까? Y/N :");
            String select = scan.next();
            System.out.println();

                if (select.equalsIgnoreCase("Y")) {
                    while (true) {
                        System.out.print("이름을 입력하세요 :");
                        String userName = scan.next();
                        System.out.print("핸드폰번호를 입력하세요 :");
                        String phone = scan.next();
                        String formattedPhone = UsersDTO.formatPhoneNumber(phone);
                        if (formattedPhone == null) {
                            System.out.println("잘못된 형식의 번호입니다. 다시 입력해주세요");
                            continue;
                        }else {
                            usersDAO.findInfo(userName,formattedPhone);
                            return;
                        }
                    }
                }else if(select.equalsIgnoreCase("N")){
                    return;
                }else {
                    System.out.println("잘못 입력 하셨습니다. 초기화면으로 돌아갑니다.");
                    return;
                }
        }
    }


}
