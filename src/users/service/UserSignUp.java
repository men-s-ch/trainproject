package users.service;

import users.bean.UsersDTO;
import users.dao.UsersDAO;

import java.util.Scanner;

public class UserSignUp implements Train{
    @Override
    public void execute() {
        Scanner scan = new Scanner(System.in);
        UsersDAO usersDAO = UsersDAO.getInstance();
        System.out.println();
        System.out.print("이름을 입력하세요 : ");
        String userName = scan.next();
        String phone = null;
        while (true) {
            System.out.print("핸드폰번호를 입력하세요 : ");
            phone = scan.next();
            phone = UsersDTO.formatPhoneNumber(phone);

            boolean exist = usersDAO.existPhone(phone);
            if (exist) {
                System.out.println();
                System.out.println("이미 가입된 번호입니다.");
                System.out.println();
                System.out.print("아이디,비밀번호를 찾으시겠습니까? Y/N :");
                String select = scan.next();
                System.out.println();
                if (select.equalsIgnoreCase("Y")) {
                    usersDAO.findInfo(userName,phone);
                    return;
                }else if(select.equalsIgnoreCase("N")){
                    return;
                }else {
                    System.out.println("잘못 입력 하셨습니다. 초기화면으로 돌아갑니다.");
                    return;
                }
            } else break;

        }
        String user_Id = null;
        while (true) {
            System.out.print("아이디를 입력하세요 : ");
            user_Id = scan.next();
            boolean exist = usersDAO.existUser_Id(user_Id);
            if (exist) {
                System.out.println("사용중인 아이디 입니다.");
            } else {
                System.out.println("사용 가능한 아이디입니다.");
                break;
            }
        }

        System.out.print("비밀번호를 입력하세요 : ");
        String password = scan.next();
        System.out.println();

        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setUserName(userName);
        usersDTO.setPhone(phone);
        usersDTO.setUser_Id(user_Id);
        usersDTO.setPassword(password);



        int su = usersDAO.userSignUp(usersDTO);

        System.out.println(userName+"님의 회원가입이 완료되었습니다.");
    }
}
